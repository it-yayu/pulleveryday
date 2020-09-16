package com.test.testmysql.task;

import com.mysql.jdbc.StringUtils;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.test.testmysql.constant.Constants;
import com.test.testmysql.pojo.RechargeSuccessLogPojo;
import com.test.testmysql.utils.DbUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SyncDataRechargeSuccessLogImpl implements SyncDataRechargeSuccessLog {

    public static Integer RechargeSuccessLogPojoCount;

    /**
     * 根据  log_id 查询数据一组一组的添加
     *
     * @param count
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static List<RechargeSuccessLogPojo> queryRechargeSuccessLog(int count, Statement stmt) throws Exception {
        List<RechargeSuccessLogPojo> entityList = new ArrayList<RechargeSuccessLogPojo>();
//        String querySql = "SELECT * FROM role_logout_log  LIMIT 1000000";
        String querySql = "SELECT * FROM recharge_success_log WHERE log_id = " + count;
        ResultSet resultSet = stmt.executeQuery(querySql);
        int i = 0;
        long start = System.currentTimeMillis();
        while (resultSet.next()) {
            RechargeSuccessLogPojo rechargeSuccessLogPojo = new RechargeSuccessLogPojo();
            String logId = resultSet.getString(1);
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
            String ip = resultSet.getString(10);
            ip = StringUtils.isNullOrEmpty(ip) ? "" : ip;
            rechargeSuccessLogPojo.setIp(ip);
            String roleVip = resultSet.getString(11);
            roleVip = StringUtils.isNullOrEmpty(roleVip) ? "" : roleVip;
            rechargeSuccessLogPojo.setRoleVip(roleVip);
            String gameOrderId = resultSet.getString(12);
            gameOrderId = StringUtils.isNullOrEmpty(gameOrderId) ? "" : gameOrderId;
            rechargeSuccessLogPojo.setGameOrderId(gameOrderId);
            String gameChannerlOrderId = resultSet.getString(13);//正常
            gameChannerlOrderId = StringUtils.isNullOrEmpty(gameChannerlOrderId) ? null : gameChannerlOrderId;
            rechargeSuccessLogPojo.setGameChannerlOrderId(gameChannerlOrderId);
            String orderAmount = resultSet.getString(14);
            orderAmount = StringUtils.isNullOrEmpty(orderAmount) ? null : orderAmount;
            rechargeSuccessLogPojo.setOrderAmount(orderAmount);
            String shareAmount = resultSet.getString(15);
            shareAmount = StringUtils.isNullOrEmpty(shareAmount) ? null : shareAmount;
            rechargeSuccessLogPojo.setShareAmount(shareAmount);
            String noShareAmount = resultSet.getString(16);
            noShareAmount = StringUtils.isNullOrEmpty(noShareAmount) ? null : noShareAmount;
            rechargeSuccessLogPojo.setNoShareAmount(noShareAmount);
            String payId = resultSet.getString(17);
            payId = StringUtils.isNullOrEmpty(payId) ? null : payId;
            rechargeSuccessLogPojo.setPayId(payId);
            String rechargeChannel = resultSet.getString(18);
            rechargeChannel = StringUtils.isNullOrEmpty(rechargeChannel) ? "" : rechargeChannel;
            rechargeSuccessLogPojo.setRechargeChannel(rechargeChannel);
            int addJewel = resultSet.getInt(19);
            rechargeSuccessLogPojo.setAddJewel(addJewel);
            int totalJewel = resultSet.getInt(20);
            rechargeSuccessLogPojo.setTotalJewel(totalJewel);
            int currencyType = resultSet.getInt(21);
            rechargeSuccessLogPojo.setCurrencyType(currencyType);
            int itemId = resultSet.getInt(22);
            rechargeSuccessLogPojo.setItemId(itemId);
            int isFirstRecharge = resultSet.getInt(23);
            rechargeSuccessLogPojo.setIsFirstRecharge(isFirstRecharge);
            String deviceModel = resultSet.getString(24);
            deviceModel = StringUtils.isNullOrEmpty(deviceModel) ? "" : deviceModel;
            rechargeSuccessLogPojo.setDeviceModel(deviceModel);
            String androidId = resultSet.getString(25);
            androidId = StringUtils.isNullOrEmpty(androidId) ? "" : androidId;
            rechargeSuccessLogPojo.setAndroidId(androidId);
            i++;
            entityList.add(rechargeSuccessLogPojo);
            log.debug(rechargeSuccessLogPojo.toString());
        }
        long end = System.currentTimeMillis();
        log.info("### queryRechargeSuccessLog ---> 查询到log_id为：" + count + "的数据的总条数：" + i + "条，耗时：" + (end - start));
        return entityList;
    }

    /**
     * 插入数据
     * 充值成功记录表
     *
     * @param list
     */
    public static void insertrRchargeSuccessLogPojo(List<RechargeSuccessLogPojo> list,  PreparedStatement preparedStatement, RechargeSuccessLogPojo rechargeSuccessLogPojo) throws Exception {
        if (list.size() < 1) {
            log.error("###  tesetInsert ---> list参数的长度是：" + list.size());
        }

        preparedStatement.setString(1, null);
        preparedStatement.setString(2, rechargeSuccessLogPojo.getServerId());
        preparedStatement.setString(3, rechargeSuccessLogPojo.getLogTime());
        preparedStatement.setString(4, rechargeSuccessLogPojo.getAccountId());
        preparedStatement.setString(5, rechargeSuccessLogPojo.getDeviceId());
        preparedStatement.setString(6, rechargeSuccessLogPojo.getRoleId());
        preparedStatement.setString(7, rechargeSuccessLogPojo.getRoleName());
        preparedStatement.setString(8, rechargeSuccessLogPojo.getRoleLevel());
        preparedStatement.setInt(9, rechargeSuccessLogPojo.getRolePower());
        preparedStatement.setString(10, rechargeSuccessLogPojo.getIp());
        preparedStatement.setString(11, rechargeSuccessLogPojo.getRoleVip());
        preparedStatement.setString(12, rechargeSuccessLogPojo.getGameOrderId());
        preparedStatement.setString(13, rechargeSuccessLogPojo.getGameChannerlOrderId());
        preparedStatement.setString(14, rechargeSuccessLogPojo.getOrderAmount());
        preparedStatement.setString(15, rechargeSuccessLogPojo.getShareAmount());
        preparedStatement.setString(16, rechargeSuccessLogPojo.getNoShareAmount());
        preparedStatement.setString(17, rechargeSuccessLogPojo.getPayId());
        preparedStatement.setString(18, rechargeSuccessLogPojo.getRechargeChannel());
        preparedStatement.setInt(19, rechargeSuccessLogPojo.getAddJewel());
        preparedStatement.setInt(20, rechargeSuccessLogPojo.getTotalJewel());
        preparedStatement.setInt(21, rechargeSuccessLogPojo.getCurrencyType());
        preparedStatement.setInt(22, rechargeSuccessLogPojo.getItemId());
        preparedStatement.setInt(23, rechargeSuccessLogPojo.getIsFirstRecharge());
        preparedStatement.setString(24, rechargeSuccessLogPojo.getDeviceModel());
        preparedStatement.setString(25, rechargeSuccessLogPojo.getDeviceId());
        log.info("###  insertrRchargeSuccessLogPojo ---> 本次执行的sql：" + preparedStatement.toString());
        //执行Sql
         preparedStatement.executeUpdate();
        //加入批处理
       // preparedStatement.addBatch();
    }


    /**
     * 一些操作啊
     */
    @Override
    public void excuteInitRechargeSuccessLog() {

        log.info("### excuteInitRechargeSuccessLog ---> 启动了..." + new java.util.Date());
        // 查询的数据源
        Connection connQuery = null;
        Statement stmt = null;
        //增加的数据源
        Connection connInsert = null;
        PreparedStatement preparedStatement = null;
        //获取数据库连接
        String insertSQL = "INSERT INTO recharge_success_log VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            //查询的数据源
            connQuery = DbUtils.getConnQuery();
            stmt = connQuery.createStatement();
            //增加的数据源
            connInsert = DbUtils.getConnInsert();
            preparedStatement = connInsert.prepareStatement(insertSQL);
            for (int i = 0; i < RechargeSuccessLogPojoCount; i++) {
                //查
                List<RechargeSuccessLogPojo> entityList = queryRechargeSuccessLog(i, stmt);
                if (entityList.size() < 1) {
                    continue;
                }
                Constants.RECHARGE_SUCCESS_LOG_COUNT += entityList.size();
                long startTime = System.currentTimeMillis();
                //增
                //关闭自动提交事务
                connInsert.setAutoCommit(false);
                for (int jj = 0; jj < entityList.size(); jj++) {
                    try {
                        insertrRchargeSuccessLogPojo(entityList,  preparedStatement, entityList.get(jj));
                    } catch (Exception e) {
                        if (e instanceof MySQLIntegrityConstraintViolationException) {
                            log.info("log_id:" + jj + "    主键重复,数据以存在，无需同步,跳过本条，直接进行下一条...");
                            continue;
                        }else if (e instanceof BatchUpdateException){
                            log.info("BatchUpdateException");
                            continue;
                        }   else {
                            log.error("excuteInitAcountCreatLog --- > 报错：" + e.getMessage() );
                        }
                    }
                    ///
                    if (jj%1000==0){
                        //1000次批处理
                        preparedStatement.executeBatch();
                    }
                }
                preparedStatement.executeBatch();
                //提交事务
                connInsert.commit();
                long endTime = System.currentTimeMillis();
                log.info("logId是：" + i + ",的所有数据插入完成，耗时：" + (endTime - startTime));
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
