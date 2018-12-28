var baseUrl = 'https://sduonline.cn';
// var baseUrl = 'http://192.168.123.244';

var openid;
var username;
var level;
var type;
var Q1,Q2,Q3,Q4,Q5,Q6,Q7;
var action1,action2,action3;
var appid,timestamp,nonceStr,signature;
var tempBy,unused;
var t=1;

function f() {
    var url = window.location.search.substring(1);
    var infos;
    infos=url.split("&");
    var id;
    for (var i=0;i<infos.length;i++){
        var temp;
        temp= infos[i].split("=");
        if (temp[0]=="id"){
            id=temp[1];
        }
    }
    console.log("id="+id);
    $.ajax({
        type : "post",
        url : baseUrl+"/wechat/wx/wxinfo",
        data : "id=" + id,
        async : false,
        success : function(json){
            tempBy = eval(json);
            var data=tempBy.data;
            username = data.username;
            openid = data.openid;
            nonceStr = data.noncestr;
            timestamp = data.timestamp;
            signature = data.signature;
            appid=data.appid;
        }
    });
    if (username!=null&&openid!=null)
        var s = "timestamp:"+timestamp+"aapid:"+appid+"noncestr:"+nonceStr+"signiture:"+signature+"username:"+username+"openid:"+openid;
    console.log(s);
    console.log("openid:"+openid);

    wx.config({
        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: appid, // 必填，公众号的唯一标识
        timestamp:timestamp , // 必填，生成签名的时间戳
        nonceStr: nonceStr, // 必填，生成签名的随机串
        signature: signature,// 必填，签名
        jsApiList: ['onMenuShareTimeline','updateTimelineShareData'] // 必填，需要使用的JS接口列表
    });
    // console.log("开始尝试接入微信updateTimelineShareData");

    wx.ready(function () {      //需在用户可能点击分享按钮前就先调用
        wx.updateTimelineShareData({
            title: '你是对的人吗？', // 分享标题
            link: baseUrl+"/wechat/wx/redirect?askerid="+openid, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
            imgUrl: '', // 分享图标
            success: function () {

            },
            fail : function (res) {
                console.log("失败"+res);
            }
        });
        wx.error(function(res){
            console.log("失败"+res);

            // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
        });

    });
    // alert("link:"+baseUrl+"/wechat/wx/redirect?askerid="+openid);
    $.ajax({
        type : "post",
        url : baseUrl+"/wechat/asker/getAsker",
        data : "openid=" + openid,
        async : false,
        success : function(json){
            tempBy = eval(json);
        }
    });
    if (tempBy.data!=null){
        t=0;
        unused = tempBy.data.typeOf;
    }
    console.log('unused:'+unused+" t:"+t);
    if (t==0) {
    if (unused==1){
        $(".button2").attr("disabled", "disabled");
        $(".button1").click(function () {
            alert("您已做过该类型，将直接跳转至结果界面");
            $.get(baseUrl+"/wechat/asker/getResult",{
                    askerid:openid
                },function sds(json) {
                    var temp = eval(json);
                    var result = temp.data;
                    if (result.length>0){
                        var m="";
                        for (var i=0;i<result.length;i++){
                            m+='第'+(i+1)+'名 ： '+result[i]+'<br>';
                        }
                        $("#p").html(m);
                    }
                    else{
                        $("#p").html("不好意思，暂时没有人回答你的问题");
                    }
                }
            );
            $(".main").addClass("hide");
            $(".resultsBy").removeClass("hide")
        })
    }
    if (unused==2){
        $(".button1").attr("disabled", "disabled");
        $(".button2").click(function () {
            alert("您已做过该类型，将直接跳转至结果界面");
            $.get(baseUrl+"/wechat/asker/getResult",{
                    askerid:openid
                },function sds(json) {
                    var temp = eval(json);
                    results = temp.data;
                    var m;
                    for (var i=0;i<results.length;i++){
                        m+='第'+(i+1)+'名 ： '+results[i].name+'<br>';
                    }
                    $("#id").html(m);
                }
            );
            $(".main").addClass("hide");
            $(".resultsBy").removeClass("hide")
        })
    }
    }
    else {
        addEvents();
    }
}


