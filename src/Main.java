import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {
//        LoginFrame g = new LoginFrame();
        Database data = new Database();
        NearestNeighbor ner = new NearestNeighbor();
        ArrayList<Location> routes = ner.alogorithm();
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
