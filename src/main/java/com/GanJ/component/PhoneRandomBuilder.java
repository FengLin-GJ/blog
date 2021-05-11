package com.GanJ.component;

import org.springframework.stereotype.Component;

/**
 * @author: GanJ
 * @Date: 2021/2/13 15:07
 * Describe: 手机验证码随机生成
 */
@Component
public class PhoneRandomBuilder {

    public static String randomBuilder(){

        String result = "";
        for(int i=0;i<6;i++){
            result += Math.round(Math.random() * 9);
        }

        return result;

    }

}
