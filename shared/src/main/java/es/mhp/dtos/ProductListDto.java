package es.mhp.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductListDto implements Dto {
    private Long id;
    @NotNull
    @Size(max = 50)
    protected String name;
    @NotNull
    @Size(max = 50)
    protected String brand;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

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

}
