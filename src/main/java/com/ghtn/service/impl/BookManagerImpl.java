package com.ghtn.service.impl;

import com.ghtn.dao.BookDao;
import com.ghtn.model.Book;
import com.ghtn.service.BookManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-4
 * Time: 下午3:45
 * To change this template use File | Settings | File Templates.
 */
@Service("bookManager")
public class BookManagerImpl extends GenericManagerImpl<Book, Long> implements BookManager {

    private BookDao bookDao;

    @Autowired
    public BookManagerImpl(BookDao bookDao) {
        super(bookDao);
        this.bookDao = bookDao;
    }
}
