package com.GanJ.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;

/**
 * @author: GanJ
 * @Date: 2021/2/20 15:34
 * Describe: 文章
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    /**
     * 文章id
     */
    @Column(name = "articleId",nullable = false)
    private long articleId;

    /**
     * 文章作者
     */
    @Column(name = "author",nullable = false)
    private String author;

    /**
     * 文章原作者
     */
    @Column(name = "originalAuthor",nullable = false)
    private String originalAuthor;

    /**
     * 文章名
     */
    @Column(name = "articleTitle",nullable = false)
    private String articleTitle;

    /**
     * 发布时间
     */
    @Column(updatable = false,nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    @org.hibernate.annotations.CreationTimestamp
    private String publishDate;

    /**
     * 最后一次修改时间
     */
    @Column(updatable = false,nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    @org.hibernate.annotations.UpdateTimestamp
    private String updateDate;

    /**
     * 文章内容
     */
    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "articleContent",nullable = false,length = 16777216)
    private String articleContent;

    /**
     * 文章标签
     */
    @Column(name = "articleTags",nullable = false)
    private String articleTags;

    /**
     * 文章类型
     */
    @Column(name = "articleType",nullable = false)
    private String articleType;

    /**
     * 博客分类
     */
    @Column(name = "articleCategories",nullable = false)
    private String articleCategories;


    /**
     * 原文链接
     * 转载：则是转载的链接
     * 原创：则是在本博客中的链接
     */
    @Column(name = "articleUrl",nullable = false)
    private String articleUrl;

    /**
     * 文章摘要
     */
    @Column(name = "articleTabloid",nullable = false)
    @Type(type = "text")
    private String articleTabloid;

    /**
     * 上一篇文章id
     */
    @Column(name = "lastArticleId")
    private long lastArticleId;

    /**
     * 下一篇文章id
     */
    @Column(name = "nextArticleId")
    private long nextArticleId;

    /**
     * 喜欢
     */
    @Column(name = "likes",nullable = false)
    private int likes = 0;

}