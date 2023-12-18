import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener {
    private JButton loginButton;
    private JButton backButton; // Add back button
    private JTextField usernameField;
    private JPasswordField passwordField;
    private InitialFrame initialFrame;

    public LoginFrame(InitialFrame initialFrame) {
        super("Login");

        this.initialFrame = initialFrame;

        // Create components
        loginButton = new JButton("Login");
        backButton = new JButton("Back"); // Initialize the back button
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        // Set layouts
        setLayout(new GridLayout(5, 2, 10, 10)); // Increase rows for the back button

        // Add components to the frame
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel()); // Empty label as a placeholder
        add(loginButton);
        add(new JLabel()); // Empty label for spacing
        add(backButton); // Add the back button

        // Add action listeners
        loginButton.addActionListener(this);
        backButton.addActionListener(this); // Add action listener for the back button

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200); // Increase height to accommodate the back button
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            loginUser();
        } else if (e.getSource() == backButton) {
            goBackToInitialFrame();
        }
    }

    private void loginUser() {
        // Get username and password
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Check if the user exists
        User user = getRegisteredUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            openAddProductFrame(user);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goBackToInitialFrame() {
        initialFrame.setVisible(true);
        dispose(); // Close the LoginFrame after going back to InitialFrame
    }

    private User getRegisteredUserByUsername(String username) {
        for (User user : initialFrame.getRegisteredUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null; // Return null if the user is not found
    }

    private void openAddProductFrame(User loggedInUser) {
        AddProductFrame addProductFrame = new AddProductFrame(loggedInUser);
        addProductFrame.setVisible(true);
        dispose(); // Close the LoginFrame after opening AddProductFrame
    }       
    
}
