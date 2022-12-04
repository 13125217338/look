$(function() {
	let curHeight = document.body.scrollHeight;
	$("#main").css("height", curHeight + "px");
});

//打开外部链接
function openUrl(code) {
	let url = "#";
	switch(code) {
		case 1: url = "https://article.xuexi.cn/articles/index.html?art_id=17478440677105407928&item_id=17478440677105407928&reedit_timestamp=1666774025000&to_audit_timestamp=2022-10-26+16%3A47%3A05&study_style_id=feeds_default&ref_read_id=641feee7-0404-468e-9734-5da068b1507c&pid=76272939092196933&ptype=100&reco_id=101f7cfde054c0a8229f000b&study_comment_disable=0&source=share&share_to=wx_single"; break;
		case 2: url = "https://article.xuexi.cn/articles/index.html?art_id=9485872833813694142&item_id=9485872833813694142&study_style_id=feeds_default&t=1666926837007&showmenu=false&ref_read_id=26e6555a-2b73-49bb-91ce-b82869f9ba80_1669717482584&pid=&ptype=-1&source=share&share_to=copylink"; break;
		case 3: url = "https://article.xuexi.cn/articles/index.html?art_id=11316459587918335475&item_id=11316459587918335475&to_audit_timestamp=2022-11-16%2007%3A54%3A43&study_style_id=feeds_default&t=1668731701647&showmenu=false&ref_read_id=74ffbaed-e0bc-4b10-8bc3-757504cfc048_1669519864482&pid=&ptype=-1&source=share&share_to=wx_single"; break;
		case 4: url = "https://article.xuexi.cn/articles/index.html?art_id=5630486294768145041&item_id=5630486294768145041&to_audit_timestamp=2022-11-16%2020%3A37%3A42&study_style_id=feeds_default&t=1668828722475&showmenu=false&ref_read_id=0608307d-e698-4857-97c3-0705d7e24719_1669517594004&pid=&ptype=-1&source=share&share_to=wx_single"; break;
		case 5: url = "https://article.xuexi.cn/articles/index.html?art_id=1748277619692640999&item_id=1748277619692640999&study_style_id=feeds_default&t=1616054611226&showmenu=false&ref_read_id=72d1f52a-a38e-4a4f-abe4-b0df920a1f27_1669517892806&pid=&ptype=-1&source=share&share_to=wx_single"; break;
		case 6: url = "https://article.xuexi.cn/articles/index.html?art_id=9485872833813694142&item_id=9485872833813694142&study_style_id=feeds_default&t=1666926837007&showmenu=false&ref_read_id=72d1f52a-a38e-4a4f-abe4-b0df920a1f27_1669517892806&pid=&ptype=-1&source=share&share_to=wx_single"; break;
		case 7: url = "https://article.xuexi.cn/articles/index.html?art_id=15767150740823513650&item_id=15767150740823513650&study_style_id=feeds_default&t=1591940709334&showmenu=false&ref_read_id=72d1f52a-a38e-4a4f-abe4-b0df920a1f27_1669517892806&pid=&ptype=-1&source=share&share_to=wx_single"; break;
		case 8: url = "https://www.12371.cn/2021/02/04/ARTI1612413834774971.shtml"; break;
		case 9: url = "https://www.12371.cn/2021/08/30/ARTI1630280234272979.shtml"; break;
		case 10: url = "https://www.12371.cn/2021/08/30/ARTI1630303484113474.shtml"; break;
		case 11: url = "https://appimg.allcitysz.com/template/displayTemplatev1/dist/index.html?id=645896&columnId=0#/newsDetail/645896/0?isShare=true&userName=鑾瑰効拢&deviceId=ee348b8d5ba75a54"; break;
		case 12: url = "https://appimg.allcitysz.com/template/displayTemplatev1/dist/index.html?id=659967&columnId=0#/newsDetail/659967/0?isShare=true&userName=鑾瑰効拢&deviceId=ee348b8d5ba75a54"; break;
		case 13: url = "https://appimg.allcitysz.com/template/displayTemplatev1/dist/index.html?id=652416&columnId=0#/newsDetail/652416/0?isShare=true&userName=鑾瑰効拢&deviceId=ee348b8d5ba75a54"; break;
		case 14: url = "https://appimg.allcitysz.com/template/displayTemplatev1/dist/index.html?id=766999&columnId=0#/newsDetail/766999/0?isShare=true&userName=鑾瑰効拢&deviceId=ee348b8d5ba75a54"; break;
		default: alert("未找到Code对应Url，Code值》 " + code);
	}
	window.open(url);
}