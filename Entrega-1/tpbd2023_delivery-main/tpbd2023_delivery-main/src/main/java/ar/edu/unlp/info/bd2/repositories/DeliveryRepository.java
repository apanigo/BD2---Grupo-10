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

	// metodos para DeliveryServiceTest
	public List<Product> getProductsByType(String type) throws DeliveryException {
		String hql = "SELECT p FROM Product p JOIN p.types t WHERE t.name = :type";
		Query<Product> query = this.sessionFactory.getCurrentSession().createQuery(hql, Product.class);
		query.setParameter("type", type);
		return query.getResultList();
	}

	// metodos para DeliveryStatisticsServiceTest
	public List<User> getTopNUserWithMoreScore(int n) {
		String hql = "FROM User u ORDER BY u.score DESC";
		Query<User> query = this.sessionFactory.getCurrentSession().createQuery(hql, User.class);
		return query.setMaxResults(n).getResultList();
	}
	
	// metodos para DeliveryStatisticsServiceTest
	public Long getNumberOfOrderDeliveredAndBetweenDates(Date startDate, Date endDate) {
		System.out.print(startDate);
		System.out.print(endDate);
		String hql = "SELECT COUNT(o.id) FROM Order o WHERE o.dateOfOrder BETWEEN :startDate AND :endDate AND o.delivered IS TRUE";
		Query<Long> query = this.sessionFactory.getCurrentSession().createQuery(hql, Long.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		return query.uniqueResult().longValue();
	}
	
	
	

}
