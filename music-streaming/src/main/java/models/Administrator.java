package models;

import java.util.List;

/**
 * Represents an administrator user with special privileges
 */
public class Administrator extends User {
    
    public Administrator(String email, String username, String password) {
        super(email, username, password);
    }
    
    // Administrator can access any playlist
    public boolean canAccessPlaylist(Playlist playlist) {
        return true;
    }
    
    // Administrator can play any song without purchase
    public boolean canPlaySong(Song song) {
        return true;
    }
    
    // Administrator can view all user playlists
    public boolean canViewUserPlaylists(EndUser user) {
        return true;
    }
    
    // Administrator can upload songs to the catalog
    public boolean uploadSong(Song song, List<Song> catalog) {
        if (song == null || catalog == null) {
            return false;
        }
        
        // Check if song already exists in catalog
        for (Song existingSong : catalog) {
            if (existingSong.getTitle().equalsIgnoreCase(song.getTitle()) &&
                existingSong.getArtist().equalsIgnoreCase(song.getArtist())) {
                return false; // Song already exists
            }
        }
        
        catalog.add(song);
        return true;
    }
    
    // Administrator can remove songs from catalog
    public boolean removeSong(Song song, List<Song> catalog) {
        if (song == null || catalog == null) {
            return false;
        }
        return catalog.remove(song);
    }
    
    @Override
    public String toString() {
        return "Administrator{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}