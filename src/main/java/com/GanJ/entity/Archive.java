package com.GanJ.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: GanJ
 * @Date: 2021/2/18 11:52
 * Describe: 文章归档
 */
@Data
@Entity
@Table(name = "archives")
public class Archive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    /**
     * 归档日期
     */
    @Column(name = "archiveName",nullable = false)
    private String archiveName;

}
