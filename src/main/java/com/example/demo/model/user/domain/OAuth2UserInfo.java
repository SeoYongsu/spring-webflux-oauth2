package com.example.demo.model.user.domain;

import com.example.demo.model.user.domain.User;
import com.example.demo.model.user.domain.type.AuthProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@ToString
@Document
public class OAuth2UserInfo {
    @Id
    private String id;

    @Indexed(unique = true)
    private String providerID;

    /**
     * ErUser FK
     */
    private String userID;

    private User user;

    private String email;

    private String mobile;

    private String name;

    private String image;

    private AuthProvider authProviderType;


    @Builder
    public OAuth2UserInfo(String providerID, String email, String mobile, String name, String image, AuthProvider authProviderType) {
        this.providerID = providerID;
        this.email = email;
        this.mobile = mobile;
        this.name = name;
        this.image = image;
        this.authProviderType = authProviderType;
    }


    public void setErUserID(String userID) {
        this.userID = userID;
    }


    public void setErUser(User user) {
        this.user = user;
    }
}
