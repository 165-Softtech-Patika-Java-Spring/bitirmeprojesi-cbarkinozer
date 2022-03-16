package com.softtech.graduationproject.app.usr.service;

import com.softtech.graduationproject.app.gen.enums.GenStatusType;
import com.softtech.graduationproject.app.gen.exceptions.ItemNotFoundException;
import com.softtech.graduationproject.app.usr.converter.UsrUserMapper;
import com.softtech.graduationproject.app.usr.dto.UsrUserDto;
import com.softtech.graduationproject.app.usr.dto.UsrUserFindByIdRequestDto;
import com.softtech.graduationproject.app.usr.dto.UsrUserSaveRequestDto;
import com.softtech.graduationproject.app.usr.dto.UsrUserUpdateRequestDto;
import com.softtech.graduationproject.app.usr.entity.UsrUser;
import com.softtech.graduationproject.app.usr.enums.UsrErrorMessage;
import com.softtech.graduationproject.app.usr.service.entityservice.UsrUserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsrUserService {

    private final UsrUserEntityService usrUserEntityService;
    private final UsrUserValidationService usrUserValidationService;

    public List<UsrUserDto> findAll() {

        List<UsrUser> usrUserList = usrUserEntityService.findAllActiveUsrUserList();

        List<UsrUserDto> usrUserDtoList = UsrUserMapper.INSTANCE.convertToUsrUserDtoList(usrUserList);

        return usrUserDtoList;
    }

    public UsrUserFindByIdRequestDto findById(Long id) {

        UsrUser usrUser = usrUserEntityService.getByIdWithControl(id);

        UsrUserFindByIdRequestDto usrUserFindByIdRequestDto = UsrUserMapper.INSTANCE
                .convertToUsrUserFindByIdRequestDto(usrUser);

        return usrUserFindByIdRequestDto;
    }

    public UsrUserDto save(UsrUserSaveRequestDto usrUserSaveRequestDto){

        UsrUser usrUser = UsrUserMapper.INSTANCE.convertToUsrUser(usrUserSaveRequestDto);

        usrUserValidationService.controlIsUsernameUnique(usrUser);

        usrUser.setStatusType(GenStatusType.ACTIVE);
        usrUser = usrUserEntityService.save(usrUser);

        UsrUserDto usrUserDto = UsrUserMapper.INSTANCE.convertToUsrUserDto(usrUser);

        return usrUserDto;
    }

    public UsrUserDto update(UsrUserUpdateRequestDto usrUserUpdateRequestDto) {

        usrUserValidationService.controlIsUserExist(usrUserUpdateRequestDto);

        UsrUser usrUser = UsrUserMapper.INSTANCE.convertToUsrUser(usrUserUpdateRequestDto);

        usrUserValidationService.controlIsUsernameUnique(usrUser);

        usrUser = usrUserEntityService.save(usrUser);

        UsrUserDto usrUserDto = UsrUserMapper.INSTANCE.convertToUsrUserDto(usrUser);

        return usrUserDto;

    }


    public void cancel(Long id) {

        UsrUser usrUser = usrUserEntityService.getByIdWithControl(id);

        usrUser.setStatusType(GenStatusType.PASSIVE);
        usrUserEntityService.save(usrUser);
    }
}
