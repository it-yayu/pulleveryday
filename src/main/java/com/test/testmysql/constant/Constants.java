package com.test.testmysql.constant;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  常量类
 */
public class Constants {
    /**单表容量*/
//    public static Integer MAX_COUNT_TABLE = 10000000;
    public static Integer MAX_COUNT_TABLE = 5000000; //测试用

    /**表的数量*/
    public static Integer ROLE_LOGIN_TABLE_COUNT = 0;
    public static Integer ACOUNT_CREAT_LOG_COUNT = 0;
    public static Integer RECHARGE_SUCCESS_LOG_COUNT = 0;

    /**角色表索引*/
    public static Integer ROLE_LOGIN_OUT_LOG_INDEX = 0;

    /**账户表索引*/
    public static Integer ACOUNT_CREAT_LOG_INDEX = 0;

    /**充值表索引*/
    public static Integer RECHARGE_SUCCESS_LOG_INDEX = 0;


    /**角色表  表名*/
    public static String ROLE_LOGIN_OUT_LOG_TABLE_NAME = "role_logout_log";

    /**角色表  基础表名*/
    public static String ROLE_LOGIN_OUT_LOG_TABLE_BASE_NAME = "role_logout_log";

    /**账户表*/
    public static String ACOUNT_CREATLOG_TABLE_NAME = "account_create_log";

    /**账户基础表*/
    public static String ACOUNT_CREATLOG_TABLE_BASE_NAME = "account_create_log";

    /**充值表*/
    public static String RECHARGE_SUCCESS_LOG_TABLE_NAME = "recharge_success_log";

    /**充值 基础表*/
    public static String RECHARGE_SUCCESS_LOG_TABLE_BASE_NAME = "recharge_success_log";

    /**
     *  系统同步时间
     */
    public static final long syncTime = 1000*60;

    /**
     *  一分钟
     */
    public static final long timeMinute = 1000 * 60 ;
    /**
     *  一小时
     */
    public static final long timeHour = 1000 * 60 * 60 ;
    /**
     * 测试时间。，默认5分钟
     */
    public static final long testTime = 1000*60*5;

    public static String syncTimeStr = "";

    public static void setSyncTimeStr(){
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        syncTimeStr = format;
    }


}
