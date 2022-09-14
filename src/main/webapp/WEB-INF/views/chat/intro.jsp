<%--
  Created by IntelliJ IDEA.
  User: data18
  Date: 2022-09-14
  Time: 오전 10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>채팅방 입장을 위한 별명 설정</title>
    <script src="https://code.jquery.com/jquery-3.6.1.js"
            integrity="sha256-3zlB5s2uwoUzrXK3BT7AX3FyvojsraNFxCc2vC/7pNI="
            crossorigin="anonymous"></script>
    <script>
        $(document).ready(function () {

            let btnSend = document.getElementById("btnSend");

            btnSend.onclick = function () {

                const f = document.getElementById("f");
                f.submit();
            }
        });

        setInterval(function () {
            $.ajax({
               url: "/chat/roomList",
               type: "get",
               dataType: "JSON",
               success: function (json) {
                   let roomHtml = "";

                   for (const room of json) {
                       roomHtml += ("<span>" + room + "</span>");
                   }

                   $("#rooms").html(roomHtml);

               }

            });
        }, 5000)
    </script>
</head>

<body>
<div><b>현재 오픈된 채팅방</b></div>
<hr/>
<div id="rooms"></div>
<br/>
<br/>
<form method="post" id="f" action="/chat/room">
    <div><b>채팅 입장</b></div>
    <hr/>
    <div><span>채팅방 이름 : <input type="text" name="roomname"></span></div>
    <div><span>채팅 별명 : <input type="text" name="nickname"></span></div>
    <button id="btnSend">채팅방 입장</button>

</form>

</body>
</html>
