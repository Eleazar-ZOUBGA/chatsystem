package chatsystem.GUI;

import chatsystem.contacts.User;
import chatsystem.DataBase.DataBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PseudonymPage extends JFrame {
    private User user;
    private DataBase db;
    private ContactListPage contactListPage;

    public PseudonymPage(User user, DataBase db, ContactListPage contactListPage) {
        this.user = user;
        this.db = db;
        this.contactListPage = contactListPage;
        setTitle("Choisir un pseudonyme");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel pseudonymPanel = new JPanel();
        pseudonymPanel.setLayout(new GridBagLayout());
        pseudonymPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel pseudonymLabel = new JLabel("Pseudonyme:");
        pseudonymPanel.add(pseudonymLabel, gbc);

        gbc.gridx = 1;
        JTextField pseudonymField = new JTextField(20);
        pseudonymPanel.add(pseudonymField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pseudonym = pseudonymField.getText();
                boolean isPseudoAvailable = user.selectName(pseudonym);

                if (isPseudoAvailable) {
                    user.setUserName(pseudonym);
                    JOptionPane.showMessageDialog(null, "Pseudonyme enregistré!");
                    contactListPage.updateTitle();
                    contactListPage.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Échec. Le pseudonyme est déjà pris.");
                }
            }
        });
        pseudonymPanel.add(saveButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton backButton = new JButton("Retour");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contactListPage.setVisible(true);
                dispose();
            }
        });
        pseudonymPanel.add(backButton, gbc);

        add(pseudonymPanel, BorderLayout.CENTER);
    }
}
