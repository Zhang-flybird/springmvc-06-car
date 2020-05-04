package com.etoak.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.etoak.bean.User;

/**
 * MyBatis的Mapper接口 ，通过mapperScanerConfig
 * */
public interface UserMapper {
	
	//根据id查询
	User queryById(int id);
	
	//查询用户列表
	List<User> queryList();

	User queryByNameAndPassword(@Param("name") String name,@Param("password") String password);
	
	
}
