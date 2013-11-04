package com.ghtn.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-4
 * Time: 上午10:57
 * To change this template use File | Settings | File Templates.
 */
public class ConstantUtil {
    public static String INDEX_BASE;

    static {
        InputStream in = ConstantUtil.class.getResourceAsStream("/constant.properties");
        Properties prop = new Properties();
        try {
            prop.load(in);
            INDEX_BASE = prop.getProperty("hibernate.search.default.indexBase").trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
