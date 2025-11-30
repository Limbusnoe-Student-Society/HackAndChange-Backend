package org.limbusnoe.data;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterDto {
    private String username;
    private String email;
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate birthDate;
    private String password;
    private String passwordConfirm;
}
