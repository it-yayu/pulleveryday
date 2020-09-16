package com.test.testmysql.task;

import com.mysql.jdbc.StringUtils;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.test.testmysql.constant.Constants;
import com.test.testmysql.pojo.AcountCreatLog;
import com.test.testmysql.pojo.RechargeSuccessLogPojo;
import com.test.testmysql.utils.DbUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *      同步创建账号日志表的数据
 */
@Slf4j
@Service
public class SyncDataAcountCreatLogImpl implements SyncDataAcountCreatLog {

    public static Integer AcountCreatLogPojoCount;

    /**
     * 根据  log_id 查询数据一组一组的添加
     *
     * @param count
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static List<AcountCreatLog> queryAcountCreatLog(int count, Statement stmt) throws Exception {
        List<AcountCreatLog> entityList = new ArrayList<AcountCreatLog>();
//        String querySql = "SELECT * FROM role_logout_log  LIMIT 1000000";
        String querySql = "SELECT * FROM account_create_log WHERE log_id = " + count + " ORDER BY server_id ASC";
        ResultSet resultSet = stmt.executeQuery(querySql);
        int i = 0;
        long start = System.currentTimeMillis();
        while (resultSet.next()) {
            AcountCreatLog acountCreatLog = new AcountCreatLog();
            String logId = resultSet.getString(1);
            if (StringUtils.isNullOrEmpty(logId)){continue; }
            logId = StringUtils.isNullOrEmpty(logId) ? "" + 0 : logId; //为空此条数据已失效，该结束，...
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
            loginType = StringUtils.isNullOrEmpty(loginType) ? "" : loginType;
            acountCreatLog.setLoginType(loginType);
            i++;
            entityList.add(acountCreatLog);
            log.debug("本次查询，实体类对象信息："+acountCreatLog.toString());
        }
        long end = System.currentTimeMillis();
        log.info("### queryAcountCreatLog ---> 查询到log_id为：" + count + "的数据的总条数：" + i + "条，耗时：" + (end - start));
        return entityList;
    }

    /**
     * 插入数据
     * 充值成功记录表
     *
     * @param list
     */
    public static void insertrAcountCreatLog(List<AcountCreatLog> list, PreparedStatement preparedStatement, AcountCreatLog acountCreatLog) throws Exception {
        if (list.size() < 1) {
            log.error("###  insertrAcountCreatLogPojo ---> list参数的长度是：" + list.size());
        }
        preparedStatement.setString(1, null);
        preparedStatement.setString(2, acountCreatLog.getServerId());
        preparedStatement.setString(3, acountCreatLog.getLogTime());
        preparedStatement.setString(4, acountCreatLog.getAcountId());
        preparedStatement.setString(5, acountCreatLog.getDeviceId());
        preparedStatement.setString(6, acountCreatLog.getIp());
        preparedStatement.setString(7, acountCreatLog.getLoginType());
        preparedStatement.setString(8, acountCreatLog.getGamechannel());
        log.info("###  insertrAcountCreatLogPojo ---> 本次执行的sql：" + preparedStatement.toString());
        //执行Sql
        preparedStatement.executeUpdate();
        //添加批处理
        //preparedStatement.addBatch();
    }


    /**
     * 一些操作啊
     */
    @Override
    public void excuteInitAcountCreatLog() {

        log.info("### excuteInitAcountCreatLog ---> 启动了..." + new Date());
        // 查询的数据源
        Connection connQuery = null;
        Statement stmt = null;
        //增加的数据源
        Connection connInsert = null;
        PreparedStatement preparedStatement = null;
        //获取数据库连接
//        INSERT INTO `account_create_log` VALUES ('13', '10010', '2019-05-21 12:51:41', '108169330619917612664', '', '58.246.2.134', '5');
        String insertSQL = "  INSERT INTO `account_create_log` VALUES (?,?,?,?,?,?,?)";
        try {
            //查询的数据源
            connQuery = DbUtils.getConnQuery();
            stmt = connQuery.createStatement();
            //增加的数据源
            connInsert = DbUtils.getConnInsert();
            preparedStatement = connInsert.prepareStatement(insertSQL);

            for (int i = 0; i < AcountCreatLogPojoCount; i++) {
                //查
                List<AcountCreatLog> entityList = queryAcountCreatLog(i, stmt);
                if (entityList.size() < 1) {
                    continue;
                }
                Constants.ACOUNT_CREAT_LOG_COUNT += entityList.size();
                long startTime = System.currentTimeMillis();
                //增
                //关闭自动提交事务
                connInsert.setAutoCommit(false);
                try{
                    for (int jj = 0; jj < entityList.size(); jj++) {
                        try {
                            insertrAcountCreatLog(entityList, preparedStatement, entityList.get(jj));
                        } catch (Exception e) {
                            if (e instanceof MySQLIntegrityConstraintViolationException) {
                                log.info("log_id:" + jj + "    主键重复,数据以存在，无需同步,跳过本条，直接进行下一条...");
                                continue;
                            }else if (e instanceof BatchUpdateException){
                                log.info("BatchUpdateException");
                                continue;
                            }
                            else {
                                log.error("excuteInitAcountCreatLog --- > 报错：" + e.getMessage() );
                            }
                        }
                        if (jj%1000==0){
                            //1000次批处理
                            preparedStatement.executeBatch();
                        }
                    }
                    //批处理
                    preparedStatement.executeBatch();
                    //提交事务
                    connInsert.commit();
                    long endTime = System.currentTimeMillis();
                    log.info("logId是：" + i + ",的所有数据插入完成，耗时：" + (endTime - startTime));
                }catch (Exception e){

                }finally {
                    if (null != preparedStatement)
                    preparedStatement.close();
                }

            }
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
}
