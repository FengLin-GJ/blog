package com.GanJ.dao;

import com.GanJ.entity.ArticleLikesRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: GanJ
 * @Date: 2021/2/7 15:51
 * Describe: 文章点赞sql
 */
public interface ArticleLikesRepository extends JpaRepository<ArticleLikesRecord, Long> {

    int countByIsRead(int isRead);

    List<ArticleLikesRecord> findByOrderByIdDesc();

    @Query(value="select likeDate from article_likes_record where articleId=?1 and likerId=?2", nativeQuery = true)
    String isLiked(long articleId, int likerId);

    @Transactional
    @Modifying
    void deleteByArticleId(long articleId);

    @Transactional
    @Modifying
    @Query(value = "update article_likes_record set isRead=0 where id=?1", nativeQuery = true)
    void readThisThumbsUp(int id);

    @Transactional
    @Modifying
    @Query(value = "update article_likes_record set isRead=0", nativeQuery = true)
    void readAllThumbsUp();
}
