import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class InitialFrame extends JFrame implements ActionListener {
   private JButton loginButton;
    private JButton registerButton;
    private List<User> registeredUsers;

    public InitialFrame() {
        super("Welcome to Your Application");

        // Initialize the list of registered users
        registeredUsers = new ArrayList<>();

        // Create components
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        // Create a JLabel to display the photo
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\Adona\\Downloads\\Yellow Red Circle Ramen Logo.png");
        Image originalImage = originalIcon.getImage();

        // Calculate the new width while maintaining the aspect ratio
        int newWidth = 300; // Adjust the desired width
        int newHeight = -1; // Setting height to -1 maintains the aspect ratio

        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel photoLabel = new JLabel(scaledIcon);

        
        // Create a JLabel to display big text
        JLabel bigTextLabel = new JLabel("Welcome!");
        bigTextLabel.setFont(new Font("Arial", Font.BOLD, 24));
        bigTextLabel.setHorizontalAlignment(JLabel.CENTER);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // Set layouts
        setLayout(new BorderLayout());
        
        //BG Color
        getContentPane().setBackground(Color.YELLOW);

        // Add components to the frame
        add(photoLabel, BorderLayout.CENTER);
        add(bigTextLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            openLoginFrame();
        } else if (e.getSource() == registerButton) {
            openRegisterFrame();
        }
    }

    private void openLoginFrame() {
        LoginFrame loginFrame = new LoginFrame(this);
        loginFrame.setVisible(true);
        dispose(); // Close the InitialFrame after opening LoginFrame
    }

    private void openRegisterFrame() {
    RegisterFrame registerFrame = new RegisterFrame(this);
    registerFrame.setVisible(true);
    dispose();
}

    public List<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InitialFrame();
        });
    }
}
