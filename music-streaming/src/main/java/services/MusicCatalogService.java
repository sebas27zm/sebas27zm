package services;

import models.Song;
import models.EndUser;
import interfaces.Searchable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

/**
 * Service for managing the music catalog and search functionality
 */
public class MusicCatalogService implements Searchable<Song> {
    private static MusicCatalogService instance;
    private List<Song> catalog;
    
    private MusicCatalogService() {
        this.catalog = new ArrayList<>();
        initializeSampleCatalog();
    }
    
    public static MusicCatalogService getInstance() {
        if (instance == null) {
            instance = new MusicCatalogService();
        }
        return instance;
    }
    
    /**
     * Initialize catalog with sample songs for demonstration
     */
    private void initializeSampleCatalog() {
        try {
            // Add some sample songs
            addSong(new Song(
                "Bohemian Rhapsody", 
                "Queen", 
                "Freddie Mercury", 
                "Rock", 
                java.time.LocalDate.of(1975, 10, 31),
                "A Night at the Opera",
                "/resources/images/queen-night-opera.jpg",
                1.99,
                "/resources/audio/bohemian-rhapsody.mp3"
            ));
            
            addSong(new Song(
                "Hotel California", 
                "Eagles", 
                "Don Felder, Don Henley, Glenn Frey", 
                "Rock", 
                java.time.LocalDate.of(1976, 12, 8),
                "Hotel California",
                "/resources/images/eagles-hotel-california.jpg",
                1.49,
                "/resources/audio/hotel-california.mp3"
            ));
            
            addSong(new Song(
                "Billie Jean", 
                "Michael Jackson", 
                "Michael Jackson", 
                "Pop", 
                java.time.LocalDate.of(1983, 1, 2),
                "Thriller",
                "/resources/images/michael-jackson-thriller.jpg",
                1.29,
                "/resources/audio/billie-jean.mp3"
            ));
            
            addSong(new Song(
                "Imagine", 
                "John Lennon", 
                "John Lennon", 
                "Rock", 
                java.time.LocalDate.of(1971, 9, 9),
                "Imagine",
                "/resources/images/john-lennon-imagine.jpg",
                0.99,
                "/resources/audio/imagine.mp3"
            ));
            
            addSong(new Song(
                "Like a Rolling Stone", 
                "Bob Dylan", 
                "Bob Dylan", 
                "Folk Rock", 
                java.time.LocalDate.of(1965, 7, 20),
                "Highway 61 Revisited",
                "/resources/images/bob-dylan-highway61.jpg",
                1.19,
                "/resources/audio/like-rolling-stone.mp3"
            ));
            
        } catch (Exception e) {
            System.err.println("Error initializing sample catalog: " + e.getMessage());
        }
    }
    
    /**
     * Add a song to the catalog
     */
    public boolean addSong(Song song) {
        if (song == null) {
            return false;
        }
        
        // Check if song already exists
        for (Song existingSong : catalog) {
            if (existingSong.equals(song)) {
                return false;
            }
        }
        
        catalog.add(song);
        return true;
    }
    
    /**
     * Remove a song from the catalog
     */
    public boolean removeSong(Song song) {
        return catalog.remove(song);
    }
    
    /**
     * Get all songs in the catalog
     */
    public List<Song> getAllSongs() {
        return new ArrayList<>(catalog);
    }
    
    /**
     * Get song by title and artist
     */
    public Song getSong(String title, String artist) {
        for (Song song : catalog) {
            if (song.getTitle().equalsIgnoreCase(title) && 
                song.getArtist().equalsIgnoreCase(artist)) {
                return song;
            }
        }
        return null;
    }
    
