package com.ll.mb.global.standard.util;

public class Ut {
    public static class str {

        public static String lcfirst(String str) {   // 가장 첫 번째 단어를 소문자화
            if (str == null || str.isEmpty()) {
                return str;
            }
            return str.substring(0, 1).toLowerCase() + str.substring(1);
        }
    }
}
