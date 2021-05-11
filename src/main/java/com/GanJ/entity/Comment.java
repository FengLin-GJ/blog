package com.GanJ.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: GanJ
 * @Date: 2021/2/5 22:37
 * Describe: 文章评论
 */
@Data
@Entity
@Table(name = "comment_record")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private long id;

    /**
     * 留言的文章id
     */
    @Column(name = "articleId",nullable = false)
    private long articleId;

    /**
     * 回复的父id 若是评论则为 0，则是评论中的回复则为对应评论的id
     */
    @Column(name = "pId",nullable = false)
    private long pId=0;

    /**
     * 评论者
     */
    @Column(name = "answererId",nullable = false)
    private int answererId;

    /**
     * 被回复者
     */
    @Column(name = "respondentId",nullable = false)
    private int respondentId;

    /**
     * 评论日期
     */
    @Column(name = "commentDate",nullable = false)
    private String commentDate;

    /**
     * 喜欢数
     */
    @Column(name = "likes",nullable = false)
    private int likes=0;

    /**
     * 评论内容
     */
    @Column(name = "commentContent",nullable = false)
    private String commentContent;

    /**
     * 该条评论是否已读  1--未读   0--已读
     */
    @Column(name = "isRead",nullable = false)
    private int isRead = 1;

}
