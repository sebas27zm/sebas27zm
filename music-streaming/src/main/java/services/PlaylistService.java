package services;

import models.Playlist;
import models.EndUser;
import models.Song;
import interfaces.Searchable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

/**
 * Service for managing playlists and playlist search functionality
 */
public class PlaylistService implements Searchable<Playlist> {
    private static PlaylistService instance;
    
    private PlaylistService() {
    }
    
    public static PlaylistService getInstance() {
        if (instance == null) {
            instance = new PlaylistService();
        }
        return instance;
    }
    
    /**
     * Create a new playlist for a user
     */
    public Playlist createPlaylist(String name, EndUser owner) {
        if (name == null || name.trim().isEmpty() || owner == null) {
            return null;
        }
        
        // Check if user already has a playlist with this name
        List<Playlist> userPlaylists = owner.getPlaylists();
        for (Playlist playlist : userPlaylists) {
            if (playlist.getName().equalsIgnoreCase(name.trim())) {
                return null; // Name already exists
            }
        }
        
        return owner.createPlaylist(name.trim());
    }
    
    /**
     * Delete a playlist
     */
    public boolean deletePlaylist(Playlist playlist, EndUser owner) {
        if (playlist == null || owner == null) {
            return false;
        }
        
        // Check if user owns the playlist
        if (!playlist.getOwner().equals(owner)) {
            return false;
        }
        
        return owner.deletePlaylist(playlist);
    }
    
    /**
     * Add song to playlist
     */
    public boolean addSongToPlaylist(Playlist playlist, Song song, EndUser user) {
        if (playlist == null || song == null || user == null) {
            return false;
        }
        
        // Check if user owns the playlist
        if (!playlist.getOwner().equals(user)) {
            return false;
        }
        
        // Check if user owns the song
        if (!user.ownsSong(song)) {
            return false;
        }
        
        return playlist.addSong(song);
    }
    
    /**
     * Remove song from playlist
     */
    public boolean removeSongFromPlaylist(Playlist playlist, Song song, EndUser user) {
        if (playlist == null || song == null || user == null) {
            return false;
        }
        
        // Check if user owns the playlist
        if (!playlist.getOwner().equals(user)) {
            return false;
        }
        
        return playlist.removeSong(song);
    }
    
    /**
     * Remove song from playlist by index
     */
    public boolean removeSongFromPlaylist(Playlist playlist, int index, EndUser user) {
        if (playlist == null || user == null) {
            return false;
        }
        
        // Check if user owns the playlist
        if (!playlist.getOwner().equals(user)) {
            return false;
        }
        
        return playlist.removeSong(index);
    }
    
    /**
     * Move song within playlist
     */
    public boolean moveSongInPlaylist(Playlist playlist, int fromIndex, int toIndex, EndUser user) {
        if (playlist == null || user == null) {
            return false;
        }
        
        // Check if user owns the playlist
        if (!playlist.getOwner().equals(user)) {
            return false;
        }
        
        return playlist.moveSong(fromIndex, toIndex);
    }
    
