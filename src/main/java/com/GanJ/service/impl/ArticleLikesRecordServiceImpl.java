package com.GanJ.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.GanJ.dao.ArticleLikesRepository;
import com.GanJ.entity.ArticleLikesRecord;
import com.GanJ.redis.StringRedisServiceImpl;
import com.GanJ.service.ArticleLikesRecordService;
import com.GanJ.service.ArticleService;
import com.GanJ.service.UserService;
import com.GanJ.utils.DataMap;
import com.GanJ.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: GanJ
 * @Date: 2021/2/7 15:50
 * Describe:
 */
@Service
@Slf4j
public class ArticleLikesRecordServiceImpl implements ArticleLikesRecordService {

    @Autowired
    ArticleLikesRepository articleLikesRepository;
    @Autowired
    UserService userService;
    @Autowired
    ArticleService articleService;
    @Autowired
    StringRedisServiceImpl stringRedisService;

    @Override
    public boolean isLiked(long articleId, String username) {
        String articleLikesRecord = articleLikesRepository.isLiked(articleId, userService.findIdByUsername(username));

        return articleLikesRecord != null;
    }

    @Override
    public void insertArticleLikesRecord(ArticleLikesRecord articleLikesRecord) {
        articleLikesRepository.save(articleLikesRecord);
    }

    @Override
    public void deleteArticleLikesRecordByArticleId(long articleId) {
        articleLikesRepository.deleteByArticleId(articleId);
    }

    @Override
    public DataMap getArticleThumbsUp(int rows, int pageNum) {
        JSONObject returnJson = new JSONObject();

        PageHelper.startPage(pageNum, rows);
        List<ArticleLikesRecord> likesRecords = articleLikesRepository.findByOrderByIdDesc();
        PageInfo<ArticleLikesRecord> pageInfo = new PageInfo<>(likesRecords);
        JSONArray returnJsonArray = new JSONArray();
        JSONObject articleLikesJson;
        for(ArticleLikesRecord a : likesRecords){
            articleLikesJson = new JSONObject();
            articleLikesJson.put("id", a.getId());
            articleLikesJson.put("articleId", a.getArticleId());
            articleLikesJson.put("likeDate", a.getLikeDate());
            articleLikesJson.put("praisePeople", userService.findUsernameById(a.getLikerId()));
            articleLikesJson.put("articleTitle", articleService.findArticleTitleByArticleId(a.getArticleId()).get("articleTitle"));
            articleLikesJson.put("isRead", a.getIsRead());
            returnJsonArray.add(articleLikesJson);
        }
        returnJson.put("result", returnJsonArray);
        returnJson.put("msgIsNotReadNum", articleLikesRepository.countByIsRead(1));

        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());
        returnJson.put("pageInfo",pageJson);

        return DataMap.success().setData(returnJson);
    }

    @Override
    public DataMap readThisThumbsUp(int id) {
        articleLikesRepository.readThisThumbsUp(id);
        stringRedisService.stringIncrement(StringUtil.ARTICLE_THUMBS_UP,-1);
        int articleThumbsUp = (int) stringRedisService.get(StringUtil.ARTICLE_THUMBS_UP);
        if(articleThumbsUp == 0){
            stringRedisService.remove(StringUtil.ARTICLE_THUMBS_UP);
        }
        return DataMap.success();
    }

    @Override
    public DataMap readAllThumbsUp() {
        articleLikesRepository.readAllThumbsUp();
        stringRedisService.remove(StringUtil.ARTICLE_THUMBS_UP);
        return DataMap.success();
    }

}
