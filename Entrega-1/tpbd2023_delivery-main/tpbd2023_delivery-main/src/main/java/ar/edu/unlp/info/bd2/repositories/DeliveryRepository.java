package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.*;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

import javax.persistence.*;

@Repository
public class DeliveryRepository {
	
	 @Autowired
	 SessionFactory sessionFactory;
	 
	 public Client saveClient (Client newClient) {
	            long ClientId = (long) sessionFactory.getCurrentSession().save(newClient);
	            return getClientById(ClientId);
	    }
	 
    public Client getClientById(long id) {
        String query = "FROM Client WHERE id = :idC";  //HQL
        return (Client) sessionFactory.getCurrentSession().createQuery(query).setParameter("idC", id).uniqueResult();
    }
    
    public DeliveryMan saveDeliveryMan (DeliveryMan newDeliveryMan) {
        long DeliveryManId = (long) sessionFactory.getCurrentSession().save(newDeliveryMan);
        return getDeliveryManById(DeliveryManId);
    }
    
    public DeliveryMan getDeliveryManById(long id) {
        String query = "FROM DeliveryMan WHERE id = :idD";  //HQL
        return (DeliveryMan) sessionFactory.getCurrentSession().createQuery(query).setParameter("idD", id).uniqueResult();
    }
    
    public Optional<User> getUserById (long id) {
    	String query = "FROM User WHERE id = :idU";  //HQL
    	return sessionFactory.getCurrentSession().createQuery(query).setParameter("idU", id).uniqueResultOptional();
    }
    
    public Optional<User> getUserByEmail (String id) {
    	String query = "FROM User WHERE email = :emailU";  //HQL
    	return sessionFactory.getCurrentSession().createQuery(query).setParameter("emailU", id).uniqueResultOptional();
    }

}
