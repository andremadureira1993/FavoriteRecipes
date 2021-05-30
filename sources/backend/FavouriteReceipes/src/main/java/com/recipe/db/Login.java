package com.recipe.db;

import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

public class Login {

    @Id
    @NotNull
    private String username;

    @NotNull
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
