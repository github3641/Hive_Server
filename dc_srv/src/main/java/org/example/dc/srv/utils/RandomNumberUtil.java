package org.example.dc.srv.utils;

import java.util.Random;

/**
 * Project: Hive_Server
 * Package: org.example.dc.srv.utils
 * Class: RandomNumberUtil
 * Author: RuiChao Lv
 * Date: 2020/8/27
 * Version: 1.0
 * Description:
 */
public class RandomNumberUtil {
    /**
     * 根据输入数字返回几位随机数
     * @param a
     * @return
     */
    public static String getRandomNumber(int a){
        Random random = new Random();
        String rn="";
        for (int i=1;i<=a;i++){
            int num = random.nextInt(10);
            rn+=num;
        }
        return rn;
    }
}
