package com.equals.accountservice.config;

import com.equals.accountservice.auth.UserService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import reactor.core.publisher.Mono;


import java.util.HashMap;
import java.util.Map;


public class CustomReactiveJwtDecoder implements ReactiveJwtDecoder {
    private final ReactiveJwtDecoder delegate;
   // private final UserService userService;

    public CustomReactiveJwtDecoder(ReactiveJwtDecoder delegate) {
        this.delegate = delegate;
        //this.userService = userService;
    }

    @Override
    public Mono<Jwt> decode(String token) {
        return delegate.decode(token)
                .flatMap(jwt -> {
                    Map<String, Object> claims = new HashMap<>(jwt.getClaims());
                    //String username = jwt.getSubject();
                    return  Mono.just(jwt);

//                    return userService.isValidSession(claims.get("sid").toString())
//                            .flatMap(isValid -> {
//                                if (!isValid) {
//                                    return Mono.error(new AccessDeniedException("Access Denied"));
//                                }
//                                return Mono.just(jwt);
//                            });
                });
    }

}

