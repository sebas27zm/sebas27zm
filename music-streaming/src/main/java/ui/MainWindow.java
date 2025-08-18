package ui;

import services.*;
import models.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Main application window that serves as the entry point for the GUI
 * Handles login, registration, and navigation to different application sections
 */
public class MainWindow extends JFrame {
    private AuthenticationService authService;
    private DataPersistenceService dataService;
    
    // GUI Components
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private LoginPanel loginPanel;
    private RegistrationPanel registrationPanel;
    private UserDashboard userDashboard;
    private AdminDashboard adminDashboard;
    
    // Card names for navigation
    private static final String LOGIN_CARD = "LOGIN";
    private static final String REGISTER_CARD = "REGISTER";
    private static final String USER_DASHBOARD_CARD = "USER_DASHBOARD";
    private static final String ADMIN_DASHBOARD_CARD = "ADMIN_DASHBOARD";
    
    public MainWindow() {
        initializeServices();
        initializeComponents();
        setupWindow();
        setupEventHandlers();
    }
    
    private void initializeServices() {
        this.authService = AuthenticationService.getInstance();
        this.dataService = DataPersistenceService.getInstance();
    }
    
    private void initializeComponents() {
        // Setup card layout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Create panels
        loginPanel = new LoginPanel(this);
        registrationPanel = new RegistrationPanel(this);
        
        // Add panels to card layout
        mainPanel.add(loginPanel, LOGIN_CARD);
        mainPanel.add(registrationPanel, REGISTER_CARD);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Show login panel initially
        cardLayout.show(mainPanel, LOGIN_CARD);
    }
    
    private void setupWindow() {
        setTitle("Music Streaming Application");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600));
        
        // Set application icon (if available)
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/resources/images/app-icon.png"));
            setIconImage(icon.getImage());
        } catch (Exception e) {
            // Icon not found, use default
        }
    }
    
    private void setupEventHandlers() {
        // Handle window closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleApplicationExit();
            }
        });
    }
    
    /**
     * Handle user login success
     */
    public void onLoginSuccess(User user) {
        if (user instanceof Administrator) {
            showAdminDashboard();
        } else if (user instanceof EndUser) {
            showUserDashboard((EndUser) user);
        }
    }
    
    /**
     * Handle user registration success
     */
    public void onRegistrationSuccess(EndUser user) {
        // Auto-login after successful registration
        showUserDashboard(user);
    }
    
    /**
     * Show login panel
     */
    public void showLogin() {
        cardLayout.show(mainPanel, LOGIN_CARD);
        loginPanel.clearFields();
    }
    
    /**
     * Show registration panel
     */
    public void showRegistration() {
        cardLayout.show(mainPanel, REGISTER_CARD);
        registrationPanel.clearFields();
    }
    
    /**
     * Show user dashboard
     */
    private void showUserDashboard(EndUser user) {
        if (userDashboard == null) {
            userDashboard = new UserDashboard(this, user);
            mainPanel.add(userDashboard, USER_DASHBOARD_CARD);
        } else {
            userDashboard.updateUser(user);
        }
        cardLayout.show(mainPanel, USER_DASHBOARD_CARD);
    }
    
    /**
     * Show admin dashboard
     */
    private void showAdminDashboard() {
        if (adminDashboard == null) {
            adminDashboard = new AdminDashboard(this);
            mainPanel.add(adminDashboard, ADMIN_DASHBOARD_CARD);
        } else {
            adminDashboard.refresh();
        }
        cardLayout.show(mainPanel, ADMIN_DASHBOARD_CARD);
    }
    
    /**
     * Handle user logout
     */
    public void onLogout() {
        authService.logout();
        
        // Dispose of dashboards to free memory
        if (userDashboard != null) {
            mainPanel.remove(userDashboard);
            userDashboard = null;
        }
        if (adminDashboard != null) {
            mainPanel.remove(adminDashboard);
            adminDashboard = null;
        }
        
        // Show login screen
        showLogin();
        
        // Show logout message
        JOptionPane.showMessageDialog(this, 
            "You have been logged out successfully.", 
            "Logout", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Handle application exit
     */
    private void handleApplicationExit() {
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to exit the application?",
            "Exit Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            // Save data before exit
            showProgressDialog("Saving data...", () -> {
                dataService.saveAllData();
            });
            
            System.exit(0);
        }
    }
    
    /**
     * Show a progress dialog while performing an operation
     */
    private void showProgressDialog(String message, Runnable operation) {
        JDialog progressDialog = new JDialog(this, "Please wait...", true);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel(message), BorderLayout.CENTER);
        panel.add(progressBar, BorderLayout.SOUTH);
        
        progressDialog.add(panel);
        progressDialog.setSize(300, 120);
        progressDialog.setLocationRelativeTo(this);
        progressDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        
        // Run operation in background thread
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                operation.run();
                return null;
            }
            
            @Override
            protected void done() {
                progressDialog.dispose();
            }
        };
        
        worker.execute();
        progressDialog.setVisible(true);
    }
    
    /**
     * Show error message dialog
     */
    public void showErrorMessage(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Show success message dialog
     */
    public void showSuccessMessage(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Show warning message dialog
     */
    public void showWarningMessage(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Get the current window for dialog positioning
     */
    public Window getCurrentWindow() {
        return this;
    }
}