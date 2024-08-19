
package com.asmitaagre.airlinemanagementmaven;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener {

    public Home() {
        setLayout(null);

        JLabel heading = new JLabel("AIR INDIA WELCOMES YOU!");
        heading.setBounds(500, 40, 1000, 40);
        heading.setForeground(Color.BLUE);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 36));
        add(heading);

        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);

        JMenu details = new JMenu("Details");
        menubar.add(details);
        
        JMenuItem customerDetails = new JMenuItem("Add Customer Details");
        customerDetails.addActionListener(this);
        details.add(customerDetails);


        JMenuItem bookFlights = new JMenuItem("Book Flight");
        bookFlights.addActionListener(this);
        details.add(bookFlights);

        JMenuItem journeyDetails = new JMenuItem("Journey Details");
        journeyDetails.addActionListener(this);
        details.add(journeyDetails);

        JMenuItem ticketCancellation = new JMenuItem("Cancel Ticket");
        ticketCancellation.addActionListener(this);
        details.add(ticketCancellation);

        JMenu ticket = new JMenu("Ticket");
        menubar.add(ticket);

        JMenuItem boardingPass = new JMenuItem("Boarding Pass");
        boardingPass.addActionListener(this);
        ticket.add(boardingPass);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String text = ae.getActionCommand();

        if (text.equals("Add Customer Details")) {
            new AddCustomer();  // Ensure this class is MongoDB compatible
        } else if (text.equals("Journey Details")) {
            new FlightsInfo();  // This opens the FlightsInfo window
        } else if(text.equals("Book Flight")) {
            new BookFlight();
        }else if(text.equals("Boarding Pass")) {
            new BoardingPass();
        }else if(text.equals("Cancel Ticket")) {
            new Cancel();
        }
        
        // Handle other menu items similarly
    }

    public static void main(String[] args) {
        new Home();
    }
}
