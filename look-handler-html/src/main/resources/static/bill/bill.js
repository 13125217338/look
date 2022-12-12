var timeCreate = null;
$(function() {
	let curHeight = document.body.scrollHeight;
	$("#main").css("height", curHeight + "px");
	$("#bill").hide();
	
	//手机浏览器长按事件
	$(".save").on("touchstart", function(e) {createImg();});
	$(".save").on("touchend", function(e) {cancleImg();});
	//微信录音
	getConfig(function(res) {
		if(res.code == 200) {initWx(res.data);}
		else {alert(res.msg);}
	});
});

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
          "onMenuShareAppMessage",
          "updateAppMessageShareData",
        ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	wx.checkJsApi({
        jsApiList: [
          "onMenuShareAppMessage",
          "updateAppMessageShareData",
        ],
    	fail: res => {
     	  alert("您的微信版本太低，请使用最新的微信客户端！" + res);
    	}
  	});
	wx.ready(function(){
		wx.updateAppMessageShareData({
		  title: '以“云”为媒 共读二十大', // 分享标题
		  desc: '奋进新征程，千里共湖湾', // 分享描述
		  link: 'https://www.huidihao.com', // 分享链接，该链接域名或路径必须与当前页面对应的公众号 JS 安全域名一致
		  imgUrl: 'https://www.huidihao.com/bill/img/shareIcon.png', // 分享图标
		  success: function (res) {
		 	console.log("分享成功！", res);
		  }
		})
	});
	wx.error(function(res){
		alert("微信验证失败！" + res);
	});
}

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

//创建图片
function createImg() {
	timeCreate = setTimeout(function() {
		html2canvas($("body")[0]).then(function(cvs) {
			var url = cvs.toDataURL(1);
			$("#downBill").attr("href", url);
			$("#downBill")[0].dispatchEvent(new MouseEvent("click"));
		});
	}, 1500);
}
//取消创建
function cancleImg() {
	console.log("结束");
	clearTimeout(timeCreate);
}