$(function() {
	let curHeight = document.body.scrollHeight;
	$("#main").css("height", curHeight + "px");
	$(".next").hide(); $("#index").hide(); $("#read").hide();
	
	var isBack = localStorage.getItem("isBack"); //特殊返回标志
	//开始加载
	if(isBack) {localStorage.removeItem("isBack"); next($(".next"));} else {startPlay();}
});

//开始播放
function startPlay() {
	setTimeout(function() {
		$("#main").css("background-image", "url('/main/img/main.png')");
		$(".next").show();
	}, 500)
}

//下一页
function next(obj) {
	$("#main").css("background-image", "url('/main/img/index.jpg')");
	$("#bg").attr("src", "/main/music/bg.mp3"); $("#bg")[0].play();
	$(obj).hide(); $("#index").show();
}


//打开外部链接
function openUrl(code) {
	let url = "#";
	switch(code) {
		case 1: url = "/study?type=1"; break;
		case 2: url = "/study?type=2"; break;
		case 3: url = "/question"; break;
		case 4: read(); return;
		case 5: url = "/upload"; break;
		default: alert("未找到Code对应Url，Code值》 " + code);
	}
	location.href = url;
}

//云共读
function read() {
	$("#main").css("background-image", "url('/main/img/read.jpg')");
	$("#index").hide(); $("#read").show();
}

//打开云共读页面
function openRead(place) {
	location.href = "/read?place=" + place;
}