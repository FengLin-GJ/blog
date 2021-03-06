package com.GanJ.controller;

import com.GanJ.constant.CodeType;
import com.GanJ.service.ArticleService;
import com.GanJ.service.CategoryService;
import com.GanJ.utils.DataMap;
import com.GanJ.utils.JsonResult;
import com.GanJ.utils.StringUtil;
import com.GanJ.utils.TransCodingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: GanJ
 * @Date: 2021/2/13 20:50
 * Describe: 分类
 */
@RestController
@Slf4j
public class CategoriesControl {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ArticleService articleService;

    /**
     * 获得所有分类名以及每个分类名的文章数目
     * @return
     */
    @GetMapping(value = "/findCategoriesNameAndArticleNum", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String findCategoriesNameAndArticleNum(){
        try {
            DataMap data = categoryService.findCategoriesNameAndArticleNum();
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Find categories name and article num exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 分页获得该分类下的文章
     * @return
     */
    @GetMapping(value = "/getCategoryArticle", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getCategoryArticle(@RequestParam("category") String category,
                                     @RequestParam("rows") int rows,
                                     @RequestParam("pageNum") int pageNum){
        try {
            if(!category.equals(StringUtil.BLANK)){
                category = TransCodingUtil.unicodeToString(category);
            }
            DataMap data = articleService.findArticleByCategory(category, rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Get category [{}] article exception", category, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


}
