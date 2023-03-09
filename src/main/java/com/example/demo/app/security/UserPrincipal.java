package com.example.demo.app.security;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class UserPrincipal implements UserDetails, OAuth2User {

    private static final long serialVersionUID = -4312574273059411541L;

    private String userID;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    private String email;
    private String mobile;

    private Map<String, Object> attributes;


    @Builder
    public UserPrincipal(String userID, String password, String email, String mobile, Collection<? extends GrantedAuthority> authorities) {
        this.userID = userID;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
        this.authorities = authorities;
    }

    /**
     * Security 인증객체 생성
     * LocalUser 전용
     * @param user
     * @return
     */
    public static UserPrincipal create(com.example.demo.model.user.domain.User user) {
        return UserPrincipal.builder()
                .userID(user.getId())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .password(user.getPassword())
                .build();
    }




    // UserDetail Override
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }



    @Override
    public String getUsername() {
        return this.userID;
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


    // OAuth2User Override
    @Override
    public String getName() {
        return this.userID;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
