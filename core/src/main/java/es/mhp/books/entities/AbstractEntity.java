package es.mhp.books.entities;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by afuentes on 28/12/15.
 */
@MappedSuperclass
public class AbstractEntity extends AbstractPersistable<Long> {
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    protected Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    protected Date lastModifiedDate;

    public void setId(Long id) {
        super.setId(id);
    }

    @Column(name = "created_name")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "last_modified_name")
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(final Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @PrePersist
    public void touchForCreate() {
        if (this.isNew()) {
            this.setCreatedDate(new Date());
        }
        this.setLastModifiedDate(new Date());
    }

    @PreUpdate
    public void touchForUpdate() {
        this.setLastModifiedDate(new Date());
    }

}