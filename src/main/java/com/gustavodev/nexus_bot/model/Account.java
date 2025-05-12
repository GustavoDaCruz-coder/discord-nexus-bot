package com.gustavodev.nexus_bot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Account {

    private String plataform;
    private String username;
    private String password;
    private Boolean mfa;
    private String owner;
    private String description;
    private Boolean active;
    private String id;
}
