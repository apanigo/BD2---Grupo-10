package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.DeliveryException;
import ar.edu.unlp.info.bd2.model.*;

import org.springframework.stereotype.Repository;
import java.util.*;

import javax.persistence.PersistenceException;

@Repository
public class DeliveryRepository extends GenericDeliveryRepository{
	
	public Client saveClient (Client newClient) throws DeliveryException {
		try {
			Long newClientId = this.saveClass(newClient);
			return this.getClientById(newClientId);
		} catch (PersistenceException  e) {
			throw new DeliveryException("Constraint Violation");
		}
	}
		 
	public Client getClientById(long id) {
	    return this.getClassByProperty("id", id, Client.class);
	}
	
	public DeliveryMan saveDeliveryMan (DeliveryMan newDeliveryMan) throws DeliveryException {
		try {
			Long newDeliveryManId = this.saveClass(newDeliveryMan);
			return this.getDeliveryManById(newDeliveryManId);
		} catch (PersistenceException  e) {
			throw new DeliveryException("Constraint Violation");
		}
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
	
	public Address saveAddress (Address newAddress) throws DeliveryException {
		try {
			Long newAddressId = this.saveClass(newAddress);
			return this.getAddressById(newAddressId);
		} catch (PersistenceException  e) {
			throw new DeliveryException("Constraint Violation");
		}
	}

	public Supplier saveSupplier(Supplier newSupplier) throws DeliveryException {
		try {
			Long newSupplierId = this.saveClass(newSupplier);
			return this.getSuppliersById(newSupplierId);
		} catch (PersistenceException  e) {
			throw new DeliveryException("Constraint Violation");
		}
	}

	private Supplier getSuppliersById(Long id) {
		return this.getClassByProperty("id", id, Supplier.class);
	}

	public List<Supplier> getSupplierByName(String name) {
		return this.getClassListByProperty("name", name, Supplier.class);
	}

	public Order saveOrder(Order newOrder) throws DeliveryException {
		try {
			Long newOrderId = this.saveClass(newOrder);
			return this.getOrderById(newOrderId).get();
		} catch (PersistenceException  e) {
			throw new DeliveryException("Constraint Violation");
		}
	}

	public Optional<Order> getOrderById(Long id) {
		return this.getOptionalById(id, Order.class);
	}
}
