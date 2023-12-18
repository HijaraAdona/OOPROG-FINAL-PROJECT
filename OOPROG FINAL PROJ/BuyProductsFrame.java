import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BuyProductsFrame extends JFrame implements ActionListener {
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
