/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fit5042.tutex.gui;

import fit5042.tutex.repository.entities.Property;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;

public class TableGUIImpl extends JFrame implements RealEstateAgencyGUI {
    
    private static final String[] TABLE_COLUMNS = {"ID", "Address", "No. of Bedrooms", "Size", "Price"};

    private final JButton closeButton;
    private final JButton addButton;
    private final JButton viewButton;
    private final JButton searchButton;
    private final JButton updateButton;
    private final JButton deleteButton;
    
    private final JPanel inputPanel;
    private final JPanel buttonPanel;

    private final JLabel propertyIdLabel;
    private final JLabel addressLabel;
    private final JLabel numberOfBedroomLabel;
    private final JLabel sizeLabel;
    private final JLabel priceLabel;

    private final JTextField propertyIdTextField;
    private final JTextField addressTextField;
    private final JTextField numberOfBedroomTextField;
    private final JTextField sizeTextField;
    private final JTextField priceTextField;
    
    private final JTable propertyTable;

    Property property;

    public TableGUIImpl(ActionListener actionListener, ListSelectionListener listSelectionListener) {
        super("Monash Real Estate Agency");

        // create buttons
        this.closeButton = new JButton("Close");
        this.viewButton = new JButton("View");
        this.searchButton = new JButton("Search");
        this.addButton = new JButton("Add");
        this.updateButton = new JButton("Update");
        this.deleteButton = new JButton("Delete");

        // create container
        Container container = this.getContentPane();

        // create labels
        this.propertyIdLabel = new JLabel("Property ID:");
        this.addressLabel = new JLabel("Address:");
        this.numberOfBedroomLabel = new JLabel("No. Of Bedroom:");
        this.sizeLabel = new JLabel("Size:");
        this.priceLabel = new JLabel("Price:");

        // create text fields
        this.propertyIdTextField = new JTextField();
        this.addressTextField = new JTextField();
        this.numberOfBedroomTextField = new JTextField();
        this.sizeTextField = new JTextField();
        this.priceTextField = new JTextField();
        
        // create table
        this.propertyTable = new JTable(new DefaultTableModel(TABLE_COLUMNS, 0));
        this.propertyTable.getSelectionModel().addListSelectionListener(listSelectionListener);       
        this.propertyTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        TableColumnModel propertyTableColumnModel = this.propertyTable.getColumnModel();
        propertyTableColumnModel.getColumn(0).setPreferredWidth(50);
        propertyTableColumnModel.getColumn(1).setPreferredWidth(300);
        propertyTableColumnModel.getColumn(2).setPreferredWidth(100);
        propertyTableColumnModel.getColumn(3).setPreferredWidth(100);
        propertyTableColumnModel.getColumn(4).setPreferredWidth(100);
        
        // create panels
        this.inputPanel = new JPanel();
        this.buttonPanel = new JPanel();

        // set layout manager
        container.setLayout(new BorderLayout());
        this.inputPanel.setLayout(new GridLayout(5,2));
        this.buttonPanel.setLayout(new GridLayout(1,4));

        // add action listeners
        this.closeButton.addActionListener(actionListener);
        this.addButton.addActionListener(actionListener);
        this.viewButton.addActionListener(actionListener);
        this.searchButton.addActionListener(actionListener);
        this.updateButton.addActionListener(actionListener);
        this.deleteButton.addActionListener(actionListener);

        // add components
        this.inputPanel.add(propertyIdLabel);
        this.inputPanel.add(propertyIdTextField);
        this.inputPanel.add(addressLabel);
        this.inputPanel.add(addressTextField);
        this.inputPanel.add(numberOfBedroomLabel);
        this.inputPanel.add(numberOfBedroomTextField);
        this.inputPanel.add(sizeLabel);
        this.inputPanel.add(sizeTextField);
        this.inputPanel.add(priceLabel);
        this.inputPanel.add(priceTextField);

        // add buttons to panel
        this.buttonPanel.add(this.addButton);
        this.buttonPanel.add(this.updateButton);
        this.buttonPanel.add(this.deleteButton);
        this.buttonPanel.add(this.searchButton);
        this.buttonPanel.add(this.viewButton);
        this.buttonPanel.add(this.closeButton);
        
        // add panels to content pane
        container.add(inputPanel,BorderLayout.NORTH);
        container.add(new JScrollPane(this.propertyTable), BorderLayout.CENTER);
        container.add(buttonPanel,BorderLayout.SOUTH);
       
        // change the default behaviour of the close button
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setSize(650, 570);       
        this.setVisible(true);
    }
    
