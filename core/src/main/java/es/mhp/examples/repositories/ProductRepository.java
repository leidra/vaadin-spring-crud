package es.mhp.examples.repositories;

import es.mhp.examples.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by afuentes on 26/01/16.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductByName(String name);

    @Query("Select p From Products p Where p.creationDate = :creationDate")
    List<Product> findProductsByCreationDate(@Param("creationDate") Date creationDate);
}
