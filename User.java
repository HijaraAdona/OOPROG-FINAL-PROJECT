import javax.swing.*;
import java.util.*;
import java.awt.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

class User {
    private String id;
    private String name;
    private String username;
    private String password;

    public User(String id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Product {
    private String productNumber;
    private String productName;
    private String description;
    private int quantity;
    private double price;

    public Product(String productNumber, String productName, String description, int quantity, double price) {
        this.productNumber = productNumber;
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product Number: " + productNumber + "\n" +
                "Product Name: " + productName + "\n" +
                "Description: " + description + "\n" +
                "Quantity: " + quantity + "\n" +
                "Price: $" + price + "\n";
    }
}

class AddProductFrame extends JFrame implements ActionListener {
    private JTextField productNumberField;
    private JTextField productNameField;
    private JTextField descriptionField;
    private JTextField quantityField;
    private JTextField priceField;
    private JButton addButton;
    private JButton proceedButton;
    private JTextArea productListArea;
    private JScrollPane scrollPane;
    private User registeredUser;
    private List<Product> productList;
    private JTextField customerIdField;
    private JTextField totalPriceField;
    private JButton logoutButton;
    private JButton viewProductsButton; // Added "View Products" button

    public AddProductFrame(User registeredUser) {
        super("Add Product");

        this.registeredUser = registeredUser;
        productList = new ArrayList<>();

        // Create components
        productNumberField = new JTextField();
        productNameField = new JTextField();
        descriptionField = new JTextField();
        quantityField = new JTextField();
        priceField = new JTextField();
        addButton = new JButton("Add Product");
        proceedButton = new JButton("Proceed to Buy");
        productListArea = new JTextArea(20, 40);
        productListArea.setEditable(false);
        scrollPane = new JScrollPane(productListArea);
        customerIdField = new JTextField();
        totalPriceField = new JTextField();
        totalPriceField.setEditable(false);
        logoutButton = new JButton("Logout");
        viewProductsButton = new JButton("View Products"); // Added "View Products" button

        // Set layouts
        setLayout(new BorderLayout());

        // Create and add panels
        add(createUserDetailsPanel(), BorderLayout.NORTH);
        add(createProductDetailsPanel(), BorderLayout.CENTER);
        add(createProductListPanel(), BorderLayout.WEST);
        add(createButtonPanel(), BorderLayout.SOUTH);

        // Add action listeners
        addButton.addActionListener(this);
        proceedButton.addActionListener(this);
        logoutButton.addActionListener(this);
        viewProductsButton.addActionListener(this);

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createUserDetailsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("User Details"));

        panel.add(createLabel("User ID:"));
        panel.add(createLabel(registeredUser.getId()));
        panel.add(createLabel("User Name:"));
        panel.add(createLabel(registeredUser.getName()));

        return panel;
    }

    private JPanel createProductDetailsPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Product Details"));

        panel.add(createLabel("Product Number:"));
        panel.add(productNumberField);
        panel.add(createLabel("Product Name:"));
        panel.add(productNameField);
        panel.add(createLabel("Description:"));
        panel.add(descriptionField);
        panel.add(createLabel("Quantity:"));
        panel.add(quantityField);
        panel.add(createLabel("Price:"));
        panel.add(priceField);

        return panel;
    }

    private JPanel createProductListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Product List"));

        productListArea.setPreferredSize(new Dimension(300, 400));

        // Set the vertical scroll policy for the JScrollPane
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(productListArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        panel.add(addButton);
        panel.add(proceedButton);
        panel.add(viewProductsButton); // Added "View Products" button
        panel.add(logoutButton);

        return panel;
    }

    private JLabel createLabel(String text) {
        return new JLabel(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addProduct();
        } else if (e.getSource() == proceedButton) {
            proceedToBuy();
        } else if (e.getSource() == logoutButton) {
            logout();
        } else if (e.getSource() == viewProductsButton) {
            viewProducts();
        }
    }

    private void addProduct() {
        // Get product details from text fields
        String productNumber = productNumberField.getText();
        String productName = productNameField.getText();
        String description = descriptionField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());

        // Create a new product
        Product product = new Product(productNumber, productName, description, quantity, price);

        // Add the product to the list
        productList.add(product);

        // Update the product list display
        updateProductListArea();

