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
    private String page;

    public MainFrame() throws ClassNotFoundException, SQLException {
        JPanel panel = new JPanel();
        setLayout(new FlowLayout());
        panel.setLayout(null);
        setVisible(true);
        updateTitle(true);

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

    private void updateTitle(boolean ok) {
        String mededeling = "";
        if (ok) {
            mededeling = "Inloggen succesvol - ";
            page = "Orders page";
            setTitle(mededeling + page + " - Username: " + LoginFrame.getUsername());
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnButton) {
            remove(order);
            add(returned);
            page = "Retours pagina";
            setTitle(page + " - Username: " + LoginFrame.getUsername());
            revalidate();
            repaint();
        }
        if (e.getSource() == orderButton) {
            remove(returned);
            add(order);
            page = "Orders page";
            setTitle(page + " - Username: " + LoginFrame.getUsername());
            revalidate();
            repaint();
        }
    }
}
