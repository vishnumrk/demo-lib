package io.ms.lib;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class Demo {

    @NotEmpty
    private String name;
    @NotEmpty
    @Email
    private String email;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
