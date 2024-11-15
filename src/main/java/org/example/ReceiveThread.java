package org.example;

import java.io.IOException;
import java.net.*;

public class ReceiveThread extends Thread {

    public static User user;
    //private static int receiverPort;
    private static int [] dest;

    public ReceiveThread(User user/*, int receiverPort*/, int [] dest){
        this.user = user;
        //this.receiverPort = receiverPort;
        this.dest = dest;
    }

    public static String receiveEcho(DatagramSocket inSocket) throws IOException{
        byte[] buf = new byte[256];
        DatagramPacket inPacket = new DatagramPacket(buf, buf.length);
        inSocket.receive(inPacket);
        String received = new String(inPacket.getData(), 0, inPacket.getLength());
        return received;
    }
    @Override
    public void run() {
        boolean ok = true;
        int n = 0;
        while (ok) {
            ok = false;
            try {
                DatagramSocket inSocket = new DatagramSocket(dest[n]);
                //DatagramSocket inSocket = new DatagramSocket(receiverPort);
                boolean running = true;

                while (running) {
                    String received = receiveEcho(inSocket);
                    String m[] = received.split(" ");
                    if (user.getUserId() == Integer.valueOf(m[0]) && user.getUserName() != m[1]) {

                    }
                    user.addList(m[0], m[1]);
                    user.updateList(m[0], m[1]);
                    //System.out.println(user.getInnerUsersList());
                }
                inSocket.close();
            } catch (IOException e) {
                ok = true;
                n++;
                if (n == dest.length){
                    System.out.println("Erreur de réception du message UDP");
                    System.exit(2);
                }
            }
        }
    }
}