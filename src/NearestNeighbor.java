import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NearestNeighbor extends Database {

    public ArrayList<Location> getRoute() throws SQLException {
        ArrayList<Location> routes = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs = null;
        Connection connection = getConnection();
        try {
            ps = connection.prepareStatement("SELECT `DeliveryLocation` FROM `customers` LIMIT 1000;");
            rs = ps.executeQuery();
            while (rs.next()) {
                String[] parts = rs.getString("DeliveryLocation").split(",", 2);
                Location lo = new Location(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
                routes.add(lo);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return routes;
    }
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2){
        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return dist;
    }
    public Location shortestDistance(ArrayList<Location> routes, double lat1, double lon1){
        Location shortest = null;
        double lowest = 300;
        for (Location location : routes) {
            if (location.isVisited()) {
            } else {
                double dist = calculateDistance(lat1, lon1, location.getLat(), location.getLongg());
                if (dist < lowest) {
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
        Location startLocation = new Location(lat1, lon1);

        totalRoute.add(startLocation);

        for (int i = 0; i < 100; i++) {
            Location shortest = shortestDistance(routes, lat1, lon1);
            totalRoute.add(shortest);
            lat1 = shortest.getLat();
            lon1 = shortest.getLongg();
        }

        return totalRoute;
    }

    public void getMessage() throws SQLException {
        ArrayList<Location> routes = alogorithm();
        StringBuilder url = new StringBuilder("https://www.google.com/maps/dir/");
        Location last = null;
        for (int counter = 0; counter < routes.size(); counter++) {
            if (counter == 0) {
                String gps = routes.get(counter).getLat() + "," + routes.get(counter).getLongg() + "/";
                url.append(gps);
            } else {
                if (!(routes.get(counter).getLat() == routes.get(counter - 1).getLat() && routes.get(counter).getLongg() == routes.get(counter - 1).getLongg())) {
                    String gps = routes.get(counter).getLat() + "," + routes.get(counter).getLongg() + "/";
                    url.append(gps);
                }
            }
        }
        System.out.println("Goedemorgen bezorger, hierbij de route van vandaag: " + url);
    }
}
