package com.GanJ.controller;

import com.GanJ.aspect.annotation.PermissionCheck;
import com.GanJ.constant.CodeType;
import com.GanJ.entity.ArticleLikesRecord;
import com.GanJ.service.ArticleLikesRecordService;
import com.GanJ.service.ArticleService;
import com.GanJ.service.RedisService;
import com.GanJ.service.UserService;
import com.GanJ.utils.DataMap;
import com.GanJ.utils.JsonResult;
import com.GanJ.utils.StringUtil;
import com.GanJ.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author: GanJ
 * @Date: 2021/2/5 16:21
 * Describe: 文章显示页面
 */
@RestController
@Slf4j
public class ShowArticleControl {

    @Autowired
    ArticleLikesRecordService articleLikesRecordService;
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;

    /**
     *  获取文章
     * @param articleId 文章id
     */
    @PostMapping(value = "/getArticleByArticleId", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getArticleById(@RequestParam("articleId") String articleId,
                                                                    @AuthenticationPrincipal Principal principal){
        String username = null;
        try {
            if(principal != null){
                username = principal.getName();
            }
            DataMap data = articleService.getArticleByArticleId(Long.parseLong(articleId),username);
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("[{}] get article [{}] exception", username, articleId, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();

    }




    /**
     * 点赞
     * @param articleId 文章号
     */
    @GetMapping(value = "/addArticleLike", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_USER")
    public String addArticleLike(@RequestParam("articleId") String articleId,
                                     @AuthenticationPrincipal Principal principal){
        String username = principal.getName();
        try {
            if(articleLikesRecordService.isLiked(Long.parseLong(articleId), username)){
                return JsonResult.fail(CodeType.ARTICLE_HAS_THUMBS_UP).toJSON();
            }

            DataMap data = articleService.updateLikeByArticleId(Long.parseLong(articleId));

            ArticleLikesRecord articleLikesRecord = new ArticleLikesRecord(Long.parseLong(articleId), userService.findIdByUsername(username), new TimeUtil().getFormatDateForFive());
            articleLikesRecordService.insertArticleLikesRecord(articleLikesRecord);
            redisService.readThumbsUpRecordOnRedis(StringUtil.ARTICLE_THUMBS_UP, 1);

            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("[{}] like article [{}] exception", username, articleId, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();

    }

}
