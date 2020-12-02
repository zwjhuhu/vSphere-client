package cn.com.skywin.cloudadmin.commons;

import java.util.UUID;

/**
 * @ClassName UUIDGenerator
 * @Description
 * @Author zwj
 * @Date 2019/7/16 17:41
 * @Version 1.0
 */
public class UUIDGenerator {
    private UUIDGenerator(){

    }

    public static String gen(){
        return UUID.randomUUID().toString().toLowerCase().replaceAll("-","");
    }
}
