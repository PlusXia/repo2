var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#notice").html("");
}

// 绑定后台建立socket连接
function connect() {
    var socket = new SockJS('/endpoint-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/game_rank', function (result) {
        	showContent(JSON.parse(result.body));
        });
    });
}

// 断开连接
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {

    stompClient.send("/app/v2/chat", {}, JSON.stringify({'content': $("#content").val()}));
}

// 追加信息
function showContent(body) {
    $("#notice").append("<tr><td>" + body.content + "</td> <td>"+new Date(body.time).toLocaleString()+"</td></tr>");
}

// 全局定义绑定事件
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    // 建立连接
    $( "#connect" ).click(function() { connect(); });
    // 取消连接
    $( "#disconnect" ).click(function() { disconnect(); });
    // 推送消息
    $( "#send" ).click(function() { sendName(); });
    connect();
});