    @Override
    public int getPropertyId() {
        String id = this.propertyIdTextField.getText();
        return id == null || id.isEmpty() ? 0 : Integer.parseInt(id);
    }
    
    public boolean isPropertySelected() {
        return (this.propertyTable.getSelectedRow() >= 0);
    }
    
    @Override
    public int getSelectedPropertyId() {
        int selectedRowIndex = this.propertyTable.getSelectedRow();
        
        String propertyId = this.propertyTable.getValueAt(selectedRowIndex, 0).toString();
        return Integer.parseInt(propertyId); 
    }

    @Override
    public Property getPropertyDetails()
    {
        return new Property(Integer.parseInt(propertyIdTextField.getText()), addressTextField.getText(), Integer.parseInt(numberOfBedroomTextField.getText()), Double.parseDouble(sizeTextField.getText()), Double.parseDouble(priceTextField.getText()));		
    }
    
    @Override
    public void displayMessageInDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public void displayPropertyDetails(Property property) {  
        this.clearPropertyTable();
        ((DefaultTableModel)this.propertyTable.getModel()).addRow(new Object[]{property.getPropertyId(), property.getAddress(), property.getNumberOfBedrooms(), property.getSize(), property.getPrice()});
    }
    
    @Override
    public void displayPropertyDetails(List<Property> properties) {    
        this.clearPropertyTable();
        
        for (Property property : properties) {
            ((DefaultTableModel)this.propertyTable.getModel()).addRow(new Object[]{property.getPropertyId(), property.getAddress(), property.getNumberOfBedrooms(), property.getSize(), property.getPrice()});
        }
    }

    @Override
    public void displaySelectedPropertyDetails(Property property) {
        this.propertyIdTextField.setText(String.valueOf(property.getPropertyId()));
        this.addressTextField.setText(property.getAddress());
        this.sizeTextField.setText(String.valueOf(property.getSize()));
        this.priceTextField.setText(String.valueOf(property.getPrice())); 
        this.numberOfBedroomTextField.setText(String.valueOf(property.getNumberOfBedrooms()));
    }
    
    private void clearPropertyTable() {     
        int numberOfRow = this.propertyTable.getModel().getRowCount();
        
        if (numberOfRow > 0) {
            DefaultTableModel tableModel = (DefaultTableModel) this.propertyTable.getModel();
            for (int index = (numberOfRow - 1); index >= 0; index --) {
                tableModel.removeRow(index);
            }            
        }
    }

    @Override
    public void clearTextFields()
    {
        propertyIdTextField.setText("");
        addressTextField.setText("");
        numberOfBedroomTextField.setText("");
        sizeTextField.setText("");
        priceTextField.setText("");
    }

    @Override
    public JButton getUpdateButton() {
        return updateButton;
    }

    @Override
    public JButton getDeleteButton() {
        return deleteButton;
    }

    @Override
    public JTable getPropertyTable() {
        return propertyTable;
    }

    @Override
    public JButton getViewButton() {
        return viewButton;
    }

    @Override
    public JButton getAddButton() {
        return addButton;
    }
    
    @Override
    public JButton getSearchButton() {
        return searchButton;
    }

    @Override
    public JButton getCloseButton() {
        return closeButton;
    }
}

