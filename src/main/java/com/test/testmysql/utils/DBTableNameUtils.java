package com.test.testmysql.utils;

import com.test.testmysql.constant.Constants;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTableNameUtils {

    /**
     * 自动生成表名，单标数据大于1000W重新建立一个表存储数据
     *
     * @param tableName
     */
    public static void generateTableNameMethod(String tableName) throws SQLException, ClassNotFoundException {

        if ("acount_creat_log".equalsIgnoreCase(tableName)) {
//            if (Constants.ACOUNT_CREAT_LOG_INDEX == 0) {
//                Constants.ACOUNT_CREATLOG_TABLE_NAME = "recharge_success_log";
//                return;
//            }
            Constants.ACOUNT_CREAT_LOG_INDEX++;
            Constants.ACOUNT_CREATLOG_TABLE_NAME = Constants.ACOUNT_CREATLOG_TABLE_BASE_NAME + Constants.ACOUNT_CREAT_LOG_INDEX;
            //建表
            creatNewTable(tableName);
        }

        if ("recharge_success_log".equalsIgnoreCase(tableName)) {
//            if (Constants.RECHARGE_SUCCESS_LOG_INDEX == 0) {
//                Constants.RECHARGE_SUCCESS_LOG_TABLE_NAME = "recharge_success_log";
//                return;
//            }
            Constants.RECHARGE_SUCCESS_LOG_INDEX++;
            Constants.RECHARGE_SUCCESS_LOG_TABLE_NAME = Constants.RECHARGE_SUCCESS_LOG_TABLE_BASE_NAME + Constants.RECHARGE_SUCCESS_LOG_INDEX;
            //建表
            creatNewTable(tableName);
        }

        if ("role_logout_log".equalsIgnoreCase(tableName)) {

//            if (Constants.ACOUNT_CREAT_LOG_INDEX == 0) {
//                Constants.ROLE_LOGIN_OUT_LOG_TABLE_NAME = "role_logout_log";
//                return;
//            }
            Constants.ROLE_LOGIN_OUT_LOG_INDEX++;
            Constants.ROLE_LOGIN_OUT_LOG_TABLE_NAME = Constants.ROLE_LOGIN_OUT_LOG_TABLE_BASE_NAME + Constants.ROLE_LOGIN_OUT_LOG_INDEX;
            //建表
            creatNewTable(tableName);
        }
    }


    /**
     * 自动建表
     *
     * @param tableName 表名
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static boolean creatNewTable(String tableName) throws SQLException, ClassNotFoundException {
        Connection conn = DbUtils.getConnInsert();
        String creatTableSql = null;
        if ("role_logout_log".equalsIgnoreCase(tableName)) {
            creatTableSql =
                    "SET FOREIGN_KEY_CHECKS=0;" +
                            "DROP TABLE IF EXISTS `" + Constants.ROLE_LOGIN_OUT_LOG_TABLE_NAME + "`;" +
                            "CREATE TABLE `" + Constants.ROLE_LOGIN_OUT_LOG_TABLE_NAME + "` (" +
                            "  `log_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT," +
                            "  `server_id` varchar(64) DEFAULT NULL," +
                            "  `log_time` datetime NOT NULL," +
                            "  `account_id` varchar(101) DEFAULT NULL," +
                            "  `device_id` varchar(64) DEFAULT NULL," +
                            "  `channel_id` varchar(64) DEFAULT NULL," +
                            "  `role_id` varchar(64) DEFAULT NULL," +
                            "  `role_name` varchar(101) DEFAULT NULL," +
                            "  `role_level` int(11) DEFAULT NULL," +
                            "  `role_power` int(11) DEFAULT NULL," +
                            "  `ip` varchar(32) DEFAULT NULL," +
                            "  `op_type` int(4) DEFAULT NULL," +
                            "  `create_time` datetime DEFAULT NULL," +
                            "  `online_time` int(11) DEFAULT NULL," +
                            "  PRIMARY KEY (`log_id`,`log_time`)," +
                            "  KEY `account_id` (`account_id`)," +
                            "  KEY `log_time` (`log_time`)" +
                            ") ENGINE=InnoDB AUTO_INCREMENT=22758 DEFAULT CHARSET=utf8" +
                            "/*!50100 PARTITION BY RANGE (to_days(log_time))" +
                            "(PARTITION role_logout_log_201906 VALUES LESS THAN (737606) ENGINE = InnoDB," +
                            " PARTITION role_logout_log_201907 VALUES LESS THAN (737637) ENGINE = InnoDB," +
                            " PARTITION role_logout_log_201908 VALUES LESS THAN (737668) ENGINE = InnoDB," +
                            " PARTITION role_logout_log_201909 VALUES LESS THAN (737698) ENGINE = InnoDB," +
                            " PARTITION role_logout_log_201910 VALUES LESS THAN (737729) ENGINE = InnoDB," +
                            " PARTITION role_logout_log_201911 VALUES LESS THAN (737759) ENGINE = InnoDB) */;";
        }
        if ("recharge_success_log".equalsIgnoreCase(tableName)) {
            creatTableSql =
                    "SET FOREIGN_KEY_CHECKS=0;" +
                            "DROP TABLE IF EXISTS `" + Constants.RECHARGE_SUCCESS_LOG_TABLE_NAME + "`;" +
                            "CREATE TABLE `" + Constants.RECHARGE_SUCCESS_LOG_TABLE_NAME + "` (" +
                            "  `log_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT," +
                            "  `server_id` varchar(64) DEFAULT NULL," +
                            "  `log_time` datetime NOT NULL," +
                            "  `account_id` varchar(101) DEFAULT NULL," +
                            "  `device_id` varchar(64) DEFAULT NULL," +
                            "  `role_id` varchar(64) DEFAULT NULL," +
                            "  `role_name` varchar(101) DEFAULT NULL," +
                            "  `role_level` int(11) DEFAULT NULL," +
                            "  `role_power` int(11) DEFAULT NULL," +
                            "  `ip` varchar(64) DEFAULT NULL," +
                            "  `role_vip` int(11) DEFAULT NULL," +
                            "  `game_order_id` varchar(64) DEFAULT NULL," +
                            "  `game_channel_order_id` varchar(64) DEFAULT NULL," +
                            "  `order_amount` decimal(14,2) DEFAULT NULL," +
                            "  `share_amount` decimal(14,2) DEFAULT NULL," +
                            "  `no_share_amount` decimal(14,2) DEFAULT NULL," +
                            "  `pay_id` int(11) DEFAULT NULL," +
                            "  `recharge_channel` varchar(64) DEFAULT NULL," +
                            "  `add_jewel` bigint(11) DEFAULT NULL," +
                            "  `total_jewel` bigint(11) DEFAULT NULL," +
                            "  `currency_type` int(11) DEFAULT NULL," +
                            "  `item_id` int(11) DEFAULT NULL," +
                            "  `is_first_recharge` int(4) DEFAULT NULL," +
                            "  `device_model` varchar(64) DEFAULT NULL," +
                            "  `android_id` varchar(64) DEFAULT NULL," +
                            "  PRIMARY KEY (`log_id`,`log_time`)," +
                            "  KEY `account_id` (`account_id`)," +
                            "  KEY `log_time` (`log_time`)" +
                            ") ENGINE=InnoDB AUTO_INCREMENT=1169 DEFAULT CHARSET=utf8" +
                            "/*!50100 PARTITION BY RANGE (to_days(log_time))" +
                            "(PARTITION recharge_success_log_201905 VALUES LESS THAN (737576) ENGINE = InnoDB," +
                            " PARTITION recharge_success_log_201906 VALUES LESS THAN (737606) ENGINE = InnoDB," +
                            " PARTITION recharge_success_log_201907 VALUES LESS THAN (737637) ENGINE = InnoDB," +
                            " PARTITION recharge_success_log_201908 VALUES LESS THAN (737668) ENGINE = InnoDB," +
                            " PARTITION recharge_success_log_201909 VALUES LESS THAN (737698) ENGINE = InnoDB," +
                            " PARTITION recharge_success_log_201910 VALUES LESS THAN (737729) ENGINE = InnoDB," +
                            " PARTITION recharge_success_log_201911 VALUES LESS THAN (737759) ENGINE = InnoDB) */;";
        }
        if ("acount_creat_log".equalsIgnoreCase(tableName)) {
            creatTableSql =
                    "SET FOREIGN_KEY_CHECKS=0;" +
                            "DROP TABLE IF EXISTS `" + Constants.ACOUNT_CREATLOG_TABLE_NAME + "`;" +
                            "CREATE TABLE `" + Constants.ACOUNT_CREATLOG_TABLE_NAME + "` (" +
                            "  `log_id` bigint(20) NOT NULL AUTO_INCREMENT," +
                            "  `server_id` varchar(64) DEFAULT NULL," +
                            "  `log_time` datetime NOT NULL," +
                            "  `account_id` varchar(101) DEFAULT NULL," +
                            "  `device_id` varchar(64) DEFAULT NULL," +
                            "  `ip` varchar(64) DEFAULT NULL," +
                            "  `login_type` int(11) DEFAULT '0'," +
                            "  PRIMARY KEY (`log_id`,`log_time`)," +
                            "  KEY `account_id` (`account_id`)," +
                            "  KEY `log_time` (`log_time`)" +
                            ") ENGINE=InnoDB AUTO_INCREMENT=2608 DEFAULT CHARSET=utf8" +
                            "/*!50100 PARTITION BY RANGE (to_days(log_time))" +
                            "(PARTITION account_create_log_201905 VALUES LESS THAN (737576) ENGINE = InnoDB," +
                            " PARTITION account_create_log_201906 VALUES LESS THAN (737606) ENGINE = InnoDB," +
                            " PARTITION account_create_log_201907 VALUES LESS THAN (737637) ENGINE = InnoDB," +
                            " PARTITION account_create_log_201908 VALUES LESS THAN (737668) ENGINE = InnoDB," +
                            " PARTITION account_create_log_201909 VALUES LESS THAN (737698) ENGINE = InnoDB," +
                            " PARTITION account_create_log_201910 VALUES LESS THAN (737729) ENGINE = InnoDB," +
                            " PARTITION account_create_log_201911 VALUES LESS THAN (737759) ENGINE = InnoDB) */;";
        }
        Statement statement = conn.createStatement();
        int i = statement.executeUpdate(creatTableSql);
        if (i > 0) return true;
        else return false;
    }
}
