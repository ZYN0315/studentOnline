package com.example.demo.controller;

import com.example.demo.service.AnserService;
import com.example.demo.vo.MessageVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import util.ResponseUtil;
import util.wechatUrl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AnserController {

    @Resource
    private AnserService anserService;

    @RequestMapping("/wechat/anser/info")
    public void info(@RequestParam("state") String askerid, @RequestParam("code") String code, HttpServletResponse response){
        int id= anserService.Info(askerid,code);
        try{
            response.sendRedirect(wechatUrl.ipv4+"/anser.html?id="+id);
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

    @RequestMapping("/wechat/anser/anserinfo")
    public MessageVo anser(@RequestParam("id") String id){
        if (id!="undefined"){
        return anserService.anser(Integer.parseInt(id));}
        return ResponseUtil.systemError("fail","invalid id");
    }

    @RequestMapping("/wechat/anser/anser")
    public MessageVo info(@RequestParam("openid") String openid, @RequestParam("askerid") String askerid,
                          @RequestParam("Q1") int Q1,@RequestParam("Q2") int Q2,@RequestParam("Q3") int Q3,
                          @RequestParam("Q4") int Q4,@RequestParam("Q5") int Q5,@RequestParam("Q6") int Q6,
                          @RequestParam("Q7") int Q7){
        return anserService.Update(openid,askerid,Q1,Q2,Q3,Q4,Q5,Q6,Q7);
    }

    @RequestMapping("/wechat/anser/getResult")
    public MessageVo result(@RequestParam("openid") String openid, @RequestParam("askerid") String askerid){
        System.out.println("openid:"+openid+" askerid:0"+askerid);
        return anserService.result(openid,askerid);
    }

    @RequestMapping("/wechat/anser/results")
    public MessageVo results(@RequestParam("askerid") String askerid){
        return anserService.results(askerid);
    }

}
