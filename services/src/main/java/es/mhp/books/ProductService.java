package es.mhp.books;

import es.mhp.books.entities.Product;
import es.mhp.dtos.ProductDto;
import es.mhp.dtos.ProductListDto;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by afuentes on 14/12/15.
 */
@Service(ProductService.SERVICE_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ProductService extends AbstractService<Product, ProductDto> {
    public static final String SERVICE_NAME = "ProductService";

    @Transactional
    protected Product saveTransactional(ProductDto dto) {
        Product product = convertToEntity(dto);

        return repository.saveAndFlush(product);
    }

    public Set<ProductListDto> findAllForList() {
        List<Product> entities = this.repository.findAll();

        return getMapperFactory().getMapperFacade().mapAsSet(entities, ProductListDto.class);
    }

}
