package ar.edu.unlp.info.bd2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unlp.info.bd2.model.DeliveryMan;

@Repository
public interface DeliveryManRepository extends CrudRepository<DeliveryMan, Long> {

    Optional<DeliveryMan> findByFree(boolean free);
    
    List<DeliveryMan> findByOrderByNumberOfSuccessOrdersDesc(Pageable pageable);
}
