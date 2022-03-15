package com.softtech.graduationproject.app.usr.controller;

import com.softtech.graduationproject.app.gen.dto.RestResponse;
import com.softtech.graduationproject.app.usr.dto.UsrUserDto;
import com.softtech.graduationproject.app.usr.dto.UsrUserFindByIdRequestDto;
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

    @Operation(
            tags = "User Controller",
            description = "Gets all users (only actives).",
            summary = "All users"
    )
    @GetMapping
    public ResponseEntity findAll(){

        List<UsrUserDto> usrUserDtoList = usrUserService.findAll();

        return ResponseEntity.ok(RestResponse.of(usrUserDtoList));
    }

    @Operation(
            tags = "User Controller",
            description = "Gets a user by id (even if it is passive).",
            summary = "Get a user"
    )
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){

        UsrUserFindByIdRequestDto usrUserFindByIdRequestDto = usrUserService.findById(id);

        return ResponseEntity.ok(RestResponse.of(usrUserFindByIdRequestDto));
    }

    @Operation(
            tags="User Controller",
            description = "Saves a new user",
            summary = "Save a user"
    )
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody UsrUserSaveRequestDto usrUserSaveRequestDto){

        UsrUserDto usrUserDto = usrUserService.save(usrUserSaveRequestDto);

        return ResponseEntity.ok(RestResponse.of(usrUserDto));

    }

    @Operation(
            tags="User Controller",
            description = "Updates a user's name, surname, username, and password by id",
            summary = "Update a user"
    )
    @PutMapping("/update")
    public ResponseEntity update(UsrUserUpdateRequestDto usrUserUpdateRequestDto){

        UsrUserDto usrUserDto = usrUserService.update(usrUserUpdateRequestDto);

        return ResponseEntity.ok(RestResponse.of(usrUserDto));

    }

    @Operation(
            tags="User Controller",
            description = "Deletes a user by canceling (setting the status type passive) by id",
            summary = "Delete a user"
    )
    @PatchMapping("/cancel/{id}")
    public ResponseEntity cancel(@PathVariable Long id){

        usrUserService.cancel(id);

        return ResponseEntity.ok(RestResponse.empty());
    }

}
