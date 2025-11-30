package org.limbusnoe.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class TokenValidationResponse {
    private boolean valid;
    private String username;
    private Set<String> roles;
}