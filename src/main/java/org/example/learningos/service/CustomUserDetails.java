package org.example.learningos.service;

import java.util.Collection;
import java.util.Collections;
import org.example.learningos.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // 닉네임 반환 메서드 추가
    public String getNickname() {
        return user.getNickname();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // role에 ROLE_ 접두사 추가
        String roleWithPrefix = user.getRole().startsWith("ROLE_")
                ? user.getRole()
                : "ROLE_" + user.getRole();
        return Collections.singleton(new SimpleGrantedAuthority(roleWithPrefix));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 기존 getUsername()는 로그인 시 사용되지만, 화면에 표시할 때는 닉네임을 사용합니다.
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