import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MainFrame extends JFrame {
    private JButton returnButton;
    private JButton orderButton;

    public MainFrame() throws ClassNotFoundException, SQLException {
        JPanel panel = new JPanel();
        setLayout(new FlowLayout());
        panel.setLayout(null);
        setVisible(true);
        setTitle("Main Page");
        setLocation(new Point(250, 100));
        add(panel);
        setSize(new Dimension(1000, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        returnButton = new JButton("Retours");
        orderButton = new JButton("Orders");

        add(returnButton);
        add(orderButton);

        add(new AllOrders());
    }
}
