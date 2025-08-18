package utils;

import models.Song;
import models.Playlist;
import interfaces.Playable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

/**
 * Implements a playback queue for songs and playlists
 * Uses a Queue data structure (FIFO - First In, First Out)
 */
public class PlaybackQueue {
    private Queue<Song> queue;
    private Song currentSong;
    private List<Song> history;
    private boolean isShuffleMode;
    private boolean isRepeatMode;
    
    public PlaybackQueue() {
        this.queue = new LinkedList<>();
        this.history = new ArrayList<>();
        this.currentSong = null;
        this.isShuffleMode = false;
        this.isRepeatMode = false;
    }
    
    /**
     * Add a song to the end of the queue
     */
    public boolean addSong(Song song) {
        if (song == null) {
            return false;
        }
        return queue.offer(song);
    }
    
    /**
     * Add multiple songs to the queue
     */
    public boolean addSongs(List<Song> songs) {
        if (songs == null || songs.isEmpty()) {
            return false;
        }
        
        for (Song song : songs) {
            if (song != null) {
                queue.offer(song);
            }
        }
        return true;
    }
    
    /**
     * Add all songs from a playlist to the queue
     */
    public boolean addPlaylist(Playlist playlist) {
        if (playlist == null || playlist.isEmpty()) {
            return false;
        }
        
        List<Song> songs = playlist.getSongs();
        if (isShuffleMode) {
            songs = new ArrayList<>(songs);
            java.util.Collections.shuffle(songs);
        }
        
        return addSongs(songs);
    }
    
    /**
     * Get the next song from the queue
     */
    public Song next() {
        if (currentSong != null) {
            history.add(currentSong);
        }
        
        Song nextSong = queue.poll();
        
        // If repeat mode is on and queue is empty, add current song back
        if (nextSong == null && isRepeatMode && currentSong != null) {
            queue.offer(currentSong);
            nextSong = queue.poll();
        }
        
        currentSong = nextSong;
        return nextSong;
    }
    
    /**
     * Get the previous song from history
     */
    public Song previous() {
        if (history.isEmpty()) {
            return currentSong;
        }
        
        // Add current song back to front of queue
        if (currentSong != null) {
            Song temp = currentSong;
            queue = addToFront(queue, temp);
        }
        
        // Get last song from history
        Song previousSong = history.remove(history.size() - 1);
        currentSong = previousSong;
        return previousSong;
    }
    
    /**
     * Helper method to add song to front of queue
     */
    private Queue<Song> addToFront(Queue<Song> originalQueue, Song song) {
        Queue<Song> newQueue = new LinkedList<>();
        newQueue.offer(song);
        newQueue.addAll(originalQueue);
        return newQueue;
    }
    
    /**
     * Peek at the next song without removing it from queue
     */
    public Song peekNext() {
        return queue.peek();
    }
    
    /**
     * Get current song
     */
    public Song getCurrentSong() {
        return currentSong;
    }
    
    /**
     * Check if queue has more songs
     */
    public boolean hasNext() {
        return !queue.isEmpty() || (isRepeatMode && currentSong != null);
    }
    
    /**
     * Check if there are previous songs in history
     */
    public boolean hasPrevious() {
        return !history.isEmpty();
    }
    
    /**
     * Clear the entire queue
     */
    public void clear() {
        queue.clear();
        history.clear();
        currentSong = null;
    }
    
    /**
     * Remove a specific song from the queue
     */
    public boolean removeSong(Song song) {
        return queue.remove(song);
    }
    
    /**
     * Get the size of the current queue
     */
    public int size() {
        return queue.size();
    }
    
    /**
     * Check if queue is empty
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }
    
    /**
     * Get a copy of all songs in the queue
     */
    public List<Song> getQueueList() {
        return new ArrayList<>(queue);
    }
    
    /**
     * Get the playback history
     */
    public List<Song> getHistory() {
        return new ArrayList<>(history);
    }
    
    /**
     * Toggle shuffle mode
     */
    public void toggleShuffle() {
        this.isShuffleMode = !this.isShuffleMode;
    }
    
    /**
     * Toggle repeat mode
     */
    public void toggleRepeat() {
        this.isRepeatMode = !this.isRepeatMode;
    }
    
    /**
     * Set shuffle mode
     */
    public void setShuffleMode(boolean shuffleMode) {
        this.isShuffleMode = shuffleMode;
    }
    
    /**
     * Set repeat mode
     */
    public void setRepeatMode(boolean repeatMode) {
        this.isRepeatMode = repeatMode;
    }
    
    // Getters
    public boolean isShuffleMode() { return isShuffleMode; }
    public boolean isRepeatMode() { return isRepeatMode; }
    
    @Override
    public String toString() {
        return "PlaybackQueue{" +
                "queueSize=" + queue.size() +
                ", currentSong=" + (currentSong != null ? currentSong.getTitle() : "none") +
                ", historySize=" + history.size() +
                ", shuffle=" + isShuffleMode +
                ", repeat=" + isRepeatMode +
                '}';
    }
}