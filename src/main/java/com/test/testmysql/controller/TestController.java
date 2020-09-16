package com.test.testmysql.controller;

import com.test.testmysql.task.DailySyncDataAcountCreatLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;


@RestController
public class TestController {
    @Autowired
 private  DailySyncDataAcountCreatLog DailySyncDataAcountCreatLog;


   @RequestMapping("/account")
    public  String test(HttpServletRequest request){
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
            DailySyncDataAcountCreatLog.executeDailySyncDataAcountCreatLog(startTime,endTime);

            return "提取数据成功!";

    }


    @RequestMapping("/recharge")
    public String test1(HttpServletRequest request){
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        DailySyncDataAcountCreatLog.executeDailySyncDataAcountCreatLog2(startTime,endTime);
      return "提取数据成功!";
    }


   @RequestMapping("/roleoutlog")
    public String test2(HttpServletRequest request){
       String startTime = request.getParameter("startTime");
       String endTime = request.getParameter("endTime");
        DailySyncDataAcountCreatLog.executeDailySyncDataAcountCreatLog3(startTime,endTime);
       return "提取数据成功!";
    }




    //@RequestMapping("/queryDaily")
    public String test3(){
        ArrayList<Object> objects = new ArrayList<>();
        DailySyncDataAcountCreatLog.executeDailySyncDataAcountCreatLog4();

        return "提取数据成功!";

    }







    @RequestMapping("/hello")
    public String hello(){
        return "hello world";
    }


}
