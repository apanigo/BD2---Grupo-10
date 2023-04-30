package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.DeliveryException;
import ar.edu.unlp.info.bd2.model.*;

import javassist.NotFoundException;
import org.hibernate.PersistentObjectException;
import org.hibernate.SessionFactory;
import org.hibernate.exception.*;
import org.hibernate.hql.internal.ast.QuerySyntaxException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

@Repository
public class DeliveryRepository {

	@Autowired
	SessionFactory sessionFactory;

	// metodos genericos
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

	public <T> T getClassByProperty(String property, Object value, Class<T> entityClass) {
		String hql = "FROM " + entityClass.getName() + " WHERE " + property + " = :value";
		Query<T> query = this.sessionFactory.getCurrentSession().createQuery(hql, entityClass);
		query.setParameter("value", value);
		return query.uniqueResult();
	}

	public <T> Optional<T> getOptionalByProperty(String property, Object value, Class<T> entityClass) {
		String hql = "FROM " + entityClass.getName() + " WHERE " + property + " = :value";
		Query<T> query = this.sessionFactory.getCurrentSession().createQuery(hql, entityClass);
		query.setParameter("value", value);
		return Optional.ofNullable(query.uniqueResult());
	}

	public <T> Optional<T> getOptionalById(Serializable id, Class<T> entityClass) {
		T entity = sessionFactory.getCurrentSession().get(entityClass, id);
		return Optional.ofNullable(entity);
	}

	public <T> List<T> getClassListByProperty(String property, Object value, Class<T> entityClass) {
		String hql = "FROM " + entityClass.getName() + " WHERE " + property + " LIKE CONCAT('%',:value,'%')";
		Query<T> query = this.sessionFactory.getCurrentSession().createQuery(hql, entityClass);
		query.setParameter("value", value);
		return query.getResultList();
	}

	public <T> List<T> getClassListByExactProperty(String propertyName, Object propertyValue, Class<T> entityClass) {
		String queryString = "FROM " + entityClass.getName() + " WHERE " + propertyName + " = :propertyValue";
		Query<T> query = this.sessionFactory.getCurrentSession().createQuery(queryString, entityClass);
		query.setParameter("propertyValue", propertyValue);
		return query.getResultList();
	}

	// metodos para DeliveryServiceTest
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

	public Product updateProductPrice(Long id, float price) throws DeliveryException {
		try {
			Product product = this.getClassByProperty("id", id, Product.class);
			product.setPrice(price);
			product.setLastPriceUpdateDate(new Date());
			this.updateClass(product);

			return product;
		} catch (Exception e) {
			if (e instanceof NoSuchElementException || e instanceof NullPointerException) {
				throw new DeliveryException("No existe el producto a actualizar");
			} else {
				throw new DeliveryException("Hubo un error");
			}
		}
	}

	public boolean addDeliveryManToOrder(Long order, DeliveryMan deliveryMan) throws DeliveryException{
		try {
			Order anOrder = this.getClassByProperty("id", order, Order.class);
			//System.out.print(deliveryMan.isFree() +" , "+ anOrder.isDelivered() +" , "+ ((anOrder.getItems() == null)||(anOrder.getItems().isEmpty())));
			if (deliveryMan.isFree() && !anOrder.isDelivered() && !anOrder.getItems().isEmpty()) {
				anOrder.setDeliveryMan(deliveryMan);
				this.updateClass(anOrder);

				deliveryMan.setFree(false);
				this.updateClass(deliveryMan);

				return true;
			}
			return false;
		} catch (Exception e) {
			if (e instanceof NoSuchElementException || e instanceof NullPointerException) {
				throw new DeliveryException("No existe la orden");
			} else {
				throw new DeliveryException("Hubo un error");
			}
		}
	}


	public Item addItemToOrder(Long order, Product product, int quantity, String description) throws DeliveryException {
		try {
			Order anOrder = this.getClassByProperty("id", order, Order.class);
			Item newItem = new Item(quantity, description, anOrder, product);
			this.saveClass(newItem);

			anOrder.addItem(newItem);
			anOrder.setTotalPrice(anOrder.getTotalPrice() + (product.getPrice() * quantity));
			this.updateClass(anOrder);

			return newItem;
		} catch (Exception e) {
			if (e instanceof NoSuchElementException || e instanceof NullPointerException) {
				throw new DeliveryException("No existe la orden");
			} else {
				throw new DeliveryException("Hubo un error");
			}
		}
	}

	public boolean setOrderAsDelivered(Long order) throws DeliveryException {
		try {
			Order anOrder = this.getClassByProperty("id", order, Order.class);
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
		} catch (Exception e) {
			if (e instanceof NoSuchElementException || e instanceof NullPointerException) {
				throw new DeliveryException("No existe la orden");
			} else {
				throw new DeliveryException("Hubo un error");
			}
		}
	}

	public Qualification addQualificatioToOrder(Long order, String commentary) throws DeliveryException {
		try {
			Order anOrder = this.getClassByProperty("id", order, Order.class);

			Qualification aQualification = new Qualification(5, commentary, anOrder);
			this.saveClass(aQualification);

			anOrder.setQualification(aQualification);
			this.updateClass(anOrder);

			return aQualification;
		} catch (Exception e) {
			if (e instanceof NoSuchElementException || e instanceof NullPointerException) {
				throw new DeliveryException("No existe la orden");
			} else {
				throw new DeliveryException("Hubo un error");
			}
		}
	}

	public List<User> getTopNUserWithMoreScore(int n) {
		String hql = "FROM User u ORDER BY u.score DESC";
		Query<User> query = this.sessionFactory.getCurrentSession().createQuery(hql, User.class);
		query.setMaxResults(n);
		return query.getResultList();
	}
}
