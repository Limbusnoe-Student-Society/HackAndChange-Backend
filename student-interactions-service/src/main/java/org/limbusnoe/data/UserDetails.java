package org.limbusnoe.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetails {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}