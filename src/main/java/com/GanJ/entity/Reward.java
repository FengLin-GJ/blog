package com.GanJ.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author GanJ
 * @date 2021/4/4 22:09
 */
@Data
@Entity
@Table(name = "reward")
public class Reward {

    @Id
    @Column(name = "id",nullable = false)
    private int id;

    @Column(name = "fundRaiser",nullable = false)
    private String fundRaiser;

    @Column(name = "fundRaisingSources",nullable = false)
    private String fundRaisingSources;

    @Column(name = "fundraisingPlace",nullable = false)
    private String fundraisingPlace;

    @Column(name = "rewardMoney",nullable = false)
    private Date rewardMoney;

    @Column(name = "remarks")
    private Date remarks;

    @Column(name = "rewardDate",nullable = false)
    private Date rewardDate;

    @Column(name = "rewardUrl",nullable = false)
    private Date rewardUrl;
}
