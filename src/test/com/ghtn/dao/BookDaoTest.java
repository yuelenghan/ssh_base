package com.ghtn.dao;

import com.ghtn.BaseTestCase;
import com.ghtn.model.Book;
import org.junit.Test;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-4
 * Time: 下午3:50
 * To change this template use File | Settings | File Templates.
 */
@Component
public class BookDaoTest extends BaseTestCase{

    private BookDao bookDao;

    @Resource
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Test
    public void testSave() {
        Book book = new Book();
        book.setName("testBook");
        book.setContent("content");

        bookDao.save(book);
    }
}
