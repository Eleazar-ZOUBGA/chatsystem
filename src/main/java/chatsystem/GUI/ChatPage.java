package chatsystem.GUI;

import chatsystem.contacts.Contact;
import chatsystem.contacts.User;
import chatsystem.network.TCP.TCPClient;
import chatsystem.DataBase.DataBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatPage extends JFrame {
    private User user;
    private Contact contact;
    private TCPClient tcpClient;
    private JTextArea chatArea;
    private DataBase db;

    public ChatPage(User user, Contact contact, DataBase db, ContactListPage contactListPage) {
        this.user = user;
        this.contact = contact;
        this.db = db;
        setTitle("Discussion avec " + contact.getName() + " - Connecté en tant que " + user.getUserName());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JTextField messageField = new JTextField(20);
        JButton sendButton = new JButton("Envoyer");
        JButton backButton = new JButton("Retour");
        JButton historyButton = new JButton("Voir l'historique");

        // Register in this ChatPage an observer
        contact.addObserver(new Contact.MessageObserver() {
            @Override
            public void updateMessage(Contact contact, String message) {
                appendMessage(contact.getName(), message);
            }
        });

        // Initialiser le client TCP pour envoyer des messages
        tcpClient = new TCPClient(contact.getAdress(), contact.getPort(), user.getUserId(), contact, db);
        new Thread(tcpClient).start();

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText();
                appendMessage("Vous", message);
                messageField.setText("");
                // Envoyer le message au contact via TCP
                tcpClient.sendMessage(message);
                // Enregistrer le message dans la base de données
                db.insertMessage(user.getUserId(), contact.getId(), message, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (contactListPage != null) {
                    contactListPage.getOpenChatPages().remove(ChatPage.this);
                }
                dispose();
            }
        });

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MessageHistoryPage(user, contact, db, contactListPage).setVisible(true);
                if (contactListPage != null) {
                    contactListPage.getOpenChatPages().remove(ChatPage.this);
                }
                dispose();
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        panel.add(messageField, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(sendButton);
        buttonPanel.add(backButton);
        buttonPanel.add(historyButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Charger les messages précédents
        loadPreviousMessages();
    }

    // Méthode pour ajouter un message à la zone de texte avec un horodatage
    public void appendMessage(String sender, String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        chatArea.append("[" + timestamp + "] " + sender + ": " + message + "\n");
    }

    public void appendMessage(String sender, String message, String time) {
        chatArea.append("[" + time + "] " + sender + ": " + message + "\n");
    }

    public void updateTitle() {
        setTitle("Discussion avec " + contact.getName() + " - Connecté en tant que " + user.getUserName());
    }

    public Contact getContact(){
        return this.contact;
    }

    // Méthode pour charger les messages précédents
    private void loadPreviousMessages() {
        List<String> messages = db.getMessagesBetween(user.getUserId(), contact.getId());
        for (String message : messages) {
            String[] parts = message.split("::");
            if (parts.length == 3) {
                if (parts[0].equals(Integer.toString(contact.getId()))){
                    appendMessage(contact.getName(), parts[1], parts[2]);
                }
                else{
                    appendMessage("Vous", parts[1], parts[2]);
                }
            }
        }
    }
}
