package com.sysmap.parrot.entities;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.sysmap.parrot.services.enumeration.RoleEnum;
import com.sysmap.parrot.services.user.FindUserResponse;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class User implements UserDetails {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private RoleEnum role;

    public User(String name, String email, String password, RoleEnum role) {
        setId();
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    protected void setId() {
        this.id = UUID.randomUUID();
    }
    public UUID getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
