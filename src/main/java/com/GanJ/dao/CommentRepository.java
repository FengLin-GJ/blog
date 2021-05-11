package com.GanJ.dao;

import com.GanJ.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author GanJ
 * @date 2021/4/5 16:31
 * Describe: 评论sql
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByArticleIdAndPIdOrderByIdDesc(long articleId, long pId);

    List<Comment> findByArticleIdAndPId(long articleId, long pId);

    List<Comment> findByOrderByIdDesc();

    List<Comment> findAllByRespondentIdAndAnswererIdNotOrderByIdDesc(int respondentId, int answererId);

    int countAllByIsReadAndRespondentIdAndAnswererIdNot(int isRead, int respondentId, int answererId);

    @Transactional
    @Modifying
    @Query(value = "update comment_record set likes=likes+1 where articleId=?1 and id=?2", nativeQuery = true)
    void updateLikeByArticleIdAndId(long articleId, long id);

    @Query(value = "select IFNULL(max(likes),0) from comment_record where articleId=?1 and id=?2", nativeQuery = true)
    int findLikesByArticleIdAndId(long articleId, long id);

    @Transactional
    @Modifying
    void deleteByArticleId(long articleId);

    @Transactional
    @Modifying
    @Query(value = "update comment_record set isRead=0 where id=?1", nativeQuery = true)
    void readCommentRecordById(int id);

    @Transactional
    @Modifying
    @Query(value = "update comment_record set isRead=0 where respondentId=?1", nativeQuery = true)
    void readCommentRecordByRespondentId(int respondentId);
}
