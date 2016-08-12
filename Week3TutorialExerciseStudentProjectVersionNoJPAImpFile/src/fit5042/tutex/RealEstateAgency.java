/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.tutex;

import fit5042.tutex.gui.RealEstateAgencyGUI;
import fit5042.tutex.repository.PropertyRepository;
import fit5042.tutex.repository.PropertyRepositoryFactory;
import fit5042.tutex.repository.entities.Property;
import fit5042.tutex.gui.TableGUIImpl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Eddie
 */
public class RealEstateAgency implements ActionListener, ListSelectionListener {

    private String name;

    private final PropertyRepository propertyRepository;
    private RealEstateAgencyGUI gui;

    public RealEstateAgency(String name) throws Exception {
        this.name = name;
        this.propertyRepository = PropertyRepositoryFactory.getInstance();
    }

    public void initView() {
        this.gui = new TableGUIImpl(this, this);
        this.displayAllProperties();
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == gui.getViewButton()) {
            this.displayAllProperties();
        } else if (event.getSource() == gui.getAddButton()) {
            this.addProperty();
            this.displayAllProperties();
        } else if (event.getSource() == gui.getSearchButton()) {
            this.searchPropertyById();
        } else if (event.getSource() == gui.getUpdateButton()) {
            this.updateProperty();
        } else if (event.getSource() == gui.getDeleteButton()) {
            this.deleteProperty();
        } else {
            this.propertyRepository.close();
            System.exit(0);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent event) {
        if ((event.getSource() == this.gui.getPropertyTable().getSelectionModel())
            && (! event.getValueIsAdjusting()))
        {
            try
            {
                if (this.gui.isPropertySelected()) {
                    int propertyId = this.gui.getSelectedPropertyId();
                
                    Property property = PropertyRepositoryFactory.getInstance().searchPropertyById(propertyId);
                    this.gui.displaySelectedPropertyDetails(property);
                }               
            }
            catch (Exception e)
            {
                gui.displayMessageInDialog(e.getMessage());
            }
        }
    }
    
    public void updateProperty() {
        try {
            Property property = this.gui.getPropertyDetails();
            this.propertyRepository.editProperty(property);
            this.displayAllProperties();
            this.gui.clearTextFields();
        } catch (Exception ex) {
            this.gui.displayMessageInDialog("Failed to update property: " + ex.getMessage());
        }
    }

    public void deleteProperty() {
        try {
            int propertyId = this.gui.getPropertyId();
            this.propertyRepository.removeProperty(propertyId);
            this.displayAllProperties();
        } catch (Exception ex) {
            this.gui.displayMessageInDialog("Failed to update property: " + ex.getMessage());
        }  finally {
            this.gui.clearTextFields();
        }
    }

    public void searchPropertyById() {
        
        int id = this.gui.getPropertyId();
        
        try {
            
            Property property = this.propertyRepository.searchPropertyById(id);
            
            if (property != null) {
                this.gui.displayPropertyDetails(property);
            } else {
                this.gui.displayMessageInDialog("Property not found");
            }  
        } catch (Exception ex) {
            this.gui.displayMessageInDialog("Failed to search property by ID: " + ex.getMessage());
        } finally {
            this.gui.clearTextFields();
        }
    }

    private void displayAllProperties() {
        try {
            List<Property> properties = this.propertyRepository.getAllProperties();
            
            if (properties != null) {
                this.gui.displayPropertyDetails(properties);
            }
            
        } catch (Exception ex) {
            this.gui.displayMessageInDialog("Failed to retrieve properties: " + ex.getMessage());
        }
    }

    private void addProperty() {
        Property property = gui.getPropertyDetails();

        try {
            this.propertyRepository.addProperty(property);
            this.displayAllProperties();
            this.gui.clearTextFields();
        } catch (Exception ex) {
            this.gui.displayMessageInDialog("Failed to add property: " + ex.getMessage());
        } finally {
            this.gui.clearTextFields();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        try {
            final RealEstateAgency agency = new RealEstateAgency("Monash Real Estate Agency");
            //JDK 1.7
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    agency.initView();
                }
            });
            
//            //JDK 1.8
//            SwingUtilities.invokeLater(()-> {
//                agency.initView();
//            });
        } catch (Exception ex) {
            System.out.println("Failed to run application: " + ex.getMessage());
        }
    }

}
