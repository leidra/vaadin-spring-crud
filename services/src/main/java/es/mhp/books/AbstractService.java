package es.mhp.books;

import es.mhp.books.repositories.GenericRepository;
import es.mhp.dtos.AbstractDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

/**
 * Created by afuentes on 14/12/15.
 */
public abstract class AbstractService<ENTITY, DTO extends AbstractDto> implements Service<DTO> {
    @Autowired
    protected GenericRepository<ENTITY> repository;
    protected static final MapperFactory mapperFactory = new DefaultMapperFactory
            .Builder().useBuiltinConverters(true).build();

    private Class<ENTITY> entityClass;
    private Class<DTO> dtoClass;

    @PostConstruct
    public void postConstruct() {
        configureMapper();
    }

    public DTO findOne(Long id) {
        ENTITY entity = this.repository.findOne(id);

        return convertToDto(entity);
    }

    public Set<DTO> findAll() {
        List<ENTITY> entities = this.repository.findAll();

        return convertToDtos(entities);
    }

    public DTO save(DTO dto) {
        Assert.notNull(dto);
        ENTITY entity = saveTransactional(dto);
        return convertToDto(entity);
    }

    public void remove(DTO dto) {
        Assert.notNull(dto);
        this.remove(dto.getId());
    }

    public void remove(Long id) {
        repository.delete(id);
    }

    protected abstract ENTITY saveTransactional(DTO dto);

    protected DTO convertToDto(ENTITY entity) {
        return getMapperFactory().getMapperFacade().map(entity, dtoClass);
    }

    protected void configureMapper() {
        Type[] actualTypeArguments = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        entityClass = (Class) actualTypeArguments[0];
        dtoClass = (Class) actualTypeArguments[1];

        getMapperFactory().classMap(entityClass, dtoClass);
    }

    protected Set<DTO> convertToDtos(List<ENTITY> entity) {
        return getMapperFactory().getMapperFacade().mapAsSet(entity, dtoClass);
    }

    protected ENTITY convertToEntity(DTO dto) {
        return getMapperFactory().getMapperFacade().map(dto, entityClass);
    }

    protected Set<ENTITY> convertToEntities(List<DTO> dto) {
        return getMapperFactory().getMapperFacade().mapAsSet(dto, entityClass);
    }

    protected MapperFactory getMapperFactory() {
        return mapperFactory;
    }
}