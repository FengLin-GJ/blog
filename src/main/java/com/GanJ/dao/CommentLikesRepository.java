package com.GanJ.dao;

import com.GanJ.entity.CommentLikesRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author GanJ
 * @date 2021/4/5 16:23
 * Describe: 评论点赞sql
 */
public interface CommentLikesRepository extends JpaRepository<CommentLikesRecord, Long> {

    @Transactional
    @Modifying
    void deleteByArticleId(long articleId);

    @Query(value = "select likeDate from comment_likes_record where articleId=?1 and pId=?2 and likerId=?3", nativeQuery = true)
    String isLiked(long articleId,long pId, int likerId);
}