    @Override
    public List<Song> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String searchTerm = name.toLowerCase().trim();
        return catalog.stream()
                .filter(song -> song.getTitle().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Song> searchByRating(int minRating, int maxRating) {
        if (minRating < 1) minRating = 1;
        if (maxRating > 10) maxRating = 10;
        if (minRating > maxRating) return new ArrayList<>();
        
        return catalog.stream()
                .filter(song -> {
                    int rating = song.getAverageRating();
                    return rating >= minRating && rating <= maxRating;
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Search songs by artist
     */
    public List<Song> searchByArtist(String artist) {
        if (artist == null || artist.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String searchTerm = artist.toLowerCase().trim();
        return catalog.stream()
                .filter(song -> song.getArtist().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }
    
    /**
     * Search songs by genre
     */
    public List<Song> searchByGenre(String genre) {
        if (genre == null || genre.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String searchTerm = genre.toLowerCase().trim();
        return catalog.stream()
                .filter(song -> song.getGenre().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }
    
    /**
     * Get top 5 highest rated songs
     */
    public List<Song> getTop5ByRating() {
        return catalog.stream()
                .filter(song -> song.getAverageRating() > 0)
                .sorted(Comparator.comparingInt(Song::getAverageRating).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
    
    /**
     * Get top 5 most purchased songs
     */
    public List<Song> getTop5ByPurchases() {
        return catalog.stream()
                .sorted(Comparator.comparingInt(Song::getPurchaseCount).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
    
    /**
     * Get top 5 most included in playlists
     */
    public List<Song> getTop5ByPlaylistInclusion() {
        return catalog.stream()
                .sorted(Comparator.comparingInt(Song::getPlaylistInclusionCount).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
    
    /**
     * Get songs by price range
     */
    public List<Song> getSongsByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0) minPrice = 0;
        if (maxPrice < minPrice) return new ArrayList<>();
        
        return catalog.stream()
                .filter(song -> song.getPrice() >= minPrice && song.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
    
    /**
     * Get songs by album
     */
    public List<Song> getSongsByAlbum(String album) {
        if (album == null || album.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return catalog.stream()
                .filter(song -> song.getAlbum() != null && 
                              song.getAlbum().equalsIgnoreCase(album.trim()))
                .collect(Collectors.toList());
    }
    
    /**
     * Get all unique genres
     */
    public List<String> getAllGenres() {
        return catalog.stream()
                .map(Song::getGenre)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
    
    /**
     * Get all unique artists
     */
    public List<String> getAllArtists() {
        return catalog.stream()
                .map(Song::getArtist)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
    
    /**
     * Get all unique albums
     */
    public List<String> getAllAlbums() {
        return catalog.stream()
                .map(Song::getAlbum)
                .filter(album -> album != null && !album.trim().isEmpty())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
    
    /**
     * Get catalog statistics
     */
    public CatalogStats getStats() {
        return new CatalogStats(
            catalog.size(),
            getAllArtists().size(),
            getAllGenres().size(),
            getAllAlbums().size(),
            catalog.stream().mapToDouble(Song::getPrice).average().orElse(0.0),
            catalog.stream().mapToInt(Song::getPurchaseCount).sum()
        );
    }
    
    /**
     * Inner class for catalog statistics
     */
    public static class CatalogStats {
        public final int totalSongs;
        public final int totalArtists;
        public final int totalGenres;
        public final int totalAlbums;
        public final double averagePrice;
        public final int totalPurchases;
        
        public CatalogStats(int totalSongs, int totalArtists, int totalGenres, 
                           int totalAlbums, double averagePrice, int totalPurchases) {
            this.totalSongs = totalSongs;
            this.totalArtists = totalArtists;
            this.totalGenres = totalGenres;
            this.totalAlbums = totalAlbums;
            this.averagePrice = Math.round(averagePrice * 100.0) / 100.0;
            this.totalPurchases = totalPurchases;
        }
        
        @Override
        public String toString() {
            return String.format(
                "Catalog Stats: %d songs, %d artists, %d genres, %d albums, " +
                "avg price: $%.2f, total purchases: %d",
                totalSongs, totalArtists, totalGenres, totalAlbums, 
                averagePrice, totalPurchases
            );
        }
    }
}