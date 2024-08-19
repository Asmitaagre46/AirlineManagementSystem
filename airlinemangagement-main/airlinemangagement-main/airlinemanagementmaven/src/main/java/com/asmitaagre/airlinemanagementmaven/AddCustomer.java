
package com.asmitaagre.airlinemanagementmaven;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.bson.Document;
import com.asmitaagre.airlinemanagementmaven.Conn;

public class AddCustomer extends JFrame implements ActionListener {

    JTextField tfname, tfphone, tfaadhar, tfnationality, tfaddress, tfemail;
    JRadioButton rbmale, rbfemale, rbothers;

    public AddCustomer() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("ADD CUSTOMER DETAILS");
        heading.setBounds(250, 20, 500, 35);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 32));
        heading.setForeground(Color.BLUE);
        add(heading);

        JLabel lblname = new JLabel("Name :");
        lblname.setBounds(60, 80, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(lblname);

        tfname = new JTextField();
        tfname.setBounds(220, 80, 150, 25);
        add(tfname);

        JLabel lblnationality = new JLabel("Nationality :");
        lblnationality.setBounds(60, 130, 150, 25);
        lblnationality.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(lblnationality);

        tfnationality = new JTextField();
        tfnationality.setBounds(220, 130, 150, 25);
        add(tfnationality);

        JLabel lblaadhar = new JLabel("Aadhar Number:");
        lblaadhar.setBounds(60, 180, 150, 25);
        lblaadhar.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(lblaadhar);

        tfaadhar = new JTextField();
        tfaadhar.setBounds(220, 180, 150, 25);
        add(tfaadhar);

        JLabel lbladdress = new JLabel("Address :");
        lbladdress.setBounds(60, 230, 150, 25);
        lbladdress.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(lbladdress);

        tfaddress = new JTextField();
        tfaddress.setBounds(220, 230, 150, 25);
        add(tfaddress);

        JLabel lblgender = new JLabel("Gender :");
        lblgender.setBounds(60, 280, 150, 25);
        lblgender.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(lblgender);

        ButtonGroup gendergroup = new ButtonGroup();

        rbmale = new JRadioButton("Male");
        rbmale.setBounds(220, 280, 70, 25);
        rbmale.setBackground(Color.WHITE);
        add(rbmale);

        rbfemale = new JRadioButton("Female");
        rbfemale.setBounds(300, 280, 70, 25);
        rbfemale.setBackground(Color.WHITE);
        add(rbfemale);

        rbothers = new JRadioButton("Others");
        rbothers.setBounds(390, 280, 70, 25);
        rbothers.setBackground(Color.WHITE);
        add(rbothers);

        gendergroup.add(rbmale);
        gendergroup.add(rbfemale);
        gendergroup.add(rbothers);

        JLabel lblphone = new JLabel("Phone :");
        lblphone.setBounds(60, 330, 150, 25);
        lblphone.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(lblphone);

        tfphone = new JTextField();
        tfphone.setBounds(220, 330, 150, 25);
        add(tfphone);

        JLabel lblemail = new JLabel("Email_ID :");
        lblemail.setBounds(60, 380, 150, 25);
        lblemail.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(lblemail);

        tfemail = new JTextField();
        tfemail.setBounds(220, 380, 150, 25);
        add(tfemail);

        JButton save = new JButton("SAVE");
        save.setBackground(Color.BLACK);
        save.setForeground(Color.WHITE);
        save.setBounds(350, 430, 150, 30);
        save.addActionListener(this);
        add(save);

        // ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/emp.png"));
        // JLabel lblimage = new JLabel(image);
        // lblimage.setBounds(580, 80, 280, 400);
        // add(lblimage);

        setSize(900, 600);
        setLocation(300, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String name = tfname.getText();
        String nationality = tfnationality.getText();
        String phone = tfphone.getText();
        String email = tfemail.getText();
        String address = tfaddress.getText();
        String aadhar = tfaadhar.getText();
        String gender = null;
        if (rbmale.isSelected()) {
            gender = "Male";
        } else if (rbfemale.isSelected()) {
            gender = "Female";
        } else {
            gender = "Others";
        }

        try {
            Conn conn = new Conn();

            // Create a document to store the customer details
            Document doc = new Document("name", name)
                    .append("nationality", nationality)
                    .append("phone", phone)
                    .append("email", email)
                    .append("address", address)
                    .append("aadhar", aadhar)
                    .append("gender", gender);

            // Insert the document into the MongoDB collection
            conn.insertDocument(doc);

            JOptionPane.showMessageDialog(null, "Customer details added successfully!");
            setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AddCustomer();
    }

}
