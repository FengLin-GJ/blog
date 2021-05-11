package com.GanJ.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author: GanJ
 * @Date: 2021/2/12 13:43
 * Describe: 文章评论点赞记录
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "comment_likes_record")
public class CommentLikesRecord {

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
     * 评论的id
     */
    @Column(name = "pId",nullable = false)
    private long pId;

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

    public CommentLikesRecord(long articleId, int pId, int likerId, String likeDate) {
        this.articleId = articleId;
        this.pId = pId;
        this.likerId = likerId;
        this.likeDate = likeDate;
    }
}
