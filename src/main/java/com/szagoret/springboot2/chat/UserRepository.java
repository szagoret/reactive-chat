package com.szagoret.springboot2.chat;

import org.springframework.data.repository.Repository;
import reactor.core.publisher.Mono;

public interface UserRepository extends Repository<User, String> {

    Mono<User> findByUsername(String username);

}
