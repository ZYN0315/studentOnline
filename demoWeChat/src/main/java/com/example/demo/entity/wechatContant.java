package com.example.demo.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "weixin")
public class wechatContant {
    private String appID;
    private String appsecret;

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getAppID() {

        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }
}
