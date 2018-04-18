package com.szagoret.springboot2.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    private String[] roles;


}
