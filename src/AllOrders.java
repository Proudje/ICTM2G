import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AllOrders extends JPanel {
    public AllOrders() throws ClassNotFoundException, SQLException {
        setPreferredSize(new Dimension(900, 600));


        String column[] = {"OrderID", "Customer name", "Order date", "Deliverd", "Retour"};
        Database data = new Database();

        JTable jt = new JTable(data.getOders(), column);
        jt.setBounds(30, 40, 200, 300);
        JScrollPane sp = new JScrollPane(jt);
        add(sp);
        setVisible(true);
    }
}
