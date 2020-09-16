package com.test.testmysql.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
@Configuration
@EnableScheduling
@Slf4j
public class Timer {
    @Scheduled(cron = "0 0 0 * * ? ") // 每天拉取充值数据
  // @Scheduled(cron = "0/30 * * * * ?")
    public static void taskAccount() throws ParseException {
        try {
            TimeTask.taskMoon();
            log.info("同步账号日志数据完成===============================================");
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Scheduled(cron = "0 0 0 * * ? ") // 每天拉取充值数据
    public void taskRecharge() throws ParseException {
        try {
            TimeTask.taskMoon2();
            log.info("同步充值日志数据完成===================================================");
        } catch (Exception e) {
            e.getMessage();
        }
    }


    @Scheduled(cron = "0 0 0 * * ? ") // 每天拉取登录登出数据
    public void taskRoleLogOut() throws ParseException {
        try {
            TimeTask.taskMoon3();
            log.info("同步登录登出日志数据完成===================================================");
        } catch (Exception e) {
            e.getMessage();
        }
    }





}
