import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainFrame extends JFrame implements ActionListener {
    private JButton orderOfTodayButton;
    private JButton orderButton;
    OrdersFrame order = new OrdersFrame();
    OrdersOfTodayFrame returned = new OrdersOfTodayFrame();

    public MainFrame() throws ClassNotFoundException, SQLException {
        JPanel panel = new JPanel();
        setLayout(new FlowLayout());
        panel.setLayout(null);
        setVisible(true);
        setLocation(new Point(250, 100));
        add(panel);
        setSize(new Dimension(1000, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        orderOfTodayButton = new JButton("Bestellingen van vandaag");
        orderButton = new JButton("Bestellingen");

        orderOfTodayButton.addActionListener(this);
        orderButton.addActionListener(this);

        add(orderOfTodayButton);
        add(orderButton);

        add(order);
    }

    /**
     * @param e
     * Opens a new frame based on which button you click on
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == orderOfTodayButton) {
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
