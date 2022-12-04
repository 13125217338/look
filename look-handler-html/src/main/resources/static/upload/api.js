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