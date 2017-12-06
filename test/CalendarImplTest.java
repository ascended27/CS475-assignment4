package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import src.*;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CalendarImplTest {

    CalendarManager cManager;
    List<Client> users;
    final long HALF_HOUR = 30 * 60 * 1000;

    @Before
    public void setUp() throws Exception {
        cManager = CalendarManagerImpl.getInstance();
        users = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Client client = new ClientImpl("Client " + i);
            users.add(client);

            Timestamp start = new Timestamp(System.currentTimeMillis());
            Timestamp stop = new Timestamp(System.currentTimeMillis() + 24 * HALF_HOUR);
//            System.out.println(String.format("Start: %s\nStop: %s", start.toString(), stop.toString()));
            System.out.println("Open Event inserted: " + cManager.getCalendar(client).insertOpenEvent(client, start, stop));
        }
    }

    @Test
    public void allUsers() {
        try {
            List<Client> allUsers = cManager.allUsers();
            Assert.assertEquals(5, allUsers.size());
        } catch (RemoteException e) {

        }
    }

    @Test
    public void scheduleGroupEvent() {
        try {
            Client client = new ClientImpl("Client 1");
            Calendar calendar = cManager.getCalendar(client);

            List<Client> allUsers = cManager.allUsers();
            allUsers.remove(client);

            Timestamp start = new Timestamp(System.currentTimeMillis());
            Timestamp stop = new Timestamp(System.currentTimeMillis() + 1 * HALF_HOUR);

            Assert.assertTrue(calendar.scheduleEvent(client, allUsers, "Test Group 1", start, stop, false));
//
//            int eventCount = 0;
//
//            for(Client theClient: allUsers)
//            {
////                System.out.println("Current Client: "+theClient.getName());
//                Calendar current = cManager.getCalendar(theClient);
//                Event event = current.retrieveEvent(theClient, start, stop);
//                if(event != null)
//                    eventCount++;
//            }
//
//            Assert.assertEquals(5, eventCount);
        } catch (RemoteException e) {
            System.out.println("There was a remote exception");
        }
    }

    @Test
    public void scheduleGroupEventCheckAttendees() {
        try {
            Client client = new ClientImpl("Client 1");
            Calendar calendar = cManager.getCalendar(client);

            List<Client> allUsers = cManager.allUsers();
            allUsers.remove(client);

            Timestamp start = new Timestamp(System.currentTimeMillis());
            Timestamp stop = new Timestamp(System.currentTimeMillis() + 1 * HALF_HOUR);

            calendar.scheduleEvent(client, allUsers, "Test Group 1", start, stop, false);

            int eventCount = 0;

            for (Client theClient : allUsers) {
//                System.out.println("Current Client: "+theClient.getName());
                Calendar calendarForClient = cManager.getCalendar(theClient);
                Event event = calendarForClient.retrieveEvent(theClient, start, stop);
                if (event != null)
                    eventCount++;
            }

            Assert.assertEquals(5, eventCount);
        } catch (RemoteException e) {
            System.out.println("There was a remote exception");
        }
    }
}