package com.GanJ.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author: GanJ
 * @Date: 2021/2/7 15:44
 * Describe: 文章点赞记录
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "article_likes_record")
public class ArticleLikesRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private long id;

    /**
     * 文章id
     */
    @Column(name = "articleId",nullable = false)
    private long articleId;

    /**
     * 点赞人
     */
    @Column(name = "likerId",nullable = false)
    private int likerId;

    /**
     * 点赞时间
     */
    @Column(name = "likeDate",nullable = false)
    private String likeDate;

    /**
     * 该条点赞是否已读  1--未读   0--已读
     */
    @Column(name = "isRead",nullable = false)
    private int isRead = 1;

    public ArticleLikesRecord(long articleId, int likerId, String likeDate) {
        this.articleId = articleId;
        this.likerId = likerId;
        this.likeDate = likeDate;
    }
}
