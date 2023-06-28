package ar.edu.unlp.info.bd2.repositories;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unlp.info.bd2.model.Client;
import ar.edu.unlp.info.bd2.model.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

	
	Long countByDeliveredFalse();
	
	Long countByDeliveredTrueAndDateOfOrderBetween(Date startDate, Date endDate);
	
	Optional<Order> findFirstByDeliveredTrueAndDateOfOrderOrderByTotalPriceDesc(Date aDate);
	
}
