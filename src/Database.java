import java.sql.*;
import java.util.ArrayList;

public class Database {
    public Connection getConnection() throws SQLException {
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
        String data[][] = new String[10][6];
        int i = 0;

        try {
            ps = connection.prepareStatement("SELECT `OrderID`, `OrderDate`, `CustomerName` FROM `orders` LEFT JOIN customers ON customers.CustomerID = orders.CustomerID LIMIT 10");
            rs = ps.executeQuery();


            while (rs.next()) {
                int id = rs.getInt("OrderID");
                String name = rs.getString("CustomerName");
                String age = rs.getString("OrderDate");
                String deliverd = "yes";
                data[i][0] = id + "";
                data[i][1] = name;
                data[i][2] = age;
                data[i][3] = deliverd;
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
        String data[][] = new String[10][3];
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
    public Customer getCustomer(int customerID) throws SQLException {
        PreparedStatement ps;
        ResultSet rs = null;
        Connection connection = getConnection();
        try {
            ps = connection.prepareStatement("SELECT CustomerID, CustomerName, PhoneNumber, DeliveryAddressLine2, DeliveryPostalCode, stateprovinces.StateProvinceName, countries.CountryName ,cities.CityName FROM `customers` JOIN cities ON PostalCityID = cities.CityID JOIN stateprovinces ON cities.StateProvinceID = stateprovinces.StateProvinceID JOIN countries ON stateprovinces.CountryID = countries.CountryID WHERE CustomerID = ?;");
            ps.setInt(1, customerID);
            rs = ps.executeQuery();
            Customer customer = new Customer();

            while (rs.next()) {
                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setName(rs.getString("CustomerName"));
                customer.setPhonenumber(rs.getString("PhoneNumber"));
                customer.setAddress(rs.getString("DeliveryAddressLine2"));
                customer.setPostalcode(rs.getString("DeliveryPostalCode"));
                customer.setStateprovincename(rs.getString("stateprovinces.StateProvinceName"));
                customer.setCountryname(rs.getString("countries.CountryName"));
                customer.setCityname(rs.getString("cities.CityName"));
            }

            return customer;

        } catch (Exception ex) {
            System.out.println("werktniet");
        }
        return null;
    }
    public boolean updateCustomer(int customerID, String name, String phone, String address, String postalcode) throws SQLException {
        PreparedStatement ps;
        Connection connection = getConnection();
        try {
            ps = connection.prepareStatement("UPDATE `customers` SET CustomerName = ?, PhoneNumber = ?, DeliveryAddressLine2 = ?, DeliveryPostalCode = ? WHERE CustomerID = ?;");
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, address);
            ps.setString(4, postalcode);
            ps.setInt(5, customerID);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
}
