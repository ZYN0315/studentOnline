package com.example.demo.controller;

import com.example.demo.service.AskerService;
import com.example.demo.vo.MessageVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class AskerController {

    @Resource
    private AskerService askerService;

    @RequestMapping("/wechat/asker/getAsker")
    public MessageVo getAsker(@RequestParam("openid") String openid){
        return askerService.getAskerInfo(openid);
    }

    @RequestMapping("/wechat/asker/getResult")
    public MessageVo getResult(@RequestParam("askerid") String askerid){
        return askerService.getResults(askerid);
    }

    @RequestMapping("/wechat/asker/info")
    public MessageVo info(@RequestParam("openid") String openid, @RequestParam("level") int level, @RequestParam("username") String username,
                          @RequestParam("type") int type,
                          @RequestParam("Q1") int Q1, @RequestParam("Q2") int Q2, @RequestParam("Q3") int Q3, @RequestParam("Q4") int Q4,
                          @RequestParam("Q5") int Q5, @RequestParam("Q6") int Q6, @RequestParam("Q7") int Q7,
                          @RequestParam() Map<String,String> action){
        return askerService.setInfo(openid,level,username,type,Q1,Q2,Q3,Q4,Q5,Q6,Q7,action.get("action1"),action.get("action2"),action.get("action3"));
    }
}
