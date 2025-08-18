package ui;

import models.*;
import services.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Administrator dashboard for managing the music streaming application
 * Provides admin-only functionality for managing songs, users, and viewing statistics
 */
public class AdminDashboard extends JPanel {
    private MainWindow mainWindow;
    
    // Services
    private AuthenticationService authService;
    private MusicCatalogService catalogService;
    private AudioPlayerService audioService;
    
    // GUI Components
    private JTabbedPane tabbedPane;
    private JLabel adminInfoLabel;
    
    // Song management components
    private JTable songsTable;
    private DefaultTableModel songsTableModel;
    private JButton addSongButton;
    private JButton removeSongButton;
    private JButton playSongButton;
    
    // User management components
    private JTable usersTable;
    private DefaultTableModel usersTableModel;
    private JButton deleteUserButton;
    private JButton viewUserDetailsButton;
    
    // Statistics components
    private JTextArea statsTextArea;
    private JButton refreshStatsButton;
    
    public AdminDashboard(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        
        initializeServices();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        refreshData();
    }
    
    private void initializeServices() {
        this.authService = AuthenticationService.getInstance();
        this.catalogService = MusicCatalogService.getInstance();
        this.audioService = AudioPlayerService.getInstance();
    }
    
    private void initializeComponents() {
        tabbedPane = new JTabbedPane();
        adminInfoLabel = new JLabel();
        
        initializeSongManagementComponents();
        initializeUserManagementComponents();
        initializeStatisticsComponents();
    }
    
