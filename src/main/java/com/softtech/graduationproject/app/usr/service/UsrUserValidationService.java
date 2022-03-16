package com.softtech.graduationproject.app.usr.service;

import com.softtech.graduationproject.app.gen.entity.BaseEntity;
import com.softtech.graduationproject.app.gen.exceptions.ItemAlreadyExistsException;
import com.softtech.graduationproject.app.gen.exceptions.ItemNotFoundException;
import com.softtech.graduationproject.app.usr.dto.UsrUserUpdateRequestDto;
import com.softtech.graduationproject.app.usr.entity.UsrUser;
import com.softtech.graduationproject.app.usr.enums.UsrErrorMessage;
import com.softtech.graduationproject.app.usr.service.entityservice.UsrUserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsrUserValidationService {

    private final UsrUserEntityService usrUserEntityService;

    public void controlIsUserExist(UsrUserUpdateRequestDto usrUserUpdateRequestDto) {

        Long id = usrUserUpdateRequestDto.getId();

        boolean isExist = usrUserEntityService.existsById(id);

        if (!isExist){

            throw new ItemNotFoundException(UsrErrorMessage.USER_NOT_FOUND);
        }
    }

    public void controlIsUsernameUnique(UsrUser usrUser){

        Optional<UsrUser> userOptional = usrUserEntityService.findByUsername(usrUser.getUsername());

        boolean didMatchedItself = didMatchedItself(userOptional, usrUser);

        if(!didMatchedItself){
            throw new ItemAlreadyExistsException(UsrErrorMessage.USERNAME_ALREADY_EXIST);
        }

    }

    private Boolean didMatchedItself(Optional<UsrUser> optional, BaseEntity ownEntity){

        BaseEntity entity;

        if(optional.isPresent()) {

            entity = optional.get();
            return entity.getId().equals(ownEntity.getId());

        }else{return true;}

    }


}
