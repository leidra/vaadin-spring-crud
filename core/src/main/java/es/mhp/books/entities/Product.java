package es.mhp.books.entities;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by afuentes on 14/12/15.
 */
@Entity
@Table(name = "products")
public class Product extends AbstractEntity {
    @NotNull
    @NotBlank
    @Size(max = 50)
    protected String name;
    @NotNull
    @NotBlank
    @Size(max = 50)
    protected String brand;

    protected User user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
