package com.GanJ.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author: GanJ
 * @Date: 2021/2/16 15:26
 * Describe: 留言中点赞
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "leave_message_likes_record")
public class LeaveMessageLikesRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private long id;

    /**
     * 文章页
     */
    @Column(name = "pageName",nullable = false)
    private String pageName;

    /**
     * 评论的id
     */
    @Column(name = "pId",nullable = false)
    private int pId;

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

    public LeaveMessageLikesRecord(String pageName, int pId, int likerId, String likeDate) {
        this.pageName = pageName;
        this.pId = pId;
        this.likerId = likerId;
        this.likeDate = likeDate;
    }
}
