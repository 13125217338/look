//获取微信配置
function getConfig(result) {
	$.post("/data/handler/audio/getConfig", {url: location.href}, result);
}