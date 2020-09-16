package com.test.testmysql.task;

import com.mysql.jdbc.StringUtils;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.test.testmysql.constant.Constants;
import com.test.testmysql.pojo.LogEntity;
import com.test.testmysql.utils.DBTableNameUtils;
import com.test.testmysql.utils.DbUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 同步数据
 */
@Slf4j
@Async
@Component
public class SyncDataRoleLogOut implements SyncData {

    public static Integer count;

    /**
     * 根据  log_id 查询数据一组一组的添加
     *
     * @param count
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    public static List<LogEntity> testQuery(String count, Statement stmt,String startTime) throws Exception {
        List<LogEntity> entityList = new ArrayList<>();
//        String querySql = "SELECT * FROM role_logout_log  LIMIT 1000000 where DATE_FORMAT(log_time,'%Y-%m-%d')="+startTime;
        String querySql = "SELECT * FROM role_logout_log WHERE DATE_FORMAT(log_time,'%Y-%m-%d') = " + startTime;
        ResultSet resultSet = stmt.executeQuery(querySql);
        int i = 0;
        long start = System.currentTimeMillis();
        while (resultSet.next()) {
            LogEntity logEntity = new LogEntity();
            String logId = resultSet.getString(1);
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
            String ip = resultSet.getString(11);
            ip = StringUtils.isNullOrEmpty(ip) ? "" : ip;
            logEntity.setIp(ip);
            int opType = resultSet.getInt(12);
            logEntity.setOpType(opType);
            String creatTime = resultSet.getString(13);
            creatTime = StringUtils.isNullOrEmpty(creatTime) ? null : creatTime;
            logEntity.setCreatTime(creatTime);
            String onlineTime = resultSet.getString(14);
            onlineTime = StringUtils.isNullOrEmpty(onlineTime) ? null : onlineTime;
            logEntity.setOnlineTime(onlineTime);
//            log.info("i:" + i + ",logId:" + logId + ",serverId:" + serverId + ",logTime:" + logTime + ",acountId:" + acountId + ",deviceId:" + deviceId + ",channelId:" + channelId + ",roleId:" + roleId + ",roleName:" + roleName + ",roleLevel:" + roleLevel + ",rolePower:" + rolePower + ",ip:" + ip + ",opType:" + opType + ",creatTime:" + creatTime + ",onlineTime:" + onlineTime);
            i++;
            entityList.add(logEntity);
        }
        long end = System.currentTimeMillis();
        log.info("### testQuery ---> 查询到log_id为：" + count + "的数据的总条数：" + i + "条，耗时：" + (end - start));
        return entityList;
    }



    public static List<LogEntity> testQuery2(String count, Statement stmt) throws Exception {
        List<LogEntity> entityList = new ArrayList<>();
//        String querySql = "SELECT * FROM role_logout_log  LIMIT 1000000 where DATE_FORMAT(log_time,'%Y-%m-%d')="+startTime;
        String querySql = "SELECT * FROM role_logout_log WHERE log_id " + count;
        ResultSet resultSet = stmt.executeQuery(querySql);
        int i = 0;
        long start = System.currentTimeMillis();
        while (resultSet.next()) {
            LogEntity logEntity = new LogEntity();
            String logId = resultSet.getString(1);
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
            String ip = resultSet.getString(11);
            ip = StringUtils.isNullOrEmpty(ip) ? "" : ip;
            logEntity.setIp(ip);
            int opType = resultSet.getInt(12);
            logEntity.setOpType(opType);
            String creatTime = resultSet.getString(13);
            creatTime = StringUtils.isNullOrEmpty(creatTime) ? null : creatTime;
            logEntity.setCreatTime(creatTime);
            String onlineTime = resultSet.getString(14);
            onlineTime = StringUtils.isNullOrEmpty(onlineTime) ? null : onlineTime;
            logEntity.setOnlineTime(onlineTime);
//            log.info("i:" + i + ",logId:" + logId + ",serverId:" + serverId + ",logTime:" + logTime + ",acountId:" + acountId + ",deviceId:" + deviceId + ",channelId:" + channelId + ",roleId:" + roleId + ",roleName:" + roleName + ",roleLevel:" + roleLevel + ",rolePower:" + rolePower + ",ip:" + ip + ",opType:" + opType + ",creatTime:" + creatTime + ",onlineTime:" + onlineTime);
            i++;
            entityList.add(logEntity);
        }
        long end = System.currentTimeMillis();
        log.info("### testQuery ---> 查询到log_id为：" + count + "的数据的总条数：" + i + "条，耗时：" + (end - start));
        return entityList;
    }

    /**
     * 插入数据
     *
     * @param list
     */

