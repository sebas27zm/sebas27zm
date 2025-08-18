package services;

import models.Song;
import models.Playlist;
import models.EndUser;
import utils.PlaybackQueue;
import interfaces.Playable;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * Service for handling audio playback functionality
 * Implements actual audio playback using Java Sound API
 */
public class AudioPlayerService implements Playable {
    private static AudioPlayerService instance;
    
    // Audio playback components
    private Clip currentClip;
    private AudioInputStream audioInputStream;
    private String currentAudioFile;
    private boolean isPlaying;
    private boolean isPaused;
    private long pausePosition;
    
    // Playback management
    private Song currentSong;
    private Playlist currentPlaylist;
    private PlaybackQueue playbackQueue;
    private boolean isPreviewMode; // For 30-second previews
    private long previewStartTime;
    private static final long PREVIEW_DURATION_MS = 30000; // 30 seconds
    
    // Volume control
    private float volume = 0.5f; // 0.0 to 1.0
    private boolean isMuted = false;
    private float volumeBeforeMute;
    
    // Playback listeners
    private List<PlaybackListener> listeners;
    
    private AudioPlayerService() {
        this.playbackQueue = new PlaybackQueue();
        this.listeners = new ArrayList<>();
        this.isPlaying = false;
        this.isPaused = false;
        this.isPreviewMode = false;
    }
    
    public static AudioPlayerService getInstance() {
        if (instance == null) {
            instance = new AudioPlayerService();
        }
        return instance;
    }
    
    /**
     * Play a single song
     */
    public boolean playSong(Song song) {
        if (song == null) {
            return false;
        }
        
        // Check if user can play this song
        AuthenticationService authService = AuthenticationService.getInstance();
        if (!authService.isLoggedIn()) {
            return false;
        }
        
        // Administrators can play any song
        if (authService.isCurrentUserAdmin()) {
            return loadAndPlaySong(song, false);
        }
        
        // End users can only play songs they own
        EndUser currentUser = authService.getCurrentEndUser();
        if (currentUser == null) {
            return false;
        }
        
        if (!currentUser.ownsSong(song)) {
            return false;
        }
        
        return loadAndPlaySong(song, false);
    }
    
    /**
     * Play a 30-second preview of a song
     */
    public boolean playPreview(Song song) {
        if (song == null) {
            return false;
        }
        
        // Anyone can play previews
        return loadAndPlaySong(song, true);
    }
    
    /**
     * Play a playlist
     */
    public boolean playPlaylist(Playlist playlist) {
        if (playlist == null || playlist.isEmpty()) {
            return false;
        }
        
        // Check if user can play this playlist
        AuthenticationService authService = AuthenticationService.getInstance();
        if (!authService.isLoggedIn()) {
            return false;
        }
        
        // Administrators can play any playlist
        if (authService.isCurrentUserAdmin()) {
            return loadPlaylist(playlist);
        }
        
        // End users can only play their own playlists
        EndUser currentUser = authService.getCurrentEndUser();
        if (currentUser == null) {
            return false;
        }
        
        if (!playlist.getOwner().equals(currentUser)) {
            return false;
        }
        
        return loadPlaylist(playlist);
    }
    
