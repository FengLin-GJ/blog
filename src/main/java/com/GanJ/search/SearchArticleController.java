package com.GanJ.search;

import com.GanJ.aspect.annotation.PermissionCheck;
import com.GanJ.constant.CodeType;
import com.GanJ.service.impl.ArticleServiceImpl;
import com.GanJ.utils.DataMap;
import com.GanJ.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author GanJ
 * @date 2021/4/8 15:10
 * Describe: 推荐文章
 */
@RestController
@Slf4j
public class SearchArticleController {

    @Autowired
    SparkService sparkService;

    @Autowired
    private ArticleServiceImpl articleService;

    @GetMapping(value = "/recommend", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String searchArticles(@RequestParam("name") String name) {
        try {
            Map<String, Integer> map = sparkService.weightRatio(sparkService.labelCount(articleService.nameUtil(name)));
            DataMap data=sparkService.comAllArticles(map);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("Homepage get paged article exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }
}
