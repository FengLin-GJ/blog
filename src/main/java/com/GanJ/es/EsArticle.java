package com.GanJ.es;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author GanJ
 * @date 2021/3/29 20:07
 * Describe: 文章搜索
 */
@Data
@Document(indexName = "articles", type = "doc", useServerConfiguration = true, createIndex = false)
public class EsArticle {

    @Id
    private Integer id;

    /**
     * 文章id
     */
    @Field(type = FieldType.Long)
    @JsonProperty("articleid")
    private long articleId;

    /**
     * 文章作者
     */
    @Field(type = FieldType.text, analyzer = "ik_max_word")
    private String author;

    /**
     * 文章原作者
     */
    @Field(type = FieldType.text, analyzer = "ik_max_word")
    @JsonProperty("originalauthor")
    private String originalAuthor;

    /**
     * 文章名
     */
    @Field(type = FieldType.text, analyzer = "ik_max_word")
    @JsonProperty("articletitle")
    private String articleTitle;

    /**
     * 发布时间
     */
//    @Temporal(TemporalType.TIMESTAMP)
//    @org.hibernate.annotations.CreationTimestamp
    @Field(type = FieldType.text)
    @JsonProperty("publishdate")
    private String publishDate;

    /**
     * 最后一次修改时间
     */
//    @Temporal(TemporalType.TIMESTAMP)
//    @org.hibernate.annotations.UpdateTimestamp
    @Field(type = FieldType.text)
    @JsonProperty("updatedate")
    private String updateDate;

    /**
     * 文章内容
     */
    @Field(type = FieldType.text, analyzer = "ik_max_word")
    @JsonProperty("articlecontent")
    private String articleContent;

    /**
     * 文章标签
     */
    @Field(type = FieldType.text, analyzer = "ik_max_word")
    @JsonProperty("articletags")
    private String articleTags;

    /**
     * 文章类型
     */
    @Field(type = FieldType.text, analyzer = "ik_max_word")
    @JsonProperty("articletype")
    private String articleType;

    /**
     * 博客分类
     */
    @Field(type = FieldType.text, analyzer = "ik_max_word")
    @JsonProperty("articlecategories")
    private String articleCategories;


    /**
     * 原文链接
     * 转载：则是转载的链接
     * 原创：则是在本博客中的链接
     */
    @Field(type = FieldType.text)
    @JsonProperty("articleurl")
    private String articleUrl;

    /**
     * 文章摘要
     */
    @Field(type = FieldType.text, analyzer = "ik_max_word")
    @JsonProperty("articletabloid")
    private String articleTabloid;

    /**
     * 上一篇文章id
     */
    @Field(type = FieldType.text)
    @JsonProperty("lastarticleid")
    private long lastArticleId;

    /**
     * 下一篇文章id
     */
    @Field(type = FieldType.text)
    @JsonProperty("nextarticleid")
    private long nextArticleId;

    /**
     * 喜欢
     */
    @Field(type = FieldType.text)
    private int likes = 0;
}
