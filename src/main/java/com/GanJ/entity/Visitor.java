package com.GanJ.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: GanJ
 * @Date: 2021/2/16 16:03
 * Describe: 访客
 */
@Data
@Entity
@Table(name = "visitor")
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    /**
     * 访客人数
     */
    @Column(name = "visitorNum",nullable = false)
    private long visitorNum;

    /**
     * 当前页的name or 文章名
     */
    @Column(name = "pageName",nullable = false)
    private String pageName;
}
