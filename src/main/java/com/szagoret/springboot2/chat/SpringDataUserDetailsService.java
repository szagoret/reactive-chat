package com.szagoret.springboot2.chat;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SpringDataUserDetailsService implements ReactiveUserDetailsService {

    private final UserRepository repository;

    public SpringDataUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }


    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return repository
                .findByUsername(username)
                .map(user -> new User(
                        user.getUsername(),
                        user.getPassword(),
                        AuthorityUtils.createAuthorityList(user.getRoles())
                ));
    }
}
