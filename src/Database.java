import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Database {
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost/test";
        String username = "root", password = "root";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public Connection gettConnection() throws SQLException {
        String url = "jdbc:mysql://localhost/nerdygadgets";
        String username = "root", password = "root";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public boolean getLogin(String username, String password) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement ps;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement("SELECT `username`, `password` FROM `user` WHERE `username` = ? AND `password` = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                return true;

            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public String[][] getOders() throws SQLException {
        PreparedStatement ps;
        ResultSet rs = null;
        try {
            Connection connection = gettConnection();
            ps = connection.prepareStatement("SELECT `OrderID`, `OrderDate`, `CustomerName` FROM `orders` LEFT JOIN customers ON customers.CustomerID = orders.CustomerID LIMIT 10");
            rs = ps.executeQuery();
        } catch (Exception ex) {
            System.out.println("Fouttt");
        }
        Button b = new Button("Click Here");

        String data[][] = new String[20][6];
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
            data[i][5] = String.valueOf(b);
            i++;
        }
        return data;
    }

    public String[][] getReturnedOrders() throws SQLException {
        PreparedStatement ps;
        ResultSet rs = null;
        try {
            Connection connection = gettConnection();
            ps = connection.prepareStatement("SELECT `OrderID`, `OrderDate`, `CustomerName` FROM `orders` LEFT JOIN customers ON customers.CustomerID = orders.CustomerID LIMIT 10");
            rs = ps.executeQuery();
        } catch (Exception ex) {
            System.out.println("Fouttt");
        }

        String data[][] = new String[20][3];
        int i = 0;
        while (rs.next()) {
            int id = rs.getInt("OrderID");
            String name = rs.getString("CustomerName");
            String age = rs.getString("OrderDate");
            data[i][0] = id + "";
            data[i][1] = name;
            data[i][2] = age;
            i++;
        }
        return data;
    }

}
