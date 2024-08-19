package com.asmitaagre.airlinemanagementmaven;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

public class BoardingPass extends JFrame implements ActionListener, KeyListener {

    JTextField aadhaarField, nameField, genderField, nationalityField, timeField;
    JComboBox<String> sourceDropdown, destinationDropdown;
    JButton generateButton;
    JLabel boardingPassLabel;
    JPanel boardingPassPanel;

    public BoardingPass() {
        setTitle("Generate Boarding Pass");
        setLayout(null);

        JLabel aadhaarLabel = new JLabel("Aadhaar Number:");
        aadhaarLabel.setBounds(30, 30, 150, 25);
        add(aadhaarLabel);

        aadhaarField = new JTextField();
        aadhaarField.setBounds(180, 30, 200, 25);
        aadhaarField.addKeyListener(this); // Add KeyListener to handle Enter key
        add(aadhaarField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 70, 150, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(180, 70, 200, 25);
        nameField.setEditable(false);
        add(nameField);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(30, 110, 150, 25);
        add(genderLabel);

        genderField = new JTextField();
        genderField.setBounds(180, 110, 200, 25);
        genderField.setEditable(false);
        add(genderField);

        JLabel nationalityLabel = new JLabel("Nationality:");
        nationalityLabel.setBounds(30, 150, 150, 25);
        add(nationalityLabel);

        nationalityField = new JTextField();
        nationalityField.setBounds(180, 150, 200, 25);
        nationalityField.setEditable(false);
        add(nationalityField);

        JLabel sourceLabel = new JLabel("Source:");
        sourceLabel.setBounds(30, 190, 150, 25);
        add(sourceLabel);

        sourceDropdown = new JComboBox<>(new String[]{"Mumbai", "Pune", "Delhi", "Hyderabad"});
        sourceDropdown.setBounds(180, 190, 200, 25);
        sourceDropdown.setSelectedItem("Mumbai"); // Set default source
        add(sourceDropdown);

        JLabel destinationLabel = new JLabel("Destination:");
        destinationLabel.setBounds(30, 230, 150, 25);
        add(destinationLabel);

        destinationDropdown = new JComboBox<>(new String[]{"Mumbai", "Pune", "Delhi", "Hyderabad"});
        destinationDropdown.setBounds(180, 230, 200, 25);
        destinationDropdown.setSelectedItem("Hyderabad"); // Set default destination
        add(destinationDropdown);

        generateButton = new JButton("Generate Boarding Pass");
        generateButton.setBounds(100, 320, 220, 30);
        generateButton.addActionListener(this);
        add(generateButton);

        boardingPassPanel = new JPanel();
        boardingPassPanel.setBounds(30, 370, 400, 200);
        boardingPassPanel.setBackground(Color.LIGHT_GRAY);
        boardingPassPanel.setLayout(new GridLayout(5, 1));
        add(boardingPassPanel);

        boardingPassLabel = new JLabel();
        boardingPassLabel.setHorizontalAlignment(SwingConstants.CENTER);
        boardingPassPanel.add(boardingPassLabel);

        setSize(500, 650);
        setLocation(400, 200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        generateBoardingPass();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            generateBoardingPass();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }

    private void generateBoardingPass() {
        String aadhaarNumber = aadhaarField.getText();

        if (aadhaarNumber.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter an Aadhaar Number");
            return;
        }

        try {
            Conn conn = new Conn();
            MongoCollection<Document> collection = conn.database.getCollection("login");  // Ensure this collection name matches your MongoDB setup

            Document query = new Document("aadhar", aadhaarNumber);
            MongoCursor<Document> cursor = collection.find(query).iterator();

            if (cursor.hasNext()) {
                Document doc = cursor.next();

                nameField.setText(doc.getString("name"));
                genderField.setText(doc.getString("gender"));
                nationalityField.setText(doc.getString("nationality"));

                // Set selected items based on MongoDB data
                String source = doc.getString("source");
                String destination = doc.getString("destination");

                sourceDropdown.setSelectedItem(source != null ? source : "Mumbai"); // Default to "Mumbai" if null
                destinationDropdown.setSelectedItem(destination != null ? destination : "Hyderabad"); // Default to "Hyderabad" if null
                
            

                // Display the boarding pass details on the panel
                boardingPassLabel.setText("<html><div style='text-align: center; font-family: Arial, sans-serif; font-size: 16px; color: #333;'>"
                    + "<b>Name:</b> " + doc.getString("name") + "<br>"
                    + "<b>Aadhaar:</b> " + aadhaarNumber + "<br>"
                    + "<b>Source:</b> " + (source != null ? source : "Mumbai") + "<br>"
                    + "<b>Destination:</b> " + (destination != null ? destination : "Hyderabad") + "<br>"
                    + "<b>Gender:</b> " + doc.getString("gender")
                    + "</div></html>");
            } else {
                 boardingPassLabel.setText("<html><div style='text-align: center; font-family: Arial, sans-serif; font-size: 16px; color: #333;'>"
                    + "<b>Aadhaar:</b> " + aadhaarNumber + "<br>"
                    + "<b>Source:</b> Mumbai<br>"
                    + "<b>Destination:</b> Hyderabad<br>"
                    + "<b>Gender:</b> Not Available"
                    + "</div></html>");
            }

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new BoardingPass();
    }
}
