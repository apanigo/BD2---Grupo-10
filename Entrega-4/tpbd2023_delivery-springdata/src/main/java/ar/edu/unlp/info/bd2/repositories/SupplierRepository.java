package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long> {

    List<Supplier> findByNameContaining(String name);

    @Query("SELECT s FROM Supplier s JOIN s.products p GROUP BY s.id ORDER BY COUNT(p) DESC")
    Page<Supplier> getSupplierWithMoreProducts(Pageable pageable);

    @Query("SELECT DISTINCT p.supplier FROM Product p WHERE p.id IN (SELECT i.product.id FROM Item i JOIN i.order o JOIN o.qualification q WHERE q.score = 1)")
    List<Supplier> getSupplierWith1StarCalifications();
}
