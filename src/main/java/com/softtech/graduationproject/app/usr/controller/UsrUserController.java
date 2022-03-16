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
            summary = "All users",
            description = "Gets all users (only actives)."

    )
    @GetMapping
    public ResponseEntity<RestResponse<List<UsrUserDto>>> findAll(){

        List<UsrUserDto> usrUserDtoList = usrUserService.findAll();

        return ResponseEntity.ok(RestResponse.of(usrUserDtoList));
    }

    @Operation(
            tags = "User Controller",
            summary = "Get a user",
            description = "Gets a user by id (even if it is passive)."
    )
    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<UsrUserFindByIdRequestDto>> findById(@PathVariable Long id){

        UsrUserFindByIdRequestDto usrUserFindByIdRequestDto = usrUserService.findById(id);

        return ResponseEntity.ok(RestResponse.of(usrUserFindByIdRequestDto));
    }

    @Operation(
            tags="User Controller",
            summary = "Save a user",
            description = "Saves a new user"
    )
    @PostMapping("/save")
    public ResponseEntity<RestResponse<UsrUserDto>> save(@RequestBody UsrUserSaveRequestDto usrUserSaveRequestDto){

        UsrUserDto usrUserDto = usrUserService.save(usrUserSaveRequestDto);

        return ResponseEntity.ok(RestResponse.of(usrUserDto));

    }

    @Operation(
            tags="User Controller",
            summary = "Update a user",
            description = "Updates a user's name, surname, username, and password by id"
    )
    @PutMapping("/update")
    public ResponseEntity<RestResponse<UsrUserDto>> update(UsrUserUpdateRequestDto usrUserUpdateRequestDto){

        UsrUserDto usrUserDto = usrUserService.update(usrUserUpdateRequestDto);

        return ResponseEntity.ok(RestResponse.of(usrUserDto));

    }

    @Operation(
            tags="User Controller",
            summary = "Cancel a user",
            description = "Deletes a user by canceling (setting the status type passive) by id"
    )
    @PatchMapping("/cancel/{id}")
    public ResponseEntity<RestResponse<?>> cancel(@PathVariable Long id){

        usrUserService.cancel(id);

        return ResponseEntity.ok(RestResponse.empty());
    }

}
