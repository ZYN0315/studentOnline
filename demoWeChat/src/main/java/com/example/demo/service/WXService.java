package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.WXMapper;
import com.example.demo.entity.WXInfo;
import com.example.demo.vo.MessageVo;
import org.springframework.stereotype.Service;
import util.ResponseUtil;

import javax.annotation.Resource;

@Service
public class WXService {

    @Resource
    private WXMapper wxMapper;

    public int returnId(String signature,String openid,String noncestr,Long timestamp,String username){
        WXInfo wxInfo = wxMapper.Select(openid);
        System.out.println(signature+"\n"+openid+"\n"+noncestr+"\n"+timestamp+"\n"+username);
        if (wxInfo==null){
            WXInfo wx=new WXInfo();
            wx.setNoncestr(noncestr);
            wx.setOpenid(openid);
            wx.setSignature(signature);
            wx.setTimestamp(timestamp);
            wx.setUsername(username);
            wxMapper.Insert(wx.getSignature(),wx.getOpenid(),wx.getNoncestr(),wx.getTimestamp(),wx.getUsername());
        }
        else {
            WXInfo wx=new WXInfo();
            wx.setId(wxInfo.getId());
            wx.setNoncestr(noncestr);
            wx.setOpenid(openid);
            wx.setSignature(signature);
            wx.setTimestamp(timestamp);
            wxMapper.Update(wx);
        }
        wxInfo = wxMapper.Select(openid);
        return wxInfo.getId();
    }

    public MessageVo returnInfo(int id,String appid){
        WXInfo wxInfo = wxMapper.SelectById(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("signature",wxInfo.getSignature());
        jsonObject.put("openid",wxInfo.getOpenid());
        jsonObject.put("noncestr",wxInfo.getNoncestr());
        jsonObject.put("timestamp",wxInfo.getTimestamp());
        jsonObject.put("username",wxInfo.getUsername());
        jsonObject.put("appid",appid);
        return ResponseUtil.success("success",jsonObject);
    }

}
