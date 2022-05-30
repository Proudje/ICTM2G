import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TimerTask;

public class ScheduledTask extends TimerTask {

    /**
     * Runs Runs NearestNeighbor alogrithm at a given timeframe
     */
    // Add your task here
    public void run() {
        LocalDateTime now = dateTimeNow();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        //LocalTime time = LocalTime.parse("22:00:00");

        // this is for testting
        LocalTime time = LocalTime.parse(dtf.format(now));

        //if (dtf.format(now).equals(dtf.format(time))) {
            NearestNeighbor ner = new NearestNeighbor();
            try {
                ArrayList<Location> routes = ner.getRoute();
                double total = routes.size();

                total = Math.ceil(total / 100);

                for (int i = 0; i < total; i++) {
                    ner.getMessage();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
   // }

    /**
     * @return Get the date of now
     */
    public LocalDateTime dateTimeNow() {
        return LocalDateTime.now();
    }
}