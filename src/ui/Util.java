package src.ui;

import javafx.collections.ObservableList;
import src.*;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Util {

    private static Util instance;
    private CalendarManager cm;
    private Client owner;
    private ObservableList<EventRow> observableList;
    private EventRow retrievedEventRow;

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
                boolean toReturn = cm.getCalendar(owner).insertOpenEvent(e.getOwner(), e.getStart(), e.getStop());
                if (toReturn && observableList != null)
                    observableList.add(new EventRow(e.getOwner().getName(), e.getTitle(), e.getStart(), e.getStop()));
                return toReturn;
            } else
                return false;
        } catch (RemoteException ex) {
            return false;
        }
    }

    public Event retrieveEvent(Timestamp start, Timestamp stop) {
        try {
            return cm.getCalendar(owner).retrieveEvent(owner, start, stop);
        } catch (RemoteException e) {
            return null;
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

    public ArrayList<Client> getUsers() {
        try {
            return (ArrayList<Client>) cm.allUsers();
        } catch (RemoteException e) {
            return new ArrayList<>();
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


    public Timestamp convertTime(String time) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");
            Timestamp toReturn;
            Date parsedDate;
            parsedDate = dateFormat.parse(time);
            toReturn = new java.sql.Timestamp(parsedDate.getTime());
            return toReturn;
        } catch (ParseException e) {
            return null;
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

    public void setRetrievedEventRow(EventRow retrievedEventRow) {
        this.retrievedEventRow = retrievedEventRow;
    }

    public EventRow getRetrievedEventRow() {
        return retrievedEventRow;
    }
}
