package com.aionstar.commons.utils;

/**
 * @author saltman155
 * @date 2020/1/18 23:03
 */

public class NetworkUtil {

    /**
     * 范围ip检查
     *          示例：
     *              address = 10.2.88.12    pattern = *.*.*.*           result: true
     *              address = 10.2.88.12    pattern = *                 result: true
     *              address = 10.2.88.12    pattern = 10.2.88.12-13     result: true
     *              address = 10.2.88.12    pattern = 10.2.88.13-125    result: false
     * @param pattern   模糊匹配式
     * @param address   待匹配项
     * @return          是否匹配
     */
    public static boolean checkIPMatching(String pattern, String address) {
        if (pattern.equals("*.*.*.*") || pattern.equals("*")) {
            return true;
        }
        String[] mask = pattern.split("\\.");
        String[] ip_address = address.split("\\.");
        for (int i = 0; i < mask.length; i++) {
            if (mask[i].equals("*") || mask[i].equals(ip_address[i]));
            else if (mask[i].contains("-")) {
                byte min = Byte.parseByte(mask[i].split("-")[0]);
                byte max = Byte.parseByte(mask[i].split("-")[1]);
                byte ip = Byte.parseByte(ip_address[i]);
                if (ip < min || ip > max)
                    return false;
            }
            else return false;
        }
        return true;
    }

}
