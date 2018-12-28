package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.AnserMapper;
import com.example.demo.dao.AskerMapper;
import com.example.demo.entity.Anser;
import com.example.demo.entity.Asker;
import com.example.demo.vo.MessageVo;
import org.springframework.stereotype.Service;
import util.ResponseUtil;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AskerService {
    @Resource
    private AskerMapper askerMapper;
    @Resource
    private AnserMapper anserMapper;

    public MessageVo getAskerInfo(String openid){
        List<Asker> askers;
        askers = askerMapper.getAskerInfo(openid);
        if (askers.size()!=0){
            Asker asker = askers.get(0);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("typeOf",asker.getType());
            return ResponseUtil.success("success",jsonObject);
        }
        else{
            return ResponseUtil.success("success");
        }
    }

    public MessageVo getResults(String askerid){
        List<Anser> ansers;
        ansers = anserMapper.getResults(askerid);
        JSONObject jsonObject = new JSONObject();
        String results[]=new String[ansers.size()];
        for (int i=0;i<ansers.size();i++){
            results[i]=ansers.get(i).getAnsername();
        }
        jsonObject.put("results",results);
        return ResponseUtil.success("success",results);
    }

    public MessageVo setInfo(String openid,int level,String username,int type,int Q1,int Q2,int Q3,int Q4,int Q5,int Q6,int Q7,String action1,
                             String action2,String action3){
        List<Asker> list = askerMapper.getAskerInfo(openid);
        if (list.size()!=0) {
            System.out.println("user has done + "+list.size());
            return ResponseUtil.systemError("user has done");
        }
        Asker asker = new Asker();
        asker.setOpenid(openid);
        asker.setUsername(username);
        asker.setLevel(level);
        asker.setType(type);
        asker.setQ1(Q1);
        asker.setQ2(Q2);
        asker.setQ3(Q3);
        asker.setQ4(Q4);
        asker.setQ5(Q5);
        asker.setQ6(Q6);
        asker.setQ7(Q7);
        asker.setAction1(action1);
        asker.setAction2(action2);
        asker.setAction3(action3);
        askerMapper.Insert(asker);
        return ResponseUtil.success("success");
    }
}
