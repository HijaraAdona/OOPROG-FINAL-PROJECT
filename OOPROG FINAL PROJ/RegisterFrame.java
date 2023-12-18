import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame implements ActionListener {
    private JTextField idField;
    private JTextField nameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private InitialFrame initialFrame;
    private JLabel label_5;
    private JLabel lblNewLabel;

    public RegisterFrame(InitialFrame initialFrame) {
        super("Register");

        this.initialFrame = initialFrame;

        // Create components
        idField = new JTextField();
        nameField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        registerButton = new JButton("Register");
        
        // Set layouts
        setLayout(new GridLayout(6, 2, 10, 10));  // Increased rows for new components

        // Add components to the frame
        add(new JLabel("ID:"));
        add(idField);
        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("Confirm Password:"));
        add(confirmPasswordField);
        add(new JLabel()); // Empty label as a placeholder
        add(registerButton);

        // Add action listener
        registerButton.addActionListener(this);

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200);  // Increased height to accommodate the new components
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            registerUserAndOpenLoginFrame();
        }
    }

     private void registerUserAndOpenLoginFrame() {
        // Get user details from text fields
        String id = idField.getText();
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Validate password and confirm password
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if ID is provided
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a user ID.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

         // Create a new user
    User user = new User(id, name, username, password);

    // Add the user to the list of registered users
    initialFrame.getRegisteredUsers().add(user);

    // Display a success message
    JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);

    // Close the RegisterFrame after registration
    dispose();
    openLoginFrame(); // Open the LoginFrame after RegisterFrame
}

   private void openLoginFrame() {
    LoginFrame loginFrame = new LoginFrame(initialFrame);
    loginFrame.setVisible(true);
}
}
