package com.test.testmysql.controller;

import com.test.testmysql.task.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 *  初始化数据的Controller
 */
@RestController
@RequestMapping("/init")
@Slf4j
public class InitController {
    @Autowired
    private SyncData syncData;
    @Autowired
    private SyncDataRechargeSuccessLog syncDataRechargeSuccessLog;
    @Autowired
    private SyncDataAcountCreatLog syncDataAcountCreatLog;
    @Async
    @RequestMapping("/roleLogOutLog/{startTime}")
    public Map<String, Object> initData(@PathVariable(value = "startTime")String startTime){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("code",200);
        map.put("msg","指令发送成功！同步role_logout_log表数据，具体记录查看日志");
        map.put("data",null);
     //   SyncDataRoleLogOut.count = Integer.valueOf(num);
        try {
            syncData.excuteInit();
        } catch (Exception e) {
            map.put("code",500);
            map.put("data",e.getMessage());
//            e.printStackTrace();
        }
        return map;
    }

    /**
     *  初始化账户创建日志表的数据
     * @param num
     * @return
     */
    @RequestMapping("/accountCreateLog/{num}")
    public Map<String, Object> accountCreateLog(@PathVariable (value = "num") String num){
        SyncDataAcountCreatLogImpl.AcountCreatLogPojoCount = Integer.valueOf(num);
        syncDataAcountCreatLog.excuteInitAcountCreatLog();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("code",200);
        map.put("msg","指令发送成功！同步account_create_log表数据，具体记录查看日志");
        map.put("data",null);
        return map;
    }

    @RequestMapping("/rechargeSuccessLog/{num}")
    public Map<String, Object> rechargeSuccessLog(@PathVariable(value = "num") String num){
        SyncDataRechargeSuccessLogImpl.RechargeSuccessLogPojoCount = Integer.valueOf(num);
        syncDataRechargeSuccessLog.excuteInitRechargeSuccessLog();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("code",200);
        map.put("msg","指令发送成功！同步recharge_success_log表数据，具体记录查看日志");
        map.put("data",null);
        return map;
    }

    @RequestMapping("/hello")
    public String sayHello(){
        log.info("项目访问成功!!!");
        return "hello";

    }
}
