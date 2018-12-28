package com.example.demo.service;

import com.example.demo.entity.AccessToken;
import com.example.demo.entity.wechatContant;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import util.wechatUrl;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class AccessTokenBean implements InitializingBean {
    @Autowired
    private wechatContant wechatContant;

    public static AccessToken accessToken;

    private wechatUrl wechatUrl=new wechatUrl();

    @Override
    public void afterPropertiesSet() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String tokenUrl = wechatUrl.url.replace("APPID", wechatContant.getAppID()).replace("APPSECRET", wechatContant.getAppsecret());
        accessToken = restTemplate.getForObject(tokenUrl, AccessToken.class);
        System.out.println("result:"+accessToken);
        System.out.println("accesstoken:" + accessToken.getAccess_token());
        System.out.println("expires_in:" + accessToken.getExpires_in());
    }

//    @Scheduled(cron = "0/5 * *  * * ? ")   //测试每20秒执行一次
    @Scheduled(cron = "0 0 */2 * * ?")  //俩小时执行一次
    public void getAccessToken() {
        System.out.println("into getAccessToken");
        RestTemplate restTemplate = new RestTemplate();
        String tokenUrl = wechatUrl.url.replace("APPID", wechatContant.getAppID()).replace("APPSECRET", wechatContant.getAppsecret());
        System.out.println("tokenurl:" + tokenUrl);
        accessToken = restTemplate.getForObject(tokenUrl, AccessToken.class);
        System.out.println("accesstoken:" + accessToken.getAccess_token());
        System.out.println("expires_in:" + accessToken.getExpires_in());
    }
}
