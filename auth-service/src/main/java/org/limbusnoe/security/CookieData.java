package org.limbusnoe.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.limbusnoe.jpa.models.Role;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class CookieData {
    public static final CookieData NO_COOKIE;

    static {
        NO_COOKIE = new CookieData();
        NO_COOKIE.setCookie(ResponseCookie.from("jwt", "").httpOnly(true).path("/").maxAge(0).sameSite("Strict").build());
    }

    private String token;
    private String username;
    private Set<Role> authorities;
    private ResponseCookie cookie;

    public static CookieData generate(UserDetails user, JwtComponent jwtComponent, ResponseCookie cookie) {
        CookieData data = new CookieData();
        data.setToken(jwtComponent.generateToken(user));
        data.setUsername(user.getUsername());
        data.setAuthorities(user.getAuthorities().stream().map(Role.class::cast).collect(Collectors.toSet()));
        data.setCookie(cookie);
        return data;
    }

    public void respond(HttpServletResponse response) {
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
