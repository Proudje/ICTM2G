import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

public class ScheduledTask extends TimerTask {

    // Add your task here
    public void run() {

        LocalDateTime now = dateTimeNow();
        //LocalTime time = LocalTime.parse("22:00:00");
        LocalTime time = LocalTime.parse("13:24:00");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        if (dtf.format(now).equals(dtf.format(time))) {
            NearestNeighbor ner = new NearestNeighbor();
            try {
                ner.getMessage();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public LocalDateTime dateTimeNow() {
        return LocalDateTime.now();
    }
}