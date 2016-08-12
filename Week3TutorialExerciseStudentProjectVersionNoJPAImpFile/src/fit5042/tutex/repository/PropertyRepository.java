package fit5042.tutex.repository;

import fit5042.tutex.repository.entities.Property;
import java.util.List;

/**
 * @autor Eddie Leung
 */
public interface PropertyRepository {

    /**
     * Add the property being passed as parameter into the repository
     *
     * @param property - the property to add
     */
    public void addProperty(Property property) throws Exception;

    /**
     * Search for a property by its property ID
     *
     * @param id - the propertyId of the property to search for
     * @return the property found
     */
    public Property searchPropertyById(int id) throws Exception;

    /**
     * Return all the properties in the repository
     *
     * @return all the properties in the repository
     */
    public List<Property> getAllProperties() throws Exception;
    
    /**
     * Remove the property, whose property ID matches the one being passed as parameter, from the repository
     *
     * @param propertyId - the ID of the property to remove
     */
    public void removeProperty(int propertyId) throws Exception;
    
    /**
     * Update a property in the repository
     *
     * @param property - the updated information regarding a property
     */
    public void editProperty(Property property) throws Exception;
    
    /**
     * Close all resources in the repository
     */
    public void close();
}
