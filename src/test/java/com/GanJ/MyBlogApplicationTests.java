package com.GanJ;

import com.GanJ.es.EsArticleService;
import com.GanJ.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

    public BlogApplicationTests() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    @Autowired
    private UserServiceImpl esArticleRepository;

    @Test
    public void contextLoads() throws IOException {
        esArticleRepository.uploadPng("GanJ");
        System.out.println();
    }

}
