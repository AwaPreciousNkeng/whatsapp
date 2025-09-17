package com.codewithpcodes.whatsapp.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizer {
    private final UserRepository repository;
    private final UserMapper mapper;
    public void synchronizeWithIdp(Jwt token) {
        log.info("Synchronizing user with Idp");
        getUserEmail(token).ifPresent(userEmail -> {
            log.info("Synchronizing user having email {}", userEmail);
//            Optional<User> optUser = repository.findByEmail(userEmail);
            User user = mapper.fromTokenAttributes(token.getClaims());
//            optUser.ifPresent(value -> user.setId(optUser.get().getId()));
            repository.save(user);
        });
    }

    private Optional<String> getUserEmail(Jwt token) {
        Map<String, Object> attributes = (Map<String, Object>) token.getClaims();
        if (attributes.containsKey("email")) {
            return Optional.of(attributes.get("email").toString());
        }
        return Optional.empty();
    }
}
