package com.GanJ.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: GanJ
 * @Date: 2021/2/23 17:17
 * Describe: 反馈
 */
@Data
@Entity
@Table(name = "feedback")
public class FeedBack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    /**
     * 反馈内容
     */
    @Column(name = "feedbackContent",nullable = false)
    private String feedbackContent;

    /**
     * 联系方式
     */
    @Column(name = "contactInfo")
    private String contactInfo;

    /**
     * 反馈人
     */
    @Column(name = "personId",nullable = false)
    private int personId;

    /**
     * 反馈时间
     */
    @Column(name = "feedbackDate",nullable = false)
    private String feedbackDate;

}
