package chatsystem.network;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import chatsystem.contacts.User;
import chatsystem.network.UDP.ReceiveThread;
import chatsystem.network.UDP.SendThread;

public class UDPTest {

    private User user1 = new User(1, "Alice");
    private User user2 = new User(2, "Jean");
    private int[] destinationPorts = {8080};
    private ReceiveThread receiveThread = new ReceiveThread(user1, destinationPorts);
    private SendThread sendThread;

    @Test
    public void testContactDicovery() throws InterruptedException{
        sendThread = new SendThread(user2, destinationPorts);
        receiveThread.start();
        sendThread.start();
        Thread.sleep(1000);
        assertEquals(1, user1.getContactsList().size());       
        sendThread = new SendThread(user1, destinationPorts);
        sendThread.start();
        Thread.sleep(5000);
        assertEquals(0, user1.getContactsList().size());
    }
}
