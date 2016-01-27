package es.mhp.books;

import es.mhp.books.entities.Product;
import es.mhp.books.repositories.ProductRepository;
import es.mhp.dtos.ProductDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        return convertEntity(product);
    }

    private ProductDto convertEntity(Product product) {
        return MAPPER_FACTORY.getMapperFacade(Product.class, ProductDto.class).map(product);
    }

    public List<ProductDto> findAll() {
        List<Product> products = repository.findAll();
        return convertEntitiesList(products);
    }

    private List<ProductDto> convertEntitiesList(List<Product> products) {
        List<ProductDto> results = new ArrayList<>();
        products.stream().forEach(p -> results.add(convertEntity(p)));

        return results;
    }

    private List<Product> convertDtoList(List<ProductDto> products) {
        List<Product> results = new ArrayList<>();
        products.stream().forEach(p -> results.add(convertDto(p)));

        return results;
    }

    private Product convertDto(ProductDto p) {
        return MAPPER_FACTORY.getMapperFacade(Product.class, ProductDto.class).mapReverse(p);
    }

    public List<ProductDto> findAll(Sort sort) {
        List<Product> products = repository.findAll(sort);
        return convertEntitiesList(products);
    }

    public List<ProductDto> findAll(Iterable<Long> iterable) {
        List<Product> products = repository.findAll(iterable);
        return convertEntitiesList(products);
    }

    public List<ProductDto> save(Iterable<ProductDto> iterable) {
        List<Product> products = convertDtoList((List<ProductDto>) iterable);
        products = repository.save(products);

        return convertEntitiesList(products);
    }

    public ProductDto save(ProductDto dto) {
        Product product = repository.save(convertDto(dto));

        return convertEntity(product);
    }

    public void flush() {
        repository.flush();
    }

    public ProductDto saveAndFlush(ProductDto s) {
        Product p = repository.saveAndFlush(convertDto(s));
        return convertEntity(p);
    }

    public void deleteInBatch(Iterable<ProductDto> iterable) {
        repository.deleteInBatch(convertDtoList((List<ProductDto>)iterable));
    }

    public void deleteAllInBatch() {
        repository.deleteAllInBatch();
    }

    public ProductDto getOne(Long aLong) {
        return convertEntity(repository.findOne(aLong));
    }

    public void delete(Long aLong) {
        repository.delete(aLong);
    }

    public void delete(ProductDto productDto) {
        repository.delete(convertDto(productDto));
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
