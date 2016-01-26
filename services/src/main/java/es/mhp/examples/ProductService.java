package es.mhp.examples;

import es.mhp.dtos.ProductDto;
import es.mhp.examples.entities.Product;
import es.mhp.examples.repositories.ProductRepository;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by afuentes on 26/01/16.
 */
@Service
public class ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private final static MapperFactory MAPPER_FACTORY = new DefaultMapperFactory.Builder().build();

    @Autowired
    private ProductRepository repository;

    public ProductDto findById(Long id) {
        Product product = repository.findOne(id);

        return MAPPER_FACTORY.getMapperFacade(Product.class, ProductDto.class).map(product);
    }
}
