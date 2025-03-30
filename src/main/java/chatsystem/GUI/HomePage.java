package chatsystem.GUI;

import chatsystem.contacts.User;
import chatsystem.DataBase.DataBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    private User user;
    private DataBase db;

    public HomePage(User user, DataBase db) {
        this.user = user;
        this.db = db;
        setTitle("Accueil - Système de Messagerie");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Assigner un identifiant automatiquement
        user.assignUserId();

        // Panneau principal avec un message de bienvenue
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BorderLayout());
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Bienvenue dans le Système de Messagerie");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomePanel.add(welcomeLabel, BorderLayout.NORTH);

        JLabel instructionLabel = new JLabel("Votre identifiant vous a été attribué automatiquement. Veuillez choisir un pseudo.");
        instructionLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        instructionLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomePanel.add(instructionLabel, BorderLayout.CENTER);

        add(welcomePanel, BorderLayout.NORTH);

        // Panneau de connexion
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel idLabel = new JLabel("Identifiant:");
        loginPanel.add(idLabel, gbc);

        gbc.gridx = 1;
        JTextField idField = new JTextField(String.valueOf(user.getUserId()), 10);
        idField.setEditable(false);
        loginPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel pseudoLabel = new JLabel("Pseudo:");
        loginPanel.add(pseudoLabel, gbc);

        gbc.gridx = 1;
        JTextField pseudoField = new JTextField(20);
        loginPanel.add(pseudoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Se connecter");
        loginButton.setFont(new Font("Serif", Font.PLAIN, 16));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pseudo = pseudoField.getText();

                // Vérifier si le pseudo est disponible
                boolean isPseudoAvailable = user.selectName(pseudo);

                if (isPseudoAvailable) {
                    user.setUserName(pseudo); // Set the selected pseudo
                    JOptionPane.showMessageDialog(null, "Connexion réussie!");
                    new ContactListPage(user, db).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Échec de la connexion. Le pseudo est déjà pris.");
                }
            }
        });
        loginPanel.add(loginButton, gbc);

        add(loginPanel, BorderLayout.CENTER);
    }
}
