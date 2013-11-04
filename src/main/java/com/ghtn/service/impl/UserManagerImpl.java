package com.ghtn.service.impl;

import com.ghtn.dao.GenericDao;
import com.ghtn.dao.UserDao;
import com.ghtn.model.User;
import com.ghtn.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-1
 * Time: 上午10:12
 * To change this template use File | Settings | File Templates.
 */
@Service("userManager")
public class UserManagerImpl extends GenericManagerImpl<User, Long> implements UserManager{

    private UserDao userDao;

    @Autowired
    public UserManagerImpl(UserDao userDao) {
        super(userDao);
        this.userDao = userDao;
    }
}
