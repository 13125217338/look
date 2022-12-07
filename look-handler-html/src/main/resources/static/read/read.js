var seeId = null;
$(function() {
	let curHeight = document.body.scrollHeight;
	$("#main").css("height", curHeight + "px");
	$("#user").hide();
});

//查看个人分享页
function see(id, name, post, msg) {
	seeId = id;
	$("#read").hide(); $("#user").show();
	$("#main").css("background-image", "url('/read/img/user.jpg')");
	
	$("#name").text(name + " " + post);
	$("#msg").text(msg);
}

//播放音频
function playAudio() {
	$("#audio").attr("src", "/data/handler/audio/getAudio?id=" + seeId);
}

//生成海报
function openBill() {
	location.href = "/bill";
}