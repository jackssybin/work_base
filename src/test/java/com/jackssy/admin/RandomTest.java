package com.jackssy.admin;

import org.junit.Test;

import java.util.Random;
public class RandomTest {


    @Test
    public void testA(){

        System.out.println(getCharAndNumr(4));
    }


    /**
     * 方法1：生成随机数字和字母组合
     * @param length
     * @return
     */
    public static String getCharAndNumr(int length) {

        Random random = new Random();

        StringBuffer valSb = new StringBuffer();

        String charStr = "0123456789abcdefghijklmnopqrstuvwxyz";

        int charLength = charStr.length();



        for (int i = 0; i < length; i++) {

            int index = random.nextInt(charLength);

            valSb.append(charStr.charAt(index));

        }

        return valSb.toString();

    }



    /**
     * 方法2：生成随机数字和字母组合
     * @param length
     * @return
     */

    public static String getCharAndNumr2(int length) {

        StringBuffer valSb = new StringBuffer();

        Random random = new Random();

        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

            if ("char".equalsIgnoreCase(charOrNum)) {

                // 字符串

                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;  // 取得大写字母还是小写字母

                valSb.append((char) (choice + random.nextInt(26)));

            } else if ("num".equalsIgnoreCase(charOrNum)) {

                // 数字

                valSb.append(String.valueOf(random.nextInt(10)));

            }

        }

        return valSb.toString();

    }



    /**
     * 方法3：生成随机数字和字母组合
     * @param length
     * @return
     */

    public static String getCharAndNumr3(int length) {

        StringBuffer valSb = new StringBuffer();

        for (int i = 0; i < length; i++) {

            String charOrNum = Math.round(Math.random()) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

            if ("char".equalsIgnoreCase(charOrNum)) {

                // 字符串

                int choice = Math.round(Math.random()) % 2 == 0 ? 65 : 97;  // 取得大写字母还是小写字母

                valSb.append((char) (choice + Math.round(Math.random()*25)));

            } else if ("num".equalsIgnoreCase(charOrNum)) {

                // 数字

                valSb.append(String.valueOf(Math.round(Math.random()*9)));

            }

        }

        return valSb.toString();

    }

}