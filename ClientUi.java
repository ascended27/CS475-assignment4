import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class ClientUi {

    public static void main(String[] args) {
        System.out.println("args.length: " + args.length);
        if (args.length == 1) {
            try {
                ClientImpl client = new ClientImpl(args[0]);
                CalendarManager cm = CalendarManagerImpl.getInstance();

                CalendarImpl cal = (CalendarImpl) cm.getCalendar(client);

                System.out.println("Owner: " + cal.getOwner().getName());

                Timestamp start = new Timestamp(System.currentTimeMillis() + 60000);
                Timestamp stop = new Timestamp((long) (System.currentTimeMillis() + 5 * (3.6 * Math.pow(6, 10))));
                cal.insertOpenEvent(client, start, stop, false);
//              Event e = cal.retrieveEvent(client, start, stop);
                Timestamp eventStart = new Timestamp(System.currentTimeMillis() + 5 * 60000);
                Timestamp eventStop = new Timestamp((long) (System.currentTimeMillis() + 2 * (3.6 * Math.pow(6, 10))));
                cal.scheduleEvent(client, new ArrayList<>(), "Test", eventStart, eventStop, false);
                ArrayList<Event> events = cal.getEventList();
                for (Event event : events) {
                    if(event.isOpen())
                        System.out.printf("Open Event: \n\tStart: %s\n\tStop: %s\n\n", event.getStart().toString(), event.getEnd().toString());
                    else {
                        System.out.printf("Event: %s\n\tStart: %s\n\tStop: %s\n\tOwner: %s\n\tOpen: %b\n\tPublic: %b\n\tAttendees: %s\n\n",
                                event.getTitle(), event.getStart().toString(), event.getEnd().toString(),
                                event.getOwner().getName(), event.isOpen(), event.isType(), event.getAttendees().toString());
                    }
                }


            } catch (RemoteException e) {
                System.out.println("Error: " + e);
            }

        } else {
            System.out.println("usage: ClientImpl <username>");
        }
    }

}
