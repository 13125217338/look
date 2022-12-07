//推送分享数据
function pushMsg(form, result) {
	$.ajax({
	  url: '/data/handler/audio/push',
	  type: 'POST',
	  data: form,
	  cache: false,
	  processData: false,
	  contentType: false,
	  success: result
	});
}

//获取所有岗位
function getPosts(result) {
	$.post("/data/handler/post/getPosts", null, result);
}