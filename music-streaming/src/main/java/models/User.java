package models;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

/**
 * Abstract base class for all users in the system
 */
public abstract class User {
    protected String email;
    protected String username;
    protected String password;
    
    // Password validation pattern
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,12}$"
    );
    
    public User(String email, String username, String password) throws IllegalArgumentException {
        validateEmail(email);
        validateUsername(username);
        validatePassword(password);
        
        this.email = email;
        this.username = username;
        this.password = password;
    }
    
    // Validation methods
    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
    
    private void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (username.length() < 3) {
            throw new IllegalArgumentException("Username must be at least 3 characters long");
        }
    }
    
    public static void validatePassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        if (password.length() < 8 || password.length() > 12) {
            throw new IllegalArgumentException("Password must be between 8 and 12 characters");
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException(
                "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
            );
        }
    }
    
    public boolean changePassword(String currentPassword, String newPassword, String confirmPassword) {
        if (!this.password.equals(currentPassword)) {
            return false;
        }
        if (!newPassword.equals(confirmPassword)) {
            return false;
        }
        if (newPassword.equals(currentPassword)) {
            return false;
        }
        
        try {
            validatePassword(newPassword);
            this.password = newPassword;
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
    
    // Getters
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    
    // Public getter for persistence (used by data service)
    public String getPassword() { return password; }
    
    // Setters
    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }
    
    public void setUsername(String username) {
        validateUsername(username);
        this.username = username;
    }
}