    private void initializeSongManagementComponents() {
        songsTableModel = new DefaultTableModel(
            new Object[]{"Title", "Artist", "Genre", "Rating", "Price", "Purchases", "In Playlists"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        songsTable = new JTable(songsTableModel);
        songsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        addSongButton = new JButton("Add Song");
        removeSongButton = new JButton("Remove Song");
        playSongButton = new JButton("Play Song");
    }
    
    private void initializeUserManagementComponents() {
        usersTableModel = new DefaultTableModel(
            new Object[]{"Username", "Full Name", "Email", "Type", "Balance", "Songs Owned", "Playlists"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        usersTable = new JTable(usersTableModel);
        usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        deleteUserButton = new JButton("Delete User");
        viewUserDetailsButton = new JButton("View Details");
    }
    
    private void initializeStatisticsComponents() {
        statsTextArea = new JTextArea(20, 50);
        statsTextArea.setEditable(false);
        statsTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        refreshStatsButton = new JButton("Refresh Statistics");
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Header panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Create tab panels
        JPanel songManagementPanel = createSongManagementPanel();
        JPanel userManagementPanel = createUserManagementPanel();
        JPanel statisticsPanel = createStatisticsPanel();
        
        // Add tabs
        tabbedPane.addTab("Song Management", songManagementPanel);
        tabbedPane.addTab("User Management", userManagementPanel);
        tabbedPane.addTab("Statistics", statisticsPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(255, 248, 240)); // Light orange background
        
        // Admin info
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(panel.getBackground());
        infoPanel.add(adminInfoLabel);
        
        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setBackground(panel.getBackground());
        
        JButton changePasswordButton = new JButton("Change Password");
        JButton logoutButton = new JButton("Logout");
        
        actionPanel.add(changePasswordButton);
        actionPanel.add(logoutButton);
        
        panel.add(infoPanel, BorderLayout.WEST);
        panel.add(actionPanel, BorderLayout.EAST);
        
        // Event handlers for header buttons
        changePasswordButton.addActionListener(e -> showChangePasswordDialog());
        logoutButton.addActionListener(e -> mainWindow.onLogout());
        
        return panel;
    }
    
    private JPanel createSongManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Table
        JScrollPane tableScrollPane = new JScrollPane(songsTable);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addSongButton);
        buttonPanel.add(removeSongButton);
        buttonPanel.add(playSongButton);
        
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createUserManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Table
        JScrollPane tableScrollPane = new JScrollPane(usersTable);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(viewUserDetailsButton);
        buttonPanel.add(deleteUserButton);
        
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createStatisticsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Text area with scroll pane
        JScrollPane scrollPane = new JScrollPane(statsTextArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Application Statistics"));
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(refreshStatsButton);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void setupEventHandlers() {
        // Song management events
        addSongButton.addActionListener(e -> showAddSongDialog());
        removeSongButton.addActionListener(e -> removeSelectedSong());
        playSongButton.addActionListener(e -> playSelectedSong());
        
        // User management events
        viewUserDetailsButton.addActionListener(e -> viewSelectedUserDetails());
        deleteUserButton.addActionListener(e -> deleteSelectedUser());
        
        // Statistics events
        refreshStatsButton.addActionListener(e -> refreshStatistics());
    }
    
    // Event handler methods
    private void showAddSongDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add New Song", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Create form fields
        JTextField titleField = new JTextField(20);
        JTextField artistField = new JTextField(20);
        JTextField composerField = new JTextField(20);
        JTextField genreField = new JTextField(20);
        JTextField albumField = new JTextField(20);
        JTextField priceField = new JTextField(20);
        JTextField audioPathField = new JTextField(20);
        
        // Layout form
        gbc.insets = new Insets(5, 5, 5, 5);
        
        int row = 0;
        addFormField(dialog, gbc, "Title:", titleField, row++);
        addFormField(dialog, gbc, "Artist:", artistField, row++);
        addFormField(dialog, gbc, "Composer:", composerField, row++);
        addFormField(dialog, gbc, "Genre:", genreField, row++);
        addFormField(dialog, gbc, "Album:", albumField, row++);
        addFormField(dialog, gbc, "Price:", priceField, row++);
        addFormField(dialog, gbc, "Audio File Path:", audioPathField, row++);
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Song");
        JButton cancelButton = new JButton("Cancel");
        
        addButton.addActionListener(e -> {
            try {
                String title = titleField.getText().trim();
                String artist = artistField.getText().trim();
                String composer = composerField.getText().trim();
                String genre = genreField.getText().trim();
                String album = albumField.getText().trim();
                String audioPath = audioPathField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                
                Song newSong = new Song(
                    title, artist, composer, genre, LocalDate.now(),
                    album.isEmpty() ? null : album, null, price, audioPath
                );
                
                Administrator admin = authService.getCurrentAdministrator();
                if (admin.uploadSong(newSong, catalogService.getAllSongs())) {
                    JOptionPane.showMessageDialog(dialog, "Song added successfully!");
                    dialog.dispose();
                    refreshSongsTable();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to add song. It may already exist.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error adding song: " + ex.getMessage());
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void addFormField(JDialog dialog, GridBagConstraints gbc, String label, JComponent field, int row) {
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        dialog.add(new JLabel(label), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        dialog.add(field, gbc);
    }
    
    private void removeSelectedSong() {
        int selectedRow = songsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a song to remove.");
            return;
        }
        
        String title = (String) songsTableModel.getValueAt(selectedRow, 0);
        String artist = (String) songsTableModel.getValueAt(selectedRow, 1);
        
        int choice = JOptionPane.showConfirmDialog(this,
            String.format("Are you sure you want to remove \"%s\" by %s?", title, artist),
            "Confirm Removal",
            JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            Song song = catalogService.getSong(title, artist);
            if (song != null && catalogService.removeSong(song)) {
                JOptionPane.showMessageDialog(this, "Song removed successfully!");
                refreshSongsTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to remove song.");
            }
        }
    }
    
    private void playSelectedSong() {
        int selectedRow = songsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a song to play.");
            return;
        }
        
        String title = (String) songsTableModel.getValueAt(selectedRow, 0);
        String artist = (String) songsTableModel.getValueAt(selectedRow, 1);
        Song song = catalogService.getSong(title, artist);
        
        if (song != null) {
            audioService.playSong(song); // Admin can play any song
        }
    }
    
    private void viewSelectedUserDetails() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to view details.");
            return;
        }
        
        String username = (String) usersTableModel.getValueAt(selectedRow, 0);
        User user = authService.getUserByUsername(username);
        
        if (user != null) {
            StringBuilder details = new StringBuilder();
            details.append("User Details\n");
            details.append("=".repeat(30)).append("\n");
            details.append("Username: ").append(user.getUsername()).append("\n");
            details.append("Email: ").append(user.getEmail()).append("\n");
            details.append("Type: ").append(user instanceof Administrator ? "Administrator" : "End User").append("\n");
            
            if (user instanceof EndUser) {
                EndUser endUser = (EndUser) user;
                details.append("Full Name: ").append(endUser.getFullName()).append("\n");
                details.append("Age: ").append(endUser.getAge()).append("\n");
                details.append("Nationality: ").append(endUser.getNationality()).append("\n");
                details.append("Balance: $").append(String.format("%.2f", endUser.getBalance())).append("\n");
                details.append("Songs Owned: ").append(endUser.getOwnedSongs().size()).append("\n");
                details.append("Playlists: ").append(endUser.getPlaylists().size()).append("\n");
            }
            
            JTextArea textArea = new JTextArea(details.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            
            JOptionPane.showMessageDialog(this, scrollPane, "User Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void deleteSelectedUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
            return;
        }
        
        String username = (String) usersTableModel.getValueAt(selectedRow, 0);
        String userType = (String) usersTableModel.getValueAt(selectedRow, 3);
        
        if ("Administrator".equals(userType)) {
            JOptionPane.showMessageDialog(this, "Cannot delete administrator accounts.");
            return;
        }
        
        int choice = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete user \"" + username + "\"?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            if (authService.deleteUser(username)) {
                JOptionPane.showMessageDialog(this, "User deleted successfully!");
                refreshUsersTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete user.");
            }
        }
    }
    
    private void showChangePasswordDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Change Password", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        JPasswordField currentPasswordField = new JPasswordField(20);
        JPasswordField newPasswordField = new JPasswordField(20);
        JPasswordField confirmPasswordField = new JPasswordField(20);
        
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Current Password:"), gbc);
        gbc.gridx = 1;
        dialog.add(currentPasswordField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("New Password:"), gbc);
        gbc.gridx = 1;
        dialog.add(newPasswordField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        dialog.add(confirmPasswordField, gbc);
        
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("Change");
        JButton cancelButton = new JButton("Cancel");
        
        okButton.addActionListener(e -> {
            String currentPassword = new String(currentPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            
            if (authService.changeCurrentUserPassword(currentPassword, newPassword, confirmPassword)) {
                JOptionPane.showMessageDialog(dialog, "Password changed successfully!");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, 
                    "Failed to change password. Please check your input and try again.");
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    // Data refresh methods
    public void refresh() {
        updateAdminInfo();
        refreshData();
    }
    
    private void updateAdminInfo() {
        Administrator admin = authService.getCurrentAdministrator();
        if (admin != null) {
            adminInfoLabel.setText(String.format("Administrator: %s (%s)", 
                admin.getUsername(), admin.getEmail()));
        }
    }
    
    private void refreshData() {
        updateAdminInfo();
        refreshSongsTable();
        refreshUsersTable();
        refreshStatistics();
    }
    
    private void refreshSongsTable() {
        songsTableModel.setRowCount(0);
        for (Song song : catalogService.getAllSongs()) {
            songsTableModel.addRow(new Object[]{
                song.getTitle(),
                song.getArtist(),
                song.getGenre(),
                song.getAverageRating() > 0 ? song.getAverageRating() : "Not rated",
                String.format("$%.2f", song.getPrice()),
                song.getPurchaseCount(),
                song.getPlaylistInclusionCount()
            });
        }
    }
    
    private void refreshUsersTable() {
        usersTableModel.setRowCount(0);
        Map<String, User> users = authService.getAllUsers();
        
        for (User user : users.values()) {
            if (user instanceof EndUser) {
                EndUser endUser = (EndUser) user;
                usersTableModel.addRow(new Object[]{
                    user.getUsername(),
                    endUser.getFullName(),
                    user.getEmail(),
                    "End User",
                    String.format("$%.2f", endUser.getBalance()),
                    endUser.getOwnedSongs().size(),
                    endUser.getPlaylists().size()
                });
            } else if (user instanceof Administrator) {
                usersTableModel.addRow(new Object[]{
                    user.getUsername(),
                    "N/A",
                    user.getEmail(),
                    "Administrator",
                    "N/A",
                    "N/A",
                    "N/A"
                });
            }
        }
    }
    
    private void refreshStatistics() {
        StringBuilder stats = new StringBuilder();
        
        stats.append("MUSIC STREAMING APPLICATION STATISTICS\n");
        stats.append("=".repeat(50)).append("\n\n");
        
        // User statistics
        stats.append("USER STATISTICS:\n");
        stats.append("-".repeat(20)).append("\n");
        stats.append("Total Users: ").append(authService.getTotalUsers()).append("\n");
        stats.append("End Users: ").append(authService.getEndUserCount()).append("\n");
        stats.append("Administrators: ").append(authService.getAdministratorCount()).append("\n\n");
        
        // Catalog statistics
        MusicCatalogService.CatalogStats catalogStats = catalogService.getStats();
        stats.append("CATALOG STATISTICS:\n");
        stats.append("-".repeat(20)).append("\n");
        stats.append("Total Songs: ").append(catalogStats.totalSongs).append("\n");
        stats.append("Total Artists: ").append(catalogStats.totalArtists).append("\n");
        stats.append("Total Genres: ").append(catalogStats.totalGenres).append("\n");
        stats.append("Total Albums: ").append(catalogStats.totalAlbums).append("\n");
        stats.append("Average Price: $").append(String.format("%.2f", catalogStats.averagePrice)).append("\n");
        stats.append("Total Purchases: ").append(catalogStats.totalPurchases).append("\n\n");
        
        // Top songs
        stats.append("TOP 5 HIGHEST RATED SONGS:\n");
        stats.append("-".repeat(30)).append("\n");
        List<Song> topRated = catalogService.getTop5ByRating();
        for (int i = 0; i < topRated.size(); i++) {
            Song song = topRated.get(i);
            stats.append(String.format("%d. %s - %s (Rating: %d)\n", 
                i + 1, song.getTitle(), song.getArtist(), song.getAverageRating()));
        }
        stats.append("\n");
        
        stats.append("TOP 5 MOST PURCHASED SONGS:\n");
        stats.append("-".repeat(30)).append("\n");
        List<Song> topPurchased = catalogService.getTop5ByPurchases();
        for (int i = 0; i < topPurchased.size(); i++) {
            Song song = topPurchased.get(i);
            stats.append(String.format("%d. %s - %s (Purchases: %d)\n", 
                i + 1, song.getTitle(), song.getArtist(), song.getPurchaseCount()));
        }
        stats.append("\n");
        
        stats.append("TOP 5 MOST ADDED TO PLAYLISTS:\n");
        stats.append("-".repeat(35)).append("\n");
        List<Song> topInPlaylists = catalogService.getTop5ByPlaylistInclusion();
        for (int i = 0; i < topInPlaylists.size(); i++) {
            Song song = topInPlaylists.get(i);
            stats.append(String.format("%d. %s - %s (In %d playlists)\n", 
                i + 1, song.getTitle(), song.getArtist(), song.getPlaylistInclusionCount()));
        }
        
        statsTextArea.setText(stats.toString());
        statsTextArea.setCaretPosition(0);
    }
}