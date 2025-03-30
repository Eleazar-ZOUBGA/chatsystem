package chatsystem.network.TCP;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import chatsystem.contacts.Contact;
import chatsystem.contacts.User;
import chatsystem.DataBase.DataBase;

public class ClientHandler implements Runnable {

    private User user;
    private final Socket socket;
    private ArrayList<Contact> contacts;
    private Contact contact;
    private DataBase db;

    public ClientHandler(User user, Socket socket, ArrayList<Contact> contacts, DataBase db) {
        this.user = user;
        this.socket = socket;
        this.contacts = contacts;
        this.db = db;
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(socket.getInputStream())) {
            int id = in.readInt();
            for (Contact c : contacts) {
                if (c.getId() == id){
                    contact = c;
                }
            }
            while (true) {
                String messageReception = in.readUTF();
                contact.receiveMessage(messageReception);  // Notify observers

                // Enregistrer le message dans la base de données
                db.insertMessage(contact.getId(), user.getUserId() ,messageReception, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
        } catch (IOException e) {
            System.out.println("Erreur dans la gestion du client : " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Erreur lors de la fermeture du socket: " + e.getMessage());
            }
        }
    }
}
