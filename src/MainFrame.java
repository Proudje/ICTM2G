import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainFrame extends JFrame implements ActionListener {
    private JButton returnButton;
    private JButton orderButton;
    AllOrders order = new AllOrders();
    ReturnedOrders returned = new ReturnedOrders();

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

        returnButton.addActionListener(this);
        orderButton.addActionListener(this);

        add(returnButton);
        add(orderButton);

        add(order);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnButton) {
            remove(order);
            add(returned);
            revalidate();
            repaint();
        }
        if (e.getSource() == orderButton) {
            remove(returned);
            add(order);
            revalidate();
            repaint();
        }
    }
}
