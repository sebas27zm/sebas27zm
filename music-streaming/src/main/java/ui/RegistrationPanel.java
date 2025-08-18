package ui;

import services.AuthenticationService;
import models.EndUser;
import utils.Nationality;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Registration panel for new user account creation
 */
public class RegistrationPanel extends JPanel {
    private MainWindow mainWindow;
    private AuthenticationService authService;
    
    // GUI Components
    private JTextField fullNameField;
    private JTextField birthDateField;
    private JComboBox<String> nationalityComboBox;
    private JTextField idNumberField;
    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JButton backButton;
    private JLabel statusLabel;
    private JButton avatarButton;
    private String selectedAvatarPath;
    
    public RegistrationPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.authService = AuthenticationService.getInstance();
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        fullNameField = new JTextField(20);
        birthDateField = new JTextField(20);
        nationalityComboBox = new JComboBox<>(Nationality.getAllDisplayNames());
        idNumberField = new JTextField(20);
        emailField = new JTextField(20);
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        registerButton = new JButton("Create Account");
        backButton = new JButton("Back to Login");
        statusLabel = new JLabel(" ");
        avatarButton = new JButton("Choose Avatar (Optional)");
        
        // Set component properties
        Font fieldFont = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
        Dimension fieldSize = new Dimension(250, 30);
        
        fullNameField.setFont(fieldFont);
        fullNameField.setPreferredSize(fieldSize);
        
        birthDateField.setFont(fieldFont);
        birthDateField.setPreferredSize(fieldSize);
        birthDateField.setToolTipText("Format: YYYY-MM-DD (e.g., 1990-01-15)");
        
        nationalityComboBox.setFont(fieldFont);
        nationalityComboBox.setPreferredSize(fieldSize);
        
        idNumberField.setFont(fieldFont);
        idNumberField.setPreferredSize(fieldSize);
        
        emailField.setFont(fieldFont);
        emailField.setPreferredSize(fieldSize);
        
        usernameField.setFont(fieldFont);
        usernameField.setPreferredSize(fieldSize);
        
        passwordField.setFont(fieldFont);
        passwordField.setPreferredSize(fieldSize);
        
        confirmPasswordField.setFont(fieldFont);
        confirmPasswordField.setPreferredSize(fieldSize);
        
        registerButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        backButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        
        statusLabel.setForeground(Color.RED);
        
