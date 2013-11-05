package com.ghtn.dao;

import com.ghtn.BaseTestCase;
import com.ghtn.model.User;
import org.junit.Test;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-1
 * Time: 上午11:32
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UserDaoTest extends BaseTestCase {


    private UserDao userDao;

    @Resource
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setName("test");
        user.setAge(10);

        userDao.save(user);

    }

    @Test
    public void testSearch() {
        String searchTerm = "test2";
        List<User> list = userDao.search(searchTerm);
        if (list != null && list.size() != 0) {
            System.out.println("list.get(0).getName() = " + list.get(0).getName());
        }
    }

    @Test
    public void testReindex() {
        userDao.reindexAll(false);
    }

    @Test
    public void testGetOld() {
        User user = userDao.getOld(1L, 2);
        logger.debug("用户名 = " + user.getName());
    }

    @Test
    public void testGetRevisions() {
        List<Number> list = userDao.getRevisions(1L);
        for (Number num : list) {
            System.out.println("num = " + num);
        }
    }
}
