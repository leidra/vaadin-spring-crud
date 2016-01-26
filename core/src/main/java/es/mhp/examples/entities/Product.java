package es.mhp.examples.entities;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by afuentes on 26/01/16.
 */
@Entity
@Table(name = "products")
public class Product extends AbstractPersistable<Long> {
    private String name;
    private Date creationDate;

    protected void setId(final Long id) {
        super.setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
