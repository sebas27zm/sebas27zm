package ui;

import services.AuthenticationService;
import models.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Login panel for user authentication
 */
public class LoginPanel extends JPanel {
    private MainWindow mainWindow;
    private AuthenticationService authService;
    
    // GUI Components
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel statusLabel;
    
    public LoginPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.authService = AuthenticationService.getInstance();
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        registerButton = new JButton("Create Account");
        statusLabel = new JLabel(" ");
        
        // Set component properties
        usernameField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        passwordField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        loginButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        registerButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        statusLabel.setForeground(Color.RED);
        
        // Set preferred sizes
        Dimension fieldSize = new Dimension(250, 30);
        usernameField.setPreferredSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);
        
        Dimension buttonSize = new Dimension(120, 35);
        loginButton.setPreferredSize(buttonSize);
        registerButton.setPreferredSize(buttonSize);
        
        // Set default button
        getRootPane().setDefaultButton(loginButton);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255)); // Light blue background
        
        // Create main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(getBackground());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Title
        JLabel titleLabel = new JLabel("Music Streaming Application");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        titleLabel.setForeground(new Color(25, 25, 112)); // Navy blue
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 40, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Please login to continue");
        subtitleLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.GRAY);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 30, 20);
        mainPanel.add(subtitleLabel, gbc);
        
        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 20, 5, 10);
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(usernameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 20);
        mainPanel.add(usernameField, gbc);
        
        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 20, 5, 10);
        mainPanel.add(passwordLabel, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 20);
        mainPanel.add(passwordField, gbc);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(getBackground());
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 10, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);
        
        // Status label
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 20, 20, 20);
        mainPanel.add(statusLabel, gbc);
        
        // Add main panel to center
        add(mainPanel, BorderLayout.CENTER);
        
        // Add welcome message at bottom
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(getBackground());
        JLabel welcomeLabel = new JLabel("Welcome! Default admin: username='admin', password='Admin123!'");
        welcomeLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
        welcomeLabel.setForeground(Color.GRAY);
        bottomPanel.add(welcomeLabel);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        // Login button action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
        
        // Register button action
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.showRegistration();
            }
        });
        
        // Enter key on password field
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        });
        
        // Enter key on username field
        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    passwordField.requestFocus();
                }
            }
        });
    }
    
    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        // Clear status
        statusLabel.setText(" ");
        
        // Validate input
        if (username.isEmpty()) {
            showError("Please enter a username.");
            usernameField.requestFocus();
            return;
        }
        
        if (password.isEmpty()) {
            showError("Please enter a password.");
            passwordField.requestFocus();
            return;
        }
        
        // Disable button during login attempt
        loginButton.setEnabled(false);
        loginButton.setText("Logging in...");
        
        // Perform login in background thread
        SwingWorker<Boolean, Void> loginWorker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                // Simulate network delay
                Thread.sleep(500);
                return authService.login(username, password);
            }
            
            @Override
            protected void done() {
                try {
                    boolean success = get();
                    
                    if (success) {
                        User user = authService.getCurrentUser();
                        mainWindow.onLoginSuccess(user);
                        showSuccess("Login successful! Welcome, " + user.getUsername());
                    } else {
                        showError("Invalid username or password.");
                        passwordField.selectAll();
                        passwordField.requestFocus();
                    }
                } catch (Exception e) {
                    showError("Login error: " + e.getMessage());
                } finally {
                    // Re-enable button
                    loginButton.setEnabled(true);
                    loginButton.setText("Login");
                }
            }
        };
        
        loginWorker.execute();
    }
    
    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(Color.RED);
    }
    
    private void showSuccess(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(new Color(0, 128, 0)); // Green
    }
    
    /**
     * Clear all input fields
     */
    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        statusLabel.setText(" ");
        usernameField.requestFocus();
    }
    
    /**
     * Set focus to username field
     */
    public void focusUsername() {
        usernameField.requestFocus();
    }
}