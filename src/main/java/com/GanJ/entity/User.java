package com.GanJ.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author: GanJ
 * @Date: 2021/2/4 11:49
 * Describe: 用户实体类
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    /**
     * 手机号
     */
    @Column(name = "phone",nullable = false)
    private String phone;

    /**
     * 用户名
     */
    @Column(name = "username",nullable = false)
    private String username;

    /**
     * 密码
     */
    @Column(name = "password",nullable = false)
    private String password;

    /**
     * 性别
     */
    @Column(name = "gender",nullable = false)
    private String gender;

    /**
     * 真实姓名
     */
    @Column(name = "trueName")
    private String trueName;

    /**
     * 生日
     */
    @Column(name = "birthday")
    private String birthday;

    /**
     * 个人简介
     */
    @Column(name = "personalBrief")
    private String personalBrief;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 最后登录时间
     */
    @Column(name = "recentlyLanded")
    private String recentlyLanded;

    /**
     * 头像地址
     */
    @Column(name = "avatarImgUrl",nullable = false)
    private String avatarImgUrl;

    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<Role> roles;

    public User(String phone, String username, String password, String gender) {
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.gender = gender;
    }

    public User(String phone, String username, String password, String gender, String trueName, String birthday, String personalBrief, String email, String recentlyLanded, String avatarImgUrl) {
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.trueName = trueName;
        this.birthday = birthday;
        this.personalBrief = personalBrief;
        this.email = email;
        this.recentlyLanded = recentlyLanded;
        this.avatarImgUrl = avatarImgUrl;
    }
}
