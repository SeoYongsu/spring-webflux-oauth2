package com.example.demo.model.user.service;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService implements ReactiveUserDetailsService {
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return null;
    }
}
