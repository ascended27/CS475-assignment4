import java.sql.Timestamp;

public class Clock extends Thread {

    private Calendar calendar;

    public Clock(Calendar calendar){
        this.calendar = calendar;
    }

    @Override
    public void run() {
        // Do it until the thread is stopped
        while(true) {
            // Get the current time
            Timestamp ts = new Timestamp(System.currentTimeMillis());

            // Look for events that start at this time
            for (Event event : calendar.getEventList()) {
                if (event.getStart().equals(ts)) {
                    // Notify user
                    // TODO: Need to figure out how to get the client in here...
                }
            }

            // Sleep the thread for 5 seconds then start over
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Clock was interrupted for User: " + calendar.getOwner());
            }
        }
    }
}
