package com.asmitaagre.airlinemanagementmaven;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class ForgotPassword extends JFrame implements ActionListener {

    JButton submit;
    JTextField tfusername;
    JPasswordField tfpassword, tfreenterpassword;

    public ForgotPassword() {
        // Set up the frame
        setTitle("Forgot Password");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Username label and text field
        JLabel lblusername = new JLabel("Username:");
        lblusername.setBounds(50, 50, 100, 25);
        add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(170, 50, 200, 25);
        add(tfusername);

        // New password label and password field
        JLabel lblpassword = new JLabel("New Password:");
        lblpassword.setBounds(50, 100, 100, 25);
        add(lblpassword);

        tfpassword = new JPasswordField();
        tfpassword.setBounds(170, 100, 200, 25);
        add(tfpassword);

        // Re-enter password label and password field
        JLabel lblreenterpassword = new JLabel("Re-enter Password:");
        lblreenterpassword.setBounds(50, 150, 150, 25);
        add(lblreenterpassword);

        tfreenterpassword = new JPasswordField();
        tfreenterpassword.setBounds(170, 150, 200, 25);
        add(tfreenterpassword);

        // Submit button
        submit = new JButton("Submit");
        submit.setBounds(150, 200, 100, 25);
        submit.addActionListener(this);
        add(submit);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = tfusername.getText();
        String newPassword = new String(tfpassword.getPassword());
        String reenteredPassword = new String(tfreenterpassword.getPassword());

        if (!newPassword.equals(reenteredPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }

        try {
            // Establish connection to MongoDB
            Conn conn = new Conn();
            MongoCollection<Document> collection = conn.database.getCollection("login");

            // Update password if username exists
            Document user = collection.find(Filters.eq("username", username)).first();

            if (user != null) {
                collection.updateOne(Filters.eq("username", username), Updates.set("password", newPassword));
                JOptionPane.showMessageDialog(this, "Password updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Username not found!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while updating the password.");
        }
    }

    public static void main(String[] args) {
        new ForgotPassword();
    }
}
