package chatsystem.network.UDP;

import chatsystem.contacts.User;
import chatsystem.contacts.UserAlreadyExists;

import java.io.IOException;
import java.net.*;

public class ReceiveThread extends Thread {

    public static User user;
    private static int [] dest;

    public ReceiveThread(User usr, int [] dst){
        user = usr;
        dest = dst;
    }

    public static String receiveEcho(DatagramSocket inSocket) throws IOException{
        byte[] buf = new byte[256];
        DatagramPacket inPacket = new DatagramPacket(buf, buf.length);
        inSocket.receive(inPacket);
        return new String(inPacket.getData(), 0, inPacket.getLength());
    }
    @Override
    public void run() {
        byte[] buf = new byte[256];
        boolean ok = true;
        int n = 0;
        while (ok) {
            ok = false;
            try {
                DatagramSocket inSocket = new DatagramSocket(dest[n]);
                boolean running = true;

                while (running) {
                    DatagramPacket inPacket = new DatagramPacket(buf, buf.length);
                    inSocket.receive(inPacket);
                    String received = new String(inPacket.getData(), 0, inPacket.getLength());
                    String m[] = received.split(" ");
                    try {
                        user.addList(m[0], m[1], inPacket.getAddress(), inPacket.getPort()+1);
                    }
                    catch (UserAlreadyExists e) {
                        user.updateList(m[0], m[1]);
                    }
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