package com.example.demo.model.user.repository

import com.example.demo.model.user.domain.OAuth2UserInfo
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ReactiveOauth2UserRepository : ReactiveMongoRepository<OAuth2UserInfo,String> {

}