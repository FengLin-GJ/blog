package com.GanJ.controller;

import com.GanJ.constant.CodeType;
import com.GanJ.service.ArticleService;
import com.GanJ.service.TagService;
import com.GanJ.utils.DataMap;
import com.GanJ.utils.JsonResult;
import com.GanJ.utils.StringUtil;
import com.GanJ.utils.TransCodingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: GanJ
 * @Date: 2021/2/16 21:27
 * Describe:
 */
@RestController
@Slf4j
public class TagsControl {

    @Autowired
    TagService tagService;
    @Autowired
    ArticleService articleService;

    /**
     * 分页获得该标签下的文章
     * @param tag
     * @return
     */
    @PostMapping(value = "/getTagArticle", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getTagArticle(@RequestParam("tag") String tag,
                                @RequestParam("rows") int rows,
                                @RequestParam("pageNum") int pageNum){
        try {
            if(tag.equals(StringUtil.BLANK)){
                return JsonResult.build(tagService.findTagsCloud()).toJSON();
            }

            tag = TransCodingUtil.unicodeToString(tag);
            DataMap data = articleService.findArticleByTag(tag, rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("Get tags exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

}
