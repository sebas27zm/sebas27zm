package models;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a song in the music streaming application
 */
public class Song {
    private String title;
    private String artist;
    private String composer;
    private String genre;
    private LocalDate releaseDate;
    private String album;
    private String albumCoverPath;
    private double price;
    private String audioFilePath;
    private int purchaseCount;
    private int playlistInclusionCount;
    private Map<String, Integer> userRatings; // username -> rating
    
    public Song(String title, String artist, String composer, String genre, 
                LocalDate releaseDate, String album, String albumCoverPath, 
                double price, String audioFilePath) {
        validateTitle(title);
        validateArtist(artist);
        validateComposer(composer);
        validateGenre(genre);
        validatePrice(price);
        validateAudioFilePath(audioFilePath);
        
        this.title = title;
        this.artist = artist;
        this.composer = composer;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.album = album;
        this.albumCoverPath = albumCoverPath != null ? albumCoverPath : getDefaultAlbumCover();
        this.price = Math.round(price * 100.0) / 100.0; // Round to 2 decimal places
        this.audioFilePath = audioFilePath;
        this.purchaseCount = 0;
        this.playlistInclusionCount = 0;
        this.userRatings = new HashMap<>();
    }
    
    // Validation methods
    private void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Song title cannot be empty");
        }
    }
    
    private void validateArtist(String artist) {
        if (artist == null || artist.trim().isEmpty()) {
            throw new IllegalArgumentException("Artist cannot be empty");
        }
    }
    
    private void validateComposer(String composer) {
        if (composer == null || composer.trim().isEmpty()) {
            throw new IllegalArgumentException("Composer cannot be empty");
        }
    }
    
    private void validateGenre(String genre) {
        if (genre == null || genre.trim().isEmpty()) {
            throw new IllegalArgumentException("Genre cannot be empty");
        }
    }
    
    private void validatePrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (price > 999.99) {
            throw new IllegalArgumentException("Price cannot exceed $999.99");
        }
    }
    
    private void validateAudioFilePath(String audioFilePath) {
        if (audioFilePath == null || audioFilePath.trim().isEmpty()) {
            throw new IllegalArgumentException("Audio file path cannot be empty");
        }
    }
    
    private String getDefaultAlbumCover() {
        return "/resources/images/default-album-cover.png";
    }
    
    // Rating management
    public void addRating(String username, int rating) {
        if (rating < 1 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 1 and 10");
        }
        userRatings.put(username, rating);
    }
    
    public int getAverageRating() {
        if (userRatings.isEmpty()) {
            return 0;
        }
        
        int sum = userRatings.values().stream().mapToInt(Integer::intValue).sum();
        return Math.round((float) sum / userRatings.size());
    }
    
    public boolean hasRating(String username) {
        return userRatings.containsKey(username);
    }
    
    public Integer getUserRating(String username) {
        return userRatings.get(username);
    }
    
    // Statistics management
    public void incrementPurchaseCount() {
        this.purchaseCount++;
    }
    
    public void incrementPlaylistInclusionCount() {
        this.playlistInclusionCount++;
    }
    
    public void decrementPlaylistInclusionCount() {
        if (this.playlistInclusionCount > 0) {
            this.playlistInclusionCount--;
        }
    }
    
    // Getters
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getComposer() { return composer; }
    public String getGenre() { return genre; }
    public LocalDate getReleaseDate() { return releaseDate; }
    public String getAlbum() { return album; }
    public String getAlbumCoverPath() { return albumCoverPath; }
    public double getPrice() { return price; }
    public String getAudioFilePath() { return audioFilePath; }
    public int getPurchaseCount() { return purchaseCount; }
    public int getPlaylistInclusionCount() { return playlistInclusionCount; }
    public int getRatingCount() { return userRatings.size(); }
    
    // Setters
    public void setTitle(String title) {
        validateTitle(title);
        this.title = title;
    }
    
    public void setArtist(String artist) {
        validateArtist(artist);
        this.artist = artist;
    }
    
    public void setComposer(String composer) {
        validateComposer(composer);
        this.composer = composer;
    }
    
    public void setGenre(String genre) {
        validateGenre(genre);
        this.genre = genre;
    }
    
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    public void setAlbum(String album) {
        this.album = album;
        if (album == null || album.trim().isEmpty()) {
            this.albumCoverPath = getDefaultAlbumCover();
        }
    }
    
    public void setAlbumCoverPath(String albumCoverPath) {
        this.albumCoverPath = albumCoverPath != null ? albumCoverPath : getDefaultAlbumCover();
    }
    
    public void setPrice(double price) {
        validatePrice(price);
        this.price = Math.round(price * 100.0) / 100.0;
    }
    
    public void setAudioFilePath(String audioFilePath) {
        validateAudioFilePath(audioFilePath);
        this.audioFilePath = audioFilePath;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Song song = (Song) obj;
        return title.equals(song.title) && artist.equals(song.artist);
    }
    
    @Override
    public int hashCode() {
        return title.hashCode() * 31 + artist.hashCode();
    }
    
    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", genre='" + genre + '\'' +
                ", rating=" + getAverageRating() +
                ", price=$" + price +
                '}';
    }
}