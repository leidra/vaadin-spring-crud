package es.mhp.books.repositories;

import es.mhp.books.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by afuentes on 26/01/16.
 */
public interface ProductRepository extends GenericRepository<Product> {
    Product findProductByName(String name);

    @Query("Select p From Product p Where p.createdDate = :createdDate")
    List<Product> findProductsByCreationDate(@Param("createdDate") Date creationDate);
}
