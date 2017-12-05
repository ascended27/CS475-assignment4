package test;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import src.*;

import javax.swing.*;
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

        for (int i = 0; i < 3; i++) {
            Client client = new ClientImpl("Client " + i);
            users.add(client);

            Timestamp start = new Timestamp(System.currentTimeMillis());
            Timestamp stop = new Timestamp(System.currentTimeMillis() + 24 * HALF_HOUR);
//            System.out.println(String.format("Start: %s\nStop: %s", start.toString(), stop.toString()));
            System.out.println("Open Event inserted: " + cManager.getCalendar(client).insertOpenEvent(client, start, stop, false));
        }
    }

    @Test
    public void allUsers() {
        JOptionPane.showMessageDialog(null, "ALL USERS");
        try {
            List<Client> allUsers = cManager.allUsers();
            Assert.assertEquals(3, allUsers.size());
        } catch (RemoteException e) {

        }
    }

    @Test
    public void scheduleGroupEvent() {
        try {
            JOptionPane.showMessageDialog(null, "GROUP EVENT");

            Client client = new ClientImpl("Client 1");
            Calendar calendar = cManager.getCalendar(client);

            //TODO: BUG IS HERE. SHOULD NOT REMOVE USERS FROM ALL USERS. INSTEAD OF MODIFYING, USE ITERATOR AND SKIP CURRENT USER
            List<Client> allUsers = cManager.allUsers();
            allUsers.remove(client);

            Timestamp start = new Timestamp(System.currentTimeMillis());
            Timestamp stop = new Timestamp(System.currentTimeMillis() + 1 * HALF_HOUR);

            Assert.assertTrue(calendar.scheduleEvent(client, allUsers, "Test Group 1", start, stop, false));

            int eventCount = 0;

            for(Client theClient: allUsers)
            {
//                System.out.println("Current Client: "+theClient.getName());
                Calendar current = cManager.getCalendar(theClient);
                Event event = current.retrieveEvent(theClient, start, stop);
                if(event != null)
                    eventCount++;
            }

            Assert.assertEquals(2, eventCount);
        } catch (RemoteException e) {
            System.out.println("There was a remote exception");
        }
    }
}