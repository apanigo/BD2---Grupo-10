package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.DeliveryMan;
import ar.edu.unlp.info.bd2.model.Supplier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long> {

    @Query("SELECT s FROM Supplier s WHERE s.name LIKE %:name%")
    List<Supplier> findByName(@Param("name") String name);
}
