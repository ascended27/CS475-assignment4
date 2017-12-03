package src;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Clock extends Thread {

    private Calendar calendar;

    public Clock(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public void run() {
        // Do it until the thread is stopped
        try {
            ArrayList<Event> eventList = (ArrayList<Event>) calendar.getEventList();

            while (true) {
                // Get the current time
                Timestamp ts = new Timestamp(System.currentTimeMillis());
                System.out.println("Time: " + ts.toString());

                // Look for events that start at this time

                if (eventList != null && eventList.size() > 0)
                    for (Event event : eventList) {
                        if (ts.compareTo(event.getStart()) >= 0 && !event.hasPassed()) {
                            // Notify user
                            calendar.getOwner().notify(event);
                            event.setPassed(true);
                        }
                    }

                // Sleep the thread for 5 seconds then start over
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println("Clock was interrupted for User: " + calendar.getOwner());
                }
            }
        } catch (RemoteException e) {
            System.out.println("Remote Exception: " + e);
        }
    }
}
