import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewProductsFrame extends JFrame {
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
