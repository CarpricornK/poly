<%@ page import="kopo.poly.util.CmmUtil" %><%--
  Created by IntelliJ IDEA.
  User: data18
  Date: 2022-09-14
  Time: 오전 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="kopo.poly.util.CmmUtil" %>
<%
    String roomname = CmmUtil.nvl(request.getParameter("roomname"));

    String nickname = CmmUtil.nvl(request.getParameter("nickname"));
%>
<html>
<head>
    <title><%=roomname%> 채팅방 입장</title>
    <script src="https://code.jquery.com/jquery-3.6.1.js"
            integrity="sha256-3zlB5s2uwoUzrXK3BT7AX3FyvojsraNFxCc2vC/7pNI="
            crossorigin="anonymous"></script>

    <script type="text/javascript">
        let data = {};
        let ws;
        const roomname = "<%=roomname%>";
        const nickname = "<%=nickname%>";



        $(document).ready(function () {

            let btnSend = document.getElementById("btnSend");
            btnSend.onclick = function () {
                send();
            }

            console.log("openSocket");
            if(ws !== undefined && ws.readyState !== WebSocket.CLOSED) {
                console.log("WebSocket is already opened.");
                return;
            }

            ws = new WebSocket("ws://" + location.host + "/ws/" + roomname + "/" + nickname);

            console.log(ws);

            ws.onopen = function (event) {
                if (event.data === undefined)
                    return;

                console.log(event.data)
                console.log(data.msg)
            };

            ws.onmessage = function (msg) {


                let data = JSON.parse(msg.data);



                if (data.name === nickname) {
                    $(".chat").append("<div>");
                    $(".chat").append("<span style='color: blue'><b>[보낸 사람] : </b></span>");
                    $(".chat").append("<span style='color: blue'> 나 </span>");
                    $(".chat").append("<span style='color: blue'><b>[발송 메시지] : </b></span>");
                    $(".chat").append("<span style='color: blue'> " + data.msg + " </span>");
                    $(".chat").append("<span style='color: blue'><b>[발송시간] : </b></span>");
                    $(".chat").append("<span style='color: blue'> " + data.date + " </span>");
                    $(".chat").append("</div>");
                    $.ajax({
                        url: "/papago/translate",
                        type: "get",
                        dataType: "JSON",
                        data: {
                            "text": data.msg
                        },
                        success: function (json) {

                            let text = json.text;
                            let translatedText = json.translatedText;
                            let tarLangType = json.tarLangType;
                            let tarLang = "";

                            if (tarLangType === "ko") {
                                tarLang = "한국어";
                            } else if (tarLangType === "en") {
                                tarLang = "영어";
                            }
                            $(".chat").append("<div>");
                            $(".chat").append("<span style='color: orange'><b>[번역] : </b></span>");
                            $(".chat").append("<span style='color: orange'> 나 </span>");
                            $(".chat").append("<span style='color: orange'><b>[발송 메시지] : </b></span>");
                            $(".chat").append("<span style='color: orange'> (원문) " + text +" </span>");
                            $(".chat").append("<span> => </span>");
                            $(".chat").append("<span> (" + tarLang + ") <b>" + translatedText + "</b> </span>");
                            $(".chat").append("<span style='color: orange'><b>[발송시간] : </b></span>");
                            $(".chat").append("<span style='color: orange'> " + data.date + " </span>");
                            $(".chat").append("</div>");
                        }
                    });

                } else if (data.name === "관리자") {
                    $(".chat").append("<div>");
                    $(".chat").append("<span style='color: red'><b>[보낸 사람] : </b></span>");
                    $(".chat").append("<span style='color: red'>" + data.name + "</span>");
                    $(".chat").append("<span style='color: red'><b>[발송 메시지] : </b></span>");
                    $(".chat").append("<span style='color: red'> " + data.msg + " </span>");
                    $(".chat").append("<span style='color: red'><b>[발송시간] : </b></span>");
                    $(".chat").append("<span style='color: red'> " + data.date + " </span>");
                    $(".chat").append("</div>");

                    $.ajax({
                        url: "/papago/translate",
                        type: "get",
                        dataType: "JSON",
                        data: {
                            "text": data.msg
                        },
                        success: function (json) {

                            let text = json.text;
                            let translatedText = json.translatedText;
                            let tarLangType = json.tarLangType;
                            let tarLang = "";

                            if (tarLangType === "ko") {
                                tarLang = "한국어";
                            } else if (tarLangType === "en") {
                                tarLang = "영어";
                            }
                            $(".chat").append("<div>");
                            $(".chat").append("<span style='color: orange'><b>[번역] : </b></span>");
                            $(".chat").append("<span style='color: orange'> data.name </span>");
                            $(".chat").append("<span style='color: orange'><b>[번역 내용] : </b></span>");
                            $(".chat").append("<span style='color: orange'> (원문) " + text +" </span>");
                            $(".chat").append("<span> => </span>");
                            $(".chat").append("<span> (" + tarLang + ") <b>" + translatedText + "</b> </span>");
                            $(".chat").append("<span style='color: orange'><b>[발송시간] : </b></span>");
                            $(".chat").append("<span style='color: orange'> " + data.date + " </span>");
                            $(".chat").append("</div>");
                        }
                    });
                } else {
                    $(".chat").append("<div>");
                    $(".chat").append("<span><b>[보낸 사람] : </b></span>");
                    $(".chat").append("<span>" + data.name + "</span>");
                    $(".chat").append("<span><b>[발송 메시지] : </b></span>");
                    $(".chat").append("<span> " + data.msg + " </span>");
                    $(".chat").append("<span><b>[발송시간] : </b></span>");
                    $(".chat").append("<span> " + data.date + " </span>");
                    $(".chat").append("</div>");

                    $.ajax({
                        url: "/papago/translate",
                        type: "get",
                        dataType: "JSON",
                        data: {
                            "text": data.msg
                        },
                        success: function (json) {

                            let text = json.text;
                            let translatedText = json.translatedText;
                            let tarLangType = json.tarLangType;
                            let tarLang = "";

                            if (tarLangType === "ko") {
                                tarLang = "한국어";
                            } else if (tarLangType === "en") {
                                tarLang = "영어";
                            }
                            $(".chat").append("<div>");
                            $(".chat").append("<span style='color: orange'><b>[번역] : </b></span>");
                            $(".chat").append("<span style='color: orange'> data.name </span>");
                            $(".chat").append("<span style='color: orange'><b>[번역 내용] : </b></span>");
                            $(".chat").append("<span style='color: orange'> (원문) " + text +" </span>");
                            $(".chat").append("<span> => </span>");
                            $(".chat").append("<span> (" + tarLang + ") <b>" + translatedText + "</b> </span>");
                            $(".chat").append("<span style='color: orange'><b>[발송시간] : </b></span>");
                            $(".chat").append("<span style='color: orange'> " + data.date + " </span>");
                            $(".chat").append("</div>");
                        }
                    });
                }

            }
        });

        function send() {
            let msgObj = $("#msg");

            if (msgObj.value !== "") {
                data.name = nickname;
                data.msg = msgObj.val();

                let temp = JSON.stringify(data);

                ws.send(temp);

                console.log("temp : "+temp)
            }
            msgObj.val("");
        }


    </script>
</head>
<body>
<h2><%=nickname%>님! <%=roomname%> 채팅방 입장하셨습니다.</h2>

<div><b>채팅내용</b></div>
<hr/>
<div class="chat"></div>
<div>
    <label for="msg">전달할 메시지 :</label><input type="text" id="msg">
    <button id="btnSend">메시지 전송</button>
</div>


</body>
</html>
