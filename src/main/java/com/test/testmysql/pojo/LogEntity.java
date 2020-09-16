package com.test.testmysql.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LogEntity {
    private String logId;
    private String serverId;
    private String logTime;
    private String acountId;
    private String deviceId;
    private String channelId;
    private String roleId;
    private String roleName;
    private int roleLevel;
    private int rolePower;
    private String ip;
    private int opType;
    private String creatTime; //这个是时间格式，但是目前是字符串类型， 如果用框架的话那么就需要改变类型
    private String onlineTime; //这个是时间格式，但是目前是字符串类型， 如果用框架的话那么就需要改变类型
}