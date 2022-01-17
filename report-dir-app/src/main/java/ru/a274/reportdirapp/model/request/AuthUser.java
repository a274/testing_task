package ru.a274.reportdirapp.model.request;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.a274.reportdirapp.model.db.Status;

import java.util.Collection;
import java.util.Collections;

public class AuthUser implements UserDetails {

    private  String id;
    private  String login;
    private  String email;
    private  String status;
    private  String password;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status.equals(Status.ACTIVE.name());
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(Status.ACTIVE.name());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status.equals(Status.ACTIVE.name());
    }

    @Override
    public boolean isEnabled() {
        return status.equals(Status.ACTIVE.name());
    }
}
