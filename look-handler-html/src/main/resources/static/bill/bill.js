$(function() {
	let curHeight = document.body.scrollHeight;
	$("#main").css("height", curHeight + "px");
	$("#bill").hide();
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
          "onMenuShareAppMessage"
        ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	wx.checkJsApi({
        jsApiList: [
          "onMenuShareAppMessage"
        ],
    	fail: res => {
     	  alert("您的微信版本太低，请使用最新的微信客户端！" + res);
    	}
  	});
	wx.ready(function(){
		wx.onMenuShareAppMessage({
		  title: '以“云”为媒 共读二十大', // 分享标题
		  desc: '奋进新征程，千里共湖湾', // 分享描述
		  link: 'https://www.huidihao.com', // 分享链接，该链接域名或路径必须与当前页面对应的公众号 JS 安全域名一致
		  imgUrl: 'https://www.huidihao.com/bill/img/shareIcon.png', // 分享图标
		  type: 'link', // 分享类型,music、video或link，不填默认为link
		  success: function () {
		    alert("分享成功！");
		  }
		});
		wx.onMenuShareTimeline({
		  title: '以“云”为媒 共读二十大', // 分享标题
		  link: 'https://www.huidihao.com', // 分享链接，该链接域名或路径必须与当前页面对应的公众号 JS 安全域名一致
		  imgUrl: 'https://www.huidihao.com/bill/img/shareIcon.png', // 分享图标
		  success: function () {
		 	alert("分享成功！");
		  }
		});
		wx.onMenuShareQQ({
		  title: '以“云”为媒 共读二十大', // 分享标题
		  desc: '奋进新征程，千里共湖湾', // 分享描述
		  link: 'https://www.huidihao.com', // 分享链接
		  imgUrl: 'https://www.huidihao.com/bill/img/shareIcon.png', // 分享图标
		  success: function () {
		  	alert("分享成功！");
		  }
		});
	});
	wx.error(function(res){
		alert("微信验证失败！" + res);
	});
}

//分享数据
function shareData() {
	//微信录音
	getConfig(function(res) {
		if(res.code == 200) {initWx(res.data);}
		else {alert(res.msg);}
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