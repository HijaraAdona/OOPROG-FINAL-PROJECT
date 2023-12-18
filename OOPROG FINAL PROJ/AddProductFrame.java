import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class AddProductFrame extends JFrame implements ActionListener {
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
