package com.blog.personalblog.util;

import cn.hutool.core.util.StrUtil;
import java.security.MessageDigest;

/**
 * @author: SuperMan
 * 欢迎关注我的公众号：码上言
 * @create: 2021-11-14
 */
public class MD5Util {

    public  static String MD5(String s) {
        if (StrUtil.isEmpty(s) || StrUtil.isBlank(s)) {
            return null;
        }
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        String str = "123456";
        System.out.println(MD5(str));
    }
}
