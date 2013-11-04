package com.ghtn.controller;

import com.ghtn.model.User;
import com.ghtn.service.UserManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-1
 * Time: 上午10:10
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    private static Logger logger = Logger.getLogger(UserController.class);
    private UserManager userManager;

    @Resource
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @RequestMapping("addUser")
    public String addUser(User user) {
        logger.debug("进入UserController--addUser");
        try {
            userManager.save(user);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return "error";
        }
        return "success";
    }
}
