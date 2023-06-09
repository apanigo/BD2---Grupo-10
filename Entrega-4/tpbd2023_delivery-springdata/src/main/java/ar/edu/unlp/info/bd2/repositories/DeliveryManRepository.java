package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.DeliveryMan;
import ar.edu.unlp.info.bd2.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryManRepository extends CrudRepository<DeliveryMan, Long> {

    @Query("SELECT d FROM DeliveryMan d WHERE d.free = :free")
    Optional<DeliveryMan> getAFreeDeliveryMan(@Param("free") boolean free);
}
