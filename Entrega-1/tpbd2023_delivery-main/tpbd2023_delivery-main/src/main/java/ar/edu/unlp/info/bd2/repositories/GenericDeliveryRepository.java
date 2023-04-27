package ar.edu.unlp.info.bd2.repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GenericDeliveryRepository {
	
	@Autowired
	SessionFactory sessionFactory;
	
	
	protected <T> Long saveClass(T model) {
	    return (Long) this.sessionFactory.getCurrentSession().save(model);
	}
	
	protected <T> Optional<T> getOptionalByProperty(String property, Object value, Class<T> entityClass) {
		String hql = "FROM " + entityClass.getName() + " WHERE " + property + " = :value";
	    Query<T> query = this.sessionFactory.getCurrentSession().createQuery(hql, entityClass);
	    query.setParameter("value", value);
	    return Optional.ofNullable(query.uniqueResult());
    }

	protected <T> T getClassByProperty(String property, Object value, Class<T> entityClass) {
		String hql = "FROM " + entityClass.getName() + " WHERE " + property + " = :value";
	    Query<T> query = this.sessionFactory.getCurrentSession().createQuery(hql, entityClass);
	    query.setParameter("value", value);
	    return query.uniqueResult();
    }
	
	protected <T> Optional<T> getOptionalById(Serializable id, Class<T> entityClass) {
	    T entity = sessionFactory.getCurrentSession().get(entityClass, id);
	    return Optional.ofNullable(entity);
	}
	
	protected <T> List<T> getClassListByProperty(String property, Object value, Class<T> entityClass) {
		String hql = "FROM " + entityClass.getName() + " WHERE " + property + " LIKE CONCAT('%',:value,'%')";
	    Query<T> query = this.sessionFactory.getCurrentSession().createQuery(hql, entityClass);
	    query.setParameter("value", value);
	    return query.getResultList();
    }
	
	protected <T> List<T> getClassListByExactProperty(String propertyName, Object propertyValue, Class<T> entityClass) {
		String queryString = "FROM " + entityClass.getName() + " WHERE " + propertyName + " = :propertyValue";
	    Query<T> query = this.sessionFactory.getCurrentSession().createQuery(queryString, entityClass);
	    query.setParameter("propertyValue", propertyValue);
	    return query.getResultList();
    }
	
//	protected <T> List<Product> getProductsByTypeName(String productTypeName) {
//		
//	}
	
}
