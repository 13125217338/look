var rec = null;
var file = null;
var recordTime = 0;
var audioUrl = null;
$(function() {
	let curHeight = document.body.scrollHeight;
	$("#main").css("height", curHeight + "px");
	//获取岗位
	getPosts(function(res) {
		if(res.code == 200) {renderPost(res.data);}
		else {alert("获取岗位失败！" + res.msg);}
	});
});

//渲染岗位
function renderPost(posts) {
	$.each(posts, function(i) {
		$("#post").append("<option value='" + posts[i] + "'>" + posts[i] + "</option>");
	});
}


//媒体设备获取
media({audio: true}, function(stream) {
	let chunks = [];
	rec = new MediaRecorder(stream);
	
	rec.onstart = function() {console.log("录音开始");}
	rec.ondataavailable = function(e) {
		console.log("推送语音数据");
		chunks.push(e.data);
	}
	
	rec.onstop = function() {
		recordTime = new Date().getTime();
		let blob = new Blob(chunks, {type: "audio/ogg; codecs=opus"});
		file = new window.File([blob], recordTime + ".ogg");
		audioUrl = window.URL.createObjectURL(blob);
		$("#audio-name").text(recordTime + ".ogg");
		console.log("录音结束");
	}
}, function(e) {alert(e);});

//媒体录音功能
function media(constrains, streamCall, errorClass) {
	if(navigator.getUserMedia){
		//旧版
        navigator.getUserMedia(constrains, streamCall, errorClass);
    } else if (navigator.webkitGetUserMedia){
        //webkit内核浏览器
        navigator.webkitGetUserMedia(constrains, streamCall, errorClass);
    } else if (navigator.mozGetUserMedia){
        //Firefox浏览器
        navagator.mozGetUserMedia(constrains, streamCall, errorClass);
    } else if (navigator.getUserMedia){
        //最新标准API
        navigator.mediaDevices.getUserMedia(constrains, streamCall, errorClass);
    } else {alert("当前浏览器不支持录音操作，请切换浏览器使用！");}
}

//播放语音
function playAudio() {
	$("#audio").attr("src", audioUrl);
	$("#audio")[0].play();
}

//提交分享
function submit() {
	let name = $("#name").val();
	let post = $("#post").val();
	if(!name) {alert("姓名必填！"); return;}
	if(!post) {alert("岗位必填！"); return;}

	let msg = $("#msg").val();
	if(!msg) {alert("个人心得必填！"); return;}
	if(msg.length > 100) {alert("个人心得不能超过100字！"); return;}

	//地点参数
	let place = $(".place input[name='select']:checked").val();
	let form = new FormData();
	form.append("place", place);
	if(file) {form.append("file", file);}
	form.append("name", name);
	form.append("post", post);
	form.append("msg", msg);
	
	//推送分享数据
	pushMsg(form, function(res) {
		if(res.code == 200) {location.href = "/read?place=" + place;}
		else {alert(res.msg);}
	});
}

//操作录音
function makeAudio(obj) {
	let val = $(obj).attr("value");
	if(val == "false") {startRec(obj);} else {stopRec(obj);}
}

//开始录音
function startRec(obj) {
	rec.start();
	$(obj).css("background-image", "url('/upload/img/stop.png')");
	$(obj).attr("value", "true");
}
//结束录音
function stopRec(obj) {
	rec.stop();
	$(obj).css("background-image", "url('/upload/img/audio.png')");
	$(obj).attr("value", "false");
}