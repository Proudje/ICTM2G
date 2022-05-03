import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ReturnedOrders extends JPanel {

    public ReturnedOrders() throws ClassNotFoundException, SQLException {
        setPreferredSize(new Dimension(900, 600));

        Database data = new Database();

        String column[]={"OrderID","CustomerName", "OrderDate"};
        JTable jt=new JTable(data.getReturnedOrders(),column);
        jt.setBounds(30,40,200,300);
        JScrollPane sp=new JScrollPane(jt);
        add(sp);
        setVisible(true);
    }
}
