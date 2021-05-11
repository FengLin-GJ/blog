package com.GanJ.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author: GanJ
 * @Date: 2021/2/22 20:15
 * Describe: 悄悄话
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "privateword")
public class PrivateWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    /**
     * 悄悄话内容
     */
    @Column(name = "privateWord",nullable = false)
    private String privateWord;

    /**
     * 发布者
     */
    @Column(name = "publisherId",nullable = false)
    private int publisherId;

    /**
     * 回复者
     */
    @Column(name = "replierId")
    private int  replierId;

    /**
     * 回复内容
     */
    @Column(name = "replyContent")
    private String replyContent;

    /**
     * 发布时间
     */
    @Column(name = "publisherDate",nullable = false)
    private String publisherDate;

    public PrivateWord(String privateWord, int publisherId, String publisherDate) {
        this.privateWord = privateWord;
        this.publisherId = publisherId;
        this.publisherDate = publisherDate;
    }
}
