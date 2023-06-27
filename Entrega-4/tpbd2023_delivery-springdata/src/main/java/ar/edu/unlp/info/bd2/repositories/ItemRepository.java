package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.Item;
import ar.edu.unlp.info.bd2.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository  extends CrudRepository<Item, Long> {
    @Query("SELECT i.product FROM Item i GROUP BY i.product.id ORDER BY SUM(i.quantity) DESC")
    List<Product> findMostDemandedProduct();


}
