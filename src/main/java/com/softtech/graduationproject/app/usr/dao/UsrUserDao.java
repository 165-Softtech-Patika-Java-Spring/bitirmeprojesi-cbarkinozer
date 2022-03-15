package com.softtech.graduationproject.app.usr.dao;

import com.softtech.graduationproject.app.gen.enums.GenStatusType;
import com.softtech.graduationproject.app.usr.entity.UsrUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsrUserDao extends JpaRepository<UsrUser, Long> {

    List<UsrUser> findAllByStatusType(GenStatusType statusType);

}
