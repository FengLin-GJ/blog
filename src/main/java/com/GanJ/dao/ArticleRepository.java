package com.GanJ.dao;

import com.GanJ.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author GanJ
 * @date 2021/4/5 17:24
 * Describe:
 */
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    int countByArticleCategories(String category);

    Article findById(int id);

    Article findByArticleId(long articleId);

    List<Article> findByOrderByIdDesc();

    List<Article> findByArticleCategoriesOrderByIdDesc(String category);

    @Query(value = "select articleId from article where id=?1", nativeQuery = true)
    Long findArticleIdById(int id);

    @Transactional
    @Modifying
    void deleteByArticleId(long articleId);

    @Query(value = "select articleId from article order by id desc limit 1", nativeQuery = true)
    Long findEndArticleId();

    @Transactional
    @Modifying
    @Query(value = "update article set originalAuthor=:#{#article.originalAuthor},articleTitle=:#{#article.articleTitle},updateDate=:#{#article.updateDate},articleContent=:#{#article.articleContent},articleTags=:#{#article.articleTags},articleType=:#{#article.articleType},articleCategories=:#{#article.articleCategories},articleUrl=:#{#article.articleUrl},articleTabloid=:#{#article.articleTabloid} where id=:#{#article.id}", nativeQuery = true)
    void updateArticleById(Article article);

    @Transactional
    @Modifying
    @Query(value = "update article set lastArticleId=?1 where articleId=?2", nativeQuery = true)
    void updateArticleLastId(long lastArticleLd, long articleId);

    @Transactional
    @Modifying
    @Query(value = "update article set nextArticleId=?1 where articleId=?2", nativeQuery = true)
    void updateArticleNextId(long nextArticleId, long articleId);

    @Transactional
    @Modifying
    @Query(value = "update article set likes=likes+1 where articleId=?1", nativeQuery = true)
    void updateLikeByArticleId(long articleId);

    @Query(value = "select IFNULL(max(likes),0) from article where articleId=?1", nativeQuery = true)
    int findLikesByArticleId(long articleId);

    @Query(value = "select * from article where articleTags like CONCAT('%',?1,'%') order by id desc", nativeQuery = true)
    List<Article> findArticleByTag(String tag);

    @Query(value = "select * from article where publishDate like CONCAT('%',?1,'%') order by id desc", nativeQuery = true)
    List<Article> findArticleByArchive(String archive);

    @Query(value = "select count(*) from article where publishDate like CONCAT('%',?1,'%')", nativeQuery = true)
    int countArticleArchiveByArchive(String archive);

    @Transactional
    @Modifying
    @Query(value = "update article set nextArticleId=?1 where articleId=?2", nativeQuery = true)
    void updateLastOrNextId01(long updateId, long articleId);

    @Transactional
    @Modifying
    @Query(value = "update article set lastArticleId=?1 where articleId=?2", nativeQuery = true)
    void updateLastOrNextId02(long updateId, long articleId);

}
