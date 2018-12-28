var baseUrl = 'https://sduonline.cn';
// var baseUrl = 'http://192.168.123.244';

var askerid;
var openid;
var ansername;
var imgurl;
var type;
var id
var results = new Array();

function load() {
    var url = window.location.search.substring(1);
    var infos;
    var is=0;
    var unused;
    infos=url.split("&");
    for (var i=0;i<infos.length;i++){
        var temp;
        temp= infos[i].split("=");
        if (temp[0]=="id"){
            id=temp[1];
        }
    }
    $.ajax({
        type : "post",
        url : baseUrl+"/wechat/anser/anserinfo",
        data : "id=" + id,
        async : false,
        success : function(json){
            tempBy = eval(json);
            var data=tempBy.data;
            openid = data.openid;
            ansername = data.ansername;
            imgurl = data.imgurl;
            askerid = data.askerid;
            var s = data.status;
            type=data.type;
            unused = data.type;
            if (s!=null)
            is=s;
            load2(s,unused);
        }
    });
    var appid,timestamp,nonceStr,signature;
    $.post(baseUrl+"/wechat/getSignature",{
        openid:openid,
        username:ansername,
        id:id
    },function (json) {
        var data=eval(json).data;
        nonceStr = data.noncestr;
        timestamp = data.timestamp;
        signature = data.signature;
        appid=data.appid;
        wx.config({
            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: appid, // 必填，公众号的唯一标识
            timestamp:timestamp , // 必填，生成签名的时间戳
            nonceStr: nonceStr, // 必填，生成签名的随机串
            signature: signature,// 必填，签名
            jsApiList: ['onMenuShareTimeline','updateTimelineShareData'] // 必填，需要使用的JS接口列表
        });

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
            });

        });
    });
    console.log(is);
    addEvents(is);
}

function load2(s,unused) {
    if (s == 0) {
        if (unused == 1) {
            $(".button1").attr("disabled", "disabled");
            $("#question1").html("1.TA是一个怎样的人");
            $("#question2").html("2.TA更喜欢哪一类小说");
            $("#question3").html("3.TA更喜欢的游戏类型");
            $("#question4").html("4.TA对什么运动更热爱");
            $("#question5").html("5.TA的颜值是");
            $("#question6").html("6.TA的声音是什么类型");
            $("#question7").html("7.TA哪一点最突出");
        }
        if (unused == 2) {
            $(".button2").attr("disabled", "disabled");
            $("#question1").html("1.我是一个怎样的人");
            $("#question2").html("2.我更喜欢哪一类小说");
            $("#question3").html("3.我更喜欢的游戏类型");
            $("#question4").html("4.我对什么运动更热爱");
            $("#question5").html("5.我的颜值是");
            $("#question6").html("6.我的声音是什么类型");
            $("#question7").html("7.我觉得自己哪一点最突出");
        }
    }
}


