<%@ page import="com.lixy.boothigh.constants.BConstant" %>
<%@ page import="com.lixy.boothigh.vo.LoginUserVO" %>
<%--
  Created by IntelliJ IDEA.
  User: zhm
  Date: 2015/7/14
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Spring4  websocket实例</title>
    <meta charset="utf-8">
    <style type="text/css">
        #connect-container {
            float: left;
            width: 400px
        }

        #connect-container div {
            padding: 5px;
        }

        #console-container {
            float: left;
            margin-left: 15px;
            width: 400px;
        }

        #console {
            border: 1px solid #CCCCCC;
            border-right-color: #999999;
            border-bottom-color: #999999;
            height: 170px;
            overflow-y: scroll;
            padding: 5px;
            width: 100%;
        }

        #console p {
            padding: 0;
            margin: 0;
        }
    </style>

    <script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>

    <script type="text/javascript">
        var ws = null;
        var url = null;
        var transports = [];

        function setConnected(connected) {
            document.getElementById('connect').disabled = connected;
            document.getElementById('disconnect').disabled = !connected;
            document.getElementById('echo').disabled = !connected;
        }

        function connect() {
            if (!url) {
                alert('请选择使用W3C的websocket还是SockJS');
                return;
            }

            ws = (url.indexOf('sockjs') != -1) ? new SockJS(url, undefined, {protocols_whitelist: transports}) : new WebSocket(url);

            ws.onopen = function () {
                setConnected(true);
                log('WebSocket: 连接成功.');
            };
            ws.onmessage = function (event) {
                log(event.data);
                //todo 根据event.data返回的数据进行弹窗或者其他类似的消息提醒
            };
            ws.onclose = function (event) {
                setConnected(false);
                log('WebSocket: 断开连接.');
                log(event);
            };
            //连接发生错误的回调方法
            ws.onerror = function () {
                log("WebSocket连接发生错误");
            };

        }

        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function () {
            disconnect();
        }

        /*
        * 断开连接
        */
        function disconnect() {
            if (ws != null) {
                ws.close();
                ws = null;
            }
            setConnected(false);
        }

        function echo() {
            if (ws != null) {
                var message = document.getElementById('message').value;
                log('<%=((LoginUserVO)session.getAttribute(BConstant.CURRENT_USER_KEY)).getUserId()%>：' + message);
                var toUser = document.getElementById('toUser').value;
                //拼接消息和接收人
                ws.send(message+'|'+toUser);
            } else {
                alert('没有建立连接，请连接服务！');
            }
        }

        /**
         * 信息封装并展示
         * @param message
         */
        function log(message) {
            var console = document.getElementById('console');
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.appendChild(document.createTextNode(message));
            console.appendChild(p);
            while (console.childNodes.length > 25) {
                console.removeChild(console.firstChild);
            }
            console.scrollTop = console.scrollHeight;
        }

        /*
        * 初始化
        * */
        function init(){
            url = 'ws://' + window.location.host + "/webSocket/<%=((LoginUserVO)session.getAttribute(BConstant.CURRENT_USER_KEY)).getUserId()%>";
            connect();
        }

    </script>
</head>
<body onload="init()">
<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websockets
    rely on Javascript being enabled. Please enable
    Javascript and reload this page!</h2></noscript>
webSocket长连接
<h5>------------------------------------------</h5>
<div>
    <div>
        <button id="connect" onclick="connect();">连接服务器</button>
        <button id="disconnect" disabled="disabled" onclick="disconnect();">断开连接</button>
    </div>
    <div>
        <textarea id="message" style="width: 350px">测试消息!</textarea>
    </div>
    <div>接收人：<input id="toUser" style="width: 350px" placeholder="请输入接收人" /></div>
    <div>
        <button id="echo" onclick="echo();" disabled="disabled">发送消息</button>
    </div>
</div>
<div id="console-container">
    <div id="console"></div>
</div>
</div>
</body>
</html>
