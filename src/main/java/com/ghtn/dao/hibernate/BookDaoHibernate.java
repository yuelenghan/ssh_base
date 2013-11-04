package com.ghtn.dao.hibernate;

import com.ghtn.dao.BookDao;
import com.ghtn.model.Book;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-4
 * Time: 下午3:47
 * To change this template use File | Settings | File Templates.
 */
@Repository("bookDao")
public class BookDaoHibernate extends GenericDaoHibernate<Book, Long> implements BookDao{
    public BookDaoHibernate () {
        super(Book.class);
    }
}