    public static void tesetInsert(List<LogEntity> list, PreparedStatement preparedStatement, LogEntity lg) throws Exception {
        if (list.size() < 1) {
            log.error("###  tesetInsert ---> list参数的长度是：" + list.size());
        }
        preparedStatement.setString(1, null);
        preparedStatement.setString(2, lg.getServerId());
        preparedStatement.setString(3, lg.getLogTime());
        preparedStatement.setString(4, lg.getAcountId());
        preparedStatement.setString(5, lg.getDeviceId());
        preparedStatement.setString(6, lg.getChannelId());
        preparedStatement.setString(7, lg.getRoleId());
        preparedStatement.setString(8, lg.getRoleName());
        preparedStatement.setInt(9, lg.getRoleLevel());
        preparedStatement.setInt(10, lg.getRolePower());
        preparedStatement.setString(11, lg.getIp());
        preparedStatement.setInt(12, lg.getOpType());
        preparedStatement.setString(13, lg.getCreatTime());
        preparedStatement.setString(14, lg.getOnlineTime());
        log.info("###  tesetInsert ---> 本次执行的sql：" + preparedStatement.toString());
        //执行Sql
        preparedStatement.executeUpdate();
        //加入批处理
//        preparedStatement.addBatch();
    }


    //    /**
//     * 一些操作啊
//     */
    @Override
    public void excuteInit() throws Exception {

        log.info("### excuteInit ---> 启动了..." + new java.util.Date());
        // 查询的数据源
        Connection connQuery = null;
        Statement stmt = null;
        //增加的数据源
        Connection connInsert = null;
        PreparedStatement preparedStatement = null;
        //获取数据库连接
        String insertSQL = "INSERT INTO role_logout_log VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        List<LogEntity> entityList = new ArrayList<>();
        int jj =0;
        try {
            //查询的数据源
            connQuery = DbUtils.getConnQuery();
            stmt = connQuery.createStatement();
            //增加的数据源
            connInsert = DbUtils.getConnInsert();


            for (int i = 0; i < count; i++) {
                //查
                entityList = SyncDataRoleLogOut.testQuery2(String.valueOf(i), stmt);
                if (entityList.size() < 1) {
                    continue;
                }
                Constants.ROLE_LOGIN_TABLE_COUNT += entityList.size();
                if (Constants.ROLE_LOGIN_TABLE_COUNT >= Constants.MAX_COUNT_TABLE) {
                    Constants.MAX_COUNT_TABLE += Constants.MAX_COUNT_TABLE;
                    //数据大于等于1000W，2000W，3000W...  新建一张表
                    DBTableNameUtils.generateTableNameMethod("role_logout_log");
                }
                insertSQL = "INSERT INTO " + Constants.ROLE_LOGIN_OUT_LOG_TABLE_NAME + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                preparedStatement = connInsert.prepareStatement(insertSQL);
                long startTime = System.currentTimeMillis();
                //关闭自动提交事务
                connInsert.setAutoCommit(false);
                //增
                for (jj=0;jj < entityList.size(); jj++) {
                    try {
                        SyncDataRoleLogOut.tesetInsert(entityList, preparedStatement, entityList.get(jj));
                    } catch (Exception e) {
//                        if (e instanceof MySQLIntegrityConstraintViolationException) {
                        if (e instanceof BatchUpdateException) {
                            log.debug("log_id:" + jj + "    主键重复,数据以存在，无需同步,跳过本条，直接进行下一条...");
                            continue;
                        }else {
                            log.error(e.getMessage());
                            continue;
                        }
                    }
                    //批处理
                    if (jj % 1000 == 0) {
                        try {
                            preparedStatement.executeBatch();
                        }catch (Exception e){
                            log.error(e.getMessage());
                            continue;
                        }

                    }
                }
                try {
                    preparedStatement.executeBatch();
                }catch (Exception e){
                    log.error(e.getMessage());
                    continue;
                }

                //提交事务
                connInsert.commit();
                long endTime = System.currentTimeMillis();
                log.info("logId是：" + i + ",的所有数据插入完成，耗时：" + (endTime - startTime));
            }
            log.warn(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ",数据同步完成！"); //新的文件，日志级别不同。
        } catch (Exception e) {
//            if (e instanceof MySQLIntegrityConstraintViolationException) {
            if (e instanceof BatchUpdateException) {
                log.error("主键重复,数据以存在，无需同步");
//                SyncDataRoleLogOut.tesetInsert(entityList, preparedStatement,entityList.get(jj+1));
            } else {
                log.error("添加失败：SQL:" + insertSQL);
                log.error("错误：" + e.getMessage());
                e.printStackTrace();

            }
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
}
