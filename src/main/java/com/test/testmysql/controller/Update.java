package com.test.testmysql.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@RestController
public class Update {
    @Autowired
    private com.test.testmysql.task.DailySyncDataAcountCreatLog DailySyncDataAcountCreatLog;
    @RequestMapping("/update")
    public void Update(){
        Calendar calendar = Calendar.getInstance();
        /* HOUR_OF_DAY 指示一天中的小时 */
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("一个小时前的时间：" + df.format(calendar.getTime()));
        System.out.println("当前的时间：" + df.format(new Date()));



        Connection con=null;
        PreparedStatement ps=null;
        DailySyncDataAcountCreatLog.executeDailySyncDataAcountCreatLog5();
        //加载驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");



            //使用驱动创建连接
            con= (Connection) DriverManager.getConnection("jdbc:mysql://192.168.0.213:3306/low_log_scheam","root","root123");
            String sql="SELECT  count(DISTINCT account_id)   FROM  recharge_success_log   \n" +
                    "                 WHERE   \n" +
                    "                 game_channel_order_id != ''   \n" +
                    "                 and account_id IN (SELECT account_id FROM account_create_log WHERE DATE_FORMAT(log_time, '%Y-%m-%d') = ?       )   \n" +
                    "                 AND DATE_FORMAT(log_time, '%Y-%m-%d') = ? ";
            ps = con.prepareStatement(sql);
//            ps.setString(1,startTime);
//            ps.setString(2.startTime);

        } catch (Exception e) {
            e.printStackTrace();
        }




    }





}
