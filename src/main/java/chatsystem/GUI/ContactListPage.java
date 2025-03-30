package chatsystem.GUI;

import chatsystem.contacts.Contact;
import chatsystem.contacts.User;
import chatsystem.DataBase.DataBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

public class ContactListPage extends JFrame {
    private User user;
    private DefaultListModel<String> contactListModel;
    private JList<String> contactList;
    private Set<ChatPage> openChatPages = new HashSet<>();
    private DataBase db;

    public ContactListPage(User user, DataBase db) {
        this.user = user;
        this.db = db;
        setTitle("Liste des Contacts - Connecté en tant que " + user.getUserName());
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panneau pour afficher la liste des contacts
        JPanel contactListPanel = new JPanel();
        contactListPanel.setLayout(new BorderLayout());
        contactListPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel contactListLabel = new JLabel("Utilisateurs en ligne");
        contactListLabel.setFont(new Font("Serif", Font.BOLD, 18));
        contactListLabel.setHorizontalAlignment(JLabel.CENTER);
        contactListPanel.add(contactListLabel, BorderLayout.NORTH);

        contactListModel = new DefaultListModel<>();
        contactList = new JList<>(contactListModel);
        contactListPanel.add(new JScrollPane(contactList), BorderLayout.CENTER);

        add(contactListPanel, BorderLayout.CENTER);

        // Panneau de boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton startChatButton = new JButton("Choisir un contact");
        startChatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedContact = contactList.getSelectedValue();
                if (selectedContact != null) {
                    Contact contact = user.getContactByName(selectedContact);
                    if (contact != null) {
                        boolean chatOpen = openChatPages.stream().anyMatch(chatPage -> chatPage.getContact().equals(contact));
                        if (!chatOpen) {
                            ChatPage chatPage = new ChatPage(user, contact, db, ContactListPage.this);
                            openChatPages.add(chatPage);
                            chatPage.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(ContactListPage.this, "Cette discussion est déjà ouverte.");
                        }
                    }
                }
            }
        });

        JButton viewHistoryButton = new JButton("Voir l'historique des messages");
        viewHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedContact = contactList.getSelectedValue();
                if (selectedContact != null) {
                    Contact contact = user.getContactByName(selectedContact);
                    if (contact != null) {
                        new MessageHistoryPage(user, contact, db, ContactListPage.this).setVisible(true);
                    }
                }
            }
        });

        JButton changePseudoButton = new JButton("Changer de pseudonyme");
        changePseudoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PseudonymPage(user, db, ContactListPage.this).setVisible(true);
                dispose();
            }
        });

        JButton backButton = new JButton("Retour");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomePage(user, db).setVisible(true);
                dispose();
            }
        });

        buttonPanel.add(startChatButton);
        buttonPanel.add(viewHistoryButton);
        buttonPanel.add(changePseudoButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Timer pour rafraîchir la liste des contacts
        Timer timer = new Timer(5000, new ActionListener() { // chaque 5 secondes
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshContactList();
                updateTitle();
            }
        });
        timer.start();

        // Initialiser la liste des contacts
        refreshContactList();
    }

    public void updateTitle() {
        setTitle("Liste des Contacts - Connecté en tant que " + user.getUserName());
        for (ChatPage chatPage : openChatPages) {
            chatPage.updateTitle();
        }
    }

    private void refreshContactList() {
        contactListModel.clear(); // Effacer les éléments existants
        for (Contact contact : user.getContactsList()) {
            contactListModel.addElement(contact.getName());
        }
    }

    public Set<ChatPage> getOpenChatPages() {
        return openChatPages;
    }
}
