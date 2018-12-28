package util;

public class wechatUrl {
    public static String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private String urlGetTicket="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
    private String urlGetAskerInfo="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    private String urlGetAnserInfo="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    private String urlGetUser ="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    private String urlGetCode="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=URL&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
    public static final String ipv4="https://sduonline.cn";
//    public static final String ipv4="http://192.168.123.244";

    public String getUrlGetCode() {
        return urlGetCode;
    }

    public String getUrlGetUser() {
        return urlGetUser;
    }

    public String getUrlGetAnserInfo() {

        return urlGetAnserInfo;
    }

    public String getUrlGetAskerInfo() {

        return urlGetAskerInfo;
    }

    public String getUrlGetTicket() {

        return urlGetTicket;
    }

}
