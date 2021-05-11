package com.GanJ.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author: GanJ
 * @Date: 2021/2/20 15:36
 * Describe: 标签
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    /**
     * 标签名
     */
    @Column(name = "tagName",nullable = false)
    private String tagName;

    /**
     * 标签大小
     */
    @Column(name = "tagSize",nullable = false)
    private int tagSize;

    public Tag(String tagName, int tagSize) {
        this.tagName = tagName;
        this.tagSize = tagSize;
    }
}
