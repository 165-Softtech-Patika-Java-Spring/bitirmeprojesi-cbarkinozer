package com.softtech.graduationproject.app.usr.dto;

import lombok.Data;

@Data
public class UsrUserUpdateRequestDto {

    private Long id;
    private String name;
    private String surname;
    private String username;
    private String password;

}
