package com.softtech.graduationproject.app.usr.controller;

import com.softtech.graduationproject.app.gen.dto.RestResponse;
import com.softtech.graduationproject.app.usr.dto.UsrUserDto;
import com.softtech.graduationproject.app.usr.service.UsrUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UsrUserController {

    private final UsrUserService usrUserService;

    @Operation(tags = "User Controller", description = "Gets all users.", summary = "All users")
    @GetMapping
    public ResponseEntity findAll(){

        List<UsrUserDto> usrUserDtoList = usrUserService.findAll();

        return ResponseEntity.ok(RestResponse.of(usrUserDtoList));
    }

    @Operation(tags = "User Controller")
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){

        UsrUserDto usrUserDto = usrUserService.findById(id);

        return ResponseEntity.ok(RestResponse.of(usrUserDto));
    }

    //public save(){}
    //public update(){}
    //public cancel(){}

}
