import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {
//        LoginFrame g = new LoginFrame();
        Database data = new Database();
        ArrayList<Location> routes = data.alogorithm();
        StringBuilder url = new StringBuilder("https://www.google.com/maps/dir/");
        for (Location r : routes) {
            String gps = r.getLat() +","+ r.getLongg()+"/";
            url.append(gps);
//            System.out.println(r.getLat() +","+ r.getLongg()+"/");
        }
        System.out.println(url);



    }
}
