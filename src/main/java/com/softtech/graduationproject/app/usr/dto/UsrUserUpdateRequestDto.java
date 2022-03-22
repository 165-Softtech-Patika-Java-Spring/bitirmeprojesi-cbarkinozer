package com.softtech.graduationproject.app.usr.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsrUserUpdateRequestDto {

    private Long id;
    private String name;
    private String surname;
    private String username;
    private String password;

}
