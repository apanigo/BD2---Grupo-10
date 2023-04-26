package ar.edu.unlp.info.bd2.repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GenericDeliveryRepository {
	
	@Autowired
	SessionFactory sessionFactory;
	
	
	protected <T> Long saveClass(T model) {
		Long classId = (Long) this.sessionFactory.getCurrentSession().save(model);
	    return classId;
	}
	
	protected <T> Optional<T> getOptionalByProperty(String propertyName, Object propertyValue, Class<T> entityClass) {
		String queryString = "FROM " + entityClass.getName() + " WHERE " + propertyName + " = :propertyValue";
	    Query<T> query = this.sessionFactory.getCurrentSession().createQuery(queryString, entityClass);
	    query.setParameter("propertyValue", propertyValue);
	    List<T> resultList = query.getResultList();
	    return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }
	
	protected <T> List<T> getSimilarsByProperty(String propertyName, Object propertyValue, Class<T> entityClass) {
		String queryString = "FROM " + entityClass.getName() + " WHERE " + propertyName + " LIKE '%:propertyValue%'";
	    Query<T> query = this.sessionFactory.getCurrentSession().createQuery(queryString, entityClass);
	    query.setParameter("propertyValue", propertyValue);
	    List<T> resultList = query.getResultList();
	    return  resultList;
    }

	protected <T> T getClassByProperty(String propertyName, Object propertyValue, Class<T> entityClass) {
		String queryString = "FROM " + entityClass.getName() + " WHERE " + propertyName + " = :propertyValue";
	    Query<T> query = this.sessionFactory.getCurrentSession().createQuery(queryString, entityClass);
	    query.setParameter("propertyValue", propertyValue);
	    List<T> resultList = query.getResultList();
	    return resultList.isEmpty() ? null : resultList.get(0);
    }
	
	protected <T> Optional<T> getOptionalById(Serializable id, Class<T> entityClass) {
	    T entity = sessionFactory.getCurrentSession().get(entityClass, id);
	    return Optional.ofNullable(entity);
	}
	
	protected <T> List<T> getClassListByProperty(String propertyName, Object propertyValue, Class<T> entityClass) {
		String queryString = "FROM " + entityClass.getName() + " WHERE " + propertyName + " LIKE CONCAT('%',:propertyValue,'%')";
	    Query<T> query = this.sessionFactory.getCurrentSession().createQuery(queryString, entityClass);
	    query.setParameter("propertyValue", propertyValue);
	    return query.getResultList();
    }
}
