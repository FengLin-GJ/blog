package com.GanJ.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: GanJ
 * @Date: 2021/2/17 20:49
 * Describe: 文章分类
 */
@Data
@Entity
@Table(name = "categories")
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    @Column(name = "categoryName",nullable = false)
    private String categoryName;

}