function addEvents(is) {
    console.log("is:"+is);
    if (askerid==openid){
        alert("即将为您跳转至结果界面");
        $.get(baseUrl + "/wechat/anser/getResult", {
            askerid: askerid,
            openid: openid
        }, function (json) {
            var temp = eval(json);
            $('#datiren').addClass("hide");
            var show="答题结果如下：";
            results = temp.data.resultsOf;
            $('.fit').html(show);
            var showAns = "";
            if (results.length>0){
                for (var i = 0; i < results.length; i++) {
                    showAns += "第"+(i+1)+"名" + ":" + results[i] + "<br>";
                }
            }
            else showAns+="暂时没人达到您的满意度哦";
            $('.ansers').html(showAns);
            $(".main").addClass("hide");
            $(".results").removeClass("hide");
        })
    }
    else{
    if (is == 1) {
        $(".button1").click(function () {
            alert("您已做过，将直接跳转至结果界面");
            $.get(baseUrl + "/wechat/anser/getResult", {
                askerid: askerid,
                openid: openid
            }, function (json) {
                var temp = eval(json);
                var action = "";
                if (temp.data.action1 != null)
                    action += temp.data.action1 + "<br>";
                if (temp.data.action2 != null)
                    action += temp.data.action2 + "<br>";
                if (temp.data.action3 != null)
                    action += temp.data.action3;
                results = temp.data.resultsOf;
                var state = temp.data.resultOf;
                var matchor = temp.data.matchOrNot;
                $('.needtoDo').html(action);
                var fir = "匹配度为level" + state;
                if (matchor == 1)
                    fir += "，达到了出题人的要求。";
                if (matchor == 0)
                    fir += "，未达到出题人的要求。";
                $(".fit").html(fir);

                var showAns = "";
                if (results.length>0){
                    for (var i = 0; i < results.length; i++) {
                        showAns += "第"+(i+1)+"名" + ":" + results[i] + "<br>";
                    }
                }
                else showAns+="暂时没人达到TA的满意度哦";
                $('.ansers').html(showAns);
            });
            $(".main").addClass("hide");
            $(".results").removeClass("hide");
        });
        $(".button2").click(function () {
            alert("您已做过，将直接跳转至结果界面");
            $.get(baseUrl + "/wechat/anser/getResult", {
                askerid: askerid,
                openid: openid
            }, function (json) {
                var temp = eval(json);
                var action = "";
                if (temp.data.action1 != null)
                    action += temp.data.action1 + "<br>";
                if (temp.data.action2 != null)
                    action += temp.data.action2 + "<br>";
                if (temp.data.action3 != null)
                    action += temp.data.action3;
                results = temp.data.resultsOf;
                var state = temp.data.resultOf;
                var matchor = temp.data.matchOrNot;
                $('.needtoDo').html(action);

                var fir = "你和TA的匹配度为level" + state;
                if (matchor == 1)
                    fir += "，达到了TA的要求。";
                if (matchor == 0)
                    fir += "，未达到TA的要求。";
                $(".fit").html(fir);

                var showAns = "";
                if (results.length>0){
                for (var i = 0; i < results.length; i++) {
                    showAns += "第"+(i+1)+"名" + ":" + results[i] + "<br>";
                }
                }
                else showAns+="暂时没人达到TA的满意度哦";
                $('.ansers').html(showAns);
            });
            $(".main").addClass("hide");
            $(".results").removeClass("hide");
        });
    }
    else if(is==0) {
        $(".button1").click(function () {
            $(".main").addClass("hide");
            $(".answer").removeClass("hide");
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
            $(".main").addClass("hide");
            $(".answer").removeClass("hide");
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

        var Q1, Q2, Q3, Q4, Q5, Q6, Q7;
        $.ajaxSetup({async: false});
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
            $.post(baseUrl + "/wechat/anser/anser", {
                openid: openid,
                askerid: askerid,
                Q1: Q1,
                Q2: Q2,
                Q3: Q3,
                Q4: Q4,
                Q5: Q5,
                Q6: Q6,
                Q7: Q7
            });

            var r = confirm("您是否也要将此分享到朋友圈？");
            if (r==true){
                var action1,action2,action3;
                $(".answer").addClass("hide");
                $(".action").removeClass("hide");
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
                $(".sharebutton_anser").click(function () {
                    $.post(baseUrl+"/wechat/asker/info",{
                        openid:openid,
                        level:level,
                        username:ansername,
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
                        if (temp.message=='user has done')
                            alert("您已当过提问者，无需再提交");
                        else{
                            var action = "指定行为1："+action1;
                            if (action2!=null)
                                action+="指定行为2："+"<br>"+action2;
                            if (action3!=null)
                                action+="指定行为3："+"<br>"+action3;
                            $('.back').html(action);
                            alert("请点击右上角分享到朋友圈");
                            $(".sharebutton_anser").addClass("hide");
                        }
                    });
                })
            }

            else{
            var state, matchor;
            $.get(baseUrl + "/wechat/anser/getResult", {
                askerid: askerid,
                openid: openid
            }, function (json) {
                var temp = eval(json);
                var action = "";
                if (temp.data.action1 != null)
                    action += temp.data.action1 + "<br>";
                if (temp.data.action2 != null)
                    action += temp.data.action2 + "<br>";
                if (temp.data.action3 != null)
                    action += temp.data.action3;
                results = temp.data.resultsOf;
                state = temp.data.resultOf;
                matchor = temp.data.matchOrNot;
                $('.needtoDo').html(action);

                var fir = "你和TA的匹配度为level" + state;
                if (matchor == 1)
                    fir += "，达到了TA的要求。";
                if (matchor == 0)
                    fir += "，未达到TA的要求。";
                $(".fit").html(fir);

                var showAns = "";
                if (results.length>0){
                    for (var i = 0; i < results.length; i++) {
                        showAns += "第"+(i+1)+"名" + ":" + results[i] + "<br>";
                    }
                }
                else showAns+="暂时没人达到TA的满意度哦";
                $('.ansers').html(showAns);
            });
            alert("即将跳转到结果界面");
            $(".answer").addClass("hide"),
                $(".results").removeClass("hide")}
        })
    }
    }
}