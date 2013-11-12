package com.ghtn.dao.hibernate;

import com.ghtn.dao.UserDao;
import com.ghtn.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-1
 * Time: 上午10:11
 * To change this template use File | Settings | File Templates.
 */
@Repository("userDao")
public class UserDaoHibernate extends AuditGenericDaoHibernate<User, Long> implements UserDao {

    public UserDaoHibernate() {
        super(User.class);
    }

}
