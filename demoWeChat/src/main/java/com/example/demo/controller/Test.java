package com.example.demo.controller;

import com.example.demo.dao.WXMapper;
import com.example.demo.entity.WXInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Controller
public class Test {
    @Resource
    WXMapper wxMapper;
    @RequestMapping("/test")
    public void testR(HttpServletResponse response, @RequestParam("signature") String signature,
                      @RequestParam("openid") String openid,@RequestParam("noncestr") String noncestr,
                      @RequestParam("timestamp") Long timestamp,@RequestParam("id") int id) throws Exception{
//        System.out.println("实现跳转");
//        response.sendRedirect("http://192.168.1.102:63342/demoWeChat/templates/test.html?username=zyn&openid=lkjasdl");
        WXInfo wxInfo = new WXInfo();
        wxInfo.setOpenid(openid);
        wxInfo.setSignature(signature);
        wxInfo.setNoncestr(noncestr);
        wxInfo.setTimestamp(timestamp);
        wxInfo.setId(id);
        wxMapper.Update(wxInfo);
    }
}
