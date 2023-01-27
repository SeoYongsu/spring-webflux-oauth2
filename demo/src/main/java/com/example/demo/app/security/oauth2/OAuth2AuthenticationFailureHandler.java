package com.example.demo.app.security.oauth2;

import java.net.URI;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
//RedirectServerAuthenticationFailureHandler   -> imp sample
public class OAuth2AuthenticationFailureHandler implements ServerAuthenticationFailureHandler {


    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        log.info("fail Handler  ->  " );


        final ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();


        ServerWebExchange exchange = webFilterExchange.getExchange();

        log.info("error Msg : " + exception.getMessage());

        ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();




        ServerHttpResponse response = exchange.getResponse();


        response.getHeaders().add("Content-Type", "application/json; charset=UTF-8");
//		header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        DataBuffer body = response.bufferFactory().wrap(exception.getMessage().toString().getBytes());



        return response.writeWith(Mono.just(body));
//		return test(e.getError().getErrorCode(), exchange, e);
    }


    private Mono<Void> test(String errorCode,ServerWebExchange exchange, OAuth2AuthenticationException e) {
        ServerHttpResponse response = exchange.getResponse();
        switch (errorCode) {
            case "SignUp_Fail":

                response.getHeaders().add("Content-Type", "application/json; charset=UTF-8");
                //			header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
                DataBuffer body = response.bufferFactory().wrap(e.getMessage().toString().getBytes());
                return  response.writeWith(Mono.just(body));

            default:
                response.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
                response.getHeaders().setLocation(URI.create("http://wwww.naver.com"));
                response.getHeaders().setContentType(MediaType.parseMediaType("text/event-stream;charset=UTF-8"));
                return response.setComplete();
        }

    }
}
