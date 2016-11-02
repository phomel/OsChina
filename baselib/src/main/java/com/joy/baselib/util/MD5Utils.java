package com.joy.baselib.util;

import java.security.MessageDigest;

/**
 * MD5工具类
 * Created by sks on 2016/4/9.
 */
public class MD5Utils {

    /**
     * 返回md5后的值
     * @param url
     * @return
     */
    public static String md5(String url) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(url.getBytes("UTF-8"));
            byte messageDigest[] = md5.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String t = Integer.toHexString(0xFF & messageDigest[i]);
                if (t.length() == 1) {
                    hexString.append("0" + t);
                } else {
                    hexString.append(t);
                }
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
