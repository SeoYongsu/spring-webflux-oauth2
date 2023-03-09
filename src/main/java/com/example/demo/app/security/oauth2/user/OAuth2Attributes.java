package com.example.demo.app.security.oauth2.user;

import com.example.demo.model.user.domain.type.AuthProvider;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Map;

@Data
public class OAuth2Attributes {
    @Id
    private String id;

    private String providerID;

    private String email;

    private String phone;

    private String name;

    private String image;

    private AuthProvider authProviderType;

    private Map<String, Object> attributes;

    @Builder
    public OAuth2Attributes(String providerID, String email, String phone, String name, String image, AuthProvider authProviderType, Map<String,Object> attributes) {
        this.providerID = providerID;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.image = image;
        this.authProviderType = authProviderType;
        this.attributes = attributes;
    }



    public static OAuth2Attributes of(AuthProvider authProviderType, Map<String, Object> attributes) {
        switch (authProviderType) {
            case KAKAO : return ofKakao(attributes);
            case NAVER : return ofNaver(attributes);
            //        case GOOGLE : return new GoogleOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }

    }

    /**
     * KAKAO UserInfo
     */
    @SuppressWarnings("unchecked")
    private static OAuth2Attributes ofKakao(Map<String, Object> attributes) {
        // kakao는 kakao_account에 유저정보가 있다. (email)
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        // kakao_account안에 또 profile이라는 JSON객체가 있다. (nickname, profile_image)
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        return OAuth2Attributes.builder()
                .providerID(attributes.get("id").toString())
                .email((String) kakaoAccount.get("email"))
                .phone(null)
                .name((String) kakaoProfile.get("nickname"))
                .image((String) kakaoProfile.get("thumbnail_image"))
                .authProviderType(AuthProvider.NAVER)
                .attributes(attributes)
                .build();

    }

    /**
     * NAVER UserInfo
     */
    @SuppressWarnings("unchecked")
    private static OAuth2Attributes ofNaver(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2Attributes.builder()
                .providerID((String) response.get("id"))
                .email((String) response.get("email"))
                .phone( (String) response.get("mobile"))
                .name((String) response.get("name"))
                .image((String) response.get("profile_image"))
                .authProviderType(AuthProvider.NAVER)
                .attributes(attributes)

                .build();

    }
}
