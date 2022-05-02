import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AllOrders extends JPanel {
    Connection connection;
    PreparedStatement ps;
    ResultSet rs = null;

    public AllOrders() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nerdygadgets", "root", "root");
            ps = connection.prepareStatement("SELECT `OrderID`, `OrderDate`, `CustomerName` FROM `orders` LEFT JOIN customers ON customers.CustomerID = orders.CustomerID LIMIT 10");
            rs = ps.executeQuery();
        } catch (Exception ex) {
            System.out.println("Fouttt");
        }
        setPreferredSize(new Dimension(900, 600));

        String data[][] = new String[20][5];
        int i = 0;
        while (rs.next()) {
            int id = rs.getInt("OrderID");
            String name = rs.getString("CustomerName");
            String age = rs.getString("OrderDate");
            String deliverd = "yes";
            String returned = "No";
            data[i][0] = id + "";
            data[i][1] = name;
            data[i][2] = age;
            data[i][3] = deliverd;
            data[i][4] = returned;
            i++;
        }

        String column[]={"OrderID","Customer name","Order date", "Deliverd", "Retour"};
        JTable jt=new JTable(data,column);
        jt.setBounds(30,40,200,300);
        JScrollPane sp=new JScrollPane(jt);
        add(sp);
        setVisible(true);
    }
}
