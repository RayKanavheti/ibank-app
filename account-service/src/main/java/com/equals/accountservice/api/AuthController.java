package com.equals.accountservice.api;

import com.equals.accountservice.auth.AuthRequest;
import com.equals.accountservice.auth.AuthService;
import com.equals.accountservice.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/account")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private ReactiveAuthenticationManager authenticationManager;
    @PostMapping("/register")
    public Mono<User> createUser(@RequestBody User user) {
        return service.saveUser(user);
    }

    @PostMapping("/token")
    public Mono<String> getToken(@RequestBody AuthRequest authRequest) {
      return Mono.just(authRequest).flatMap(
                authRequest1 -> authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()))
                        .flatMap(auth -> {
                            if (auth.isAuthenticated()) {
                                return Mono.just(service.generateToken(authRequest.getUsername()));
                            } else {
                                return Mono.empty();
                            }
                        })
        );

    }
}
