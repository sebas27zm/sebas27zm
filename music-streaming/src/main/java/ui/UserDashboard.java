package ui;

import models.*;
import services.*;
import utils.PlaybackQueue;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Main dashboard for end users
 * Provides access to music catalog, playlists, playback controls, and user account management
 */
public class UserDashboard extends JPanel {
    private MainWindow mainWindow;
    private EndUser currentUser;
    
    // Services
    private MusicCatalogService catalogService;
    private PlaylistService playlistService;
    private AudioPlayerService audioService;
    
    // GUI Components
    private JTabbedPane tabbedPane;
    private JLabel userInfoLabel;
    private JLabel balanceLabel;
    
    // Catalog tab components
    private JTable catalogTable;
    private DefaultTableModel catalogTableModel;
    private JTextField searchField;
    private JComboBox<String> searchTypeComboBox;
    private JButton searchButton;
    private JButton purchaseButton;
    private JButton previewButton;
    private JButton playButton;
    
    // Playlist tab components
    private JList<Playlist> playlistList;
    private DefaultListModel<Playlist> playlistListModel;
    private JTable playlistSongsTable;
    private DefaultTableModel playlistSongsTableModel;
    private JButton createPlaylistButton;
    private JButton deletePlaylistButton;
    private JButton addToPlaylistButton;
    private JButton removeFromPlaylistButton;
    private JButton playPlaylistButton;
    
    // My Music tab components
    private JTable myMusicTable;
    private DefaultTableModel myMusicTableModel;
    private JButton rateButton;
    private JButton addToQueueButton;
    
    // Top 5 tab components
    private JTable topRatedTable;
    private JTable topPurchasedTable;
    private JTable topPlaylistTable;
    
    // Playback control components
    private JPanel playbackControlPanel;
    private JButton playPauseButton;
    private JButton stopButton;
    private JButton previousButton;
    private JButton nextButton;
    private JSlider volumeSlider;
    private JLabel currentSongLabel;
    private JProgressBar progressBar;
    
    public UserDashboard(MainWindow mainWindow, EndUser user) {
        this.mainWindow = mainWindow;
        this.currentUser = user;
        
        initializeServices();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        refreshData();
    }
    
    private void initializeServices() {
        this.catalogService = MusicCatalogService.getInstance();
        this.playlistService = PlaylistService.getInstance();
        this.audioService = AudioPlayerService.getInstance();
    }
    
    private void initializeComponents() {
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // User info components
        userInfoLabel = new JLabel();
        balanceLabel = new JLabel();
        
        // Initialize all tab components
        initializeCatalogTab();
        initializePlaylistTab();
        initializeMyMusicTab();
        initializeTop5Tab();
        initializePlaybackControls();
    }
    
