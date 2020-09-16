package com.test.testmysql.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AcountCreatLog {
    private String logId;
    private String serverId;
    private String logTime;
    private String acountId;
    private String deviceId;
    private String ip;
    private String loginType;
    private String gamechannel;

}