//添加与获取排名
function getOrderAndPut(data, result) {
	$.post("/data/handler/question/getOrderAndPut", data, result);
}

//获取用户排名数据
function get(name, result) {
	$.post("/data/handler/question/get", {name: name}, result);
}