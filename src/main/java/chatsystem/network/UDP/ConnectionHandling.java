package chatsystem.network.UDP;

import chatsystem.contacts.User;

public class ConnectionHandling {

    private static Thread recvThread;
    private static Thread sendThread;

    public static void create(User user, int [] dest){
        recvThread = new ReceiveThread(user, dest);
        sendThread = new SendThread(user, dest);
    }

    public static void start() {
        recvThread.start();
        sendThread.start();
    }
}