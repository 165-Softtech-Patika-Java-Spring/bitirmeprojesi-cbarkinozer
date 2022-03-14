package com.softtech.graduationproject.app.usr.dao;

import com.softtech.graduationproject.app.usr.entity.UsrUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsrUserDao extends JpaRepository<UsrUser, Long> {

}
