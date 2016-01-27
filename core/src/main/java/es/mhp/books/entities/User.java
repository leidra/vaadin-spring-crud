package es.mhp.books.entities;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by afuentes on 14/12/15.
 */
@Entity
@Table(name="users")
public class User extends AbstractPersistable<Long> {
    @NotNull
    @Size(max = 50)
    protected String name;
    @NotNull
    @Size(max = 250)
    protected String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
