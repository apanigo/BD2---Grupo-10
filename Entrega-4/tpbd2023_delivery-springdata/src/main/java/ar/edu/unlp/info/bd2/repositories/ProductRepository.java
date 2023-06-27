package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByNameContaining(String name);

    List<Product> findByTypesName(String type);

    List<Product> findByLastPriceUpdateDateLessThanEqual(Date date);

    List<Product> findTop5ByOrderByPriceDesc();

    @Query("FROM Product WHERE id NOT IN (SELECT i.product.id FROM Item i)")
    List<Product> getProductsNoAddedToOrders(); //probé findProductsNotInItems - findProductsNotInAnyItem - findByItemsIsNull - findByNotInItems -
}