        // Set default nationality
        nationalityComboBox.setSelectedItem("United States");
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 248, 255)); // Light lavender background
        
        // Create main panel with scroll pane
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(getBackground());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Title
        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 25, 112)); // Navy blue
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 30, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);
        
        // Form fields
        int row = 1;
        
        // Full Name
        row = addFormField(mainPanel, "Full Name:", fullNameField, row);
        
        // Birth Date
        row = addFormField(mainPanel, "Birth Date:", birthDateField, row);
        
        // Add birth date help text
        JLabel birthDateHelp = new JLabel("(Format: YYYY-MM-DD, must be 18+ years old)");
        birthDateHelp.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
        birthDateHelp.setForeground(Color.GRAY);
        gbc.gridx = 1;
        gbc.gridy = row++;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(birthDateHelp, gbc);
        
        // Nationality
        row = addFormField(mainPanel, "Nationality:", nationalityComboBox, row);
        
        // ID Number
        row = addFormField(mainPanel, "ID Number:", idNumberField, row);
        
        // Avatar selection
        row = addFormField(mainPanel, "Avatar:", avatarButton, row);
        
        // Email
        row = addFormField(mainPanel, "Email:", emailField, row);
        
        // Username
        row = addFormField(mainPanel, "Username:", usernameField, row);
        
        // Password
        row = addFormField(mainPanel, "Password:", passwordField, row);
        
        // Add password requirements
        JLabel passwordHelp = new JLabel("<html>8-12 characters, at least 1 uppercase, 1 lowercase,<br>1 number, and 1 special character</html>");
        passwordHelp.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 11));
        passwordHelp.setForeground(Color.GRAY);
        gbc.gridx = 1;
        gbc.gridy = row++;
        gbc.insets = new Insets(0, 10, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(passwordHelp, gbc);
        
        // Confirm Password
        row = addFormField(mainPanel, "Confirm Password:", confirmPasswordField, row);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(getBackground());
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 10, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);
        
        // Status label
        gbc.gridy = row;
        gbc.insets = new Insets(10, 20, 20, 20);
        mainPanel.add(statusLabel, gbc);
        
        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private int addFormField(JPanel panel, String labelText, JComponent component, int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Label
        JLabel label = new JLabel(labelText);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 20, 5, 10);
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(label, gbc);
        
        // Component
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 20);
        panel.add(component, gbc);
        
        return row + 1;
    }
    
    private void setupEventHandlers() {
        // Register button action
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRegistration();
            }
        });
        
        // Back button action
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.showLogin();
            }
        });
        
        // Avatar button action
        avatarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectAvatar();
            }
        });
    }
    
    private void selectAvatar() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Avatar Image");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Image files", "jpg", "jpeg", "png", "gif", "bmp"));
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedAvatarPath = fileChooser.getSelectedFile().getAbsolutePath();
            avatarButton.setText("Avatar Selected ✓");
            avatarButton.setForeground(new Color(0, 128, 0));
        }
    }
    
    private void performRegistration() {
        // Clear status
        statusLabel.setText(" ");
        statusLabel.setForeground(Color.RED);
        
        // Get form data
        String fullName = fullNameField.getText().trim();
        String birthDateStr = birthDateField.getText().trim();
        String nationality = (String) nationalityComboBox.getSelectedItem();
        String idNumber = idNumberField.getText().trim();
        String email = emailField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        // Validate input
        if (!validateInput(fullName, birthDateStr, nationality, idNumber, 
                          email, username, password, confirmPassword)) {
            return;
        }
        
        // Parse birth date
        LocalDate birthDate;
        try {
            birthDate = LocalDate.parse(birthDateStr);
        } catch (DateTimeParseException e) {
            showError("Invalid birth date format. Please use YYYY-MM-DD.");
            birthDateField.requestFocus();
            return;
        }
        
        // Check username availability
        if (!authService.isUsernameAvailable(username)) {
            showError("Username is already taken. Please choose another.");
            usernameField.requestFocus();
            return;
        }
        
        // Check email availability
        if (!authService.isEmailAvailable(email)) {
            showError("Email is already registered. Please use another email.");
            emailField.requestFocus();
            return;
        }
        
        // Disable button during registration
        registerButton.setEnabled(false);
        registerButton.setText("Creating Account...");
        
        // Perform registration in background thread
        SwingWorker<Boolean, Void> registrationWorker = new SwingWorker<Boolean, Void>() {
            private EndUser newUser;
            private String errorMessage;
            
            @Override
            protected Boolean doInBackground() throws Exception {
                try {
                    // Simulate processing delay
                    Thread.sleep(1000);
                    
                    // Create new user
                    newUser = new EndUser(
                        fullName, birthDate, nationality, idNumber,
                        selectedAvatarPath, email, username, password, confirmPassword
                    );
                    
                    // Register user
                    return authService.registerEndUser(newUser);
                } catch (Exception e) {
                    errorMessage = e.getMessage();
                    return false;
                }
            }
            
            @Override
            protected void done() {
                try {
                    boolean success = get();
                    
                    if (success && newUser != null) {
                        // Auto-login the new user
                        authService.login(username, password);
                        mainWindow.onRegistrationSuccess(newUser);
                        showSuccess("Account created successfully! Welcome, " + fullName + "!");
                    } else {
                        String message = errorMessage != null ? errorMessage : "Registration failed. Please try again.";
                        showError(message);
                    }
                } catch (Exception e) {
                    showError("Registration error: " + e.getMessage());
                } finally {
                    // Re-enable button
                    registerButton.setEnabled(true);
                    registerButton.setText("Create Account");
                }
            }
        };
        
        registrationWorker.execute();
    }
    
    private boolean validateInput(String fullName, String birthDateStr, String nationality,
                                String idNumber, String email, String username,
                                String password, String confirmPassword) {
        
        if (fullName.isEmpty()) {
            showError("Please enter your full name.");
            fullNameField.requestFocus();
            return false;
        }
        
        if (birthDateStr.isEmpty()) {
            showError("Please enter your birth date.");
            birthDateField.requestFocus();
            return false;
        }
        
        if (nationality == null || nationality.isEmpty()) {
            showError("Please select your nationality.");
            nationalityComboBox.requestFocus();
            return false;
        }
        
        if (idNumber.isEmpty()) {
            showError("Please enter your ID number.");
            idNumberField.requestFocus();
            return false;
        }
        
        if (email.isEmpty()) {
            showError("Please enter your email address.");
            emailField.requestFocus();
            return false;
        }
        
        if (!email.contains("@") || !email.contains(".")) {
            showError("Please enter a valid email address.");
            emailField.requestFocus();
            return false;
        }
        
        if (username.isEmpty()) {
            showError("Please enter a username.");
            usernameField.requestFocus();
            return false;
        }
        
        if (username.length() < 3) {
            showError("Username must be at least 3 characters long.");
            usernameField.requestFocus();
            return false;
        }
        
        if (password.isEmpty()) {
            showError("Please enter a password.");
            passwordField.requestFocus();
            return false;
        }
        
        if (confirmPassword.isEmpty()) {
            showError("Please confirm your password.");
            confirmPasswordField.requestFocus();
            return false;
        }
        
        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match.");
            confirmPasswordField.requestFocus();
            return false;
        }
        
        // Validate password strength
        try {
            models.User.validatePassword(password);
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
            passwordField.requestFocus();
            return false;
        }
        
        return true;
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
        fullNameField.setText("");
        birthDateField.setText("");
        nationalityComboBox.setSelectedItem("United States");
        idNumberField.setText("");
        emailField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        statusLabel.setText(" ");
        avatarButton.setText("Choose Avatar (Optional)");
        avatarButton.setForeground(null);
        selectedAvatarPath = null;
        fullNameField.requestFocus();
    }
}