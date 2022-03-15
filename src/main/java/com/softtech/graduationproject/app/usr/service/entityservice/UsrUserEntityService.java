package com.softtech.graduationproject.app.usr.service.entityservice;

import com.softtech.graduationproject.app.gen.enums.GenStatusType;
import com.softtech.graduationproject.app.gen.service.BaseEntityService;
import com.softtech.graduationproject.app.usr.dao.UsrUserDao;
import com.softtech.graduationproject.app.usr.entity.UsrUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UsrUserEntityService extends BaseEntityService<UsrUser, UsrUserDao> {

    public UsrUserEntityService(UsrUserDao usrUserDao) {
        super(usrUserDao);
    }

    public List<UsrUser> findAllActiveUsrUserList(){

        return getDao().findAllByStatusType(GenStatusType.ACTIVE);
    }

    public UsrUser save(UsrUser usrUser){

        usrUser.setStatusType(GenStatusType.ACTIVE);

        usrUser = super.save(usrUser);

        return usrUser;
    }

}
