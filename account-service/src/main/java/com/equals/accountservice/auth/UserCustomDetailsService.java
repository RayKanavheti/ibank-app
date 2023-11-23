package com.equals.accountservice.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class UserCustomDetailsService implements ReactiveUserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return repository.findByUsername(username)
           .flatMap( user -> Mono.just(new UserCustomDetails(user)));
    }
}
//
//@Component
//public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager {
//
//    private final ReactiveUserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;
//
//    public CustomReactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
//        this.userDetailsService = userDetailsService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public Mono<Authentication> authenticate(Authentication authentication) {
//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        return userDetailsService.findByUsername(username)
//                .flatMap(userDetails -> {
//                    if (passwordEncoder.matches(password, userDetails.getPassword())) {
//                        return Mono.just(new PreAuthenticatedAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
//                    } else {
//                        return Mono.empty();
//                    }
//                })
//                .switchIfEmpty(Mono.empty()); // Return empty if user is not found or password doesn't match
//    }
//
//
//}

//@Component
//public class UserCustomDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository repository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> credential = repository.findByUsername(username);
//        return credential.map(UserCustomDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found with name :" + username));
//    }
//}