    /**
     * Load and play a song
     */
    private boolean loadAndPlaySong(Song song, boolean preview) {
        try {
            // Stop current playback
            stop();
            
            // Load the audio file
            String audioFilePath = song.getAudioFilePath();
            File audioFile = new File(audioFilePath);
            
            // For demonstration purposes, we'll simulate audio loading
            // In a real implementation, you would load actual audio files
            if (!audioFile.exists()) {
                // Create a mock audio experience for demonstration
                simulateAudioPlayback(song, preview);
                return true;
            }
            
            // Load actual audio file
            audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            currentClip = AudioSystem.getClip();
            currentClip.open(audioInputStream);
            
            // Set up playback
            this.currentSong = song;
            this.currentPlaylist = null;
            this.isPreviewMode = preview;
            this.currentAudioFile = audioFilePath;
            
            // Set volume
            setClipVolume(volume);
            
            // Add line listener for playback events
            currentClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    handlePlaybackStopped();
                }
            });
            
            // Start playback
            currentClip.start();
            this.isPlaying = true;
            this.isPaused = false;
            
            if (preview) {
                this.previewStartTime = System.currentTimeMillis();
                // Start a timer to stop preview after 30 seconds
                startPreviewTimer();
            }
            
            notifyPlaybackStarted(song);
            return true;
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading audio file: " + e.getMessage());
            // Fallback to simulation
            simulateAudioPlayback(song, preview);
            return true;
        }
    }
    
    /**
     * Simulate audio playback for demonstration when actual audio files are not available
     */
    private void simulateAudioPlayback(Song song, boolean preview) {
        this.currentSong = song;
        this.currentPlaylist = null;
        this.isPreviewMode = preview;
        this.isPlaying = true;
        this.isPaused = false;
        this.currentClip = null; // Indicate simulation mode
        
        System.out.println("🎵 Now playing" + (preview ? " (preview)" : "") + ": " + 
                          song.getTitle() + " by " + song.getArtist());
        
        notifyPlaybackStarted(song);
        
        if (preview) {
            // Simulate 30-second preview
            new Thread(() -> {
                try {
                    Thread.sleep(PREVIEW_DURATION_MS);
                    if (isPreviewMode && currentSong == song) {
                        stop();
                        System.out.println("⏹️ Preview ended for: " + song.getTitle());
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
    
    /**
     * Load a playlist for playback
     */
    private boolean loadPlaylist(Playlist playlist) {
        playbackQueue.clear();
        playbackQueue.addPlaylist(playlist);
        this.currentPlaylist = playlist;
        
        // Start playing first song
        Song firstSong = playbackQueue.next();
        if (firstSong != null) {
            return loadAndPlaySong(firstSong, false);
        }
        
        return false;
    }
    
    /**
     * Start preview timer
     */
    private void startPreviewTimer() {
        new Thread(() -> {
            try {
                Thread.sleep(PREVIEW_DURATION_MS);
                if (isPreviewMode && isPlaying) {
                    stop();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    
    @Override
    public boolean play() {
        if (currentClip != null && !isPlaying) {
            if (isPaused) {
                // Resume from pause position
                currentClip.setFramePosition((int) pausePosition);
            }
            currentClip.start();
            isPlaying = true;
            isPaused = false;
            notifyPlaybackResumed();
            return true;
        } else if (currentSong != null && currentClip == null) {
            // Simulation mode - resume
            isPlaying = true;
            isPaused = false;
            System.out.println("▶️ Resumed: " + currentSong.getTitle());
            notifyPlaybackResumed();
            return true;
        }
        return false;
    }
    
    @Override
    public boolean pause() {
        if (currentClip != null && isPlaying) {
            pausePosition = currentClip.getFramePosition();
            currentClip.stop();
            isPlaying = false;
            isPaused = true;
            notifyPlaybackPaused();
            return true;
        } else if (currentSong != null && isPlaying) {
            // Simulation mode - pause
            isPlaying = false;
            isPaused = true;
            System.out.println("⏸️ Paused: " + currentSong.getTitle());
            notifyPlaybackPaused();
            return true;
        }
        return false;
    }
    
    @Override
    public boolean stop() {
        if (currentClip != null) {
            currentClip.stop();
            currentClip.close();
            currentClip = null;
        }
        
        if (audioInputStream != null) {
            try {
                audioInputStream.close();
            } catch (IOException e) {
                System.err.println("Error closing audio stream: " + e.getMessage());
            }
            audioInputStream = null;
        }
        
        if (currentSong != null) {
            System.out.println("⏹️ Stopped: " + currentSong.getTitle());
        }
        
        isPlaying = false;
        isPaused = false;
        pausePosition = 0;
        isPreviewMode = false;
        currentAudioFile = null;
        
        notifyPlaybackStopped();
        return true;
    }
    
    /**
     * Play next song in queue
     */
    public boolean playNext() {
        if (playbackQueue.hasNext()) {
            Song nextSong = playbackQueue.next();
            return loadAndPlaySong(nextSong, false);
        }
        return false;
    }
    
    /**
     * Play previous song
     */
    public boolean playPrevious() {
        if (playbackQueue.hasPrevious()) {
            Song previousSong = playbackQueue.previous();
            return loadAndPlaySong(previousSong, false);
        }
        return false;
    }
    
    /**
     * Add song to playback queue
     */
    public boolean addToQueue(Song song) {
        return playbackQueue.addSong(song);
    }
    
    /**
     * Add playlist to queue
     */
    public boolean addPlaylistToQueue(Playlist playlist) {
        return playbackQueue.addPlaylist(playlist);
    }
    
    // Volume control methods
    public void setVolume(float volume) {
        this.volume = Math.max(0.0f, Math.min(1.0f, volume));
        if (currentClip != null) {
            setClipVolume(this.volume);
        }
    }
    
    public float getVolume() {
        return volume;
    }
    
    public void mute() {
        if (!isMuted) {
            volumeBeforeMute = volume;
            setVolume(0.0f);
            isMuted = true;
        }
    }
    
    public void unmute() {
        if (isMuted) {
            setVolume(volumeBeforeMute);
            isMuted = false;
        }
    }
    
    public boolean isMuted() {
        return isMuted;
    }
    
    private void setClipVolume(float volume) {
        if (currentClip != null) {
            try {
                FloatControl gainControl = (FloatControl) currentClip.getControl(FloatControl.Type.MASTER_GAIN);
                float range = gainControl.getMaximum() - gainControl.getMinimum();
                float gain = (range * volume) + gainControl.getMinimum();
                gainControl.setValue(gain);
            } catch (Exception e) {
                System.err.println("Could not set volume: " + e.getMessage());
            }
        }
    }
    
    @Override
    public int getDurationSeconds() {
        if (currentClip != null) {
            return (int) (currentClip.getMicrosecondLength() / 1_000_000);
        }
        // Return estimated duration for simulation
        return 210; // 3.5 minutes average
    }
    
    @Override
    public int getCurrentPositionSeconds() {
        if (currentClip != null) {
            return (int) (currentClip.getMicrosecondPosition() / 1_000_000);
        }
        return 0;
    }
    
    @Override
    public boolean seekTo(int positionSeconds) {
        if (currentClip != null && positionSeconds >= 0) {
            long microseconds = positionSeconds * 1_000_000L;
            if (microseconds <= currentClip.getMicrosecondLength()) {
                currentClip.setMicrosecondPosition(microseconds);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isPlaying() {
        return isPlaying;
    }
    
    @Override
    public boolean isPaused() {
        return isPaused;
    }
    
    // Getters
    public Song getCurrentSong() { return currentSong; }
    public Playlist getCurrentPlaylist() { return currentPlaylist; }
    public PlaybackQueue getPlaybackQueue() { return playbackQueue; }
    public boolean isPreviewMode() { return isPreviewMode; }
    
    // Event handling
    private void handlePlaybackStopped() {
        if (!isPreviewMode && playbackQueue.hasNext()) {
            // Auto-play next song
            playNext();
        } else {
            // Playback finished
            isPlaying = false;
            isPaused = false;
            notifyPlaybackFinished();
        }
    }
    
    // Listener management
    public void addPlaybackListener(PlaybackListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }
    
    public void removePlaybackListener(PlaybackListener listener) {
        listeners.remove(listener);
    }
    
    private void notifyPlaybackStarted(Song song) {
        for (PlaybackListener listener : listeners) {
            listener.onPlaybackStarted(song);
        }
    }
    
    private void notifyPlaybackPaused() {
        for (PlaybackListener listener : listeners) {
            listener.onPlaybackPaused(currentSong);
        }
    }
    
    private void notifyPlaybackResumed() {
        for (PlaybackListener listener : listeners) {
            listener.onPlaybackResumed(currentSong);
        }
    }
    
    private void notifyPlaybackStopped() {
        for (PlaybackListener listener : listeners) {
            listener.onPlaybackStopped(currentSong);
        }
    }
    
    private void notifyPlaybackFinished() {
        for (PlaybackListener listener : listeners) {
            listener.onPlaybackFinished(currentSong);
        }
    }
    
    /**
     * Interface for playback event listeners
     */
    public interface PlaybackListener {
        default void onPlaybackStarted(Song song) {}
        default void onPlaybackPaused(Song song) {}
        default void onPlaybackResumed(Song song) {}
        default void onPlaybackStopped(Song song) {}
        default void onPlaybackFinished(Song song) {}
    }
}