package com.GanJ.redis;

/**
 * @author: GanJ
 * @Date: 2021/2/14 15:31
 * Describe:
 */
public interface RedisService {

    /**
     * 判断key是否存在
     */
    Boolean hasKey(String key);

}
