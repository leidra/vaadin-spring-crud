package es.mhp.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto extends AbstractDto {
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
