
package com.asmitaagre.airlinemanagementmaven;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Random;

public class BookFlight extends JFrame implements ActionListener {
    
    JTextField tfaadhar;
    JLabel tfname, tfnationality, tfaddress, labelgender, labelfname, labelfcode;
    JButton bookflight, fetchButton, flight;
    Choice source, destination;
    
    MongoCollection<Document> passengersCollection, flightsCollection, reservationsCollection;
    
    public BookFlight() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel heading = new JLabel("Book Flight");
        heading.setBounds(420, 20, 500, 35);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 32));
        heading.setForeground(Color.BLUE);
        add(heading);
        
        JLabel lblaadhar = new JLabel("Aadhar");
        lblaadhar.setBounds(60, 80, 150, 25);
        lblaadhar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblaadhar);
        
        tfaadhar = new JTextField();
        tfaadhar.setBounds(220, 80, 150, 25);
        add(tfaadhar);
        
        fetchButton = new JButton("Fetch User");
        fetchButton.setBackground(Color.BLACK);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setBounds(380, 80, 120, 25);
        fetchButton.addActionListener(this);
        add(fetchButton);
        
        JLabel lblname = new JLabel("Name");
        lblname.setBounds(60, 130, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblname);
        
        tfname = new JLabel();
        tfname.setBounds(220, 130, 150, 25);
        add(tfname);
        
        JLabel lblnationality = new JLabel("Nationality");
        lblnationality.setBounds(60, 180, 150, 25);
        lblnationality.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblnationality);
        
        tfnationality = new JLabel();
        tfnationality.setBounds(220, 180, 150, 25);
        add(tfnationality);
        
        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(60, 230, 150, 25);
        lbladdress.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbladdress);
        
        tfaddress = new JLabel();
        tfaddress.setBounds(220, 230, 150, 25);
        add(tfaddress);
        
        JLabel lblgender = new JLabel("Gender");
        lblgender.setBounds(60, 280, 150, 25);
        lblgender.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblgender);
        
        labelgender = new JLabel();
        labelgender.setBounds(220, 280, 150, 25);
        add(labelgender);
        
        JLabel lblsource = new JLabel("Source");
        lblsource.setBounds(60, 330, 150, 25);
        lblsource.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblsource);
        
        source = new Choice();
        source.setBounds(220, 330, 150, 25);       
        add(source);
        
        JLabel lbldest = new JLabel("Destination");
        lbldest.setBounds(60, 380, 150, 25);
        lbldest.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbldest);
        
        destination = new Choice();
        destination.setBounds(220, 380, 150, 25);       
        add(destination);
        
        try {
            Conn c = new Conn();
            MongoDatabase database = c.database;
            flightsCollection = database.getCollection("flight");
            
            // Fetch source and destination options from flights collection
            MongoCursor<Document> cursor = flightsCollection.find().iterator();
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                source.add(doc.getString("departure"));
                destination.add(doc.getString("arrival"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        flight = new JButton("Fetch Flights");
        flight.setBackground(Color.BLACK);
        flight.setForeground(Color.WHITE);
        flight.setBounds(380, 380, 120, 25);
        flight.addActionListener(this);
        add(flight);
        
        JLabel lblfname = new JLabel("Flight Name");
        lblfname.setBounds(60, 430, 150, 25);
        lblfname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblfname);
        
        labelfname = new JLabel();
        labelfname.setBounds(220, 430, 150, 25);
        add(labelfname);
        
        JLabel lblfcode = new JLabel("Flight Code");
        lblfcode.setBounds(60, 480, 150, 25);
        lblfcode.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblfcode);
        
        labelfcode = new JLabel();
        labelfcode.setBounds(220, 480, 150, 25);
        add(labelfcode);
        
        // ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/details.jpg"));
        // Image i2 = i1.getImage().getScaledInstance(450, 320, Image.SCALE_DEFAULT);
        // ImageIcon image = new ImageIcon(i2);
        // JLabel lblimage = new JLabel(image);
        // lblimage.setBounds(550, 80, 500, 410);
        // add(lblimage);
        
        bookflight = new JButton("Book Flight");
        bookflight.setBackground(Color.BLACK);
        bookflight.setForeground(Color.WHITE);
        bookflight.setBounds(220, 580, 150, 25);
        bookflight.addActionListener(this);
        add(bookflight);
        
        setSize(1100, 700);
        setLocation(200, 50);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            String aadhar = tfaadhar.getText();
            
            try {
                Conn conn = new Conn();
                passengersCollection = conn.database.getCollection("login");

                Document query = new Document("aadhar", aadhar);
                Document passenger = passengersCollection.find(query).first();
                
                if (passenger != null) {
                    tfname.setText(passenger.getString("name")); 
                    tfnationality.setText(passenger.getString("nationality")); 
                    tfaddress.setText(passenger.getString("address"));
                    labelgender.setText(passenger.getString("gender"));
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter correct aadhar");                
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == flight) {
            String src = source.getSelectedItem();
            String dest = destination.getSelectedItem();
            try {
                Conn conn = new Conn();
                flightsCollection = conn.database.getCollection("flight");

                Document query = new Document("departure", src).append("arrival", dest);
                Document flight = flightsCollection.find(query).first();
                
                if (flight != null) {
                    labelfname.setText(flight.getString("airline")); 
                    labelfcode.setText(flight.getString("flightNumber")); 
                } else {
                    JOptionPane.showMessageDialog(null, "No Flights Found");                
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Random random = new Random();
            
            String aadhar = tfaadhar.getText();
            String name = tfname.getText(); 
            String nationality = tfnationality.getText();
            String flightname = labelfname.getText(); 
            String flightcode = labelfcode.getText();
            String src = source.getSelectedItem(); 
            String des = destination.getSelectedItem();
            
            
            try {
                Conn conn = new Conn();
                reservationsCollection = conn.database.getCollection("reservations");

                Document reservation = new Document("PNR", "PNR-" + random.nextInt(1000000))
                    .append("TIC", "TIC-" + random.nextInt(10000))
                    .append("aadhar", aadhar)
                    .append("name", name)
                    .append("nationality", nationality)
                    .append("source", src)
                    .append("destination", des)
                    .append("airline", flightname)
                    .append("flightNumber", flightcode);
                    

                reservationsCollection.insertOne(reservation);

                JOptionPane.showMessageDialog(null, "Flight Booked Successfully");
                setVisible(false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        new BookFlight();
    }
}
