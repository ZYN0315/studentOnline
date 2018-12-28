package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.AnserMapper;
import com.example.demo.dao.AskerMapper;
import com.example.demo.entity.Anser;
import com.example.demo.entity.Asker;
import com.example.demo.entity.wechatContant;
import com.example.demo.vo.MessageVo;
import com.github.kevinsawicki.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.ResponseUtil;
import util.wechatUrl;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
public class AnserService {
    @Resource
    private AnserMapper anserMapper;
    @Resource
    private AskerMapper askerMapper;
    @Autowired
    private wechatContant wechatContant;

    private wechatUrl wechatUrl=new wechatUrl();

    public int Info(String askerid,String code){
        List<Asker> askers = getAskerInfo(askerid);
        Asker asker = askers.get(0);
        Anser anser=new Anser();
        anser.setType(asker.getType());
        anser.setStatus(0);
        anser.setAskerid(askerid);
        //        获取用户信息
        if (code != null) {
            String url_getInfo = wechatUrl.getUrlGetAnserInfo().replace("APPID", wechatContant.getAppID()).replace("SECRET", wechatContant.getAppsecret()).replace("CODE", code);
            HttpRequest httpRequest = HttpRequest.get(url_getInfo);
            if (httpRequest.ok()) {
                JSONObject object = JSON.parseObject(httpRequest.body());
//                System.out.println(object);
                anser.setOpenid(object.getString("openid"));
                if (object != null&&object.get("errcode")==null) {
                    String url_getUser = wechatUrl.getUrlGetUser().replace("ACCESS_TOKEN", object.getString("access_token")).replace("OPENID", object.getString("openid"));
                    httpRequest = HttpRequest.get(url_getUser);
                    object = JSON.parseObject(httpRequest.body());
//                    System.out.println(object);
                    if (object != null) {
                        anser.setAnsername(object.getString("nickname"));
                        anser.setImgurl(object.getString("headimgurl"));
                    }
                }
            }
        }
//        System.out.println("ansername:"+anser.getAnsername());
        if (anserMapper.getAnser(anser.getAskerid(),anser.getOpenid())==null)
            anserMapper.Insert(anser);
//        System.out.println("akserid:"+anser.getAskerid()+" openid:"+anser.getOpenid());
        int id = anserMapper.getId(anser.getAskerid(),anser.getOpenid());
        return id;
    }

    public MessageVo anser(int id){
        JSONObject jsonObject = new JSONObject();
        Anser anser=anserMapper.anser(id);
        if (anser!=null){
            jsonObject.put("ansername",anser.getAnsername());
            jsonObject.put("openid",anser.getOpenid());
            jsonObject.put("imgurl",anser.getImgurl());
            jsonObject.put("askerid",anser.getAskerid());
            jsonObject.put("status",anser.getStatus());
            jsonObject.put("type",anser.getType());
        }
        return ResponseUtil.success("success",jsonObject);
    }

    public Anser getInfo(String openid){
        Anser anser=new Anser();
        return anser;
    }

    public List<Asker> getAskerInfo(String askerid){
        List<Asker> asker =  askerMapper.getAskerInfo(askerid);
        return asker;
    }

    public MessageVo Update(String openid,String askerid,int Q1,int Q2,int Q3,int Q4,int Q5,int Q6,int Q7){
        List<Asker> askers = askerMapper.getAskerInfo(askerid);
        Asker asker = askers.get(0);
        int result=0;
        if (Q1==asker.getQ1())
            result++;
        if (Q2==asker.getQ2())
            result++;
        if (Q3==asker.getQ3())
            result++;
        if (Q4==asker.getQ4())
            result++;
        if (Q5==asker.getQ5())
            result++;
        if (Q6==asker.getQ6())
            result++;
        if (Q7==asker.getQ7())
            result++;
        Anser anser =anserMapper.getAnser(askerid,openid);
        anser.setResult(result);
        if (result>=asker.getLevel())
            anser.setMatchornot(1);
        else if (result<asker.getLevel())
            anser.setMatchornot(0);
        anser.setStatus(1);
        anserMapper.Update(anser);
        return ResponseUtil.success("success");
    }

    public MessageVo result(String openid,String askerid){
        Anser anser = anserMapper.getAnser(askerid,openid);
        List<Asker> askers = askerMapper.getAskerInfo(askerid);
        Asker asker = askers.get(0);
        List<Anser> ansers = anserMapper.getResults(askerid);
        List<String> results = new LinkedList<>();
        int size=ansers.size();
        for(int i=0;i<size;i++) {
            if (ansers.get(i).getMatchornot()!=null&&ansers.get(i).getMatchornot()==1) {
                if (ansers.get(i).getOpenid()==askerid)
                    continue;
                results.add(ansers.get(i).getAnsername());
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultOf",anser.getResult());
        jsonObject.put("matchOrNot",anser.getMatchornot());
        jsonObject.put("action1",asker.getAction1());
        jsonObject.put("action2",asker.getAction2());
        jsonObject.put("action3",asker.getAction3());
        jsonObject.put("resultsOf",results);
        return ResponseUtil.success("success",jsonObject);
    }

    public MessageVo results(String askerid){
        List<Anser> ansers = anserMapper.getResults(askerid);
        String []results = new String[ansers.size()];
        for (int i=0;i<ansers.size();i++)
            results[i]=ansers.get(i).getAnsername();
        JSONObject jsonObject =new JSONObject();
        jsonObject.put("results",results);
        return ResponseUtil.success("success",jsonObject);
    }

}
