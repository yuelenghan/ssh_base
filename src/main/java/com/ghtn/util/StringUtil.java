package com.ghtn.util;

public class StringUtil {

    public static boolean isNullStr(String s) {
        if (s == null || s.trim().equals("") || s.trim().equals("null")) {
            return true;
        }
        return false;
    }
}
