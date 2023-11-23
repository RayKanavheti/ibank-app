package com.equals.accountservice.auth;


import com.equals.accountservice.config.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {
    @Value("${client.scope}")
    private List<String> scopeList;
    @Value("${client.name}")
    private String audience;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository repository;

    @Autowired
    private JwtService jwtService;
    public Mono<User> saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }
    public String generateToken(String username) {
        String sid = UUID.randomUUID().toString();
        String scopes = String.join(" ", scopeList);
        Map<String, Object> claims = new HashMap<>(Map.of(
                "iss", "https://ibank.equals.co.zw",
                "sid", sid,
                "aud", audience,
                "iat", System.currentTimeMillis(),
                "exp", System.currentTimeMillis() + 1000 * 60 * 30
        ));

        return jwtService.generateToken(claims,username);
    }
}
