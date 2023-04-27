package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.DeliveryException;
import ar.edu.unlp.info.bd2.model.*;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

import javax.persistence.NoResultException;
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

	public Item getItemById(long id) {
		return this.getClassByProperty("id", id, Item.class);
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

	public Item saveItem (Item newItem) throws DeliveryException {
		try {
			Long newItemId = this.saveClass(newItem);
			return this.getItemById(newItemId);
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

	public Product saveProduct(Product newProduct) throws DeliveryException{
		try {
			Long newProductId = this.saveClass(newProduct);
			return this.getProductById(newProductId).get();
		} catch (NoSuchElementException ne) {
			return null;
		}
	}

	public Optional<Product> getProductById(Long id) {
		return this.getOptionalById(id, Product.class);
	}

	public ProductType saveProductType(ProductType newProductType) throws DeliveryException{
		try {
			Long newProductTypeId = this.saveClass(newProductType);
			return this.getProductTypeById(newProductTypeId).get();
		} catch (NoSuchElementException ne) {
			return null;
		}
	}

	public Optional<ProductType> getProductTypeById(Long id) {
		return this.getOptionalById(id, ProductType.class);
	}

	public List<Product> getProductsByName(String name) {
		return this.getClassListByProperty("name", name, Product.class);
	}

	public List<Product> getProductsByType(String type) throws DeliveryException {
		String hql = "SELECT p FROM Product p JOIN p.types t WHERE t.name = :type";
		Query<Product> query = this.sessionFactory.getCurrentSession().createQuery(hql, Product.class);
		query.setParameter("type", type);
		if (query.getResultList().size() != 0) {
			return query.getResultList();
		} else {
			throw new DeliveryException("No existe el tipo de producto");
		}
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


	public Product updateProductPrice(Long id, float price) throws DeliveryException {
		try {
			Product product = this.getProductById(id).get();
			product.setPrice(price);
			product.setLastPriceUpdateDate(new Date());
			this.sessionFactory.getCurrentSession().update(product);
			return product;
		} catch (NoSuchElementException e) {
			throw new DeliveryException("No existe el producto a actualizar");
		}
	}

	public boolean addDeliveryManToOrder(Long order, DeliveryMan deliveryMan) throws DeliveryException{
		try {
			Order anOrder = this.getOrderById(order).get();
			//System.out.print(deliveryMan.isFree() +" , "+ anOrder.isDelivered() +" , "+ ((anOrder.getItems() == null)||(anOrder.getItems().isEmpty())));
			if (deliveryMan.isFree() && !anOrder.isDelivered() && !anOrder.getItems().isEmpty()) {
				anOrder.setDeliveryMan(deliveryMan);
				this.sessionFactory.getCurrentSession().update(anOrder);

				deliveryMan.setFree(false);
				this.sessionFactory.getCurrentSession().update(deliveryMan);

				return true;
			}
			return false;
		} catch (NoSuchElementException e) {
			throw new DeliveryException("No existe la orden");
		}
	}


	public Item addItemToOrder(Long order, Product product, int quantity, String description) throws DeliveryException {
		try {
			Order anOrder = this.getOrderById(order).get();
			Item newItem = new Item(quantity, description, anOrder, product);
			Item savedItem = this.saveItem(newItem);

			anOrder.addItem(savedItem);
			anOrder.setTotalPrice(anOrder.getTotalPrice() + (product.getPrice() * quantity));
			this.sessionFactory.getCurrentSession().update(anOrder);

			return savedItem;
		} catch(PersistenceException e) {
			throw new DeliveryException("");
		}
	}


	public boolean setOrderAsDelivered(Long order) throws DeliveryException {
		try {
			Order anOrder = this.getOrderById(order).get();
			if(anOrder.getDeliveryMan() != null){
				anOrder.setDelivered(true);
				this.sessionFactory.getCurrentSession().update(anOrder);

				DeliveryMan aDeliveryMan = anOrder.getDeliveryMan();
				aDeliveryMan.setNumberOfSuccessOrders(aDeliveryMan.getNumberOfSuccessOrders() + 1);
				aDeliveryMan.setScore(aDeliveryMan.getNumberOfSuccessOrders());
				aDeliveryMan.setFree(true);
				this.sessionFactory.getCurrentSession().update(aDeliveryMan);

				Client aClient = anOrder.getClient();
				aClient.setScore(aClient.getScore() + 1);
				this.sessionFactory.getCurrentSession().update(aClient);

				return true;
			}
			return false;
		} catch (NoSuchElementException e) {
			throw new DeliveryException("No existe la orden");
		}
	}
}
