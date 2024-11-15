package org.example;

import java.io.IOException;
import java.net.*;

public class SendThread extends Thread {

    public static User user;
    //private static int senderPort;
    private static int [] dest;

    public SendThread(User user/*, int senderPort*/, int [] dest){
        this.user = user;
        //this.senderPort = senderPort;
        this.dest = dest;
    }

    public static void sendEcho(DatagramSocket outSocket, String msg) throws IOException{
        byte[] buf = msg.getBytes();
        InetAddress destAddress = InetAddress.getByName("255.255.255.255");
        for (int d : dest){
            int destPort = d;
            DatagramPacket outPacket = new DatagramPacket(buf, buf.length, destAddress, destPort);
            outSocket.send(outPacket);
        }
    }

    @Override
    public void run() {
        try {
            DatagramSocket outSocket = new DatagramSocket(/*senderPort*/);
            boolean running = true;

            while (running) {
                if (user.getUserId() != -1) {
                    sendEcho(outSocket, user.getUserId() + " " + user.getUserName());
                }
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    System.err.println("Interuption of sleep");
                }

            }
            outSocket.close();
        } catch (IOException e){
            System.out.println("Erreur d'envoie du message UDP");
            System.exit(1);
        }
    }
}