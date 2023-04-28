package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.DeliveryException;
import ar.edu.unlp.info.bd2.model.*;

import org.hibernate.PersistentObjectException;
import org.hibernate.exception.*;
import org.hibernate.hql.internal.ast.QuerySyntaxException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

@Repository
public class DeliveryRepository extends GenericDeliveryRepository{

	public <T> void saveClass(T model) throws DeliveryException {
		try {
			this.sessionFactory.getCurrentSession().save(model);
		} catch (Exception e) {
			if (e instanceof PersistentObjectException) {
				throw new DeliveryException("Detached entity passed to persist");
			} else if (e instanceof OptimisticLockException) {
				throw new DeliveryException("Error while committing the transaction");
			} else if (e instanceof QuerySyntaxException) {
				throw new DeliveryException("Unexpected token in query");
			} else if (e instanceof ConstraintViolationException) {
				throw new DeliveryException("Constraint Violation");
			} else {
				throw new DeliveryException("Hubo un error");
			}
		}
	}

	public <T> void updateClass(T model) throws DeliveryException {
		try {
			this.sessionFactory.getCurrentSession().update(model);
		} catch (Exception e) {
			if (e instanceof OptimisticLockException) {
				throw new DeliveryException("Error while committing the transaction");
			} else if (e instanceof QuerySyntaxException) {
				throw new DeliveryException("Unexpected token in query");
			} else if (e instanceof ConstraintViolationException) {
				throw new DeliveryException("Constraint Violation");
			} else {
				throw new DeliveryException("Hubo un error");
			}
		}
	}

	public Client getClientById(long id) {
		return this.getClassByProperty("id", id, Client.class); //tiene sentido esto o mejor hacerlo directo en service? no esta mal
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

	private Supplier getSuppliersById(Long id) {
		return this.getClassByProperty("id", id, Supplier.class);
	}

	public List<Supplier> getSupplierByName(String name) {
		return this.getClassListByProperty("name", name, Supplier.class);
	}

	public Optional<Product> getProductById(Long id) {
		return this.getOptionalById(id, Product.class);
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

	public Optional<Order> getOrderById(Long id) {
		return this.getOptionalById(id, Order.class);
	}


	public Product updateProductPrice(Long id, float price) throws DeliveryException {
		try {
			Product product = this.getProductById(id).get();
			product.setPrice(price);
			product.setLastPriceUpdateDate(new Date());
			this.updateClass(product);

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
				this.updateClass(anOrder);

				deliveryMan.setFree(false);
				this.updateClass(deliveryMan);

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
			this.saveClass(newItem);

			anOrder.addItem(newItem);
			anOrder.setTotalPrice(anOrder.getTotalPrice() + (product.getPrice() * quantity));
			this.updateClass(anOrder);

			return newItem;
		} catch(PersistenceException e) {
			throw new DeliveryException("");
		}
	}


	public boolean setOrderAsDelivered(Long order) throws DeliveryException {
		try {
			Order anOrder = this.getOrderById(order).get();
			if(anOrder.getDeliveryMan() != null){
				anOrder.setDelivered(true);
				this.updateClass(anOrder);

				DeliveryMan aDeliveryMan = anOrder.getDeliveryMan();
				aDeliveryMan.setNumberOfSuccessOrders(aDeliveryMan.getNumberOfSuccessOrders() + 1);
				aDeliveryMan.setScore(aDeliveryMan.getNumberOfSuccessOrders());
				aDeliveryMan.setFree(true);
				this.updateClass(aDeliveryMan);

				Client aClient = anOrder.getClient();
				aClient.setScore(aClient.getScore() + 1);
				this.updateClass(aClient);

				return true;
			}
			return false;
		} catch (NoSuchElementException e) {
			throw new DeliveryException("No existe la orden");
		}
	}

	public Qualification addQualificatioToOrder(Long order, String commentary) throws DeliveryException {
		try {
			Order anOrder = this.getOrderById(order).get();

			Qualification aQualification = new Qualification(5, commentary, anOrder);
			this.saveClass(aQualification);

			anOrder.setQualification(aQualification);
			this.updateClass(anOrder);

			return aQualification;
		} catch (NoSuchElementException e) {
			throw new DeliveryException("No existe la orden");
		}
	}
}
