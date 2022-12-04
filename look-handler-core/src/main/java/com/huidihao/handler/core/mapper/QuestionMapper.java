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
	 * @return 排名
	 */
	@Select("select num from (select * from (select (@i:= @i + 1) as num, `name` from (select @i:= 0) t, question q order by score desc) t having t.`name` = #{name}) t")
	public int getOrder(@Param("name") String name);
	
	/**
	 * @描述 获取当前用户排名数据
	 * @param name 答题人员
	 * @return 排名数据
	 */
	@Select("select t.* from (select (@i:= @i + 1) as num, `name`, score from (select @i:= 0) t, question q order by score desc) t having t.`name` = #{name}")
	public QuestionDto get(@Param("name") String name);
}
