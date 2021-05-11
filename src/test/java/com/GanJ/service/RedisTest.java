package com.GanJ.service;

import com.GanJ.dao.VisitorRepository;
import com.GanJ.redis.HashRedisServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: GanJ
 * @Date: 2021/2/6 14:30
 * Describe:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    HashRedisServiceImpl hashRedisService;
    @Autowired
    VisitorRepository visitorRepository;

    @Test
    public void redisTest(){
    }

}
