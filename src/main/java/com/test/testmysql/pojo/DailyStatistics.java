package com.test.testmysql.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Calendar;

@Getter
@Setter
@ToString
public class DailyStatistics {
    //
    private Integer logId;
    //
    private String serverId;
    //
    private Calendar logTime;
    //
    private Integer dailyRegister;
    //
    private Integer activeNumber;
    //
    private BigDecimal payAmount;
    //
    private Integer payNumber;
    //
    private Integer fristNumber;
    //
    private BigDecimal fristAmount;
    //
    private BigDecimal sumpersonnel;
    //
    private BigDecimal fristSumAmount;
    //
    private BigDecimal rate;
    //
    private BigDecimal arpu;
    //
    private BigDecimal arppu;

    //
    private BigDecimal maxOnlineNumber;
    //
    private Integer gameNumber;
    //
    private BigDecimal aveGametime;
    //
    private BigDecimal aveGamenumber;
    //
    private Integer newPlayers;

    private Integer sumGametime;

    private BigDecimal newRegRecmon;

    private Integer newRegRecMum;

    private  Integer doublePlayNum;

   private  BigDecimal newRegRate;

   private BigDecimal newRegArpu;
   private BigDecimal newRegArppu;

   private Integer cumRegister;

   private BigDecimal cumRecharge;

   private Integer cumRecNum;

   private BigDecimal cumLtv;

   private  String channelId;

   private Integer newActive;

   private Integer newRegister;

   private BigDecimal newRecharge;



}
