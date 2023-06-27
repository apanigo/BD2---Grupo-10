package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.ProductType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTypeRepository extends CrudRepository<ProductType, Long> {
    @Query("SELECT pt FROM ProductType pt LEFT JOIN pt.products p GROUP BY pt.id ORDER BY COUNT(p) ASC")
    List<ProductType> getTop3ProductTypesWithLessProducts(Pageable pageable);
}
