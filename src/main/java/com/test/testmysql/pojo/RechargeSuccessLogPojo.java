package com.test.testmysql.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 *      充值表的实体类
 *      有部分数据格式和数据库表中不同，
 *      如使用框架和后续修改，注意修改数据类型
 */
@Getter
@Setter
@ToString
public class RechargeSuccessLogPojo {
    private String logId;
    private String serverId;
    private String logTime; //时间格式，如使用框架，需要改变类型
    private String accountId;
    private String deviceId;
    private String roleId;
    private String roleName;
    private String roleLevel;
    private int rolePower;
    private String ip;
    private String roleVip;
    private String gameOrderId;
    private String gameChannerlOrderId;
    private String orderAmount;
    private String shareAmount;
    private String noShareAmount;
    private String payId;
    private String rechargeChannel;
    private int addJewel;
    private int totalJewel;
    private int currencyType;
    private int itemId;
    private int isFirstRecharge;
    private String deviceModel;
    private String androidId;
}