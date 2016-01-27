package es.mhp.books.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by afuentes on 14/12/15.
 */
@NoRepositoryBean
public interface GenericRepository<T> extends JpaRepository<T, Long> {
}