        // Clear the input fields
        clearProductFields();
    }

    private void proceedToBuy() {
        BuyProductsFrame buyProductsFrame = new BuyProductsFrame(productList, registeredUser, this);
        buyProductsFrame.setVisible(true);
        // Close the AddProductFrame after opening BuyProductsFrame
    }

    private void viewProducts() {
        // Display a separate frame to view the list of products
        ViewProductsFrame viewProductsFrame = new ViewProductsFrame(productList);
        viewProductsFrame.setVisible(true);
    }

    private void updateProductListArea() {
        productListArea.setText("");
        for (Product product : productList) {
            productListArea.append(product.toString() + "---------------\n");
        }
    }

    private void clearProductFields() {
        productNumberField.setText("");
        productNameField.setText("");
        descriptionField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }

    private void logout() {
        // Open the InitialFrame after logout
        InitialFrame initialFrame = new InitialFrame();
        initialFrame.setVisible(true);
        dispose(); // Close the AddProductFrame after logout
    }

    public List<Product> getProductList() {
        return productList;
    }
}


class BuyProductsFrame extends JFrame implements ActionListener {
    private List<Product> productList;
    private User registeredUser;
    private AddProductFrame addProductFrame;
    private JTextField customerIdField;
    private JTextField totalPriceField;
    private JButton buyButton;
    private JButton logoutButton;

    public BuyProductsFrame(List<Product> productList, User registeredUser, AddProductFrame addProductFrame) {
        super("Buy Products");

        this.productList = productList;
        this.registeredUser = registeredUser;
        this.addProductFrame = addProductFrame;

        // Create components
        customerIdField = new JTextField();
        totalPriceField = new JTextField();
        buyButton = new JButton("Buy Products");
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);

        // Set layouts
        setLayout(new BorderLayout());

        // Create and add panels
        add(createUserDetailsPanel(), BorderLayout.NORTH);
        add(createProductListPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        createButtonPanel().add(logoutButton);

        // Add action listener
        buyButton.addActionListener(this);
        logoutButton.addActionListener(this);
        
        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createUserDetailsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("User Details"));

        panel.add(createLabel("User ID:"));
        panel.add(createLabel(registeredUser.getId()));
        panel.add(createLabel("User Name:"));
        panel.add(createLabel(registeredUser.getName()));
        add(createButtonPanel(), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createProductListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Product List"));

        JTextArea productListArea = new JTextArea(10, 30);
        productListArea.setEditable(false);

        // Display the products in the product list
        for (Product product : productList) {
            productListArea.append(product.toString() + "---------------\n");
        }

        JScrollPane scrollPane = new JScrollPane(productListArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        panel.add(createLabel("Customer ID:"));
        panel.add(customerIdField);
        panel.add(createLabel("Total Price:"));
        panel.add(totalPriceField);
        panel.add(buyButton);

        return panel;
    }

    private JLabel createLabel(String text) {
        return new JLabel(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buyButton) {
            buyProducts();
        } else if (e.getSource() == logoutButton) { // Handle logout button
            logout();
        }
    }

    private void logout() {
        // Open the InitialFrame after logout
        InitialFrame initialFrame = new InitialFrame();
        initialFrame.setVisible(true);
        dispose(); // Close the BuyProductsFrame after logout
    }

    private void buyProducts() {
        // Get customer ID and calculate total price
        String customerId = customerIdField.getText();
        double totalPrice = calculateTotalPrice();

        // Display the total price
        totalPriceField.setText(String.valueOf(totalPrice));

        // You can add further logic here, such as updating a database or sending an order confirmation

        // Clear the product list and close frames
        productList.clear();
        addProductFrame.dispose();
        dispose();
    }

    private double calculateTotalPrice() {
        double totalPrice = 0;
        for (Product product : productList) {
            totalPrice += product.getQuantity() * product.getPrice();
        }
        return totalPrice;
    }
}
class ViewProductsFrame extends JFrame {
    private JTextArea productListArea;
    private JScrollPane scrollPane;

    public ViewProductsFrame(List<Product> productList) {
        super("View Products");

        // Create components
        productListArea = new JTextArea(20, 40);
        productListArea.setEditable(false);
        scrollPane = new JScrollPane(productListArea);

        // Set layouts
        setLayout(new BorderLayout());

        // Create and add panels
        add(createProductListPanel(), BorderLayout.CENTER);

        // Set frame properties
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        // Display the products in the text area
        for (Product product : productList) {
            productListArea.append(product.toString() + "---------------\n");
        }
    }

    private JPanel createProductListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Product List"));

        productListArea.setPreferredSize(new Dimension(300, 400));

        // Set the vertical scroll policy for the JScrollPane
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(productListArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
}
class RegisterFrame extends JFrame implements ActionListener {
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



class InitialFrame extends JFrame implements ActionListener {
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

class LoginFrame extends JFrame implements ActionListener {
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