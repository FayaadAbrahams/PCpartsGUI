package za.ac.cput.adp2.mymavendb.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import za.ac.cput.adp2.mymavendb.dao.partDAO;
import za.ac.cput.adp2.mymavendb.domain.Parts;
//@Author Fayaad Abrahams 
//Student Number: 218221630 

public class PCpartApplicationGUI implements ActionListener {

    //Java Swing Componenets
    private JFrame mainFrame;
    private JFrame secFrame;

    private JPanel centerPanel;
    private JPanel eastPanel;
    private JPanel secCenterPanel;

    private JLabel pcPartIDLabel;
    private JTextField partIDText;

    private JLabel pcPartLabel;
    private JTextField partNameText;

    private JLabel pcPriceLabel;
    private JTextField partPriceText;

    private JLabel deleteLabel;
    private JComboBox<String> changeBox;

    private JTable table;
    public DefaultTableModel model;
    ArrayList<Parts> partsList = new ArrayList();

    private JPanel jPanel;
    private JButton addButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JButton readButton;
    private JButton updateButton;

    Parts parts;
    partDAO pDAO;

    public PCpartApplicationGUI() {
        //Frames and Panels for GUI 
        mainFrame = new JFrame("PC Parts List");
        secFrame = new JFrame("Table from Database");
        secCenterPanel = new JPanel();
        centerPanel = new JPanel();
        eastPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //Panel for buttons
        jPanel = new JPanel();
        jPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //Labels and TextFields
        pcPartLabel = new JLabel("Part Name");
        partNameText = new JTextField(20);
        partNameText.setSize(30, 10);

        pcPriceLabel = new JLabel("Part Price");
        partPriceText = new JTextField(10);
        partPriceText.setSize(30, 10);

        pcPartIDLabel = new JLabel("Part ID");
        partIDText = new JTextField(10);
        partIDText.setSize(30, 10);

        deleteLabel = new JLabel("Select and ID to delete or update:");

        //Combo Box to delete values
        changeBox = new JComboBox<>();

        //JTable to Display records
        model = new DefaultTableModel();
        table = new JTable();

        //Button and Button Panel Config
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        readButton = new JButton("Read");
        deleteButton = new JButton("Delete");
        cancelButton = new JButton("Cancel");

        //Button Colours
        addButton.setBackground(Color.yellow);
        readButton.setBackground(Color.yellow);
        cancelButton.setBackground(Color.red);
        deleteButton.setBackground(Color.yellow);
        updateButton.setBackground(Color.yellow);

        addButton.setForeground(Color.BLACK);
        readButton.setForeground(Color.BLACK);
        cancelButton.setForeground(Color.white);
        deleteButton.setForeground(Color.BLACK);
        updateButton.setForeground(Color.BLACK);

        //Main Panel Behaviour
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);

    }

    public void setGUI() throws SQLException {
        //Setting the Layout Panels
        centerPanel.setLayout(new GridLayout(10, 3));
        jPanel.setLayout(new GridLayout(0, 1, 10, 10));

        //Adding Components to centerPanel
        centerPanel.add(pcPartIDLabel);
        centerPanel.add(partIDText);
        centerPanel.add(pcPartLabel);
        centerPanel.add(partNameText);
        centerPanel.add(pcPriceLabel);
        centerPanel.add(partPriceText);
        centerPanel.add(deleteLabel);
        centerPanel.add(changeBox);

        //Adding Table to Second Frame Panel
        secCenterPanel.add(table);

        //Model for JTable
        model.addColumn("Part ID");
        model.addColumn("Part Name");
        model.addColumn("Part Price");
        secCenterPanel.add(new JScrollPane(table));

        //Adding Components to the jPanel for Buttons
        jPanel.add(addButton);
        jPanel.add(updateButton);
        jPanel.add(readButton);
        jPanel.add(deleteButton);
        jPanel.add(cancelButton);

        eastPanel.add(jPanel);

        //Adding Action listeners to the Buttons
        addButton.addActionListener(this);
        cancelButton.addActionListener(this);
        readButton.addActionListener(this);
        deleteButton.addActionListener(this);
        updateButton.addActionListener(this);

        //MainFrame Settings
        mainFrame.add(centerPanel, BorderLayout.WEST);
        mainFrame.add(eastPanel, BorderLayout.EAST);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.show();
        pDAO = new partDAO();

        //Second Frame Settings
        secFrame.setVisible(false);
        secFrame.add(secCenterPanel, BorderLayout.CENTER);

        //Close the Frame only
        secFrame.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                secFrame.dispose();
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //Adding a record to the Database
        if (e.getSource() == addButton) {
            String partIDString = partIDText.getText();
            String partPriceString = partPriceText.getText();
            String partNameString = partNameText.getText();

            Parts p = new Parts(partIDString, partNameString, partPriceString);
            try {
                parts = pDAO.save(p);
                if (parts.equals(p)) {
                    JOptionPane.showMessageDialog(null, "Successfully Added Part");
                } else {
                    JOptionPane.showMessageDialog(null, "Could not add part");
                }
            } catch (SQLException ex) {
                System.out.println("SQLException in addButton");
            }
//====================================================================================================================
            //Display Values into JTable -> Database
        } else if (e.getSource() == readButton) {
            //Code for Getting Values into JTable
            partsList = pDAO.getAll();
            table.setModel(model);
            model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            for (int i = 0; i < partsList.size(); i++) {
                String pcPartIDTable = partsList.get(i).getPcPartID();
                String pcPartNameTable = partsList.get(i).getPcPartName();
                String pcPartPriceTable = partsList.get(i).getPcPartPrice();

                Object[] partsData = {pcPartIDTable, pcPartNameTable, pcPartPriceTable};
                model.addRow(partsData);

            }
            //Updates the Combo Box
            changeBox.removeAllItems();
            for (int i = 0; i < partsList.size(); i++) {
                String partsNum = String.valueOf(partsList.get(i).getPcPartID());
                changeBox.addItem(partsNum);
            }
            secFrame.pack();
            secFrame.setLocationRelativeTo(null);
            secFrame.setVisible(true);

//====================================================================================================================
            //Removes values from Database
        } else if (e.getSource() == deleteButton) {
            pDAO.delete(changeBox.getSelectedItem().toString());
            JOptionPane.showMessageDialog(null, "Deleted the record");

            //Updates the Combo Box
            partsList = pDAO.getAll();
            changeBox.removeAllItems();
            for (int i = 0; i < partsList.size(); i++) {
                String partsNum = String.valueOf(partsList.get(i).getPcPartID());
                changeBox.addItem(partsNum);
            }
//====================================================================================================================
        } else if (e.getSource() == updateButton) {
            try {
                pDAO.update(changeBox.getSelectedItem().toString(), partPriceText.getText());
            } catch (SQLException ex) {
                Logger.getLogger(PCpartApplicationGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "Updated the record");
            partsList = pDAO.getAll();
            table.setModel(model);
            model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            for (int i = 0; i < partsList.size(); i++) {
                String pcPartIDTable = partsList.get(i).getPcPartID();
                String pcPartNameTable = partsList.get(i).getPcPartName();
                String pcPartPriceTable = partsList.get(i).getPcPartPrice();

                Object[] partsData = {pcPartIDTable, pcPartNameTable, pcPartPriceTable};
                model.addRow(partsData);

            }
//            Updates the Combo Box
            changeBox.removeAllItems();
            for (int i = 0; i < partsList.size(); i++) {
                String partsNum = String.valueOf(partsList.get(i).getPcPartID());
                changeBox.addItem(partsNum);
            }

        } else if (e.getSource() == cancelButton) {
            System.exit(0);
        }

    }

    public static void main(String[] args) throws SQLException {
        new PCpartApplicationGUI().setGUI();
    }
}
