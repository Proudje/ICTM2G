import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TimerTask;

public class NearestNeighbor extends Database {

    public ArrayList<Location> getRoute() throws SQLException {
        ArrayList<Location> routes = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs = null;
        Connection connection = getConnection();
        ScheduledTask task = new ScheduledTask();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dtf.format(task.dateTimeNow());
        try {
            ps = connection.prepareStatement("SELECT customers.DeliveryLocation, orders.OrderID FROM customers JOIN orders ON orders.CustomerID = customers.CustomerID WHERE orders.OrderDate = ? AND orders.Delivered = 0;");
            ps.setString(1, date);
            rs = ps.executeQuery();
            while (rs.next()) {
                String[] parts = rs.getString("DeliveryLocation").split(",", 2);
                int orderId = rs.getInt("OrderID");
                Location lo = new Location(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]), orderId);
                routes.add(lo);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return routes;
    }

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

    public Location shortestDistance(ArrayList<Location> routes, double lat1, double lon1) throws SQLException {
        Location shortest = null;
        double lowest = 999999999;
        for (Location location : routes) {
            if (location.isVisited()) {
            } else {
                double dist = calculateDistance(lat1, lon1, location.getLat(), location.getLongg());
                if (dist < lowest || dist == lowest) {
                    lowest = dist;
                    shortest = location;
                }
            }
        }
        shortest.setVisited(true);
        return shortest;
    }

    public ArrayList<Location> alogorithm() throws SQLException {
        double lat1 = 52.499220;
        double lon1 = 6.081578;
        ArrayList<Location> routes = getRoute();
        ArrayList<Location> totalRoute = new ArrayList<>();
        Location startLocation = new Location(lat1, lon1, 0);

        totalRoute.add(startLocation);

            for (int i = 0; i < 100; i++) {
                if (routes.size() > i) {
                    Location shortest = shortestDistance(routes, lat1, lon1);

                    totalRoute.add(shortest);
                    lat1 = shortest.getLat();
                    lon1 = shortest.getLongg();
                }
            }

        return totalRoute;
    }

    public String getMessage() throws SQLException {
        ArrayList<Location> routes = alogorithm();
        StringBuilder url = new StringBuilder("https://map.project-osrm.org/?z=9&center=52.219387%2C5.429993");
        Location last = null;
        for (int counter = 0; counter < routes.size(); counter++) {
            if (counter == 0) {
                String gps = "&loc=" + routes.get(counter).getLat() + "," + routes.get(counter).getLongg() + "/";
                url.append(gps);
            } else {
                if (!(routes.get(counter).getLat() == routes.get(counter - 1).getLat() && routes.get(counter).getLongg() == routes.get(counter - 1).getLongg())) {
                    String gps = "&loc=" + routes.get(counter).getLat() + "," + routes.get(counter).getLongg() + "/";
                    url.append(gps);
                }
            }
        }
        url.append("&hl=en&alt=0&srv=0");
        System.out.println("Goedemorgen bezorger, hierbij de route van vandaag: " + url);
        return "Goedemorgen bezorger, hierbij de route van vandaag: " + url;
    }
}
