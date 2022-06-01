import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NearestNeighbor extends Database {

    /**
     * @return Arraylist of Locations that are not Delivered and that are from today
     * @throws SQLException
     */
    public ArrayList<Location> getRoute() throws SQLException {
        ArrayList<Location> routes = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs = null;
        Connection connection = getConnection();
        ScheduledTask task = new ScheduledTask();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dtf.format(task.dateTimeNow());
        try {
            ps = connection.prepareStatement("SELECT customers.DeliveryLocation, orders.OrderID, customers.CustomerName, customers.PhoneNumber, customers.DeliveryAddressLine2 FROM customers JOIN orders ON orders.CustomerID = customers.CustomerID WHERE orders.OrderDate = ? AND orders.Delivered = 0;");
            ps.setString(1, date);
            rs = ps.executeQuery();
            // This will separate the Longitude and Latitude
            while (rs.next()) {
                String[] parts = rs.getString("DeliveryLocation").split(",", 2);
                int orderId = rs.getInt("OrderID");
                Location lo = new Location(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]), orderId);
                Customer cu = new Customer(rs.getString("customers.CustomerName"), rs.getString("customers.DeliveryAddressLine2"), rs.getString("customers.PhoneNumber"));
                lo.setCustomer(cu);
                routes.add(lo);
            }
            ps.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            connection.close();
        }
        return routes;
    }

    /**
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return Double how long the distance is between 2 points in KM
     */
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        if (lat1 == lat2 && lon1 == lon2) {
            return 0;
        }
        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return dist;
    }

    /**
     * @param routes
     * @param lat1
     * @param lon1
     * @return The location which is the nearest from the given location
     * @throws SQLException
     */
    public Location shortestDistance(ArrayList<Location> routes, double lat1, double lon1) throws SQLException {
        Location shortest = null;
        double lowest = 9999999;
        for (Location location : routes) {
            if (location.isVisited()) {
            } else {
                double dist = calculateDistance(lat1, lon1, location.getLat(), location.getLongg());
                // checks if the given location is lower as the current lowest location
                // If it is lower it will set a new lowest
                if (dist < lowest || dist == lowest) {
                    lowest = dist;
                    shortest = location;
                }
            }
        }
        // Sets the location to visited so it won't go in the algorithm again
        shortest.setVisited(true);
        return shortest;
    }

    /**
     * @return Arraylist of (100)locations from the Nearest Neigbhor algortihm
     * @throws SQLException
     */
    public ArrayList<Location> alogorithm() throws SQLException, IOException {
        // Location of Windesheim Campus
        double lat1 = 52.499220;
        double lon1 = 6.081578;

        ArrayList<Location> routes = getRoute();
        ArrayList<Location> totalRoute = new ArrayList<>();
        Location startLocation = new Location(lat1, lon1, 0);

        totalRoute.add(startLocation);
        for (int i = 0; i < 100; i++) {
            if (routes.size() > i) {
                // adds the lowest location to a arraylist
                Location shortest = shortestDistance(routes, lat1, lon1);

                totalRoute.add(shortest);
                lat1 = shortest.getLat();
                lon1 = shortest.getLongg();
            }
        }

        System.out.println(totalRoute.size());
//        WriteDataToExcel w = new WriteDataToExcel(totalRoute);

        return totalRoute;
    }

    /**
     * @return Gives a String of the route that the delivery driver needs to ride in the console
     * @throws SQLException
     */
    public String getMessage() throws SQLException, IOException {
        ArrayList<Location> routes = alogorithm();
        // Link where the route will be displayed on
        StringBuilder url = new StringBuilder("https://map.project-osrm.org/?z=9&center=52.219387%2C5.429993");
        Location last = null;
        for (int counter = 0; counter < routes.size(); counter++) {
            if (counter == 0) {
                String gps = "&loc=" + routes.get(counter).getLat() + "," + routes.get(counter).getLongg() + "/";
                url.append(gps);//toevoegen aan url
            } else {
                // If there are multiple orders on the same location this will prevend it from being multiple times in the url
                if (!(routes.get(counter).getLat() == routes.get(counter - 1).getLat() && routes.get(counter).getLongg() == routes.get(counter - 1).getLongg())) {
                    String gps = "&loc=" + routes.get(counter).getLat() + "," + routes.get(counter).getLongg() + "/";
                    url.append(gps);
                }
            }
        }
        // This adds the Windesheim location in the end of the route
        url.append("&loc=52.499220%2C6.081578&hl=en&alt=0&srv=0");
        System.out.println("Goedemorgen bezorger, hierbij de route van vandaag: " + url);
        return "Goedemorgen bezorger, hierbij de route van vandaag: " + url;
    }
}
