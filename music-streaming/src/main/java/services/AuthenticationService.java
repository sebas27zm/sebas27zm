package services;

import models.User;
import models.EndUser;
import models.Administrator;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for handling user authentication and session management
 */
public class AuthenticationService {
    private static AuthenticationService instance;
    private Map<String, User> users; // username -> user
    private User currentUser;
    
    private AuthenticationService() {
        this.users = new HashMap<>();
        this.currentUser = null;
        initializeDefaultAdmin();
    }
    
    public static AuthenticationService getInstance() {
        if (instance == null) {
            instance = new AuthenticationService();
        }
        return instance;
    }
    
    /**
     * Initialize a default administrator account
     */
    private void initializeDefaultAdmin() {
        try {
            Administrator admin = new Administrator(
                "admin@musicstream.com", 
                "admin", 
                "Admin123!"
            );
            users.put(admin.getUsername(), admin);
        } catch (Exception e) {
            System.err.println("Failed to create default admin: " + e.getMessage());
        }
    }
    
    /**
     * Register a new end user
     */
    public boolean registerEndUser(EndUser user) {
        if (user == null) {
            return false;
        }
        
        // Check if username already exists
        if (users.containsKey(user.getUsername())) {
            return false;
        }
        
        // Check if email already exists
        for (User existingUser : users.values()) {
            if (existingUser.getEmail().equalsIgnoreCase(user.getEmail())) {
                return false;
            }
        }
        
        users.put(user.getUsername(), user);
        return true;
    }
    
    /**
     * Register a new administrator
     */
    public boolean registerAdministrator(Administrator admin) {
        if (admin == null) {
            return false;
        }
        
        // Check if username already exists
        if (users.containsKey(admin.getUsername())) {
            return false;
        }
        
        // Check if email already exists
        for (User existingUser : users.values()) {
            if (existingUser.getEmail().equalsIgnoreCase(admin.getEmail())) {
                return false;
            }
        }
        
        users.put(admin.getUsername(), admin);
        return true;
    }
    
    /**
     * Authenticate a user with username and password
     */
    public boolean login(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        
        User user = users.get(username);
        if (user == null) {
            return false;
        }
        
        if (user.authenticate(username, password)) {
            this.currentUser = user;
            return true;
        }
        
        return false;
    }
    
    /**
     * Log out the current user
     */
    public void logout() {
        this.currentUser = null;
    }
    
    /**
     * Check if a user is currently logged in
     */
    public boolean isLoggedIn() {
        return this.currentUser != null;
    }
    
    /**
     * Get the current logged-in user
     */
    public User getCurrentUser() {
        return this.currentUser;
    }
    
    /**
     * Check if current user is an administrator
     */
    public boolean isCurrentUserAdmin() {
        return this.currentUser instanceof Administrator;
    }
    
    /**
     * Get current user as EndUser (if applicable)
     */
    public EndUser getCurrentEndUser() {
        if (this.currentUser instanceof EndUser) {
            return (EndUser) this.currentUser;
        }
        return null;
    }
    
    /**
     * Get current user as Administrator (if applicable)
     */
    public Administrator getCurrentAdministrator() {
        if (this.currentUser instanceof Administrator) {
            return (Administrator) this.currentUser;
        }
        return null;
    }
    
    /**
     * Check if username is available
     */
    public boolean isUsernameAvailable(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return !users.containsKey(username);
    }
    
    /**
     * Check if email is available
     */
    public boolean isEmailAvailable(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        for (User user : users.values()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Change password for current user
     */
    public boolean changeCurrentUserPassword(String currentPassword, String newPassword, String confirmPassword) {
        if (this.currentUser == null) {
            return false;
        }
        
        return this.currentUser.changePassword(currentPassword, newPassword, confirmPassword);
    }
    
    /**
     * Get user by username
     */
    public User getUserByUsername(String username) {
        return users.get(username);
    }
    
    /**
     * Get user by email
     */
    public User getUserByEmail(String email) {
        for (User user : users.values()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Get total number of registered users
     */
    public int getTotalUsers() {
        return users.size();
    }
    
    /**
     * Get number of end users
     */
    public int getEndUserCount() {
        int count = 0;
        for (User user : users.values()) {
            if (user instanceof EndUser) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Get number of administrators
     */
    public int getAdministratorCount() {
        int count = 0;
        for (User user : users.values()) {
            if (user instanceof Administrator) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Delete a user account (admin only)
     */
    public boolean deleteUser(String username) {
        if (!isCurrentUserAdmin()) {
            return false;
        }
        
        User userToDelete = users.get(username);
        if (userToDelete == null) {
            return false;
        }
        
        // Don't allow deletion of current user
        if (userToDelete.equals(this.currentUser)) {
            return false;
        }
        
        users.remove(username);
        return true;
    }
    
    /**
     * Get all users (admin only)
     */
    public Map<String, User> getAllUsers() {
        if (!isCurrentUserAdmin()) {
            return new HashMap<>();
        }
        return new HashMap<>(users);
    }
}