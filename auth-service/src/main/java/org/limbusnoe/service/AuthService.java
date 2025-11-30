package org.limbusnoe.service;

import lombok.RequiredArgsConstructor;
import org.limbusnoe.data.LoginDto;
import org.limbusnoe.data.RegisterDto;
import org.limbusnoe.jpa.models.Role;
import org.limbusnoe.jpa.models.User;
import org.limbusnoe.jpa.repository.RoleRepository;
import org.limbusnoe.jpa.repository.UserRepository;
import org.limbusnoe.security.CookieData;
import org.limbusnoe.security.JwtComponent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtComponent jwtComponent;

    public Optional<User> getUser(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Set<Role> getRolesFor(long id) {
        return roleRepository.findRolesByUsersId(id);
    }

    @Transactional(readOnly = true)
    public Set<Role> getRolesFor(String username) {
        return roleRepository.findRolesByUsersUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public CookieData addUser(RegisterDto register) {
        if (userRepository.existsByUsername(register.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Никнейм уже занят");
        }
        if(userRepository.existsByEmail(register.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Почта уже занята");
        }
        if (!register.getPassword().equals(register.getPasswordConfirm())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Повторный пароль введён неверно");
        }
        User user = new User();
        user.setUsername(register.getUsername());
        user.setEmail(register.getEmail());
        user.setName(register.getName());
        user.setSurname(register.getSurname());
        user.setPatronymic(register.getPatronymic());
        user.setBirthDate(register.getBirthDate());
        String password = register.getPassword();
        do {
            password = passwordEncoder.encode(password);
        } while (passwordEncoder.upgradeEncoding(password));
        user.setPassword(password);
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Нет подходящей роли: {%s}".formatted("ROLE_USER")));
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
        return generateCookieData(user);
    }

    @Transactional
    public CookieData loginUser(LoginDto login) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return generateCookieData(userDetails);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials");
        }
    }

    public ResponseCookie createCookie(String token) {
        return ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofHours(1))
                .sameSite("Strict")
                .build();
    }

    private CookieData generateCookieData(UserDetails user, String token) {
        return CookieData.generate(user, jwtComponent, createCookie(token));
    }

    public CookieData generateCookieData(UserDetails user) {
        String token = jwtComponent.generateToken(user);
        return generateCookieData(user, token);
    }
}
