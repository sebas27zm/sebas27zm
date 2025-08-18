package services;

import models.*;
import utils.Nationality;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Service for handling data persistence using simple file I/O
 * This is a simplified implementation without external JSON dependencies
 * In a production environment, you would use proper JSON serialization libraries
 */
public class DataPersistenceService {
    private static DataPersistenceService instance;
    private static final String DATA_DIR = "/workspace/music-streaming/data/";
    private static final String USERS_FILE = DATA_DIR + "users.dat";
    private static final String SONGS_FILE = DATA_DIR + "songs.dat";
    private static final String APP_STATE_FILE = DATA_DIR + "app_state.dat";
    
    private DataPersistenceService() {
        createDataDirectory();
    }
    
    public static DataPersistenceService getInstance() {
        if (instance == null) {
            instance = new DataPersistenceService();
        }
        return instance;
    }
    
    /**
     * Create data directory if it doesn't exist
     */
    private void createDataDirectory() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }
    
    /**
     * Save all users to file (simplified implementation)
     */
    public boolean saveUsers(Map<String, User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (Map.Entry<String, User> entry : users.entrySet()) {
                User user = entry.getValue();
                if (user instanceof Administrator) {
                    Administrator admin = (Administrator) user;
                    writer.println("ADMIN|" + admin.getUsername() + "|" + admin.getEmail() + "|" + admin.getPassword());
                } else if (user instanceof EndUser) {
                    EndUser endUser = (EndUser) user;
                    writer.println("ENDUSER|" + endUser.getUsername() + "|" + endUser.getEmail() + "|" + 
                                 endUser.getPassword() + "|" + endUser.getFullName() + "|" + 
                                 endUser.getBirthDate() + "|" + endUser.getNationality() + "|" + 
                                 endUser.getIdNumber() + "|" + endUser.getAvatarPath() + "|" + 
                                 endUser.getBalance());
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Load all users from file (simplified implementation)
     */
    public Map<String, User> loadUsers() {
        Map<String, User> users = new HashMap<>();
        File file = new File(USERS_FILE);
        
        if (!file.exists()) {
            return users;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 4) {
                    try {
                        if ("ADMIN".equals(parts[0])) {
                            Administrator admin = new Administrator(parts[2], parts[1], parts[3]);
                            users.put(admin.getUsername(), admin);
                        } else if ("ENDUSER".equals(parts[0]) && parts.length >= 10) {
                            // This is simplified - in production you'd handle this more securely
                            EndUser endUser = new EndUser(
                                parts[4], // fullName
                                LocalDate.parse(parts[5]), // birthDate
                                parts[6], // nationality
                                parts[7], // idNumber
                                parts[8], // avatarPath
                                parts[2], // email
                                parts[1], // username
                                parts[3], // password
                                parts[3]  // confirmPassword (same for loading)
                            );
                            // Adjust balance (subtract welcome bonus since constructor adds it)
                            double savedBalance = Double.parseDouble(parts[9]);
                            endUser.addBalance(savedBalance - 2.99);
                            users.put(endUser.getUsername(), endUser);
                        }
                    } catch (Exception e) {
                        System.err.println("Error parsing user line: " + line + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        
        return users;
    }
    
    /**
     * Save all songs to file (simplified implementation)
     */
    public boolean saveSongs(List<Song> songs) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SONGS_FILE))) {
            for (Song song : songs) {
                writer.println(song.getTitle() + "|" + song.getArtist() + "|" + 
                             song.getComposer() + "|" + song.getGenre() + "|" + 
                             (song.getReleaseDate() != null ? song.getReleaseDate() : "") + "|" + 
                             (song.getAlbum() != null ? song.getAlbum() : "") + "|" + 
                             song.getAlbumCoverPath() + "|" + song.getPrice() + "|" + 
                             song.getAudioFilePath() + "|" + song.getPurchaseCount() + "|" + 
                             song.getPlaylistInclusionCount());
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error saving songs: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Load all songs from file (simplified implementation)
     */
    public List<Song> loadSongs() {
        List<Song> songs = new ArrayList<>();
        File file = new File(SONGS_FILE);
        
        if (!file.exists()) {
            return songs;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 11) {
                    try {
                        LocalDate releaseDate = null;
                        if (!parts[4].isEmpty()) {
                            releaseDate = LocalDate.parse(parts[4]);
                        }
                        
                        Song song = new Song(
                            parts[0], // title
                            parts[1], // artist
                            parts[2], // composer
                            parts[3], // genre
                            releaseDate, // releaseDate
                            parts[5].isEmpty() ? null : parts[5], // album
                            parts[6], // albumCoverPath
                            Double.parseDouble(parts[7]), // price
                            parts[8]  // audioFilePath
                        );
                        
                        // Set statistics (this is simplified)
                        // In production, you'd have proper methods to set these values
                        
                        songs.add(song);
                    } catch (Exception e) {
                        System.err.println("Error parsing song line: " + line + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading songs: " + e.getMessage());
        }
        
        return songs;
    }
    
    /**
     * Save application state (simplified implementation)
     */
    public boolean saveAppState(AppState state) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(APP_STATE_FILE))) {
            writer.println("version=" + state.version);
            writer.println("lastSavedTime=" + state.lastSavedTime);
            writer.println("totalUsers=" + state.totalUsers);
            writer.println("totalSongs=" + state.totalSongs);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving app state: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Load application state (simplified implementation)
     */
    public AppState loadAppState() {
        AppState state = new AppState();
        File file = new File(APP_STATE_FILE);
        
        if (!file.exists()) {
            return state;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("version=")) {
                    state.version = line.substring(8);
                } else if (line.startsWith("totalUsers=")) {
                    state.totalUsers = Integer.parseInt(line.substring(11));
                } else if (line.startsWith("totalSongs=")) {
                    state.totalSongs = Integer.parseInt(line.substring(11));
                }
                // Note: lastSavedTime parsing omitted for simplicity
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading app state: " + e.getMessage());
        }
        
        return state;
    }
    
    /**
     * Save all data (simplified implementation)
     */
    public boolean saveAllData() {
        try {
            System.out.println("💾 Saving application data...");
            
            // Note: In a real implementation, you would properly integrate with services
            // This is a simplified approach for demonstration
            
            AppState state = new AppState();
            state.lastSavedTime = new Date();
            
            return saveAppState(state);
        } catch (Exception e) {
            System.err.println("Error saving all data: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Load all data (simplified implementation)
     */
    public boolean loadAllData() {
        try {
            System.out.println("📂 Loading application data...");
            
            // Note: In a real implementation, you would properly integrate with services
            // This is a simplified approach for demonstration
            
            AppState state = loadAppState();
            System.out.println("📊 Loaded app state: " + state.version);
            
            return true;
        } catch (Exception e) {
            System.err.println("Error loading all data: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Application state class
     */
    public static class AppState {
        public Date lastSavedTime;
        public int totalUsers;
        public int totalSongs;
        public String version = "1.0.0";
        
        public AppState() {
            this.lastSavedTime = new Date();
        }
    }
}