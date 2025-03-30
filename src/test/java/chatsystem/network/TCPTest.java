package chatsystem.network;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.InetAddress;

import org.junit.jupiter.api.Test;

import chatsystem.contacts.Contact;
import chatsystem.contacts.User;
import chatsystem.contacts.UserAlreadyExists;
import chatsystem.network.TCP.TCPClient;
import chatsystem.network.TCP.TCPServer;
import chatsystem.DataBase.DataBase;
import chatsystem.DataBase.DatabaseControllers;

public class TCPTest {

    @Test
    public void testEchangeMessage() throws UserAlreadyExists, IOException, InterruptedException {
        User user1 = new User(0, "test");
        User user2 = new User(1, "client");
        user1.setPort(1234);
        user1.addList("1", "client", InetAddress.getLocalHost(), 12345);
        user2.setPort(12345);
        user2.addList("0", "test", InetAddress.getLocalHost(), 1234);

        // Initialisation de la base de données
        DataBase db = new DataBase("messagerie.db");
        DatabaseControllers dbController = new DatabaseControllers("messagerie.db");
        db.connect(dbController.connect());
        db.createUserMessagesTable();

        Contact serverContact = user2.getContactByName("test");
        Contact client = user1.getContactByName("client");

        TCPServer server = new TCPServer(user1, user1.getPort(), user1.getContactsList(), db);
        new Thread(server).start();

        TCPClient tcpClient = new TCPClient(serverContact.getAdress(), serverContact.getPort(), user2.getUserId(), serverContact, db);
        new Thread(tcpClient).start();

        String messageClient = "TEST";
        client.addObserver(new Contact.MessageObserver() {
            @Override
            public void updateMessage(Contact contact, String message) {
                assertEquals(messageClient, message);
                assertEquals(client, contact);
            }
        });

        Thread.sleep(200);
        tcpClient.sendMessage(messageClient);
    }
}
