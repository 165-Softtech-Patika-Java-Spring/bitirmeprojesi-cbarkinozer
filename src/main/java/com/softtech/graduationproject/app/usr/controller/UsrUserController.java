package com.softtech.graduationproject.app.usr.controller;

import com.softtech.graduationproject.app.gen.dto.RestResponse;
import com.softtech.graduationproject.app.usr.dto.UsrUserDto;
import com.softtech.graduationproject.app.usr.dto.UsrUserSaveRequestDto;
import com.softtech.graduationproject.app.usr.dto.UsrUserUpdateRequestDto;
import com.softtech.graduationproject.app.usr.service.UsrUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(tags="User Controller")
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody UsrUserSaveRequestDto usrUserSaveRequestDto){

        UsrUserDto usrUserDto = usrUserService.save(usrUserSaveRequestDto);

        return ResponseEntity.ok(RestResponse.of(usrUserDto));

    }

    @Operation(tags="User Controller")
    @PutMapping("/update")
    public ResponseEntity update(UsrUserUpdateRequestDto usrUserUpdateRequestDto){

        UsrUserDto usrUserDto = usrUserService.update(usrUserUpdateRequestDto);

        return ResponseEntity.ok(RestResponse.of(usrUserDto));

    }

    //public cancel(){}

}
