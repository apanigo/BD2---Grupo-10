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
import java.time.LocalDate;
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

	public <T> Optional<T> getOptionalById(String idProperty, Serializable id, Class<T> entityClass) {
		String hql = "FROM " + entityClass.getName() + " WHERE " + idProperty + " = :id";
		Query<T> query = this.sessionFactory.getCurrentSession().createQuery(hql, entityClass);
		query.setParameter("id", id);
		return Optional.ofNullable(query.uniqueResult());
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

	// métodos para DeliveryServiceTest
	public List<Product> getProductsByType(String type) throws DeliveryException {
		String hql = "SELECT p FROM Product p JOIN p.types t WHERE t.name = :type";
		Query<Product> query = this.sessionFactory.getCurrentSession().createQuery(hql, Product.class);
		query.setParameter("type", type);
		return query.getResultList();
	}

	// métodos para DeliveryStatisticsServiceTest
	public List<User> getTopNUserWithMoreScore(int n) {
		String hql = "FROM User u ORDER BY u.score DESC";
		Query<User> query = this.sessionFactory.getCurrentSession().createQuery(hql, User.class);
		return query.setMaxResults(n).getResultList();
	}

	public Long getNumberOfOrderDeliveredAndBetweenDates(Date startDate, Date endDate) {
		String hql = "SELECT COUNT(o.id) FROM Order o WHERE o.dateOfOrder BETWEEN :startDate AND :endDate AND o.delivered IS TRUE";
		Query<Long> query = this.sessionFactory.getCurrentSession().createQuery(hql, Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		return query.uniqueResult().longValue();
	}

	public Optional<Order> getOrderDeliveredMoreExpansiveInDate(Date aDate){
		String hql = "FROM Order o WHERE o.delivered IS TRUE AND o.dateOfOrder = :aDate AND o.totalPrice = (SELECT MAX(oo.totalPrice) from Order oo WHERE oo.delivered IS TRUE AND oo.dateOfOrder LIKE :aDate)";
		Query<Order> query = this.sessionFactory.getCurrentSession().createQuery(hql, Order.class);
		query.setParameter("aDate", aDate);
		return Optional.ofNullable(query.uniqueResult());
	}

	public List<Supplier> getSuppliersWithoutProducts(){
		String hql = "FROM Supplier s WHERE NOT EXISTS (SELECT p FROM Product p WHERE p.supplier = s.id)";
		Query<Supplier> query = this.sessionFactory.getCurrentSession().createQuery(hql, Supplier.class);
		return query.list();
	}

	public List<Product> getProductsWithPriceDateOlderThan(int days){
		Date queryDate = java.sql.Date.valueOf(LocalDate.now().minusDays(days));
		String hql = "FROM Product p WHERE lastPriceUpdateDate <= :queryDate";
		Query<Product> query = this.sessionFactory.getCurrentSession().createQuery(hql, Product.class);
		query.setParameter("queryDate", queryDate);
		return query.getResultList();
	}

	public List<Product> getTop5MoreExpansiveProducts() {
		String hql = "FROM Product p ORDER BY p.price DESC";
		Query<Product> query = this.sessionFactory.getCurrentSession().createQuery(hql, Product.class);
		return query.setMaxResults(5).getResultList();
	}

    public List<Product> getProductsNoAddedToOrders() {
		String hql= "SELECT p " +
					"FROM Product p " +
					"WHERE p NOT IN (" +
					"    SELECT DISTINCT pi.id " +
					"    FROM Item i " +
					"    JOIN i.product pi" +
					")";
		Query<Product> query = this.sessionFactory.getCurrentSession().createQuery(hql, Product.class);
		return query.list();
    }

	public List<ProductType> getTop3ProductTypesWithLessProducts() {
		String hql= "SELECT pt " +
					"FROM ProductType pt " +
					"LEFT JOIN pt.products p " +
					"GROUP BY pt.id " +
					"ORDER BY COUNT(p) ASC";
		Query<ProductType> query = this.sessionFactory.getCurrentSession().createQuery(hql, ProductType.class);
		return query.setMaxResults(3).getResultList();
	}

	public Supplier getSupplierWithMoreProducts() {
		String hql= "SELECT s " +
					"FROM Supplier s " +
					"JOIN s.products p " +
					"GROUP BY s.id " +
					"ORDER BY COUNT(p) DESC";
		Query<Supplier> query = this.sessionFactory.getCurrentSession().createQuery(hql, Supplier.class);
		return query.setMaxResults(1).uniqueResult();
	}

	public List<Supplier> getSupplierWith1StarCalifications() {
		String hql = "SELECT DISTINCT p.supplier " +
				"FROM Product p " +
				"WHERE p.id IN (" +
				"    SELECT i.product.id " +
				"    FROM Item i " +
				"    JOIN i.order o " +
				"    JOIN o.qualification q " +
				"    WHERE q.score = 1" +
				")";
		Query<Supplier> query = this.sessionFactory.getCurrentSession().createQuery(hql, Supplier.class);
		return query.getResultList();
	}
}
