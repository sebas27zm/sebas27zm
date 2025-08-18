package models;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an end user of the music streaming application
 */
public class EndUser extends User {
    private String fullName;
    private LocalDate birthDate;
    private String nationality;
    private String idNumber;
    private String avatarPath;
    private double balance;
    private List<Song> ownedSongs;
    private List<Playlist> playlists;
    
    // Welcome bonus constant
    private static final double WELCOME_BONUS = 2.99;
    
    public EndUser(String fullName, LocalDate birthDate, String nationality, 
                   String idNumber, String avatarPath, String email, 
                   String username, String password, String confirmPassword) 
                   throws IllegalArgumentException {
        super(email, username, password);
        
        // Validate password confirmation
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        
        validateAge(birthDate);
        validateFullName(fullName);
        validateNationality(nationality);
        validateIdNumber(idNumber);
        
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.idNumber = idNumber;
        this.avatarPath = avatarPath != null ? avatarPath : getDefaultAvatarPath();
        this.balance = WELCOME_BONUS; // Welcome bonus
        this.ownedSongs = new ArrayList<>();
        this.playlists = new ArrayList<>();
    }
    
    // Validation methods
    private void validateAge(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date cannot be null");
        }
        
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < 18) {
            throw new IllegalArgumentException("User must be at least 18 years old");
        }
    }
    
    private void validateFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        if (fullName.trim().length() < 2) {
            throw new IllegalArgumentException("Full name must be at least 2 characters long");
        }
    }
    
    private void validateNationality(String nationality) {
        if (nationality == null || nationality.trim().isEmpty()) {
            throw new IllegalArgumentException("Nationality cannot be empty");
        }
    }
    
    private void validateIdNumber(String idNumber) {
        if (idNumber == null || idNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("ID number cannot be empty");
        }
    }
    
    private String getDefaultAvatarPath() {
        return "/resources/images/default-avatar.png";
    }
    
    // Balance management
    public boolean addBalance(double amount) {
        if (amount <= 0) {
            return false;
        }
        this.balance += amount;
        return true;
    }
    
    public boolean purchaseSong(Song song) {
        if (song == null) {
            return false;
        }
        if (this.balance < song.getPrice()) {
            return false;
        }
        if (ownedSongs.contains(song)) {
            return false; // Already owned
        }
        
        this.balance -= song.getPrice();
        this.ownedSongs.add(song);
        song.incrementPurchaseCount();
        return true;
    }
    
    public boolean ownsSong(Song song) {
        return ownedSongs.contains(song);
    }
    
    // Playlist management
    public Playlist createPlaylist(String name) {
        Playlist playlist = new Playlist(name, this);
        this.playlists.add(playlist);
        return playlist;
    }
    
    public boolean deletePlaylist(Playlist playlist) {
        return this.playlists.remove(playlist);
    }
    
    public boolean rateSong(Song song, int rating) {
        if (!ownsSong(song)) {
            return false;
        }
        if (rating < 1 || rating > 10) {
            return false;
        }
        
        song.addRating(this.username, rating);
        return true;
    }
    
    // Getters
    public String getFullName() { return fullName; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getNationality() { return nationality; }
    public String getIdNumber() { return idNumber; }
    public String getAvatarPath() { return avatarPath; }
    public double getBalance() { return balance; }
    public List<Song> getOwnedSongs() { return new ArrayList<>(ownedSongs); }
    public List<Playlist> getPlaylists() { return new ArrayList<>(playlists); }
    
    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
    
    // Setters
    public void setFullName(String fullName) {
        validateFullName(fullName);
        this.fullName = fullName;
    }
    
    public void setNationality(String nationality) {
        validateNationality(nationality);
        this.nationality = nationality;
    }
    
    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath != null ? avatarPath : getDefaultAvatarPath();
    }
    
    @Override
    public String toString() {
        return "EndUser{" +
                "fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", balance=" + balance +
                ", songsOwned=" + ownedSongs.size() +
                ", playlists=" + playlists.size() +
                '}';
    }
}