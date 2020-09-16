package com.test.testmysql.utils;

import com.test.testmysql.task.DailySyncDataAcountCreatLog;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

@Slf4j
public class TimeTask {

   private static DailySyncDataAcountCreatLog testService= SpringContextUtil.getBean(DailySyncDataAcountCreatLog.class);

    public static void taskMoon() throws ParseException {
        log.info("调用同步account日志代码=======================================================");
        testService.executeDailySyncDataAcountCreatLog("1","2");
    }
    public static void taskMoon2() throws ParseException {
        log.info("调用同步recharge日志代码=======================================================");
        testService.executeDailySyncDataAcountCreatLog2("1","2");
    }
    public static void taskMoon3() throws ParseException {
        log.info("调用同步登录登出日志代码========================================================");
        testService.executeDailySyncDataAcountCreatLog3("1","2");
    }
    public static void taskMoon4() throws ParseException {
        testService.executeDailySyncDataAcountCreatLog4();
    }





}
