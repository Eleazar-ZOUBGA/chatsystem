package chatsystem.network.TCP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import chatsystem.contacts.Contact;
import chatsystem.contacts.User;
import chatsystem.DataBase.DataBase;
import chatsystem.DataBase.DatabaseControllers;

public class TCPServer implements Runnable {
    private User user;
    private static int PORT; // Port d'écoute
    private final ServerSocket serverSocket;

    private ArrayList<Contact> contacts;
    private DataBase db;

    public TCPServer(User user, int port, ArrayList<Contact> contacts, DataBase db) throws IOException {
        this.user = user;
        PORT = port;
        serverSocket = new ServerSocket(PORT);
        this.contacts = contacts;
        this.db = db;
    }

    public void run() {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("La connexion a été bien établie avec : " + clientSocket);
                ClientHandler handler = new ClientHandler(user, clientSocket, contacts, db);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            System.err.println("Erreur dans le serveur :");
            e.printStackTrace();
        }
        finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Erreur lors de l'arrêt du serveur :");
                e.printStackTrace();
            }
        }
    }
}
