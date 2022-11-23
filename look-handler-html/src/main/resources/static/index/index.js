var rec = null;
var md = navigator.mediaDevices;
if(!md) {alert("当前浏览器不支持录音操作，请切换浏览器使用！");}

md.getUserMedia({audio: true}).then(stream => {
	var chunks = [];
	rec = new MediaRecorder(stream);
	
	rec.onstart = function() {console.log("录音开始");}
	rec.ondataavailable = function(e) {
		console.log("推送语音数据");
		chunks.push(e.data);
	}
	
	rec.onstop = function() {
		var form = new FormData();
		var blob = new Blob(chunks, {type: "audio/ogg; codecs=opus"});
		var file = new window.File([blob], "ad.ogg");
		form.append("file", file);
		$.ajax({
		  url: '/data/handler/audio/push',
		  type: 'POST',
		  data: form,
		  cache: false,
		  processData: false,
		  contentType: false,
		  success: function(res) {
			if(res.code == 200) {alert("语音已推送成功！"); location.reload();}
			else {alert(res.msg);}
		  }
		});
	}
})

function startRec() {rec.start();}
function stopRec() {rec.stop();}

//听语音
function listening(id) {
	$("#AL").attr("src", "/data/handler/audio/getAudio?id=" + id);
}