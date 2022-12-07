package com.huidihao.handler.api.open;

import java.util.List;

/**
 * @作者 ChengShi
 * @日期 2022年12月7日
 * @版本 1.0
 * @描述 岗位Api
 */
public interface PostApi {
	/**
	 * @描述 获取所有岗位信息
	 * @return 所有岗位信息
	 */
	public List<String> getPosts();
}
