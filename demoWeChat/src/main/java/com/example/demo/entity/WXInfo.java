package com.example.demo.entity;

public class WXInfo {
    int id;
    String signature;
    String openid;
    String noncestr;
    Long timestamp;
    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {

        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getOpenid() {

        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSignature() {

        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
