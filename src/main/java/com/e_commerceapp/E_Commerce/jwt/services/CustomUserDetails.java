package com.e_commerceapp.E_Commerce.jwt.services;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Objects;
import com.e_commerceapp.E_Commerce.model.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Long id;
    private String username;
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public static CustomUserDetails build(Users user) {

        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(c -> (GrantedAuthority) new SimpleGrantedAuthority(c.getAppenum().name()))
                .toList();

        return new CustomUserDetails(
                user.getUserid(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
    @Override
public boolean equals(Object o) {

    if (this == o) {
        return true;
    }

    if (o == null || getClass() != o.getClass()) {
        return false;
    }

    CustomUserDetails customUserDetails = (CustomUserDetails) o;

    return Objects.equals(id, customUserDetails.id);
}
}