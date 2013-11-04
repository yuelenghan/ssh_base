package com.ghtn.service;

import com.ghtn.BaseTestCase;
import com.ghtn.model.User;
import org.junit.Test;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-1
 * Time: 下午3:40
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UserManagerTest extends BaseTestCase{

    @Resource
    private UserManager userManager;

    @Test
    public void testSave() {
        User user = new User();
        user.setName("test2");
        user.setAge(20);

        userManager.save(user);
    }
}
