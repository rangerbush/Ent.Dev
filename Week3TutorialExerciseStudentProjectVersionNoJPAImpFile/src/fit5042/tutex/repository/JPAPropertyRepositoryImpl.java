package fit5042.tutex.repository;

import fit5042.tutex.repository.entities.Property;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
/**
 * A simple repository implementation that uses ArrayList as the data storage
 * 
 * @author Eddie Leung
 */
public class JPAPropertyRepositoryImpl implements PropertyRepository {
    
     private static final String pu = "Week3TutorialExerciseStudentSolutionPU";
         private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;
    
    
    protected JPAPropertyRepositoryImpl()
    {
        this.entityManagerFactory = Persistence.createEntityManagerFactory(pu);
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }
    
    @Override
    public void addProperty(Property property) throws Exception
    {

        
                EntityTransaction transaction = this.entityManager.getTransaction();
        
        try {
            transaction.begin();
            this.entityManager.persist(property);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        
        
    }
    
    @Override
    public Property searchPropertyById(int id) throws Exception
    {   
        Property property = this.entityManager.find(Property.class, id);       
        return property;

    }
    
    @Override
    public List<Property> getAllProperties() throws Exception
    {
        return this.entityManager.createNamedQuery(Property.GET_ALL_QUERY_NAME).getResultList();
    }

    @Override
    public void removeProperty(int propertyId) throws Exception {
        EntityTransaction transaction = this.entityManager.getTransaction();
        
        try {
            transaction.begin();
            this.entityManager.remove(this.searchPropertyById(propertyId));
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void editProperty(Property property) throws Exception {
        EntityTransaction transaction = this.entityManager.getTransaction();
        
        try {
            transaction.begin();
            this.entityManager.merge(property);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void close() {
        EntityTransaction transaction = this.entityManager.getTransaction();
         
        try {
            transaction.begin();
            this.entityManager.close();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
    
    
}
