$(function() {
	let curHeight = document.body.scrollHeight;
	$("#main").css("height", curHeight + "px");
	$("#bill").hide();
});

//生成海报
function createBill() {
	let msg = $("#msg").val();
	if(!msg) {alert("分享内容不能为空！"); return;}
	if(msg.length > 10) {alert("必须十字以内！"); return;}
	
	$("#create").hide(); $("#bill").show();
	$("#main").css("background-image", "url('/bill/img/bill.jpg')");
	$("#diyInfo").text(msg);
	$("#qrCode").qrcode(getQrConfig());
}

//获取二维码配置
function getQrConfig() {
	return {
		width: 60,
		height: 60,
		text: "https://" + location.host
	};
}