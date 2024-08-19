package com.asmitaagre.airlinemanagementmaven;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;

public class Login extends JFrame implements ActionListener {

    JButton forgotPassword, login, reset;
    JTextField tfusername;
    JPasswordField tfpassword;

    public Login() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        //ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("com.asmitaagre.airlinemanagementmaven/icons/front.jpg"));
        //JLabel image = new JLabel(img);
        //image.setBounds(0, 0, 400, 250);
        //add(image);

        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(50, 35, 100, 20);
        add(lblusername);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(50, 75, 100, 20);
        add(lblpassword);

        tfusername = new JTextField();
        tfusername.setBounds(130, 35, 200, 20);
        add(tfusername);

        tfpassword = new JPasswordField();
        tfpassword.setBounds(130, 75, 200, 20);
        add(tfpassword);

        reset = new JButton("Reset");
        reset.setBounds(75, 115, 100, 20);
        reset.addActionListener(this);
        add(reset);

        login = new JButton("Login");
        login.setBounds(195, 115, 100, 20);
        login.addActionListener(this);
        add(login);

        forgotPassword = new JButton("Forgot Password!");
        forgotPassword.setBounds(115, 155, 150, 20);
        forgotPassword.addActionListener(this);
        add(forgotPassword);

        setTitle("Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login) {
            String username = tfusername.getText();
            String password = new String(tfpassword.getPassword());

            try {
                Conn c = new Conn();
                MongoCollection<Document> collection = c.collection;

                // Query the collection to find the user with the given username and password
                Document userDoc = collection.find(and(eq("username", username), eq("password", password))).first();

                if (userDoc != null) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                    new Home();
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password!");
                }

                c.close(); // Close the MongoDB connection

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == forgotPassword) {
            new ForgotPassword();
        } else if (ae.getSource() == reset) {
            tfusername.setText("");
            tfpassword.setText("");
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
