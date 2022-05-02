import javax.swing.*;
import java.sql.*;

public class Database {
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost/test";
        String username = "root", password = "";
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

}
