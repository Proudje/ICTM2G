import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Location extends Database {
    private int orderID;
    private double lat;
    private double longg;
    private boolean visited;
    private Customer customer;

    public Location(double lat, double longg, int orderID) {
        this.lat = lat;
        this.longg = longg;
        this.orderID = orderID;
    }

    public double getLat() {
        return lat;
    }

    public double getLongg() {
        return longg;
    }

    public int getOrderID() {
        return orderID;
    }

    public boolean isVisited() {
        return visited;
    }

    /**
     * @param visited
     * Sets order to deliverd when its been in the NearestNeighbor algorithm
     * @throws SQLException
     */
    public void setVisited(boolean visited) throws SQLException {
        if (orderID != 0) {
            this.visited = visited;
            PreparedStatement ps;
            Connection connection = getConnection();
            try {
                ps = connection.prepareStatement("UPDATE `orders` SET `Delivered` = '1' WHERE `orders`.`OrderID` = ?");
                ps.setInt(1, this.orderID);
                ps.executeUpdate();
                ps.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            } finally {
                connection.close();
            }
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
