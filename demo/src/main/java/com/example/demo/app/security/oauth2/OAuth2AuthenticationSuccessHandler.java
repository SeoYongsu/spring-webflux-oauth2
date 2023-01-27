package com.example.demo.app.security.oauth2;

import com.example.demo.app.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
//ServerAuthenticationSuccessHandler		 		-> interFace
//RedirectServerAuthenticationSuccessHandler   		-> imp sample
//DelegatingServerAuthenticationSuccessHandler 		-> imp sample
//WebFilterChainServerAuthenticationSuccessHandler 	-> imp sample
public class OAuth2AuthenticationSuccessHandler extends RedirectServerAuthenticationSuccessHandler{


    private final JwtTokenProvider tokenProvider;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        final ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();

        log.info("Success Handler 진입");
        ServerWebExchange exchange = webFilterExchange.getExchange();

        String accessToken = tokenProvider.createAccessToken(authentication);

        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add("Content-Type", "application/json; charset=UTF-8");
        DataBuffer body = response.bufferFactory().wrap( accessToken.getBytes() );
        return response.writeWith( Mono.just(body) );

//		return Mono.empty();
    }


}