package com.huidihao.handler.core.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.huidihao.handler.api.dto.QuestionDto;

/**
 * @作者 ChengShi
 * @日期 2022年12月3日
 * @版本 1.0
 * @描述 答题Mapper
 */
@Repository
public interface QuestionMapper {
	/**
	 * @描述 获取排序号
	 * @param name 答题人员
	 * @param post 岗位
	 * @return 排名
	 */
	@Select("select num from (select * from (select (@i:= @i + 1) as num, `name`, post from (select @i:= 0) t, question q order by score desc) "
			+ "t having t.`name` = #{name} and t.`post` = #{post}) t")
	public int getOrder(@Param("name") String name, @Param("post") String post);
	
	/**
	 * @描述 获取当前用户排名数据
	 * @param name 答题人员
	 * @param post 岗位
	 * @return 排名数据
	 */
	@Select("select * from (select (@i:= @i + 1) as num, `name`, score, post from (select @i:= 0) t, question q order by score desc) "
			+ "t having t.`name` = #{name} and t.`post` = #{post}")
	public QuestionDto get(@Param("name") String name, @Param("post") String post);
}
