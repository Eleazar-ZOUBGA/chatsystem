package chatsystem.network.TCP;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import chatsystem.contacts.Contact;
import chatsystem.DataBase.DataBase;

public class TCPClient implements Runnable{
    private static InetAddress SERVER_ADDRESS;
    private static int SERVER_PORT;

    private int id;
    private DataOutputStream out;
    private boolean running = true;
    private Contact contact;
    private DataBase db;

    public TCPClient(InetAddress address, int port, int id, Contact contact, DataBase db){
        SERVER_ADDRESS = address;
        SERVER_PORT = port;
        this.id = id;
        this.contact = contact;
        this.db = db;
    }

    public void run() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);   // Demande de connexion
            DataOutputStream out = new DataOutputStream(socket.getOutputStream())) { 
            System.out.println("connexion etablie");
        
            out.writeInt(id);

            this.out = out; // Initialisation de out

            while(running){}
            
        } catch (IOException e ) {
            System.err.println("Erreur dans le client");
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        try {
            out.writeUTF(message); // Envoi du message

            // Enregistrer le message dans la base de données
            //db.insertMessage(id, contact.getId(), message, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } catch (IOException e) {
            System.err.println("Erreur lors de l'envoi du message");
            e.printStackTrace();
        }
    }

    public void stop(){
        running = false;
    }
}
