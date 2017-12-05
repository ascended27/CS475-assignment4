package src.ui;

import javafx.collections.ObservableList;
import src.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Util {

    private static Util instance;
    private CalendarManager cm;
    private Client owner;
    private ObservableList<EventRow> observableList;

    private Util() throws RemoteException {
        cm = CalendarManagerImpl.getInstance();
    }

    public static Util getInstance() {
        if (instance == null) {
            try {
                instance = new Util();
            } catch (RemoteException ex) {
                return null;
            }
        }
        return instance;
    }

    public ArrayList<Event> getEventList() {
        try {
            if (owner != null) {
                Calendar cal = cm.getCalendar(owner);
                ArrayList<Event> toReturn = new ArrayList<>();
                toReturn.addAll(cal.getEventList());
                return toReturn;
            } else
                return null;
        } catch (RemoteException e) {
            return null;
        }
    }

    public boolean scheduleEvent(Event e) {
        try {
            if (owner != null) {

                boolean toReturn = cm.getCalendar(owner).scheduleEvent(e.getOwner(), e.getAttendees(), e.getTitle(), e.getStart(), e.getStop(), e.isType());

                // May split an open event so just clear them all out and replace them
                if (toReturn && observableList != null) {
                    observableList.clear();
                    for (Event event : cm.getCalendar(owner).getEventList()) {
                        EventRow er = new EventRow(event.getOwner().getName(), event.getTitle(), event.getStart(), event.getStop());
                        observableList.addAll(er);
                    }
                }

                return toReturn;
            } else return false;
        } catch (RemoteException ex) {
            return false;
        }
    }

    public boolean insertOpenEvent(Event e) {
        try {
            if (owner != null) {
                boolean toReturn = cm.getCalendar(owner).insertOpenEvent(e.getOwner(), e.getStart(), e.getStop(), e.isType());
                if (toReturn && observableList != null)
                    observableList.add(new EventRow(e.getOwner().getName(), e.getTitle(), e.getStart(), e.getStop()));
                return toReturn;
            } else
                return false;
        } catch (RemoteException ex) {
            return false;
        }
    }

    public boolean killClock() {
        try {
            if (owner != null) {
                Calendar cal = cm.getCalendar(owner);
                if (cal != null) {
                    cal.killClock(owner);
                    return true;
                } else
                    return false;
            } else return false;
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean checkUser(String username) {
        try {
            for (Client user : cm.allUsers()) {
                if (user.getName().equals(username)) {
                    owner = user;
                    break;
                }
            }
            if (owner == null) {
                owner = new ClientImpl(username);
            }
            return true;
        } catch (RemoteException e) {
            return false;
        }
    }

    public void registerTableList(ObservableList<EventRow> list) {
        this.observableList = list;
    }

    public ObservableList<EventRow> getTableList() {
        return observableList;
    }

    public void setOwner(Client client) {
        this.owner = client;
    }

    public Client getOwner() {
        return this.owner;
    }
}
