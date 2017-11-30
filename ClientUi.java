import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class ClientUi {

    public static void main(String[] args) {
        System.out.println("args.length: " + args.length);
        if (args.length < 1) {
            System.out.println("usage: ClientImpl <username>");
            System.exit(1);
        }

        String name = args[0];

        // Install security manager.  This is only necessary
        // if the remote object's client stub does not reside
        // on the client machine (it resides on the server).
        //  System.setSecurityManager(new SecurityManager());

        // Get a remote reference to the EchoImpl class
        String strName = "rmi://localhost/CalendarService";
//      String strName = "rmi://localhost:2934/EchoService";
        System.out.println("Client: Looking up " + strName + "...");
        CalendarManager remCM = null;

        try {
            remCM = (CalendarManager) Naming.lookup(strName);
        } catch (Exception e) {
            System.out.println("Client: Exception thrown looking up " + strName);
            System.exit(1);
        }

        try {
            System.setSecurityManager(new SecurityManager());
            System.out.println("Server: Registering Client Service");
            ClientImpl client = new ClientImpl(name);
            Naming.rebind("ClientService", client);
            System.out.println("Server: Ready...");

            // Get the calendar
            Calendar cal = remCM.getCalendar(client);
            System.out.println("Owner: " + cal.getOwner().getName());

            // Insert Open Event
            Timestamp start = new Timestamp(System.currentTimeMillis() + 60000);
            Timestamp stop = new Timestamp((long) (System.currentTimeMillis() + 5 * (3.6 * Math.pow(6, 10))));
            System.out.println("Open Event inserted: " + cal.insertOpenEvent(client, start, stop, false));

            // Schedule Event
            Timestamp eventStart = new Timestamp(System.currentTimeMillis() + 5 * 60000);
            Timestamp eventStop = new Timestamp((long) (System.currentTimeMillis() + 2 * (3.6 * Math.pow(6, 10))));
            System.out.println("Event Scheduled: " + cal.scheduleEvent(client, new ArrayList<>(), "Test", eventStart, eventStop, false));

            // Print all the events
            ArrayList<Event> events = (ArrayList<Event>) cal.getEventList();
            for (Event event : events) {
                if (event.isOpen())
                    System.out.printf("Open Event: \n\tStart: %s\n\tStop: %s\n\n", event.getStart().toString(), event.getEnd().toString());
                else {
                    System.out.printf("Event: %s\n\tStart: %s\n\tStop: %s\n\tOwner: %s\n\tOpen: %b\n\tPublic: %b\n\tAttendees: %s\n\n",
                            event.getTitle(), event.getStart().toString(), event.getEnd().toString(),
                            event.getOwner().getName(), event.isOpen(), event.isType(), event.getAttendees().toString());
                }
            }
        } catch (Exception e) {
            System.out.println("Client: Exception thrown calling getCalendar: " + e);
            System.exit(1);
        }
    }

}