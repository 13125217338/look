var rec = null;
var md = navigator.mediaDevices;
var file = null;
var recordTime = 0;
if(!md) {alert("当前浏览器不支持录音操作，请切换浏览器使用！");}
$(function() {
	let curHeight = document.body.scrollHeight;
	$("#main").css("height", curHeight + "px");
});

//媒体录音功能
md.getUserMedia({audio: true}).then(stream => {
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
		console.log("录音结束");
	}
})

//提交分享
function submit() {
	let msg = $("#msg").val();
	if(!msg) {alert("个人心得必填！"); return;}
	if(msg.length > 100) {alert("个人心得不能超过100字！"); return;}

	//地点参数
	let place = $(".place input[name='select']:checked").val();
	let form = new FormData();
	form.append("place", place);
	if(file) {form.append("file", file);}
	form.append("name", $("#name").val());
	form.append("post", $("#post").val());
	form.append("msg", msg);
	
	//推送分享数据
	pushMsg(form, function(res) {
		if(res.code == 200) {location.href = "/read?place=" + place;}
		else {alert(res.msg);}
	});
}

//开始录音
function startRec() {rec.start();}
//结束录音
function stopRec() {
	rec.stop();
	$("#audio-name").text(recordTime + ".ogg");
}