package com.GanJ.controller;

import com.GanJ.aspect.annotation.PermissionCheck;
import com.GanJ.constant.CodeType;
import com.GanJ.entity.FriendLink;
import com.GanJ.redis.HashRedisServiceImpl;
import com.GanJ.redis.StringRedisServiceImpl;
import com.GanJ.service.*;
import com.GanJ.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: GanJ
 * @Date: 2021/2/25 16:14
 * Describe: 超级管理页面
 */
@RestController
@Slf4j
public class SuperAdminControl {

    @Autowired
    PrivateWordService privateWordService;
    @Autowired
    FeedBackService feedBackService;
    @Autowired
    VisitorService visitorService;
    @Autowired
    UserService userService;
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleLikesRecordService articleLikesRecordService;
    @Autowired
    StringRedisServiceImpl stringRedisService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    FriendLinkService friendLinkService;
    @Autowired
    RedisService redisService;

    @Autowired
    HashRedisServiceImpl hashRedisService;

    /**
     * 获得所有悄悄话
     *
     * @return
     */
    @PostMapping(value = "/getAllPrivateWord", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String getAllPrivateWord() {
        try {
            DataMap data = privateWordService.getAllPrivateWord();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("Get all private word exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 回复悄悄话
     *
     * @return
     */
    @PostMapping(value = "/replyPrivateWord", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String replyPrivateWord(@AuthenticationPrincipal Principal principal,
                                   @RequestParam("replyContent") String replyContent,
                                   @RequestParam("replyId") String id) {
        String username = principal.getName();
        try {
            DataMap data = privateWordService.replyPrivateWord(replyContent, username, Integer.parseInt(id));
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("[{}] Reply [{}] private word [{}] exception", username, id, replyContent, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();

    }

    /**
     * 分页获得所有反馈信息
     *
     * @param rows    一页大小
     * @param pageNum 当前页
     */
    @GetMapping(value = "/getAllFeedback", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String getAllFeedback(@RequestParam("rows") int rows,
                                 @RequestParam("pageNum") int pageNum) {
        try {
            DataMap data = feedBackService.getAllFeedback(rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("Get all feedback exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 获得统计信息
     *
     * @return
     */
    @GetMapping(value = "/getStatisticsInfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String getStatisticsInfo() {
        try {
            Map<String, Object> dataMap = new HashMap<>(8);
            Long totalVisitor = redisService.getVisitorNumOnRedis(StringUtil.VISITOR, "totalVisitor");
            Long yesterdayVisitor = redisService.getVisitorNumOnRedis(StringUtil.VISITOR, "yesterdayVisitor");
            dataMap.put("allVisitor", totalVisitor);
            dataMap.put("allUser", userService.countUserNum());
            dataMap.put("yesterdayVisitor", yesterdayVisitor);
            dataMap.put("articleNum", articleService.countArticle());
            if (stringRedisService.hasKey(StringUtil.ARTICLE_THUMBS_UP)) {
                int articleThumbsUp = (int) stringRedisService.get(StringUtil.ARTICLE_THUMBS_UP);
                dataMap.put("articleThumbsUpNum", articleThumbsUp);
            } else {
                dataMap.put("articleThumbsUpNum", 0);
            }
            DataMap data = DataMap.success().setData(dataMap);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("Get statistics info exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 获得文章管理
     *
     * @return
     */
    @PostMapping(value = "/getArticleManagement", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String getArticleManagement(@RequestParam("rows") int rows,
                                       @RequestParam("pageNum") int pageNum) {
        try {
            DataMap data = articleService.getArticleManagement(rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("Get article management exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 删除文章
     *
     * @param id 文章id
     */
    @GetMapping(value = "/deleteArticle", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String deleteArticle(@RequestParam("id") String id) {
        try {
            if (StringUtil.BLANK.equals(id) || id == null) {
                return JsonResult.build(DataMap.fail(CodeType.DELETE_ARTICLE_FAIL)).toJSON();
            }
            DataMap data = articleService.deleteArticle(Long.parseLong(id));
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("Delete article [{}] exception", id, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 获得文章点赞信息
     */
    @PostMapping(value = "/getArticleThumbsUp", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String getArticleThumbsUp(@RequestParam("rows") int rows,
                                     @RequestParam("pageNum") int pageNum) {
        try {
            DataMap data = articleLikesRecordService.getArticleThumbsUp(rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("Get article thumbsUp exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 已读一条点赞信息
     */
    @GetMapping(value = "/readThisThumbsUp", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String readThisThumbsUp(@RequestParam("id") int id) {
        try {
            DataMap data = articleLikesRecordService.readThisThumbsUp(id);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("Read one thumbsUp [{}] exception", id, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 已读所有点赞信息
     */
    @GetMapping(value = "/readAllThumbsUp", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String readAllThumbsUp() {
        try {
            DataMap data = articleLikesRecordService.readAllThumbsUp();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("Read all thumbsUp exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 获得所有分类
     */
    @GetMapping(value = "/getArticleCategories", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String getArticleCategories() {
        try {
            DataMap data = categoryService.findAllCategories();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("Get article categories exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 添加或删除分类
     */
    @PostMapping(value = "/updateCategory", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String updateCategory(@RequestParam("categoryName") String categoryName,
                                 @RequestParam("type") int type) {
        try {
            DataMap data = categoryService.updateCategory(categoryName, type);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("Update type [{}] article categories [{}] exception", type, categoryName, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 获得链接
     */
    @PostMapping(value = "/getFriendLink", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String getFriendLink() {
        try {
            DataMap data = friendLinkService.getAllFriendLink();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("Get friendLink exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 添加或编辑链接
     */
    @PostMapping(value = "/updateFriendLink", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String addFriendLink(@RequestParam("id") String id,
                                @RequestParam("blogger") String blogger,
                                @RequestParam("url") String url) {
        try {
            FriendLink friendLink = new FriendLink(blogger, url);
            DataMap data;
            if (StringUtil.BLANK.equals(id)) {
                data = friendLinkService.addFriendLink(friendLink);
            } else {
                data = friendLinkService.updateFriendLink(friendLink, Integer.parseInt(id));
            }
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("Update friendLink [{}] exception", blogger, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 删除链接
     */
    @PostMapping(value = "/deleteFriendLink", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String deleteFriendLink(@RequestParam("id") int id) {
        try {
            DataMap data = friendLinkService.deleteFriendLink(id);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("Delete friendLink [{}] exception", id, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    @PostMapping(value = "/getMessage", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getMessage(@RequestParam("type") String type) {
        try {
            DataMap data = DataMap.success().setData(hashRedisService.getAllFieldAndValue(type));
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("错误", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    @PostMapping(value = "/addMessage", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String addMessage(@RequestParam("type") String type, @RequestParam("key") String key, @RequestParam("value") String value) {
        try {
            hashRedisService.put(type, key, value);
            return JsonResult.build(DataMap.success(CodeType.UPDATE_FRIEND_LINK_SUCCESS)).toJSON();
        } catch (Exception e) {
            log.error("错误", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    @PostMapping(value = "/deleteMessage", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String deleteMessage(@RequestParam("type") String type, @RequestParam("key") String key) {
        try {
            hashRedisService.hashDelete(type, key);
            return JsonResult.build(DataMap.success(CodeType.UPDATE_FRIEND_LINK_SUCCESS)).toJSON();
        } catch (Exception e) {
            log.error("错误", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    @PostMapping(value = "/modifyMessage", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PermissionCheck(value = "ROLE_SUPERADMIN")
    public String modifyMessage(@RequestParam("type") String type, @RequestParam("key") String key, @RequestParam("value") String value) {
        try {
            hashRedisService.hashDelete(type, key);
            hashRedisService.put(type, key, value);
            return JsonResult.build(DataMap.success(CodeType.UPDATE_FRIEND_LINK_SUCCESS)).toJSON();
        } catch (Exception e) {
            log.error("错误", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }
}
