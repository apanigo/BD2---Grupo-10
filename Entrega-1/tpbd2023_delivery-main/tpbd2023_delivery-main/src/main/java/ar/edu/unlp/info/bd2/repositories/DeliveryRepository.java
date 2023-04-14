package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.*;

import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class DeliveryRepository extends GenericDeliveryRepository{
	
	public Client saveClient (Client newClient) {
		Long newClientId = this.saveClass(newClient);
		return this.getClientById(newClientId);
	}
		 
	public Client getClientById(long id) {
	    return this.getClassByProperty("id", id, Client.class);
	}
	
	public DeliveryMan saveDeliveryMan (DeliveryMan newDeliveryMan) {
		Long newDeliveryManId = this.saveClass(newDeliveryMan);
		return this.getDeliveryManById(newDeliveryManId);
	}
	
	public DeliveryMan getDeliveryManById(long id) {
		return this.getClassByProperty("id", id, DeliveryMan.class);
	}
	
	public Optional<User> getUserById (long id) {
		return this.getOptionalById(id, User.class);
	}
	
	public Optional<User> getUserByEmail (String email) {
		return this.getOptionalByProperty("email", email, User.class);
	}
	
	public Optional<DeliveryMan> getFreeDeliveryMan() {
		Boolean free = true;
		return this.getOptionalByProperty("free", free, DeliveryMan.class);
	}
	
	public DeliveryMan updateDeliveryMan(DeliveryMan aDeliveryMan) {
		this.sessionFactory.getCurrentSession().update(aDeliveryMan);
		return this.getDeliveryManById(aDeliveryMan.getId());
	}
	
	public Address getAddressById(long id) {
		return this.getClassByProperty("id", id, Address.class);
	}
	
	public Address saveAddress (Address newAddress) {
		Long newAddressId = this.saveClass(newAddress);
		return this.getAddressById(newAddressId);
	}

}