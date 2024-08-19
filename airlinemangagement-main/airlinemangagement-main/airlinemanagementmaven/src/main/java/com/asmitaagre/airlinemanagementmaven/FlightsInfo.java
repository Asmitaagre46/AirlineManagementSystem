
package com.asmitaagre.airlinemanagementmaven;

import javax.swing.*;
import java.awt.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
// import net.proteanit.sql.DbUtils;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class FlightsInfo extends JFrame {

    public FlightsInfo() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JTable table = new JTable();
        
        try {
            Conn conn = new Conn();
            
            // Get the "flight" collection
            MongoCollection<Document> flightCollection = conn.database.getCollection("flight");

            // Find all documents in the collection
            MongoCursor<Document> cursor = flightCollection.find().iterator();

            // Create a DefaultTableModel to hold the data
            DefaultTableModel model = new DefaultTableModel();
            ArrayList<String> columnNames = new ArrayList<>();

            // If there are documents, set up the columns and rows
            if (cursor.hasNext()) {
                Document firstDoc = cursor.next();

                // Set up the column names
                for (String key : firstDoc.keySet()) {
                    columnNames.add(key);
                    model.addColumn(key);
                }

                // Add the first row
                model.addRow(getRowData(firstDoc, columnNames));

                // Add the rest of the rows
                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                    model.addRow(getRowData(doc, columnNames));
                }
            }

            // Set the model to the table
            table.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 0, 800, 500);
        add(jsp);

        setSize(800, 500);
        setLocation(400, 200);
        setVisible(true);
    }

    private Object[] getRowData(Document doc, ArrayList<String> columnNames) {
        Object[] rowData = new Object[columnNames.size()];
        for (int i = 0; i < columnNames.size(); i++) {
            rowData[i] = doc.get(columnNames.get(i));
        }
        return rowData;
    }

    public static void main(String[] args) {
        new FlightsInfo();
    }
}
