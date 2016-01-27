package es.mhp.books;

import java.util.Set;

/**
 * Created by afuentes on 2/01/16.
 */
public interface Service<DTO> {
    DTO findOne(Long id);

    Set<DTO> findAll();

    void remove(DTO dto);

    void remove(Long id);

    DTO save(DTO dto);
}