    /**
     * Rename playlist
     */
    public boolean renamePlaylist(Playlist playlist, String newName, EndUser user) {
        if (playlist == null || newName == null || newName.trim().isEmpty() || user == null) {
            return false;
        }
        
        // Check if user owns the playlist
        if (!playlist.getOwner().equals(user)) {
            return false;
        }
        
        // Check if user already has a playlist with this name
        List<Playlist> userPlaylists = user.getPlaylists();
        for (Playlist existingPlaylist : userPlaylists) {
            if (!existingPlaylist.equals(playlist) && 
                existingPlaylist.getName().equalsIgnoreCase(newName.trim())) {
                return false; // Name already exists
            }
        }
        
        try {
            playlist.setName(newName.trim());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
    /**
     * Get all playlists for a user
     */
    public List<Playlist> getUserPlaylists(EndUser user) {
        if (user == null) {
            return new ArrayList<>();
        }
        return user.getPlaylists();
    }
    
    @Override
    public List<Playlist> searchByName(String name) {
        // This method searches across all playlists that the current user can access
        AuthenticationService authService = AuthenticationService.getInstance();
        if (!authService.isLoggedIn()) {
            return new ArrayList<>();
        }
        
        if (name == null || name.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String searchTerm = name.toLowerCase().trim();
        List<Playlist> searchablePlayists = new ArrayList<>();
        
        // If current user is admin, they can search all playlists
        if (authService.isCurrentUserAdmin()) {
            // For admin, we would need to collect all playlists from all users
            // This is a simplified implementation
            return new ArrayList<>(); // Would need access to all users
        } else {
            // For end users, search only their own playlists
            EndUser currentUser = authService.getCurrentEndUser();
            if (currentUser != null) {
                searchablePlayists = currentUser.getPlaylists();
            }
        }
        
        return searchablePlayists.stream()
                .filter(playlist -> playlist.getName().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Playlist> searchByRating(int minRating, int maxRating) {
        AuthenticationService authService = AuthenticationService.getInstance();
        if (!authService.isLoggedIn()) {
            return new ArrayList<>();
        }
        
        if (minRating < 1) minRating = 1;
        if (maxRating > 10) maxRating = 10;
        if (minRating > maxRating) return new ArrayList<>();
        
        List<Playlist> searchablePlayists = new ArrayList<>();
        
        // If current user is admin, they can search all playlists
        if (authService.isCurrentUserAdmin()) {
            // For admin, we would need to collect all playlists from all users
            // This is a simplified implementation
            return new ArrayList<>(); // Would need access to all users
        } else {
            // For end users, search only their own playlists
            EndUser currentUser = authService.getCurrentEndUser();
            if (currentUser != null) {
                searchablePlayists = currentUser.getPlaylists();
            }
        }
        
        return searchablePlayists.stream()
                .filter(playlist -> {
                    int rating = playlist.getAverageRating();
                    return rating >= minRating && rating <= maxRating;
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Search playlists by song content
     */
    public List<Playlist> searchPlaylistsContainingSong(Song song, EndUser user) {
        if (song == null || user == null) {
            return new ArrayList<>();
        }
        
        return user.getPlaylists().stream()
                .filter(playlist -> playlist.containsSong(song))
                .collect(Collectors.toList());
    }
    
    /**
     * Get playlists sorted by creation date
     */
    public List<Playlist> getPlaylistsByCreationDate(EndUser user, boolean ascending) {
        if (user == null) {
            return new ArrayList<>();
        }
        
        List<Playlist> playlists = new ArrayList<>(user.getPlaylists());
        if (ascending) {
            playlists.sort(Comparator.comparing(Playlist::getCreationDate));
        } else {
            playlists.sort(Comparator.comparing(Playlist::getCreationDate).reversed());
        }
        return playlists;
    }
    
    /**
     * Get playlists sorted by rating
     */
    public List<Playlist> getPlaylistsByRating(EndUser user, boolean ascending) {
        if (user == null) {
            return new ArrayList<>();
        }
        
        List<Playlist> playlists = new ArrayList<>(user.getPlaylists());
        if (ascending) {
            playlists.sort(Comparator.comparingInt(Playlist::getAverageRating));
        } else {
            playlists.sort(Comparator.comparingInt(Playlist::getAverageRating).reversed());
        }
        return playlists;
    }
    
    /**
     * Get playlists sorted by song count
     */
    public List<Playlist> getPlaylistsBySongCount(EndUser user, boolean ascending) {
        if (user == null) {
            return new ArrayList<>();
        }
        
        List<Playlist> playlists = new ArrayList<>(user.getPlaylists());
        if (ascending) {
            playlists.sort(Comparator.comparingInt(Playlist::getSongCount));
        } else {
            playlists.sort(Comparator.comparingInt(Playlist::getSongCount).reversed());
        }
        return playlists;
    }
    
    /**
     * Get user's playlist statistics
     */
    public PlaylistStats getUserPlaylistStats(EndUser user) {
        if (user == null) {
            return new PlaylistStats(0, 0, 0, 0.0);
        }
        
        List<Playlist> playlists = user.getPlaylists();
        int totalPlaylists = playlists.size();
        int totalSongs = playlists.stream().mapToInt(Playlist::getSongCount).sum();
        int totalDuration = playlists.stream().mapToInt(Playlist::getTotalDurationSeconds).sum();
        
        double averageRating = 0.0;
        if (!playlists.isEmpty()) {
            List<Playlist> ratedPlaylists = playlists.stream()
                    .filter(p -> p.getAverageRating() > 0)
                    .collect(Collectors.toList());
            
            if (!ratedPlaylists.isEmpty()) {
                averageRating = ratedPlaylists.stream()
                        .mapToInt(Playlist::getAverageRating)
                        .average()
                        .orElse(0.0);
            }
        }
        
        return new PlaylistStats(totalPlaylists, totalSongs, totalDuration, averageRating);
    }
    
    /**
     * Inner class for playlist statistics
     */
    public static class PlaylistStats {
        public final int totalPlaylists;
        public final int totalSongs;
        public final int totalDurationSeconds;
        public final double averageRating;
        
        public PlaylistStats(int totalPlaylists, int totalSongs, int totalDurationSeconds, double averageRating) {
            this.totalPlaylists = totalPlaylists;
            this.totalSongs = totalSongs;
            this.totalDurationSeconds = totalDurationSeconds;
            this.averageRating = Math.round(averageRating * 100.0) / 100.0;
        }
        
        public String getFormattedDuration() {
            int hours = totalDurationSeconds / 3600;
            int minutes = (totalDurationSeconds % 3600) / 60;
            int seconds = totalDurationSeconds % 60;
            
            if (hours > 0) {
                return String.format("%d:%02d:%02d", hours, minutes, seconds);
            } else {
                return String.format("%d:%02d", minutes, seconds);
            }
        }
        
        @Override
        public String toString() {
            return String.format(
                "Playlist Stats: %d playlists, %d songs, duration: %s, avg rating: %.1f",
                totalPlaylists, totalSongs, getFormattedDuration(), averageRating
            );
        }
    }
}