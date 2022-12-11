var problemsList = [];
var result = {}, resultMapping = [];
var curIndex = 0, maxIndex = 20;
var makeInfo = {};
$(function() {
	let curHeight = document.body.scrollHeight;
	$("#main").css("height", curHeight + "px");
	$("#ranking").hide(); $("#make").hide(); $("#finish").hide();
	//初始观湖
	getPostsToPlace("观湖");
	//获取答题数据
	$.get("/question/question.txt", function(res) {
		problemsList = JSON.parse(res).problemsList;
	});
});

//通过地点获取不同部门
function getPostsToPlace(place) {
	//获取岗位
	getPosts(place, function(res) {
		if(res.code == 200) {renderPost(res.data);}
		else {alert("获取岗位失败！" + res.msg);}
	});
}

//渲染岗位
function renderPost(posts) {
	$("#post").html(""); //先置空
	$("#post").append('<option value="">&lt;下拉选择&gt;</option>');
	$.each(posts, function(i) {
		$("#post").append("<option value='" + posts[i] + "'>" + posts[i] + "</option>");
	});
}

//随机20题
function randow20() {
	for(let i = 0; i < maxIndex; i++) {
		let num = Math.floor(Math.random() * problemsList.length);
		while(result[num]) {
			if(++num > problemsList.length) {num = 0;}
		}
		resultMapping.push(num);
		//记录结果
		result[num] = problemsList[num];
		result[num].aStr = sortGetStr(result[num].answer);
	}
}

//提交姓名
function submit() {
	makeInfo.place = $(".in-select input[name='select']:checked").val();
	makeInfo.name = $("#inName").val();
	makeInfo.post = $("#post").val();
	makeInfo.contact = $("#contact").val();
	makeInfo.time = new Date().getTime();
	if(!makeInfo.name) {alert("姓名必填！"); return;}
	if(!makeInfo.post) {alert("岗位必填！"); return;}
	
	//获取当前用户排名数据
	get({name: makeInfo.name, post: makeInfo.post}, function(res) {
		if(res.code == 200) {
			$("#rank-score").text("你的最高分：" + (res.data ? res.data.score : "-") + "分");
			$("#rank-num").text("目前排名：" + (res.data ? res.data.num : "-") + "名");
			$("#user").hide(); $("#ranking").show();
			$("#main").css("background-image", "url('/question/img/ranking.jpg')");
		} else {alert(res.msg);}
	});
}

//开始答题
function start() {
	$("#ranking").hide(); $("#make").show();
	$("#main").css("background-image", "url('/question/img/make.jpg')");
	//随机20题
	randow20(); $("#size").text("/" + maxIndex);
	curIndex = 0;
	let recordTime = new Date().getTime();
	let zMaxTime = 1000 * 60 * 20; //20分钟最大
	$("#m").text("20"); //初始展示时间 - 分钟
	setInterval(function() {
		let zTime = new Date().getTime() - recordTime;
		if(zTime > zMaxTime) {alert("当前答题已超时！"); location.reload();}
		let lastTime =  zMaxTime - zTime;
		//分钟字符串
		let m = Math.floor(lastTime / (1000 * 60)) + "";
		$("#m").text((m.length == 1 ? ("0" + m) : m));
		//秒字符串
		let s = Math.floor((lastTime % (1000 * 60)) / 1000) + "";
		$("#s").text((s.length == 1 ? ("0" + s) : s));
	}, 1000);
	render(); //渲染题目
}

//上一题
function last() {
	if(curIndex == 0) {alert("当前为第一页，无法再返回！"); return;}
	curIndex--;
	render(); //渲染题目
}

//排序数组并获得数组拼接值
function sortGetStr(arr) {
	arr = arr.sort();
	let str = ""; 
	for(let i in arr) {str += arr[i];}
	return str;
}

//完成答题
function finish() {
	$("#make").hide(); $("#finish").show();
	$("#main").css("background-image", "url('/question/img/result.jpg')");
	let score = 0; //答题分数
	$.each(resultMapping, function(i) {
		score += (result[resultMapping[i]].result ? 5 : 0);
	});
	
	$("#score").text(score + "分");
	$("#score-tip").text("恭喜（" + makeInfo.name + "）在本次答题获得" + score + "分！");
	
	//添加与获取排名
	getOrderAndPut({name: makeInfo.name, post: makeInfo.post, score: score, place: makeInfo.place, contact: makeInfo.contact}, function(res) {
		if(res.code == 200) {$("#order").text("排名第" + res.data + "名");}
		else {alert(res.msg);}
	});
}

//下一页
function next() {
	if(curIndex >= (maxIndex - 1)) {finish(); return;}
	
	// 设置答案是否正确
	let vals = $("input[name='result']:checked");
	let values = [];
	$.each(vals, function(i) {values.push(vals[i].value);});
	if(values.length == 0) {alert("未选择任何答案！"); return;}
	
	//如果一样则成功
	result[resultMapping[curIndex]].result = sortGetStr(values) == result[resultMapping[curIndex]].aStr;
	curIndex++;
	render(); //渲染题目
}

//渲染题目
function render() {
	$("#resole").text(result[resultMapping[curIndex]].problems);
	let ot = result[resultMapping[curIndex]].option;
	let isMany = result[resultMapping[curIndex]].answer.length > 1;
	$("#result").html(""); //先置空
	//循环配置生成
	for(let i in ot) {
		//取出对象key
		let key = Object.keys(ot[i])[0];
		//通过key，value生成html
		let opHtml = "<div onclick=\"checkBt(this, " + isMany + ")\">" +
			"<input type=" + (isMany ? "'checkbox'" : "'radio'") + "name=\"result\" value=\"" + key + "\" />" +
			"<span>" + key + "." + ot[i][key] + "</span></div>";
		$("#result").append(opHtml);
	}
	$("#num").text(curIndex + 1);
}

//选中
function checkBt(obj, isMany) {
	if(isMany) {$(obj).find("input").click();}
	else {$(obj).find("input").prop("checked", "checked");}
}