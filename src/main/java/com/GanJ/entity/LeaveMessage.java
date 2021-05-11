package com.GanJ.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author: GanJ
 * @Date: 2021/2/15 12:31
 * Describe: 留言
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "leave_message_record")
public class LeaveMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    /**
     * 留言页
     */
    @Column(name = "pageName",nullable = false)
    private String pageName;

    /**
     * 留言的父id 若是留言则为 0，则是留言中的回复则为对应留言的id
     */
    @Column(name = "pId",nullable = false)
    private int pId=0;

    /**
     * 留言者
     */
    @Column(name = "answererId",nullable = false)
    private int answererId;

    /**
     * 被回复者
     */
    @Column(name = "respondentId",nullable = false)
    private int respondentId;

    /**
     * 留言日期
     */
    @Column(name = "leaveMessageDate",nullable = false)
    private String leaveMessageDate;

    /**
     * 喜欢数
     */
    @Column(name = "likes",nullable = false)
    private int likes=0;

    /**
     * 留言内容
     */
    @Column(name = "leaveMessageContent",nullable = false)
    private String leaveMessageContent;

    /**
     * 该条留言是否已读  1--未读   0--已读
     */
    @Column(name = "isRead",nullable = false)
    private int isRead = 1;

    public LeaveMessage(String pageName, int answererId, int respondentId, String leaveMessageDate, String leaveMessageContent) {
        this.pageName = pageName;
        this.answererId = answererId;
        this.respondentId = respondentId;
        this.leaveMessageDate = leaveMessageDate;
        this.leaveMessageContent = leaveMessageContent;
    }
}