function addEvents() {
    $(".button1").click(function () {
        type = 1;
        $("#question1").html("1.我是一个怎样的人");
        $("#question2").html("2.我更喜欢哪一类小说");
        $("#question3").html("3.我更喜欢的游戏类型");
        $("#question4").html("4.我对什么运动更热爱");
        $("#question5").html("5.我的颜值是");
        $("#question6").html("6.我的声音是什么类型");
        $("#question7").html("7.我觉得自己哪一点最突出");
        if (t==1){
        $(".main").addClass("hide");
        $(".answer").removeClass("hide");}
        var swiper = new Swiper('.swiper-container', {
            pagination: {
                el: '.swiper-pagination',
                clickable: false,
                renderBullet: function (index, className) {
                    return '<span class="' + className + '">' + (index + 1) + '</span>';
                }
            }
        });
    });
    $(".button2").click(function () {
        type = 2;
        $("#question1").html("1.TA是一个怎样的人");
        $("#question2").html("2.TA更喜欢哪一类小说");
        $("#question3").html("3.TA更喜欢的游戏类型");
        $("#question4").html("4.TA对什么运动更热爱");
        $("#question5").html("5.TA的颜值是");
        $("#question6").html("6.TA的声音是什么类型");
        $("#question7").html("7.TA哪一点最突出");
        if (t==1){
            $(".main").addClass("hide");
            $(".answer").removeClass("hide");}
        var swiper = new Swiper('.swiper-container', {
            pagination: {
                el: '.swiper-pagination',
                clickable: false,
                renderBullet: function (index, className) {
                    return '<span class="' + className + '">' + (index + 1) + '</span>';
                }
            }
        });
    });


    $(".option").click(function () {
        $("#slide1").attr("class", "swiper-slide")
    });
    $(".option2").click(function () {
        $("#slide2").attr("class", "swiper-slide")
    });
    $(".option3").click(function () {
        $("#slide3").attr("class", "swiper-slide")
    });
    $(".option4").click(function () {
        $("#slide4").attr("class", "swiper-slide")
    });
    $(".option5").click(function () {
        $("#slide5").attr("class", "swiper-slide")
    });
    $(".option6").click(function () {
        $("#slide6").attr("class", "swiper-slide")
    });
    $(".option7").click(function () {
        $("#slide7").attr("class", "swiper-slide")
    });

    //获取问题答案
    $(".sub").click(function () {
        var temp = document.getElementsByClassName("option");
        for (var i = 0; i < temp.length; i++) {
            if (temp[i].checked == true) {
                Q1 = temp[i].value
            }
        }
        var temp2 = document.getElementsByClassName("option2");
        for (var i = 0; i < temp2.length; i++) {
            if (temp2[i].checked == true)
                Q2 = temp2[i].value
        }
        var temp3 = document.getElementsByClassName("option3");
        for (var i = 0; i < temp3.length; i++) {
            if (temp3[i].checked == true)
                 Q3 = temp3[i].value
        }
        var temp4 = document.getElementsByClassName("option4");
        for (var i = 0; i < temp4.length; i++) {
            if (temp4[i].checked == true)
                 Q4 = temp4[i].value
        }
        var temp5 = document.getElementsByClassName("option5");
        for (var i = 0; i < temp5.length; i++) {
            if (temp5[i].checked == true)
                 Q5 = temp5[i].value
        }
        var temp6 = document.getElementsByClassName("option6");
        for (var i = 0; i < temp6.length; i++) {
            if (temp6[i].checked == true)
                 Q6 = temp6[i].value
        }
        var temp7 = document.getElementsByClassName("option7");
        for (var i = 0; i < temp7.length; i++) {
            if (temp7[i].checked == true)
                Q7 = temp7[i].value
        }
        $(".answer").addClass("hide")
        $(".action").removeClass("hide")
        // alert("Q1:"+Q1+" Q2:"+Q2)
    });


    $(".subAction").click(function () {
        var checks = document.getElementsByName("action");
        var index = new Array();
        var n = 0;
        for(var i=0;i<checks.length;i++){
            if(checks[i].checked){
                index.push(i);
                n++;
            }
}
        var t = document.getElementsByClassName("level");
        level = t[0].value;
        if (level==0)
            alert("请选择匹配等级");
        else if (n==0||n>3)
        alert("只能选择1-3个动作，您选中动作数为："+n);
        else {
            if (index[0]!=null)
                action1=checks[index[0]].value;
            if (index[1]!=null)
                action2=checks[index[1]].value;
            if (index[2]!=null)
                action3=checks[index[2]].value;
            $("#showaction1").html("1:"+action1);
            if (action2!=null)
                $("#showaction2").html("2:"+action2);
            if (action3!=null)
                $("#showaction3").html("3:"+action3);
            $(".action").addClass("hide");
            $(".share").removeClass("hide")
        }
    });
    $(".sharebutton").click(function () {
        $.post(baseUrl+"/wechat/asker/info",{
            openid:openid,
            level:level,
            username:username,
            type:type,
            Q1:Q1,
            Q2:Q2,
            Q3:Q3,
            Q4:Q4,
            Q5:Q5,
            Q6:Q6,
            Q7:Q7,
            action1:action1,
            action2:action2,
            action3:action3
        },function (json) {
            var temp=eval(json);
            console.log(temp.message)
        });
        var action = "指定行为1："+action1;
        if (action2!=null)
            action+="指定行为2："+"<br>"+action2;
        if (action3!=null)
            action+="指定行为3："+"<br>"+action3;
        $('.back').html(action);
        alert("请点击右上角分享到朋友圈");
        $(".sharebutton").addClass("hide");
})
}