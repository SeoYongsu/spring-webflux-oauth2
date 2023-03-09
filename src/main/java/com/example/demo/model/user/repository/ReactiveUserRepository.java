package com.example.demo.model.user.repository;

import com.example.demo.model.user.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ReactiveUserRepository extends ReactiveMongoRepository<User,String>{

}