    private void initializeCatalogTab() {
        // Search components
        searchField = new JTextField(20);
        searchTypeComboBox = new JComboBox<>(new String[]{"Title", "Artist", "Genre"});
        searchButton = new JButton("Search");
        
        // Catalog table
        catalogTableModel = new DefaultTableModel(
            new Object[]{"Title", "Artist", "Genre", "Rating", "Price", "Album"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        catalogTable = new JTable(catalogTableModel);
        catalogTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Action buttons
        purchaseButton = new JButton("Purchase Song");
        previewButton = new JButton("Preview (30s)");
        playButton = new JButton("Play Song");
    }
    
    private void initializePlaylistTab() {
        // Playlist list
        playlistListModel = new DefaultListModel<>();
        playlistList = new JList<>(playlistListModel);
        playlistList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Playlist songs table
        playlistSongsTableModel = new DefaultTableModel(
            new Object[]{"Title", "Artist", "Genre", "Rating"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        playlistSongsTable = new JTable(playlistSongsTableModel);
        
        // Playlist action buttons
        createPlaylistButton = new JButton("Create Playlist");
        deletePlaylistButton = new JButton("Delete Playlist");
        addToPlaylistButton = new JButton("Add to Playlist");
        removeFromPlaylistButton = new JButton("Remove from Playlist");
        playPlaylistButton = new JButton("Play Playlist");
    }
    
    private void initializeMyMusicTab() {
        // My music table
        myMusicTableModel = new DefaultTableModel(
            new Object[]{"Title", "Artist", "Genre", "My Rating", "Avg Rating", "Price Paid"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        myMusicTable = new JTable(myMusicTableModel);
        
        // Action buttons
        rateButton = new JButton("Rate Song");
        addToQueueButton = new JButton("Add to Queue");
    }
    
    private void initializeTop5Tab() {
        // Create table models for top 5 lists
        DefaultTableModel topRatedModel = new DefaultTableModel(
            new Object[]{"Rank", "Title", "Artist", "Rating"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        topRatedTable = new JTable(topRatedModel);
        
        DefaultTableModel topPurchasedModel = new DefaultTableModel(
            new Object[]{"Rank", "Title", "Artist", "Purchases"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        topPurchasedTable = new JTable(topPurchasedModel);
        
        DefaultTableModel topPlaylistModel = new DefaultTableModel(
            new Object[]{"Rank", "Title", "Artist", "In Playlists"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        topPlaylistTable = new JTable(topPlaylistModel);
    }
    
    private void initializePlaybackControls() {
        playPauseButton = new JButton("▶");
        stopButton = new JButton("⏹");
        previousButton = new JButton("⏮");
        nextButton = new JButton("⏭");
        volumeSlider = new JSlider(0, 100, 50);
        currentSongLabel = new JLabel("No song playing");
        progressBar = new JProgressBar();
        
        // Set button properties
        Dimension buttonSize = new Dimension(50, 30);
        playPauseButton.setPreferredSize(buttonSize);
        stopButton.setPreferredSize(buttonSize);
        previousButton.setPreferredSize(buttonSize);
        nextButton.setPreferredSize(buttonSize);
        
        volumeSlider.setPreferredSize(new Dimension(100, 30));
        progressBar.setStringPainted(true);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Create tab panels
        JPanel catalogPanel = createCatalogPanel();
        JPanel playlistPanel = createPlaylistPanel();
        JPanel myMusicPanel = createMyMusicPanel();
        JPanel top5Panel = createTop5Panel();
        
        // Add tabs
        tabbedPane.addTab("Music Catalog", catalogPanel);
        tabbedPane.addTab("My Playlists", playlistPanel);
        tabbedPane.addTab("My Music", myMusicPanel);
        tabbedPane.addTab("Top 5 Charts", top5Panel);
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Create playback control panel
        playbackControlPanel = createPlaybackControlPanel();
        add(playbackControlPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(240, 248, 255));
        
        // User info
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userInfoPanel.setBackground(panel.getBackground());
        userInfoPanel.add(userInfoLabel);
        userInfoPanel.add(Box.createHorizontalStrut(20));
        userInfoPanel.add(balanceLabel);
        
        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setBackground(panel.getBackground());
        
        JButton addBalanceButton = new JButton("Add Balance");
        JButton changePasswordButton = new JButton("Change Password");
        JButton logoutButton = new JButton("Logout");
        
        actionPanel.add(addBalanceButton);
        actionPanel.add(changePasswordButton);
        actionPanel.add(logoutButton);
        
        panel.add(userInfoPanel, BorderLayout.WEST);
        panel.add(actionPanel, BorderLayout.EAST);
        
        // Add event handlers for header buttons
        addBalanceButton.addActionListener(e -> showAddBalanceDialog());
        changePasswordButton.addActionListener(e -> showChangePasswordDialog());
        logoutButton.addActionListener(e -> mainWindow.onLogout());
        
        return panel;
    }
    
    private JPanel createCatalogPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchTypeComboBox);
        searchPanel.add(searchButton);
        
        // Table panel
        JScrollPane tableScrollPane = new JScrollPane(catalogTable);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(previewButton);
        buttonPanel.add(purchaseButton);
        buttonPanel.add(playButton);
        
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createPlaylistPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Split pane for playlists and songs
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        // Left side - playlist list
        JPanel playlistPanel = new JPanel(new BorderLayout());
        playlistPanel.setBorder(BorderFactory.createTitledBorder("My Playlists"));
        playlistPanel.add(new JScrollPane(playlistList), BorderLayout.CENTER);
        
        JPanel playlistButtonPanel = new JPanel(new FlowLayout());
        playlistButtonPanel.add(createPlaylistButton);
        playlistButtonPanel.add(deletePlaylistButton);
        playlistPanel.add(playlistButtonPanel, BorderLayout.SOUTH);
        
        // Right side - playlist songs
        JPanel songsPanel = new JPanel(new BorderLayout());
        songsPanel.setBorder(BorderFactory.createTitledBorder("Songs in Playlist"));
        songsPanel.add(new JScrollPane(playlistSongsTable), BorderLayout.CENTER);
        
        JPanel songButtonPanel = new JPanel(new FlowLayout());
        songButtonPanel.add(addToPlaylistButton);
        songButtonPanel.add(removeFromPlaylistButton);
        songButtonPanel.add(playPlaylistButton);
        songsPanel.add(songButtonPanel, BorderLayout.SOUTH);
        
        splitPane.setLeftComponent(playlistPanel);
        splitPane.setRightComponent(songsPanel);
        splitPane.setDividerLocation(300);
        
        panel.add(splitPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createMyMusicPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Table
        JScrollPane tableScrollPane = new JScrollPane(myMusicTable);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(rateButton);
        buttonPanel.add(addToQueueButton);
        
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createTop5Panel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top rated
        JPanel topRatedPanel = new JPanel(new BorderLayout());
        topRatedPanel.setBorder(BorderFactory.createTitledBorder("Top 5 Highest Rated Songs"));
        topRatedPanel.add(new JScrollPane(topRatedTable), BorderLayout.CENTER);
        
        // Top purchased
        JPanel topPurchasedPanel = new JPanel(new BorderLayout());
        topPurchasedPanel.setBorder(BorderFactory.createTitledBorder("Top 5 Most Purchased Songs"));
        topPurchasedPanel.add(new JScrollPane(topPurchasedTable), BorderLayout.CENTER);
        
        // Top in playlists
        JPanel topPlaylistPanel = new JPanel(new BorderLayout());
        topPlaylistPanel.setBorder(BorderFactory.createTitledBorder("Top 5 Most Added to Playlists"));
        topPlaylistPanel.add(new JScrollPane(topPlaylistTable), BorderLayout.CENTER);
        
        panel.add(topRatedPanel);
        panel.add(topPurchasedPanel);
        panel.add(topPlaylistPanel);
        
        return panel;
    }
    
    private JPanel createPlaybackControlPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(245, 245, 245));
        
        // Control buttons
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlPanel.setBackground(panel.getBackground());
        controlPanel.add(previousButton);
        controlPanel.add(playPauseButton);
        controlPanel.add(stopButton);
        controlPanel.add(nextButton);
        
        // Volume control
        JPanel volumePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        volumePanel.setBackground(panel.getBackground());
        volumePanel.add(new JLabel("Volume:"));
        volumePanel.add(volumeSlider);
        
        // Song info and progress
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(panel.getBackground());
        infoPanel.add(currentSongLabel, BorderLayout.NORTH);
        infoPanel.add(progressBar, BorderLayout.SOUTH);
        
        panel.add(controlPanel, BorderLayout.WEST);
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(volumePanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private void setupEventHandlers() {
        // Catalog tab events
        searchButton.addActionListener(e -> performSearch());
        purchaseButton.addActionListener(e -> purchaseSelectedSong());
        previewButton.addActionListener(e -> previewSelectedSong());
        playButton.addActionListener(e -> playSelectedSong());
        
        // Playlist tab events
        playlistList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                refreshPlaylistSongs();
            }
        });
        createPlaylistButton.addActionListener(e -> createNewPlaylist());
        deletePlaylistButton.addActionListener(e -> deleteSelectedPlaylist());
        playPlaylistButton.addActionListener(e -> playSelectedPlaylist());
        
        // My music tab events
        rateButton.addActionListener(e -> rateSelectedSong());
        addToQueueButton.addActionListener(e -> addSelectedSongToQueue());
        
        // Playback control events
        playPauseButton.addActionListener(e -> togglePlayback());
        stopButton.addActionListener(e -> stopPlayback());
        previousButton.addActionListener(e -> playPrevious());
        nextButton.addActionListener(e -> playNext());
        volumeSlider.addChangeListener(e -> adjustVolume());
        
        // Audio service listener
        audioService.addPlaybackListener(new AudioPlayerService.PlaybackListener() {
            @Override
            public void onPlaybackStarted(Song song) {
                SwingUtilities.invokeLater(() -> {
                    currentSongLabel.setText("♪ " + song.getTitle() + " - " + song.getArtist());
                    playPauseButton.setText("⏸");
                });
            }
            
            @Override
            public void onPlaybackPaused(Song song) {
                SwingUtilities.invokeLater(() -> {
                    playPauseButton.setText("▶");
                });
            }
            
            @Override
            public void onPlaybackStopped(Song song) {
                SwingUtilities.invokeLater(() -> {
                    currentSongLabel.setText("No song playing");
                    playPauseButton.setText("▶");
                    progressBar.setValue(0);
                });
            }
        });
    }
    
    // Event handler methods
    private void performSearch() {
        String searchTerm = searchField.getText().trim();
        String searchType = (String) searchTypeComboBox.getSelectedItem();
        
        if (searchTerm.isEmpty()) {
            refreshCatalogTable();
            return;
        }
        
        List<Song> results;
        switch (searchType) {
            case "Artist":
                results = catalogService.searchByArtist(searchTerm);
                break;
            case "Genre":
                results = catalogService.searchByGenre(searchTerm);
                break;
            default:
                results = catalogService.searchByName(searchTerm);
                break;
        }
        
        updateCatalogTable(results);
    }
    
    private void purchaseSelectedSong() {
        int selectedRow = catalogTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a song to purchase.");
            return;
        }
        
        String title = (String) catalogTableModel.getValueAt(selectedRow, 0);
        String artist = (String) catalogTableModel.getValueAt(selectedRow, 1);
        Song song = catalogService.getSong(title, artist);
        
        if (song == null) {
            JOptionPane.showMessageDialog(this, "Song not found.");
            return;
        }
        
        if (currentUser.ownsSong(song)) {
            JOptionPane.showMessageDialog(this, "You already own this song.");
            return;
        }
        
        if (currentUser.getBalance() < song.getPrice()) {
            JOptionPane.showMessageDialog(this, 
                String.format("Insufficient balance. You need $%.2f but only have $%.2f.", 
                            song.getPrice(), currentUser.getBalance()));
            return;
        }
        
        int choice = JOptionPane.showConfirmDialog(this,
            String.format("Purchase \"%s\" by %s for $%.2f?", 
                        song.getTitle(), song.getArtist(), song.getPrice()),
            "Confirm Purchase",
            JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            if (currentUser.purchaseSong(song)) {
                JOptionPane.showMessageDialog(this, "Song purchased successfully!");
                updateUserInfo();
                refreshMyMusicTable();
            } else {
                JOptionPane.showMessageDialog(this, "Purchase failed. Please try again.");
            }
        }
    }
    
    private void previewSelectedSong() {
        int selectedRow = catalogTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a song to preview.");
            return;
        }
        
        String title = (String) catalogTableModel.getValueAt(selectedRow, 0);
        String artist = (String) catalogTableModel.getValueAt(selectedRow, 1);
        Song song = catalogService.getSong(title, artist);
        
        if (song != null) {
            audioService.playPreview(song);
        }
    }
    
    private void playSelectedSong() {
        int selectedRow = catalogTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a song to play.");
            return;
        }
        
        String title = (String) catalogTableModel.getValueAt(selectedRow, 0);
        String artist = (String) catalogTableModel.getValueAt(selectedRow, 1);
        Song song = catalogService.getSong(title, artist);
        
        if (song != null) {
            if (!currentUser.ownsSong(song)) {
                JOptionPane.showMessageDialog(this, 
                    "You must purchase this song before playing it in full. You can preview it for 30 seconds.");
                return;
            }
            audioService.playSong(song);
        }
    }
    
    private void createNewPlaylist() {
        String name = JOptionPane.showInputDialog(this, "Enter playlist name:");
        if (name != null && !name.trim().isEmpty()) {
            Playlist playlist = playlistService.createPlaylist(name.trim(), currentUser);
            if (playlist != null) {
                refreshPlaylistList();
                JOptionPane.showMessageDialog(this, "Playlist created successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create playlist. Name may already exist.");
            }
        }
    }
    
    private void deleteSelectedPlaylist() {
        Playlist selectedPlaylist = playlistList.getSelectedValue();
        if (selectedPlaylist == null) {
            JOptionPane.showMessageDialog(this, "Please select a playlist to delete.");
            return;
        }
        
        int choice = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete the playlist \"" + selectedPlaylist.getName() + "\"?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            if (playlistService.deletePlaylist(selectedPlaylist, currentUser)) {
                refreshPlaylistList();
                refreshPlaylistSongs();
                JOptionPane.showMessageDialog(this, "Playlist deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete playlist.");
            }
        }
    }
    
    private void playSelectedPlaylist() {
        Playlist selectedPlaylist = playlistList.getSelectedValue();
        if (selectedPlaylist == null) {
            JOptionPane.showMessageDialog(this, "Please select a playlist to play.");
            return;
        }
        
        if (selectedPlaylist.isEmpty()) {
            JOptionPane.showMessageDialog(this, "This playlist is empty.");
            return;
        }
        
        audioService.playPlaylist(selectedPlaylist);
    }
    
    private void rateSelectedSong() {
        int selectedRow = myMusicTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a song to rate.");
            return;
        }
        
        String title = (String) myMusicTableModel.getValueAt(selectedRow, 0);
        String artist = (String) myMusicTableModel.getValueAt(selectedRow, 1);
        Song song = catalogService.getSong(title, artist);
        
        if (song != null) {
            String ratingStr = JOptionPane.showInputDialog(this, 
                "Rate this song (1-10):", 
                song.hasRating(currentUser.getUsername()) ? 
                song.getUserRating(currentUser.getUsername()).toString() : "");
            
            if (ratingStr != null && !ratingStr.trim().isEmpty()) {
                try {
                    int rating = Integer.parseInt(ratingStr.trim());
                    if (currentUser.rateSong(song, rating)) {
                        JOptionPane.showMessageDialog(this, "Song rated successfully!");
                        refreshMyMusicTable();
                        refreshCatalogTable();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to rate song. Rating must be between 1 and 10.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number between 1 and 10.");
                }
            }
        }
    }
    
    private void addSelectedSongToQueue() {
        int selectedRow = myMusicTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a song to add to queue.");
            return;
        }
        
        String title = (String) myMusicTableModel.getValueAt(selectedRow, 0);
        String artist = (String) myMusicTableModel.getValueAt(selectedRow, 1);
        Song song = catalogService.getSong(title, artist);
        
        if (song != null) {
            audioService.addToQueue(song);
            JOptionPane.showMessageDialog(this, "Song added to playback queue!");
        }
    }
    
    // Playback control methods
    private void togglePlayback() {
        if (audioService.isPlaying()) {
            audioService.pause();
        } else {
            audioService.play();
        }
    }
    
    private void stopPlayback() {
        audioService.stop();
    }
    
    private void playPrevious() {
        audioService.playPrevious();
    }
    
    private void playNext() {
        audioService.playNext();
    }
    
    private void adjustVolume() {
        float volume = volumeSlider.getValue() / 100.0f;
        audioService.setVolume(volume);
    }
    
    // Dialog methods
    private void showAddBalanceDialog() {
        String amountStr = JOptionPane.showInputDialog(this, "Enter amount to add to your balance:");
        if (amountStr != null && !amountStr.trim().isEmpty()) {
            try {
                double amount = Double.parseDouble(amountStr.trim());
                if (amount > 0) {
                    if (currentUser.addBalance(amount)) {
                        updateUserInfo();
                        JOptionPane.showMessageDialog(this, 
                            String.format("$%.2f added to your balance successfully!", amount));
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to add balance.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a positive amount.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
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
            
            if (currentUser.changePassword(currentPassword, newPassword, confirmPassword)) {
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
    public void updateUser(EndUser user) {
        this.currentUser = user;
        updateUserInfo();
        refreshData();
    }
    
    private void updateUserInfo() {
        userInfoLabel.setText(String.format("Welcome, %s (%s)", 
            currentUser.getFullName(), currentUser.getUsername()));
        balanceLabel.setText(String.format("Balance: $%.2f", currentUser.getBalance()));
    }
    
    private void refreshData() {
        updateUserInfo();
        refreshCatalogTable();
        refreshPlaylistList();
        refreshMyMusicTable();
        refreshTop5Tables();
    }
    
    private void refreshCatalogTable() {
        updateCatalogTable(catalogService.getAllSongs());
    }
    
    private void updateCatalogTable(List<Song> songs) {
        catalogTableModel.setRowCount(0);
        for (Song song : songs) {
            catalogTableModel.addRow(new Object[]{
                song.getTitle(),
                song.getArtist(),
                song.getGenre(),
                song.getAverageRating() > 0 ? song.getAverageRating() : "Not rated",
                String.format("$%.2f", song.getPrice()),
                song.getAlbum() != null ? song.getAlbum() : "Single"
            });
        }
    }
    
    private void refreshPlaylistList() {
        playlistListModel.clear();
        for (Playlist playlist : currentUser.getPlaylists()) {
            playlistListModel.addElement(playlist);
        }
    }
    
    private void refreshPlaylistSongs() {
        playlistSongsTableModel.setRowCount(0);
        Playlist selectedPlaylist = playlistList.getSelectedValue();
        if (selectedPlaylist != null) {
            for (Song song : selectedPlaylist.getSongs()) {
                playlistSongsTableModel.addRow(new Object[]{
                    song.getTitle(),
                    song.getArtist(),
                    song.getGenre(),
                    song.getAverageRating() > 0 ? song.getAverageRating() : "Not rated"
                });
            }
        }
    }
    
    private void refreshMyMusicTable() {
        myMusicTableModel.setRowCount(0);
        for (Song song : currentUser.getOwnedSongs()) {
            Integer userRating = song.getUserRating(currentUser.getUsername());
            myMusicTableModel.addRow(new Object[]{
                song.getTitle(),
                song.getArtist(),
                song.getGenre(),
                userRating != null ? userRating : "Not rated",
                song.getAverageRating() > 0 ? song.getAverageRating() : "Not rated",
                String.format("$%.2f", song.getPrice())
            });
        }
    }
    
    private void refreshTop5Tables() {
        // Refresh top rated
        DefaultTableModel topRatedModel = (DefaultTableModel) topRatedTable.getModel();
        topRatedModel.setRowCount(0);
        List<Song> topRated = catalogService.getTop5ByRating();
        for (int i = 0; i < topRated.size(); i++) {
            Song song = topRated.get(i);
            topRatedModel.addRow(new Object[]{
                i + 1,
                song.getTitle(),
                song.getArtist(),
                song.getAverageRating()
            });
        }
        
        // Refresh top purchased
        DefaultTableModel topPurchasedModel = (DefaultTableModel) topPurchasedTable.getModel();
        topPurchasedModel.setRowCount(0);
        List<Song> topPurchased = catalogService.getTop5ByPurchases();
        for (int i = 0; i < topPurchased.size(); i++) {
            Song song = topPurchased.get(i);
            topPurchasedModel.addRow(new Object[]{
                i + 1,
                song.getTitle(),
                song.getArtist(),
                song.getPurchaseCount()
            });
        }
        
        // Refresh top in playlists
        DefaultTableModel topPlaylistModel = (DefaultTableModel) topPlaylistTable.getModel();
        topPlaylistModel.setRowCount(0);
        List<Song> topInPlaylists = catalogService.getTop5ByPlaylistInclusion();
        for (int i = 0; i < topInPlaylists.size(); i++) {
            Song song = topInPlaylists.get(i);
            topPlaylistModel.addRow(new Object[]{
                i + 1,
                song.getTitle(),
                song.getArtist(),
                song.getPlaylistInclusionCount()
            });
        }
    }
}