package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.AccessTokenBean;
import com.example.demo.service.WXService;
import com.example.demo.vo.MessageVo;
import com.github.kevinsawicki.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import util.CreateString;
import com.example.demo.entity.wechatContant;
import util.ResponseUtil;
import util.wechatUrl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@RestController
public class WXController {

    @Autowired
    private wechatContant wechatContant;

    @Resource
    private WXService wxService;

    private wechatUrl wechat=new wechatUrl();

    @RequestMapping("/wechat/get/userinfo")
    public void userInfo(@RequestParam("code") String code, HttpServletResponse response) throws Exception {
        JSONObject jsonObject = new JSONObject();
        System.out.println("accessToken:"+AccessTokenBean.accessToken.getAccess_token());
        //        获取用户信息
        if (code != null) {
            String url_getInfo = wechat.getUrlGetAskerInfo().replace("APPID", wechatContant.getAppID()).replace("SECRET", wechatContant.getAppsecret()).replace("CODE", code);
            HttpRequest httpRequest = HttpRequest.get(url_getInfo);
            if (httpRequest.ok()) {
                JSONObject object = JSON.parseObject(httpRequest.body());
//                System.out.println("code:"+code);
//                System.out.println(object);
                if (object != null&&object.get("errcode")==null) {
                    String url_getUser = wechat.getUrlGetUser().replace("ACCESS_TOKEN", object.getString("access_token")).replace("OPENID", object.getString("openid"));
                    httpRequest = HttpRequest.get(url_getUser);
                    object = JSON.parseObject(httpRequest.body());
                    if (object != null) {
                        jsonObject.put("openid", object.get("openid"));
                        jsonObject.put("username", object.get("nickname"));
                    }
                }
            }
            if (jsonObject != null && jsonObject.get("openid") != null){
                int id=wxService.returnId(jsonObject.getString("signature"),jsonObject.getString("openid"),
                        jsonObject.getString("noncestr"), jsonObject.getLong("timestamp"),jsonObject.getString("username"));
            }
        }
        //            向前端传送获取朋友圈转发需要的参数
        if (AccessTokenBean.accessToken.getAccess_token() != null && AccessTokenBean.accessToken.getExpires_in() > 0) {
            String url_getTicket = wechat.getUrlGetTicket().replace("ACCESS_TOKEN", AccessTokenBean.accessToken.getAccess_token());
            HttpRequest httpRequest = HttpRequest.get(url_getTicket);
            if (httpRequest.ok()) {
                JSONObject object = JSON.parseObject(httpRequest.body());
                if (object != null) {
                    int id=wxService.returnId(jsonObject.getString("signature"),jsonObject.getString("openid"),
                            jsonObject.getString("noncestr"), jsonObject.getLong("timestamp"),jsonObject.getString("username"));
                    String ticket = object.getString("ticket");
                    String nonceStr = CreateString.createString();
                    String url = wechatUrl.ipv4+ "/main.html?id="+id;
                    Long timestamp = System.currentTimeMillis();
                    timestamp = timestamp / 1000;
                    String s = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url;
                    String signature = org.apache.commons.codec.digest.DigestUtils.sha1Hex(s);
                    jsonObject.put("timestamp", timestamp);
                    jsonObject.put("appid", wechatContant.getAppID());
                    jsonObject.put("noncestr", nonceStr);
                    jsonObject.put("signature", signature);
                    System.out.println(s);
                }
            }
        }

        if (jsonObject != null && jsonObject.get("signature") != null && jsonObject.get("openid") != null){
            int id=wxService.returnId(jsonObject.getString("signature"),jsonObject.getString("openid"),
                    jsonObject.getString("noncestr"), jsonObject.getLong("timestamp"),jsonObject.getString("username"));
            response.sendRedirect(wechatUrl.ipv4+"/main.html?id="+id);
        }
    }

    @RequestMapping("/wechat/wx/redirect")
    public void Redirect(@RequestParam("askerid") String askerid,HttpServletResponse response) throws Exception{
        response.sendRedirect(wechat.getUrlGetCode().replace("APPID",wechatContant.getAppID()).replace("STATE",askerid).replace("URL",wechat.ipv4+"/wechat/anser/info"));
    }

    @RequestMapping("/wechat/wx/wxinfo")
    public MessageVo wxInfo(@RequestParam("id") int id){
        return wxService.returnInfo(id,wechatContant.getAppID());
    }

    @RequestMapping("/wechat/getUrl")
    public void Url(HttpServletResponse response) throws Exception{
        String base = URLEncoder.encode(wechatUrl.ipv4+"/wechat/get/userinfo","utf-8");
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + wechatContant.getAppID() +
                "&redirect_uri=" + base + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        response.sendRedirect(url);
    }

    @RequestMapping("/wechat/getSignature")
    public MessageVo getSign(HttpServletRequest request,@RequestParam("openid") String openid,
                             @RequestParam("username") String username,@RequestParam("id") String id){
        JSONObject jsonObject = new JSONObject();
        //            向前端传送获取朋友圈转发需要的参数
        if (AccessTokenBean.accessToken.getAccess_token() != null && AccessTokenBean.accessToken.getExpires_in() > 0) {
            String url_getTicket = wechat.getUrlGetTicket().replace("ACCESS_TOKEN", AccessTokenBean.accessToken.getAccess_token());
            HttpRequest httpRequest = HttpRequest.get(url_getTicket);
            if (httpRequest.ok()) {
                JSONObject object = JSON.parseObject(httpRequest.body());
                if (object != null) {
                    String ticket = object.getString("ticket");
                    String nonceStr = CreateString.createString();
                    String url = wechatUrl.ipv4+"/anser.html?id="+id;
                    Long timestamp = System.currentTimeMillis();
                    timestamp = timestamp / 1000;
                    String s = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url;
                    String signature = org.apache.commons.codec.digest.DigestUtils.sha1Hex(s);
                    jsonObject.put("timestamp", timestamp);
                    jsonObject.put("appid", wechatContant.getAppID());
                    jsonObject.put("noncestr", nonceStr);
                    jsonObject.put("signature", signature);
                    wxService.returnId(signature,openid,nonceStr,timestamp,username);
                }
            }
        }
        return ResponseUtil.success("success",jsonObject);
    }

}
