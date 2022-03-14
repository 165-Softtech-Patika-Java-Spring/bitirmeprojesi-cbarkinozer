package com.softtech.graduationproject.app.usr.service;

import com.softtech.graduationproject.app.usr.service.entityservice.UsrUserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsrUserService {
    private final UsrUserEntityService usrUserEntityService;
}
