//添加与获取排名
function getOrderAndPut(data, result) {
	$.post("/data/handler/question/getOrderAndPut", data, result);
}

//获取用户排名数据
function get(data, result) {
	$.post("/data/handler/question/get", data, result);
}

//获取所有岗位
function getPosts(place, result) {
	$.post("/data/handler/post/getPosts", {place: place}, result);
}