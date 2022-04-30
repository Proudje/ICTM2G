import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AllOrders extends JPanel {
    Connection connection;
    PreparedStatement ps;

    public AllOrders() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nerdygadgets", "root", "root");
            ps = connection.prepareStatement("SELECT `OrderID`, `OrderDate` FROM `orders` LIMIT 10");
        } catch (Exception ex) {

        }

        setPreferredSize(new Dimension(900, 600));
        String data[][]={
                {"101","Amit","9/11/2022", "Yes", "No"},
                {"102","Kees","9/11/2022", "Yes", "No"},
                {"103","Jan","9/11/2022", "Yes", "No"},
                {"104","Pieter","9/11/2022", "Yes", "No"},
                {"105","Erik","9/11/2022", "Yes", "No"}};
        String column[]={"OrderID","Customer name","Order date", "Deliverd", "Retour"};
        JTable jt=new JTable(data,column);
        jt.setBounds(30,40,200,300);
        JScrollPane sp=new JScrollPane(jt);
        add(sp);
        setVisible(true);
    }
}
