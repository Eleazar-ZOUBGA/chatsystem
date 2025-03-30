package chatsystem;

import chatsystem.GUI.HomePage;
import chatsystem.network.TCP.TCPServer;
import chatsystem.network.UDP.ConnectionHandling;
import chatsystem.contacts.User;
import chatsystem.DataBase.DataBase;
import chatsystem.DataBase.DatabaseControllers;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static final int[] dest = {4000, 4001, 4002, 4003, 4005, 4006, 4007, 4008, 4009};

    public static void sleep(int n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            System.err.println("Interruption of sleep");
        }
    }

    public static void main(String[] args) {
        User user = new User(-1, "_");

        // Initialisation de la base de données
        DataBase db = new DataBase("messagerie.db");
        DatabaseControllers dbController = new DatabaseControllers("messagerie.db");
        db.connect(dbController.connect());
        db.createUserMessagesTable();

        // Lancement de l'interface graphique
        SwingUtilities.invokeLater(() -> {
            HomePage homePage = new HomePage(user, db);
            homePage.setVisible(true);
        });

        // Lance UDP : contact discovery
        ConnectionHandling.create(user, dest);
        ConnectionHandling.start();

        // Code pour lancer le serveur TCP :
        sleep(100); // attente pour bien initialiser le numéro de port
        try {
            TCPServer server = new TCPServer(user, user.getPort(), user.getContactsList(), db);
            new Thread(server).start();
        } catch (IOException e) {
            System.err.println("Erreur creation serveur");
            System.exit(1);
        }

        // Utilisation sur le terminal
        /*
        user.assignUserId();
        System.out.println("ID : " + user.getUserId());

        // choix du nom
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean hasName = false;
        while (!hasName) {
            try {
                System.out.println("Choose a name : ");
                String name = reader.readLine();
                hasName = user.selectName(name);
            } catch (IOException e) {
                System.err.println("Erreur ReadLine");
            }
        }
        System.out.println("Name : " + user.getUserName());

        boolean running = true;
        while (running) {
            try {
                System.out.println("Choisir :");
                System.out.println("0 : Voir la liste de contact");
                System.out.println("1 : Choisir avec qui parler");
                System.out.println("q : Quitter");
                System.out.println("autre : rien");
                switch (reader.readLine()) {
                    case "0":
                        System.out.println("Contacts :");
                        for (Contact u : user.getContactsList()) {
                            System.out.println(u);
                        }
                        break;

                    case "1":
                        System.out.println("Choisir un contact : ");
                        String name = reader.readLine();
                        Contact user1 = null;
                        for (Contact u : user.getContactsList()) {
                            if (u.getName().equals(name)) {
                                user1 = u;
                            }
                        }
                        if (user1 != null) {
                            TCPClient tcpClient = new TCPClient(user1.getAdress(), user1.getPort(), user.getUserId(), user1, db);
                            new Thread(tcpClient).start();

                            boolean chatting = true;
                            while (chatting) {
                                System.out.println("Entrez un message (ou 'q' pour quitter la conversation) : ");
                                String message = reader.readLine();
                                if (message.equals("q")) {
                                    chatting = false;
                                    tcpClient.stop(); // Arrêter le client TCP
                                } else {
                                    tcpClient.sendMessage(message); // Envoyer le message
                                }
                            }

                        } else {
                            System.out.println("Le contact n'existe pas");
                        }
                        break;
                    case "q":
                        running = false;
                        break;
                    default:
                        System.out.println();
                }
            } catch (IOException e) {
                System.err.println("Erreur ReadLine");
            }
        }
        */
    }
}
