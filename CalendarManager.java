import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * The Calendar Manager class will handle all
 * the different Calendar objects that are being
 * used by the system.
 * <p>
 * ------- From the PDF ------------------------------
 * The Calendar Manager acts as a class factory for Calendar objects.
 * Each Calendar User Interface program should have a command-line argument
 * that is the name of its user. If a calendar does not already exist for
 * that user, the User Interface object should create a calendar object
 * for that user by invoking the appropriate method in the Calendar Manager object.
 * The User Interface can also query the Calendar Manager to obtain a list
 * of names of other users in the work group.
 * ---------------------------------------------------
 */
public interface CalendarManager extends Remote {

    /**
     * Gets the user's calendar if it exists otherwise creates a new
     * calendar and returns that.
     *
     * @param user The user of the calendar to get
     * @return The calendar
     * @throws RemoteException
     */
    Calendar getCalendar(User user) throws RemoteException;

	public Calendar makeCalendar(User user);
}
