package com.test.testmysql.controller;

import com.test.testmysql.task.DailySyncDataAcountCreatLog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  每日同步数据的控制器
 */
@RestController
@RequestMapping("/sync")
@Slf4j
public class SyncDataController {

    @Autowired
    private DailySyncDataAcountCreatLog dailySyncDataAcountCreatLog;

    @RequestMapping("/syncData")
    public Map<String, Object> syncData(){
        log.warn("开始同步数据" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
//        Runnable runnable = () ->dailySyncDataAcountCreatLog.executeDailySyncDataAcountCreatLog();
//        Thread thread = new Thread(runnable);
//        thread.start();

        dailySyncDataAcountCreatLog.executeDailySyncDataAcountCreatLog("1","2");

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("code",200);
        map.put("msg","指令发送成功！开始同步role_logout_log表数据，具体记录查看日志");
        map.put("data",null);
        return map;
    }

}
