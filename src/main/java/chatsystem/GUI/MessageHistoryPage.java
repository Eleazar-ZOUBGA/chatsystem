package chatsystem.GUI;

import chatsystem.contacts.Contact;
import chatsystem.contacts.User;
import chatsystem.DataBase.DataBase;
import chatsystem.network.TCP.TCPClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MessageHistoryPage extends JFrame {
    private User user;
    private Contact contact;
    private DataBase db;
    private JTextArea chatArea;
    private TCPClient tcpClient;

    public MessageHistoryPage(User user, Contact contact, DataBase db, ContactListPage contactListPage) {
        this.user = user;
        this.contact = contact;
        this.db = db;
        setTitle("Historique des messages avec " + contact.getName());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        JTextField messageField = new JTextField(20);
        add(messageField, BorderLayout.SOUTH);

        JButton continueButton = new JButton("Continuer la conversation");
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChatPage chatPage = new ChatPage(user, contact, db, contactListPage);
                chatPage.setVisible(true);
                if (contactListPage != null) {
                    contactListPage.getOpenChatPages().add(chatPage);
                }
                dispose();
            }
        });

        JButton backButton = new JButton("Retour");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contactListPage.setVisible(true);
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(continueButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Charger les messages précédents
        loadPreviousMessages();
    }

    // Méthode pour ajouter un message à la zone de texte avec un horodatage
    public void appendMessage(String sender, String message, String time) {
        chatArea.append("[" + time + "]" + sender + " : " + message + "\n");
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
