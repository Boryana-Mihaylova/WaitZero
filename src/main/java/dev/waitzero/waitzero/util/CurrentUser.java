package dev.waitzero.waitzero.util;

import dev.waitzero.waitzero.model.entity.UserRole;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class CurrentUser {

    private Long id;
    private String username;
    private UserRole role;




    public Long getId() {
        return id;
    }

    public CurrentUser setId(Long id) {
        this.id = id;
        return this;
    }


    public UserRole getRole() {
        return role;
    }

    public CurrentUser setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public CurrentUser setUsername(String username) {
        this.username = username;
        return this;
    }
}
