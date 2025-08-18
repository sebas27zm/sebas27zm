package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a playlist created by an end user
 */
public class Playlist {
    private String name;
    private LocalDate creationDate;
    private List<Song> songs;
    private EndUser owner;
    
    public Playlist(String name, EndUser owner) {
        validateName(name);
        validateOwner(owner);
        
        this.name = name;
        this.owner = owner;
        this.creationDate = LocalDate.now();
        this.songs = new ArrayList<>();
    }
    
    // Validation methods
    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Playlist name cannot be empty");
        }
        if (name.trim().length() > 100) {
            throw new IllegalArgumentException("Playlist name cannot exceed 100 characters");
        }
    }
    
    private void validateOwner(EndUser owner) {
        if (owner == null) {
            throw new IllegalArgumentException("Playlist owner cannot be null");
        }
    }
    
    // Song management
    public boolean addSong(Song song) {
        if (song == null) {
            return false;
        }
        
        // Check if user owns the song
        if (!owner.ownsSong(song)) {
            return false;
        }
        
        // Check if song is already in playlist
        if (songs.contains(song)) {
            return false;
        }
        
        songs.add(song);
        song.incrementPlaylistInclusionCount();
        return true;
    }
    
    public boolean removeSong(Song song) {
        if (song == null) {
            return false;
        }
        
        boolean removed = songs.remove(song);
        if (removed) {
            song.decrementPlaylistInclusionCount();
        }
        return removed;
    }
    
    public boolean removeSong(int index) {
        if (index < 0 || index >= songs.size()) {
            return false;
        }
        
        Song removedSong = songs.remove(index);
        removedSong.decrementPlaylistInclusionCount();
        return true;
    }
    
    public boolean containsSong(Song song) {
        return songs.contains(song);
    }
    
    public boolean moveSong(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex >= songs.size() || 
            toIndex < 0 || toIndex >= songs.size()) {
            return false;
        }
        
        if (fromIndex == toIndex) {
            return true;
        }
        
        Song song = songs.remove(fromIndex);
        songs.add(toIndex, song);
        return true;
    }
    
    // Calculate average rating
    public int getAverageRating() {
        if (songs.isEmpty()) {
            return 0;
        }
        
        int totalRating = 0;
        int songsWithRating = 0;
        
        for (Song song : songs) {
            int rating = song.getAverageRating();
            if (rating > 0) {
                totalRating += rating;
                songsWithRating++;
            }
        }
        
        if (songsWithRating == 0) {
            return 0;
        }
        
        return Math.round((float) totalRating / songsWithRating);
    }
    
    // Get total duration (this would require audio file analysis in a real implementation)
    public int getTotalDurationSeconds() {
        // For now, return estimated duration based on song count
        // In a real implementation, this would read actual audio file durations
        return songs.size() * 210; // Assume average 3.5 minutes per song
    }
    
    public String getFormattedDuration() {
        int totalSeconds = getTotalDurationSeconds();
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        
        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%d:%02d", minutes, seconds);
        }
    }
    
    // Getters
    public String getName() { return name; }
    public LocalDate getCreationDate() { return creationDate; }
    public List<Song> getSongs() { return new ArrayList<>(songs); }
    public EndUser getOwner() { return owner; }
    public int getSongCount() { return songs.size(); }
    
    public Song getSong(int index) {
        if (index < 0 || index >= songs.size()) {
            return null;
        }
        return songs.get(index);
    }
    
    // Setters
    public void setName(String name) {
        validateName(name);
        this.name = name;
    }
    
    // Check if playlist is empty
    public boolean isEmpty() {
        return songs.isEmpty();
    }
    
    // Clear all songs
    public void clear() {
        for (Song song : songs) {
            song.decrementPlaylistInclusionCount();
        }
        songs.clear();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Playlist playlist = (Playlist) obj;
        return name.equals(playlist.name) && 
               owner.equals(playlist.owner) &&
               creationDate.equals(playlist.creationDate);
    }
    
    @Override
    public int hashCode() {
        return name.hashCode() * 31 + owner.hashCode() + creationDate.hashCode();
    }
    
    @Override
    public String toString() {
        return "Playlist{" +
                "name='" + name + '\'' +
                ", songs=" + songs.size() +
                ", rating=" + getAverageRating() +
                ", duration='" + getFormattedDuration() + '\'' +
                ", created=" + creationDate +
                '}';
    }
}