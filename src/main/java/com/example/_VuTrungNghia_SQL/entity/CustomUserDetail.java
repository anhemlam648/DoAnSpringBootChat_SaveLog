package com.example._VuTrungNghia_SQL.entity;

import com.example._VuTrungNghia_SQL.repository.IuserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class CustomUserDetail implements UserDetails {

    private final User user;

    private final IuserRepository iuserRepository;


    public CustomUserDetail(User user, IuserRepository iuserRepository) {
        this.user = user;
        this.iuserRepository = iuserRepository;
        ;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(iuserRepository.getRolesOfUser(user.getId())).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
