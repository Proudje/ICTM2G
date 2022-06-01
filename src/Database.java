import javax.swing.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Database {
    /**
     * @return Connection of the Database
     * @throws SQLException
     */
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

    /**
     * @param username
     * @param password_plaintext
     * @return True if the login was succesfull else returns False
     * @throws SQLException
     */
    public boolean getLogin(String username, String password_plaintext) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement ps;
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

    /**
     * @return String Array of Orders from the database
     * @throws SQLException
     */
    public String[][] getOders() throws SQLException {
        PreparedStatement ps;
        ResultSet rs = null;
        Connection connection = getConnection();
        String[][] data;
        int i = 0;
        try {
            ps = connection.prepareStatement("SELECT `OrderID`, `OrderDate`, `CustomerName`, customers.CustomerID, `Delivered` FROM `orders` LEFT JOIN customers ON customers.CustomerID = orders.CustomerID", ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs = ps.executeQuery();
            // Counts how many records there are
            // Only works when you have TYPE_SCROLL_SENSITIVE and CONCUR_UPDATABLE added to your connection.prepareStatement
            int rowcount = 0;
            if (rs.last()) {
                rowcount = rs.getRow();
                rs.beforeFirst();
            }
            // rowcount has the amount of records in it
            data = new String[rowcount][7];
            while (rs.next()) {
                int id = rs.getInt("OrderID");
                String name = rs.getString("CustomerName");
                String age = rs.getString("OrderDate");
                int customerID = rs.getInt("CustomerID");
                int deliverd = rs.getInt("Delivered");
                if (deliverd == 0) {
                    data[i][4] = "Niet geleverd";
                } else if (deliverd == 1) {
                    data[i][4] = "Geleverd";
                }
                data[i][0] = id + "";
                data[i][1] = customerID + "";
                data[i][2] = name;
                data[i][3] = age;
                data[i][5] = "";
                i++;
            }
            ps.close();
            return data;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            connection.close();
        }
        // If there are no products in the order (Just to be safe :) )
        data = new String[0][7];
        return data;
    }

    /**
     * @param orderID
     * @return String Array of products from a order thats given in the param
     * @throws SQLException
     */
    public String[][] getProductsFromOrder(int orderID) throws SQLException {
        PreparedStatement ps;
        ResultSet rs = null;
        Connection connection = getConnection();
        int i = 0;
        String[][] data;
        try {
            ps = connection.prepareStatement("SELECT orderlines.StockItemID, orderlines.Quantity, orderlines.UnitPrice, stockitems.StockItemName, stockitemholdings.QuantityOnHand FROM `orderlines` LEFT JOIN stockitems ON stockitems.StockItemID = orderlines.StockItemID LEFT JOIN stockitemholdings ON stockitems.StockItemID = stockitemholdings.StockItemID WHERE OrderID = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ps.setInt(1, orderID);
            rs = ps.executeQuery();

            // Counts how many records there are
            // Only works when you have TYPE_SCROLL_SENSITIVE and CONCUR_UPDATABLE added to your connection.prepareStatement
            int rowcount = 0;
            if (rs.last()) {
                rowcount = rs.getRow();
                rs.beforeFirst();
            }
            // rowcount has the amount of records in it
            data = new String[rowcount][7];

            while (rs.next()) {
                int id = rs.getInt("StockItemID");
                String name = rs.getString("StockItemName");
                int qty = rs.getInt("Quantity");
                int stock = rs.getInt("QuantityOnHand");
                int price = rs.getInt("UnitPrice");
                int totalprice = price * qty;
                data[i][0] = String.valueOf(id);
                data[i][1] = name;
                data[i][2] = String.valueOf(qty);
                data[i][3] = String.valueOf(stock);
                data[i][4] = String.valueOf(price);
                data[i][5] = String.valueOf(totalprice);
                i++;
            }
            ps.close();
            return data;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            connection.close();
        }
        // If there are no products in the order (Just to be safe :) )
        data = new String[0][7];
        return data;
    }

    public String[][] getOrderFromToday() throws SQLException {
        PreparedStatement ps;
        ResultSet rs = null;
        Connection connection = getConnection();
        int i = 0;
        String[][] data;
        ScheduledTask task = new ScheduledTask();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dtf.format(task.dateTimeNow());
        try {
            ps = connection.prepareStatement("SELECT OrderID, CustomerName, orders.CustomerID FROM orders JOIN customers ON orders.CustomerID = customers.CustomerID WHERE OrderDate = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, date);
            rs = ps.executeQuery();

            // Counts how many records there are
            // Only works when you have TYPE_SCROLL_SENSITIVE and CONCUR_UPDATABLE added to your connection.prepareStatement
            int rowcount = 0;
            if (rs.last()) {
                rowcount = rs.getRow();
                rs.beforeFirst();
            }
            // rowcount has the amount of records in it
            data = new String[rowcount][4];

            while (rs.next()) {
                int id = rs.getInt("OrderID");
                String name = rs.getString("CustomerName");
                int customerID = rs.getInt("CustomerID");
                data[i][0] = id + "";
                data[i][1] = customerID + "";
                data[i][2] = name;
                data[i][3] = "";
                i++;
            }
            ps.close();
            return data;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            connection.close();
        }
        // If there are no products in the order (Just to be safe :) )
        data = new String[0][7];
        return data;
    }


    /**
     * @param orderID
     * @return Customer information from a order thats given in the param
     * @throws SQLException
     */
    public Customer getCustomer(int orderID) throws SQLException {
        PreparedStatement ps;
        ResultSet rs = null;
        Connection connection = getConnection();
        try {
            ps = connection.prepareStatement("SELECT customers.CustomerID, customers.CustomerName, PhoneNumber, DeliveryAddressLine2, DeliveryPostalCode, stateprovinces.StateProvinceName, countries.CountryName ,cities.CityName FROM `customers` JOIN cities ON PostalCityID = cities.CityID JOIN stateprovinces ON cities.StateProvinceID = stateprovinces.StateProvinceID JOIN countries ON stateprovinces.CountryID = countries.CountryID JOIN orders ON customers.CustomerID = orders.CustomerID WHERE orders.OrderID = ?");
            ps.setInt(1, orderID);
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
            System.out.println(ex.getMessage());
        }
        return null;
    }

    /**
     * @param customerID
     * @param name
     * @param phone
     * @param address
     * @param postalcode
     * @return True if Customer is succesfully updated else returns False
     * @throws SQLException
     */
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
            JOptionPane.showMessageDialog(null, "Bijwerken niet gelukt, controleer de ingvulde gegevens.");
        }
        return false;
    }

    /**
     * @param productID
     * @param orderID
     * @param productName
     * @param qty
     * @param stock
     * @param price
     * @return True if Product is succesfully updated else returns False
     * @throws SQLException
     */
    public boolean updateProductFromOrder(int productID,int orderID, String productName, int qty, int stock, int price) throws SQLException {
        PreparedStatement ps;
        Connection connection = getConnection();
        try {
            ps = connection.prepareStatement("UPDATE `orderlines`  SET Quantity = ?, UnitPrice = ? WHERE OrderID = ? AND StockItemID = ?");
            ps.setInt(1, qty);
            ps.setInt(2, price);
            ps.setInt(3, orderID);
            ps.setInt(4, productID);
            ps.executeUpdate();
            ps = connection.prepareStatement("UPDATE `stockitems`  SET StockItemName = ? WHERE StockItemID = ?");
            ps.setString(1, productName);
            ps.setInt(2, productID);
            ps.executeUpdate();
            ps = connection.prepareStatement("UPDATE `stockitemholdings`  SET QuantityOnHand = ? WHERE StockItemID = ?");
            ps.setInt(1, stock);
            ps.setInt(2, productID);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Bijwerken niet gelukt, controleer de ingvulde gegevens.");
        }
        return false;
    }
}
