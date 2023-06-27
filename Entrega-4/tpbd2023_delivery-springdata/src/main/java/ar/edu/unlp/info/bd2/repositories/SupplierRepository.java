package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.DeliveryMan;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.Supplier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long> {

    List<Supplier> findByNameContaining(String name);

    @Query("SELECT s FROM Supplier s JOIN s.products p GROUP BY s.id ORDER BY COUNT(p) DESC")
    List<Supplier> getSupplierWithMoreProducts();

    @Query("SELECT DISTINCT p.supplier FROM Product p WHERE p.id IN (SELECT i.product.id FROM Item i JOIN i.order o JOIN o.qualification q WHERE q.score = 1)")
    List<Supplier> getSupplierWith1StarCalifications();
}
