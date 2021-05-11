package com.GanJ.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author: GanJ
 * @Date: 2021/2/16 14:39
 * Describe:
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "friendlink")
public class FriendLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    /**
     * 博主
     */
    @Column(name = "blogger",nullable = false)
    private String blogger;

    /**
     * 博主url
     */
    @Column(name = "url",nullable = false)
    private String url;

    public FriendLink(String blogger, String url){
        this.blogger = blogger;
        this.url = url;
    }

}
