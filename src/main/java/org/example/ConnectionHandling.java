package org.example;

import java.io.IOException;
import java.net.*;

public class ConnectionHandling {

    /*
    private static User user;
    private static int senderPort;
    private static int receiverPort;
    private static int [] dest;

    public static void create(User usr, int o, int i, int [] d){
        user = usr;
        senderPort = o;
        receiverPort = i;
        dest = d;
    }

    public static void connection() {
        Thread tr = new ReceiveThread(user, receiverPort);
        tr.start();
        Thread ts = new SendThread(user, senderPort, dest);
        ts.start();
    }
    */
    private static Thread recvThread;
    private static Thread sendThread;

    public static void create(User user/*, int senderPort, int receiverPort*/, int [] dest){
        recvThread = new ReceiveThread(user/*, receiverPort*/, dest);
        sendThread = new SendThread(user/*, senderPort*/, dest);
    }

    public static void start() {
        recvThread.start();
        sendThread.start();
    }
}
