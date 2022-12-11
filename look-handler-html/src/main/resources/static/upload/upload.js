var rec = null;
var file = null;
var recordTime = 0;
var audioUrl = null;
var isWX = false,localId = null;
$(function() {
	let curHeight = document.body.scrollHeight;
	$("#main").css("height", curHeight + "px");
	//获取岗位
	getPostsToPlace("观湖");
});

//通过地点获取不同部门
function getPostsToPlace(place) {
	//获取岗位
	getPosts(place, function(res) {
		if(res.code == 200) {renderPost(res.data);}
		else {alert("获取岗位失败！" + res.msg);}
	});
}

//渲染岗位
function renderPost(posts) {
	$("#post").html(""); //先置空
	$("#post").append('<option value="">&lt;下拉选择&gt;</option>');
	$.each(posts, function(i) {
		$("#post").append("<option value='" + posts[i] + "'>" + posts[i] + "</option>");
	});
}

//媒体设备获取
media({audio: true}, function(stream) {
	let chunks = [];
	rec = new MediaRecorder(stream);
	
	rec.onstart = function() {isWX = false; console.log("录音开始");}
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
    } else {
    	//微信录音
    	getConfig(function(res) {
			if(res.code == 200) {initWx(res.data);}
			else {alert(res.msg);}
		});
    }
}

//初始微信配置
function initWx(wxConfig) {
	//微信语音操作
	wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: wxConfig.appId, // 必填，公众号的唯一标识
	    timestamp: wxConfig.timestamp, // 必填，生成签名的时间戳
	    nonceStr: wxConfig.nonceStr, // 必填，生成签名的随机串
	    signature: wxConfig.signature,// 必填，签名，见附录1
	    jsApiList: [
          "checkJsApi",
          "startRecord", //开始录音接口
          "stopRecord", // 停止录音接口
          "uploadVoice", //上传语音接口
          "onVoiceRecordEnd" // 超过一分钟自动停止api
        ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	wx.checkJsApi({
        jsApiList: [
          "startRecord",
          "stopRecord",
          "uploadVoice",
          "onVoiceRecordEnd"
        ],
    	fail: res => {
     	  alert("您的微信版本太低，请使用最新的微信客户端！" + res);
    	}
  	});
	wx.ready(function(){
		isWX = true;
	});
	wx.error(function(res){
		alert("微信验证失败！" + res);
	});
	// 录音时间超过一分钟没有停止的时候会执行 complete 回调
	wx.onVoiceRecordEnd({
		complete: function (res) {
		    localId = res.localId;
		    $(obj).css("background-image", "url('/upload/img/audio.png')");
			$(obj).attr("value", "false");
		}
	});
}

//播放语音
function playAudio() {
	if(isWX) {
		wx.playVoice({
		  localId: localId // 需要播放的音频的本地ID，由 stopRecord 接口获得
		});
	} else {
		$("#audio").attr("src", audioUrl);
		$("#audio")[0].play();
	}
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
	form.append("contact", $("#contact").val());
	
	if(isWX && localId) {
		wx.uploadVoice({
		  localId: localId, // 需要上传的音频的本地ID，由 stopRecord 接口获得
		  isShowProgressTips: 1, // 默认为1，显示进度提示
		  success: function (res) {
		    form.append("audioId", res.serverId); // 返回音频的服务器端ID
		    pushAudioMsg(form, place);
		  }
		});
	} else {pushAudioMsg(form, place);}
}

//推送语音信息
function pushAudioMsg (form, place) {
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
	if(isWX) {wx.startRecord();} else {rec.start();}
	
	$(obj).css("background-image", "url('/upload/img/stop.png')");
	$(obj).attr("value", "true");
}
//结束录音
function stopRec(obj) {
	if(isWX) {
		wx.stopRecord({
		  success: function (res) {
		    recordTime = new Date().getTime();
		    localId = res.localId;
		    $("#audio-name").text(recordTime + ".ogg");
		  }
		});
	} else {rec.stop();}
	
	$(obj).css("background-image", "url('/upload/img/audio.png')");
	$(obj).attr("value", "false");
}