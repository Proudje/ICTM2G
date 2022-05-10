import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Database {
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost/nerdygadgets";
        String username = "root", password = "";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean getLogin(String username, String password_plaintext) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement ps;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement("SELECT `HashedPassword` FROM `people` WHERE `FullName` = ?");
            ps.setString(1, username);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                String stored_hash = result.getString("HashedPassword");
                if (BCrypt.checkpw(password_plaintext, stored_hash)) {
                    return true;
                }
                ps.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {

            connection.close();
        }
        return false;
    }

    public String[][] getOders() throws SQLException {
        PreparedStatement ps;
        ResultSet rs = null;
        Connection connection = getConnection();
        String data[][] = new String[20][5];
        int i = 0;

        try {
            ps = connection.prepareStatement("SELECT `OrderID`, `OrderDate`, `CustomerName` FROM `orders` LEFT JOIN customers ON customers.CustomerID = orders.CustomerID LIMIT 10");
            rs = ps.executeQuery();


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
            ps.close();
            return data;

        } catch (Exception ex) {
            System.out.println("Fouttt");
        } finally {
            connection.close();
        }
        return data;
    }

    public String[][] getReturnedOrders() throws SQLException {
        PreparedStatement ps;
        ResultSet rs = null;
        Connection connection = getConnection();
        String data[][] = new String[20][3];
        int i = 0;

        try {
            ps = connection.prepareStatement("SELECT `OrderID`, `OrderDate`, `CustomerName` FROM `orders` LEFT JOIN customers ON customers.CustomerID = orders.CustomerID LIMIT 10");
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("OrderID");
                String name = rs.getString("CustomerName");
                String age = rs.getString("OrderDate");
                data[i][0] = id + "";
                data[i][1] = name;
                data[i][2] = age;
                i++;
            }
            ps.close();
            return data;
        } catch (Exception ex) {
            System.out.println("Fouttt");
        } finally {
            connection.close();
        }
        return data;
    }
}
