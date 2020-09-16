package com.test.testmysql.task;

import com.mysql.jdbc.StringUtils;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.test.testmysql.pojo.AcountCreatLog;
import com.test.testmysql.pojo.LogEntity;
import com.test.testmysql.pojo.RechargeSuccessLogPojo;
import com.test.testmysql.utils.DbUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 每日同步数据service
 * 角色创建日志表
 */
@Slf4j
@Service
public class DailySyncDataAcountCreatLogImpl implements DailySyncDataAcountCreatLog {


    @Override
    public void executeDailySyncDataAcountCreatLog(String startTime,String endTime) {
        log.info("### executeDailySyncDataAcountCreatLog ---> 启动了..." + new Date());
        // 查询的数据源
        Connection connQuery = null;
        Statement stmt = null;
        //增加的数据源
        Connection connInsert = null;
        PreparedStatement preparedStatement = null;
       Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date time=cal.getTime();
        String format = new SimpleDateFormat("yyyy-MM-dd").format(time);
        String dateStr = format;
        //获取数据库连接
        String insertSQL = "  INSERT INTO `account_create_log` VALUES (?,?,?,?,?,?,?,?)";
        try {
            //查询的数据源
            connQuery = DbUtils.getConnQuery();
            stmt = connQuery.createStatement();
            //增加的数据源
            connInsert = DbUtils.getConnInsert();
            preparedStatement = connInsert.prepareStatement(insertSQL);
            //查
            List<AcountCreatLog> entityList = queryDailyAcountCreatLog(dateStr, stmt,startTime,endTime);
            if (entityList.size() < 1) {
                log.warn("该日期下没有查到任何数据！" + dateStr);
                return;
            }
            long startTime1 = System.currentTimeMillis();
            //增
            //关闭自动提交事务
            connInsert.setAutoCommit(false);
            SyncDataAcountCreatLogImpl syncDataAcountCreatLog = new SyncDataAcountCreatLogImpl();
            //判断数据库中是否已经存了要拉取的数据
            String checkSql="SELECT * FROM account_create_log WHERE DATE_FORMAT(log_time, '%Y-%m-%d')='" + dateStr + "'";
           // String checkSql="SELECT * FROM account_create_log WHERE DATE_FORMAT(log_time, '%Y-%m-%d ') between "+"'"+startTime+"'"+"  and   "+"'"+endTime+"'";
            ResultSet resultSet = preparedStatement.executeQuery(checkSql);
            if(!resultSet.next()) {
                for (int jj = 0; jj < entityList.size(); jj++) {
                    try {
                        syncDataAcountCreatLog.insertrAcountCreatLog(entityList, preparedStatement, entityList.get(jj));
                    } catch (Exception e) {
                        if (e instanceof MySQLIntegrityConstraintViolationException) {
                            log.info("本次同步数据日期:" + dateStr + " 主键重复,数据以存在，无需同步,跳过本条，直接进行下一条...");
                            continue;
                        } else {
                            log.error("### executeDailySyncDataAcountCreatLog--->发生错误了" + e.getMessage());
                        }
                    }
//                if (jj%1000==0){
//                    //批处理
//                    preparedStatement.executeBatch();
//                }
                }
            }else{
                System.out.println("创建账号数据已经插入完成,无需重复操作");
                log.info("数据已经插入完成,无需重复操作");
            }
            //preparedStatement.executeBatch();
            //提交事务
            connInsert.commit();
            long endTime1 = System.currentTimeMillis();
            log.info("数据时间是：" + dateStr + ",的所有数据插入完成，耗时：" + (endTime1 - startTime1));
            log.warn(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ",账号表的数据同步完成！"); //新的文件，日志级别不同。
        } catch (Exception e) {
            log.error("添加失败：SQL:" + insertSQL);
            log.error("错误：" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connQuery != null) {
                try {
                    connQuery.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connInsert != null) {
                try {
                    connInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void executeDailySyncDataAcountCreatLog2(String startTime ,String endTime) {
        log.info("### executeDailySyncDataAcountCreatLog ---> 启动了..." + new Date());
        // 查询的数据源
        Connection connQuery = null;
        Statement stmt = null;
        //增加的数据源
        Connection connInsert = null;
        PreparedStatement preparedStatement = null;
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date time=cal.getTime();
        String format = new SimpleDateFormat("yyyy-MM-dd").format(time);
        String dateStr = format;
        //获取数据库连接
        String insertSQL = "  INSERT INTO `recharge_success_log` VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            //查询的数据源
            connQuery = DbUtils.getConnQuery();
            stmt = connQuery.createStatement();
            //增加的数据源
            connInsert = DbUtils.getConnInsert();
            preparedStatement = connInsert.prepareStatement(insertSQL);
            //查
            List<RechargeSuccessLogPojo> entityList = queryDailyAcountCreatLog2(dateStr, stmt,startTime,endTime);
            if (entityList.size() < 1) {
                log.warn("该日期下没有查到任何数据！" + dateStr);
                return;
            }
            long startTime1 = System.currentTimeMillis();
            //增
            //关闭自动提交事务
            connInsert.setAutoCommit(false);
           // SyncDataAcountCreatLogImpl syncDataAcountCreatLog = new SyncDataAcountCreatLogImpl();
            SyncDataRechargeSuccessLogImpl syncDataRechargeSuccessLog = new SyncDataRechargeSuccessLogImpl();
            //判断数据库中是否已经存了要拉取的数据
            String checkSql="SELECT * FROM recharge_success_log WHERE DATE_FORMAT(log_time, '%Y-%m-%d')='" + dateStr + "'";
           // String checkSql="SELECT * FROM recharge_success_log WHERE DATE_FORMAT(log_time, '%Y-%m-%d ') between "+"'"+startTime+"'"+"  and   "+"'"+endTime+"'";
            ResultSet resultSet = preparedStatement.executeQuery(checkSql);
            if(!resultSet.next()) {
                for (int jj = 0; jj < entityList.size(); jj++) {
                    try {
                        syncDataRechargeSuccessLog.insertrRchargeSuccessLogPojo(entityList, preparedStatement, entityList.get(jj));
                    } catch (Exception e) {
                        if (e instanceof MySQLIntegrityConstraintViolationException) {
                            log.info("本次同步数据日期:" + dateStr + " 主键重复,数据以存在，无需同步,跳过本条，直接进行下一条...");
                            continue;
                        } else {
                            log.error("### executeDailySyncDataAcountCreatLog--->发生错误了" + e.getMessage());
                        }
                    }
//                if (jj%1000==0){
//                    //批处理
//                    preparedStatement.executeBatch();
//                }
                }
            }else{
                System.out.println("充值账号数据已经插入完成,无需重复操作");
                log.info("数据已经插入完成,无需重复操作");
            }
            //preparedStatement.executeBatch();
            //提交事务
            connInsert.commit();
            long endTime1 = System.currentTimeMillis();
            log.info("数据时间是：" + dateStr + ",的所有数据插入完成，耗时：" + (endTime1 - startTime1));
            log.warn(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ",充值表的数据同步完成！"); //新的文件，日志级别不同。
        } catch (Exception e) {
            log.error("添加失败：SQL:" + insertSQL);
            log.error("错误：" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connQuery != null) {
                try {
                    connQuery.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connInsert != null) {
                try {
                    connInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    @Override
    public void executeDailySyncDataAcountCreatLog3(String startTime,String endTime) {
        log.info("### 登录登出表---> 启动了..." + new Date());
        // 查询的数据源
        Connection connQuery = null;
        Statement stmt = null;
        //增加的数据源
        Connection connInsert = null;
        PreparedStatement preparedStatement = null;
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date time=cal.getTime();
        String format = new SimpleDateFormat("yyyy-MM-dd").format(time);
        String dateStr = format;
        //获取数据库连接
        String insertSQL = "  INSERT INTO `role_logout_log` VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            //查询的数据源
            connQuery = DbUtils.getConnQuery();
            stmt = connQuery.createStatement();
            log.info("查询的数据源连接成功=========================================");
            //增加的数据源
            connInsert = DbUtils.getConnInsert();
            log.info("插入的数据源连接成功===========================================");
            preparedStatement = connInsert.prepareStatement(insertSQL);
            log.info("开始去查询数据");
            //查
            List<LogEntity> entityList = queryDailyAcountCreatLog3(dateStr,stmt,startTime,endTime);
            log.info("查询返回结果");
            if (entityList.size() < 1) {
                log.warn("该日期下没有查到任何数据！" + dateStr);
                return;
            }
            long startTime1 = System.currentTimeMillis();
            //增
            //关闭自动提交事务
            connInsert.setAutoCommit(false);
         //   SyncDataAcountCreatLogImpl syncDataAcountCreatLog = new SyncDataAcountCreatLogImpl();
            SyncDataRoleLogOut syncDataRoleLogOut = new SyncDataRoleLogOut();
            //判断数据库中是否已经存了要拉取的数据
            String checkSql="SELECT * FROM role_logout_log WHERE DATE_FORMAT(log_time, '%Y-%m-%d')='" + dateStr + "'";
            //String checkSql="SELECT * FROM role_logout_log WHERE DATE_FORMAT(log_time, '%Y-%m-%d ') between "+"'"+startTime+"'"+"  and   "+"'"+endTime+"'";
            ResultSet resultSet = preparedStatement.executeQuery(checkSql);
            if (!resultSet.next()) {
               for (int jj = 0; jj < entityList.size(); jj++) {
                   try {
                       syncDataRoleLogOut.tesetInsert(entityList, preparedStatement, entityList.get(jj));
                   } catch (Exception e) {
                       if (e instanceof MySQLIntegrityConstraintViolationException) {
                           log.info("本次同步数据日期:" + dateStr + " 主键重复,数据以存在，无需同步,跳过本条，直接进行下一条...");
                           continue;
                       } else if (e instanceof BatchUpdateException) {
                           log.info("BatchUpdateException");
                           continue;
                       } else {
                           log.error("### executeDailySyncDataAcountCreatLog--->发生错误了" + e.getMessage());
                       }
                   }
               }
               //提交事务
               connInsert.commit();
           }else{
               System.out.println("登录登出表数据已经插入完成,无需重复操作");
               log.info("数据已经插入完成,无需重复操作");
           }
            long endTime1 = System.currentTimeMillis();
            log.info("数据时间是：" + dateStr + ",的所有数据插入完成，耗时：" + (endTime1 - startTime1));
            log.warn(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ",登录登出表的数据同步完成！"); //新的文件，日志级别不同。
        } catch (Exception e) {
            log.error("错误：" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connQuery != null) {
                try {
                    connQuery.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connInsert != null) {
                try {
                    connInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    @Override
    public void executeDailySyncDataAcountCreatLog4() {
        log.info("### 日报表转移数据 ---> 启动了..." + new Date());
/*        // 查询的数据源
        Connection connQuery = null;
        Statement stmt = null;
        //增加的数据源
        Connection connInsert = null;
        PreparedStatement preparedStatement = null;
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date time=cal.getTime();
        String format = new SimpleDateFormat("yyyy-MM-dd").format(time);
        String dateStr = format;
        //获取数据库连接
        String insertSQL = "  INSERT INTO `daily_statistics` VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            //查询的数据源
            connQuery = DbUtils.getConnQuery();
            stmt = connQuery.createStatement();
            //增加的数据源
            connInsert = DbUtils.getConnInsert();
            preparedStatement = connInsert.prepareStatement(insertSQL);
            //查
            List<AcountCreatLog> entityList = queryDailyAcountCreatLog(startTime,endTime, stmt);
            if (entityList.size() < 1) {
                log.warn("该日期下没有查到任何数据！" + dateStr);
                return;
            }
            long startTime = System.currentTimeMillis();
            //增
            //关闭自动提交事务
            connInsert.setAutoCommit(false);
            SyncDataAcountCreatLogImpl syncDataAcountCreatLog = new SyncDataAcountCreatLogImpl();
            for (int jj = 0; jj < entityList.size(); jj++) {
                try {
                    syncDataAcountCreatLog.insertrAcountCreatLog(entityList,  preparedStatement, entityList.get(jj));
                } catch (Exception e) {
                    if (e instanceof MySQLIntegrityConstraintViolationException) {
                        log.info("本次同步数据日期:" +  dateStr + " 主键重复,数据以存在，无需同步,跳过本条，直接进行下一条...");
                        continue;
                    }else {
                        log.error("### executeDailySyncDataAcountCreatLog--->发生错误了" + e.getMessage());
                    }
                }

            }

            //提交事务
            connInsert.commit();
            long endTime = System.currentTimeMillis();
            log.info("数据时间是：" + dateStr + ",的所有数据插入完成，耗时：" + (endTime - startTime));
            log.warn(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ",登录登出表的数据同步完成！"); //新的文件，日志级别不同。
        } catch (Exception e) {
            try {
                connInsert.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            log.error("添加失败：SQL:" + insertSQL);
            log.error("错误：" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connQuery != null) {
                try {
                    connQuery.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connInsert != null) {
                try {
                    connInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }




    /**
     * 查询原表数据
     * <p>
     * AcountCreatLog
     *
     * @param  ： 格式：yyyy-MM-dd   2019-05-21
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static List<AcountCreatLog> queryDailyAcountCreatLog(String dateStr, Statement stmt,String startTime,String endTime) throws Exception {
        List<AcountCreatLog> entityList = new ArrayList<AcountCreatLog>();
        String querySql = "SELECT * FROM account_create_log WHERE DATE_FORMAT(log_time, '%Y-%m-%d')='" + dateStr + "'";
//       String querySql = "SELECT * FROM account_create_log WHERE DATE_FORMAT(log_time, '%Y-%m-%d') = '2019-12-07' ";
   //   String querySql = "SELECT * FROM account_create_log WHERE DATE_FORMAT(log_time, '%Y-%m-%d ') between "+"'"+startTime+"'"+"  and   "+"'"+endTime+"'";
        ResultSet resultSet = stmt.executeQuery(querySql);
        int i = 0;
        long start = System.currentTimeMillis();
        while (resultSet.next()) {
            AcountCreatLog acountCreatLog = new AcountCreatLog();
            String logId = resultSet.getString(1);
            if (StringUtils.isNullOrEmpty(logId)) {
                continue;
            }
            logId = StringUtils.isNullOrEmpty(logId) ? "" + 0 : logId; //为空此条数据已失效，该结束，.
            if (false) {
                ////////////////////////该行取消注释后，本段代码可以重构，复用性//////////////////////////////
            }
            acountCreatLog.setLogId(logId);
            String serverId = resultSet.getString(2);
            serverId = StringUtils.isNullOrEmpty(serverId) ? "" : serverId;
            acountCreatLog.setServerId(serverId);
            String logTime = resultSet.getString(3);
            logTime = StringUtils.isNullOrEmpty(logTime) ? null : logTime;
            acountCreatLog.setLogTime(logTime);
            String acountId = resultSet.getString(4);
            acountId = StringUtils.isNullOrEmpty(acountId) ? "" : acountId;
            acountCreatLog.setAcountId(acountId);
            String deviceId = resultSet.getString(5);
            deviceId = StringUtils.isNullOrEmpty(deviceId) ? "" : deviceId;
            acountCreatLog.setDeviceId(deviceId);
            String ip = resultSet.getString(6);
            ip = StringUtils.isNullOrEmpty(ip) ? "" : ip;
            acountCreatLog.setIp(ip);
            String loginType = resultSet.getString(7);
            loginType = StringUtils.isNullOrEmpty(loginType) ? null : loginType;
            acountCreatLog.setLoginType(loginType);
            String gamechannel = resultSet.getString(11);
            gamechannel = StringUtils.isNullOrEmpty(gamechannel) ? "" : gamechannel;
            acountCreatLog.setGamechannel(gamechannel);
            i++;
            entityList.add(acountCreatLog);
            log.debug("本次查询，实体类对象信息：" + acountCreatLog.toString());
        }
        long end = System.currentTimeMillis();
        log.info("### queryDailyAcountCreatLog ---> 查询到日期为：" + dateStr + "的数据的总条数：" + i + "条，耗时：" + (end - start));
        return entityList;
    }



    public static List<RechargeSuccessLogPojo> queryDailyAcountCreatLog2(String dateStr, Statement stmt,String startTime,String endTime) throws Exception {
        List<RechargeSuccessLogPojo> entityList = new ArrayList<RechargeSuccessLogPojo>();
         String querySql = "SELECT * FROM recharge_success_log WHERE DATE_FORMAT(log_time, '%Y-%m-%d')='" + dateStr + "'";
//        String querySql = "SELECT * FROM recharge_success_log WHERE DATE_FORMAT(log_time, '%Y-%m-%d') = '2019-12-07' ";
  //     String querySql = "SELECT * FROM recharge_success_log WHERE DATE_FORMAT(log_time, '%Y-%m-%d ') between "+"'"+startTime+" '"+"  and   "+" '"+endTime+"'";
        ResultSet resultSet = stmt.executeQuery(querySql);
        int i = 0;
        long start = System.currentTimeMillis();
        while (resultSet.next()) {
            RechargeSuccessLogPojo rechargeSuccessLogPojo = new RechargeSuccessLogPojo();
            String logId = resultSet.getString(1);
            if (StringUtils.isNullOrEmpty(logId)) {
                continue;
            }
            logId = StringUtils.isNullOrEmpty(logId) ? "" + 0 : logId; //为空此条数据已失效，该结束，.
            if (false) {
                ////////////////////////该行取消注释后，本段代码可以重构，复用性//////////////////////////////
            }
            logId = StringUtils.isNullOrEmpty(logId) ? "" + 0 : logId; //为空此条数据已失效，该结束，...
            rechargeSuccessLogPojo.setLogId(logId);
            String serverId = resultSet.getString(2);
            serverId = StringUtils.isNullOrEmpty(serverId) ? "" : serverId;
            rechargeSuccessLogPojo.setServerId(serverId);
            String logTime = resultSet.getString(3);
            logTime = StringUtils.isNullOrEmpty(logTime) ? null : logTime;
            rechargeSuccessLogPojo.setLogTime(logTime);
            String acountId = resultSet.getString(4);
            acountId = StringUtils.isNullOrEmpty(acountId) ? "" : acountId;
            rechargeSuccessLogPojo.setAccountId(acountId);
            String deviceId = resultSet.getString(5);
            deviceId = StringUtils.isNullOrEmpty(deviceId) ? "" : deviceId;
            rechargeSuccessLogPojo.setDeviceId(deviceId);
            String roleId = resultSet.getString(6);
            roleId = StringUtils.isNullOrEmpty(roleId) ? "" : roleId;
            rechargeSuccessLogPojo.setRoleId(roleId);
            String roleName = resultSet.getString(7);
            roleName = StringUtils.isNullOrEmpty(roleName) ? "" : roleName;
            rechargeSuccessLogPojo.setRoleName(roleName);
            String roleLevel = resultSet.getString(8);
            roleLevel = StringUtils.isNullOrEmpty(roleLevel) ? "" : roleLevel;
            rechargeSuccessLogPojo.setRoleLevel(roleLevel);
            int rolePower = resultSet.getInt(9);
            rechargeSuccessLogPojo.setRolePower(rolePower);
            String roleVip = resultSet.getString(10);
            roleVip = StringUtils.isNullOrEmpty(roleVip) ? "" : roleVip;
            rechargeSuccessLogPojo.setRoleVip(roleVip);
            String gameOrderId = resultSet.getString(11);
            gameOrderId = StringUtils.isNullOrEmpty(gameOrderId) ? "" : gameOrderId;
            rechargeSuccessLogPojo.setGameOrderId(gameOrderId);
            String gameChannerlOrderId = resultSet.getString(12);//正常
            gameChannerlOrderId = StringUtils.isNullOrEmpty(gameChannerlOrderId) ? null : gameChannerlOrderId;
            rechargeSuccessLogPojo.setGameChannerlOrderId(gameChannerlOrderId);
            String orderAmount = resultSet.getString(13);
            orderAmount = StringUtils.isNullOrEmpty(orderAmount) ? null : orderAmount;
            rechargeSuccessLogPojo.setOrderAmount(orderAmount);
            String shareAmount = resultSet.getString(14);
            shareAmount = StringUtils.isNullOrEmpty(shareAmount) ? null : shareAmount;
            rechargeSuccessLogPojo.setShareAmount(shareAmount);
            String noShareAmount = resultSet.getString(15);
            noShareAmount = StringUtils.isNullOrEmpty(noShareAmount) ? null : noShareAmount;
            rechargeSuccessLogPojo.setNoShareAmount(noShareAmount);
            String payId = resultSet.getString(16);
            payId = StringUtils.isNullOrEmpty(payId) ? null : payId;
            rechargeSuccessLogPojo.setPayId(payId);
            String rechargeChannel = resultSet.getString(17);
            rechargeChannel = StringUtils.isNullOrEmpty(rechargeChannel) ? "" : rechargeChannel;
            rechargeSuccessLogPojo.setRechargeChannel(rechargeChannel);
            int addJewel = resultSet.getInt(18);
            rechargeSuccessLogPojo.setAddJewel(addJewel);
            int totalJewel = resultSet.getInt(19);
            rechargeSuccessLogPojo.setTotalJewel(totalJewel);
            int currencyType = resultSet.getInt(20);
            rechargeSuccessLogPojo.setCurrencyType(currencyType);
            int itemId = resultSet.getInt(21);
            rechargeSuccessLogPojo.setItemId(itemId);
            int isFirstRecharge = resultSet.getInt(22);
            rechargeSuccessLogPojo.setIsFirstRecharge(isFirstRecharge);
            String deviceModel = resultSet.getString(23);
            deviceModel = StringUtils.isNullOrEmpty(deviceModel) ? "" : deviceModel;
            rechargeSuccessLogPojo.setDeviceModel(deviceModel);
            String androidId = resultSet.getString(24);
            androidId = StringUtils.isNullOrEmpty(androidId) ? "" : androidId;
            rechargeSuccessLogPojo.setAndroidId(androidId);
            String ip = resultSet.getString(25);
            ip = StringUtils.isNullOrEmpty(ip) ? "" : ip;
            rechargeSuccessLogPojo.setIp(ip);
            i++;
            entityList.add(rechargeSuccessLogPojo);
            log.debug("本次查询，实体类对象信息：" + rechargeSuccessLogPojo.toString());
        }
        long end = System.currentTimeMillis();
        log.info("### queryDailyAcountCreatLog ---> 查询到日期为：" + dateStr + "的数据的总条数：" + i + "条，耗时：" + (end - start));
        return entityList;
    }


    public static List<LogEntity> queryDailyAcountCreatLog3(String dateStr, Statement stmt,String startTime,String endTime) throws Exception {
        List<LogEntity> entityList = new ArrayList<LogEntity>();
        String querySql = "SELECT * FROM role_logout_log WHERE DATE_FORMAT(log_time, '%Y-%m-%d')='" + dateStr + "'";
      // String querySql = "SELECT * FROM role_logout_log WHERE DATE_FORMAT(log_time, '%Y-%m-%d') between "+"'"+startTime+"'"+"  and   "+ "'"+endTime+"'";
        //String querySql = "SELECT * FROM role_logout_log WHERE DATE_FORMAT(log_time, '%Y-%m-%d') = '2019-10-27 '";
        log.info("执行sql");
        stmt.setQueryTimeout(60);
        ResultSet resultSet = stmt.executeQuery(querySql);
        int i = 0;
        long start = System.currentTimeMillis();
        log.info("开始遍历");
        while (resultSet.next()) {
            LogEntity logEntity = new LogEntity();
            String logId = resultSet.getString(1);
            if (StringUtils.isNullOrEmpty(logId)) {
                continue;
            }
            logId = StringUtils.isNullOrEmpty(logId) ? "" + 0 : logId; //为空此条数据已失效，该结束，.
            if (false) {
                ////////////////////////该行取消注释后，本段代码可以重构，复用性//////////////////////////////
            }
            logId = StringUtils.isNullOrEmpty(logId) ? "" + 0 : logId;
            logEntity.setLogId(logId);
            String serverId = resultSet.getString(2);
            serverId = StringUtils.isNullOrEmpty(serverId) ? "" : serverId;
            logEntity.setServerId(serverId);
            String logTime = resultSet.getString(3);
            logTime = StringUtils.isNullOrEmpty(logTime) ? null : logTime;
            logEntity.setLogTime(logTime);
            String acountId = resultSet.getString(4);
            acountId = StringUtils.isNullOrEmpty(acountId) ? "" : acountId;
            logEntity.setAcountId(acountId);
            String deviceId = resultSet.getString(5);
            deviceId = StringUtils.isNullOrEmpty(deviceId) ? "" : deviceId;
            logEntity.setDeviceId(deviceId);
            String channelId = resultSet.getString(6);
            channelId = StringUtils.isNullOrEmpty(channelId) ? "" : channelId;
            logEntity.setChannelId(channelId);
            String roleId = resultSet.getString(7);
            roleId = StringUtils.isNullOrEmpty(roleId) ? "" : roleId;
            logEntity.setRoleId(roleId);
            String roleName = resultSet.getString(8);
            roleName = StringUtils.isNullOrEmpty(roleName) ? "" : roleName;
            logEntity.setRoleName(roleName);
            int roleLevel = resultSet.getInt(9);
            logEntity.setRoleLevel(roleLevel);
            int rolePower = resultSet.getInt(10);
            logEntity.setRolePower(rolePower);
            String ip = resultSet.getString(20);
            ip = StringUtils.isNullOrEmpty(ip) ? "" : ip;
            logEntity.setIp(ip);
            int opType = resultSet.getInt(11);
            logEntity.setOpType(opType);
            String creatTime = resultSet.getString(12);
            creatTime = StringUtils.isNullOrEmpty(creatTime) ? null : creatTime;
            logEntity.setCreatTime(creatTime);
            String onlineTime = resultSet.getString(13);
            onlineTime = StringUtils.isNullOrEmpty(onlineTime) ? null : onlineTime;
            logEntity.setOnlineTime(onlineTime);
//            log.info("i:" + i + ",logId:" + logId + ",serverI
            i++;
            entityList.add(logEntity);
            log.debug("本次查询，实体类对象信息：" + logEntity.toString());
        }
        long end = System.currentTimeMillis();
        log.info("### queryDailyAcountCreatLog ---> 查询到日期为：" + dateStr + "的数据的总条数：" + i + "条，耗时：" + (end - start));
        return entityList;
    }



    @Override
    public void executeDailySyncDataAcountCreatLog5() {
        log.info("### 开始 ---> 启动了..." + new Date());
        // 查询的数据源
        Connection connQuery = null;
        Statement stmt = null;
        //增加的数据源
//        Connection connInsert = null;
//        PreparedStatement preparedStatement = null;
//        Calendar cal=Calendar.getInstance();
//        cal.add(Calendar.DATE,-1);
//        Date time=cal.getTime();
//        String format = new SimpleDateFormat("yyyy-MM-dd").format(time);
//        String dateStr = format;
        String sql = "SELECT  count(DISTINCT account_id)   FROM  recharge_success_log   \n" +
                "                 WHERE   \n" +
                "                 game_channel_order_id != ''   \n" +
                "                 and account_id IN (SELECT account_id FROM account_create_log WHERE DATE_FORMAT(log_time, '%Y-%m-%d') = '2019-05-21'       )   \n" +
                "                 AND DATE_FORMAT(log_time, '%Y-%m-%d') = '2019-05-21' ";
        try {
            connQuery = DbUtils.getConnQuery();
            stmt = connQuery.prepareStatement(sql);

            //获取数据库连接
            String insertSQL = "  update `daily_statistics` set new_pay_number = ? where DATE_FORMAT(log_time, '%Y-%m-%d')= ?";


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
