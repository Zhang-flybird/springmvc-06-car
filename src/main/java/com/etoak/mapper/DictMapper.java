package com.etoak.mapper;

import java.util.List;

import com.etoak.bean.Dict;

public interface DictMapper {

	//根据groupId获取字典列表
	List<Dict> queryList(String groupId);
}
