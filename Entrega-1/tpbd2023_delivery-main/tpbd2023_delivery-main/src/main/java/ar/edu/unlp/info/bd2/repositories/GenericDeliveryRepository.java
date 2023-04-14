package ar.edu.unlp.info.bd2.repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GenericDeliveryRepository {
	
	@Autowired
	SessionFactory sessionFactory;
	
	
	protected <T> Long saveClass(T model) {
		Long classId = (Long) this.sessionFactory.getCurrentSession().save(model);
	    return classId;
	}
	
	protected <T> Optional<T> getOptionalByProperty(String propertyName, Object propertyValue, Class<T> entityClass) {
		CriteriaBuilder builder = this.sessionFactory.getCurrentSession().getCriteriaBuilder();
		// Crea un objeto CriteriaBuilder para la entityClass
	    CriteriaQuery<T> criteria = builder.createQuery(entityClass);
	    // Selecciona todas las entidades del tipo entityClass
        criteria.select(criteria.from(entityClass));
        // Filtra las entidades según el valor (propertyValue) de la propiedad (propertyName)
        criteria.where(builder.equal(criteria.from(entityClass).get(propertyName), propertyValue));
        // Ejecuta query y obtiene la lista de resultados
        List<T> resultList = this.sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
        // Si la lista está vacía retorna un opcional vacío
	    return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }

	protected <T> T getClassByProperty(String propertyName, Object propertyValue, Class<T> entityClass) {
        CriteriaBuilder builder = this.sessionFactory.getCurrentSession().getCriteriaBuilder();
        // Crea un objeto CriteriaBuilder para la entityClass
        CriteriaQuery<T> criteria = builder.createQuery(entityClass);
        // Selecciona todas las entidades del tipo entityClass
        criteria.select(criteria.from(entityClass));
        // Filtra las entidades según el valor (propertyValue) de la propiedad (propertyName)
        criteria.where(builder.equal(criteria.from(entityClass).get(propertyName), propertyValue));
        // Ejecuta la query y retorna el resultado único
        return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
    }
	
	protected <T> Optional<T> getOptionalById(Serializable id, Class<T> entityClass) {
	    T entity = sessionFactory.getCurrentSession().get(entityClass, id);
	    return Optional.ofNullable(entity);
	}
}
