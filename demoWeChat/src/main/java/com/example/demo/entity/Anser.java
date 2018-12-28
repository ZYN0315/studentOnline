package com.example.demo.entity;

public class Anser {
    private String ansername;
    private Integer type;
    private Integer result;//表示匹配的个数
    private String askerid;
    private Integer matchornot;//表示是否匹配成功 成功为1 不成功为0
    private String openid;
    private String imgurl;
    private Integer status;//0表示没做过 1表示做过
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {

        this.openid = openid;
    }

    public Integer getMatchornot() {
        return matchornot;
    }

    public void setMatchornot(Integer matchornot) {
        this.matchornot = matchornot;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {

        return status;
    }

    public void setAskerid(String askerid) {
        this.askerid = askerid;
    }



    public void setType(Integer type) {

        this.type = type;
    }

    public void setAnsername(String ansername) {

        this.ansername = ansername;
    }

    public String getAskerid() {

        return askerid;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {

        this.result = result;
    }

    public Integer getType() {

        return type;
    }

    public String getAnsername() {

        return ansername;
    }
}
