package fit5042.tutex.repository.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author cyan
 */
@Entity
@NamedQueries({@NamedQuery(name = Property.GET_ALL_QUERY_NAME, query = "SELECT p FROM Property p")})
@Table(name = "Property")
public class Property implements Serializable {
    
    public static final String GET_ALL_QUERY_NAME = "Property.getAllProperties";
    
    private int propertyId;    
    private String address;
    private double size;
    private int numberOfBedrooms;
    private double price;
    
   

    public Property() {

    }
    
    public Property(Property property) {
        propertyId = property.propertyId;
        size = property.size;
        numberOfBedrooms = property.numberOfBedrooms;
        price = property.price;
        address = property.address;

    }

    public Property(int propertyId,  String address, int numberOfBedrooms, double size, double price) {
        this.propertyId = propertyId;
        this.address = address;
        this.size = size;
        this.numberOfBedrooms = numberOfBedrooms;
        this.price = price;
    }

    @Id
    @Column(name = "property_id")
    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }



 
    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Column(name = "number_of_bedrooms")
    public int getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    public void setNumberOfBedrooms(int numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * @return the value of attribute address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Change the value of attribute address to a new value
     *
     * @param address - the new value of attribute address
     */
    public void setAddress(String address) {
        this.address = address;
    }

}
