package com.etoak.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etoak.bean.User;
import com.etoak.mapper.UserMapper;
import com.etoak.service.UserService;
import com.etoak.vo.PageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserMapper userMapper;
	
	@Override
	public User queryById(int id) {
		return userMapper.queryById(id);
	}

	@Override
	public PageVo<User> queryList(int pageNum, int pageSize) {
		//设置分页条件
		PageHelper.startPage(pageNum,pageSize);
		//查询用户列表
		List<User> userList=userMapper.queryList();
		//创建pageInfo
		PageInfo<User> pageInfo = new PageInfo<>(userList);
		//返回结果
		return new PageVo<User>(pageInfo.getPageNum(),
				pageInfo.getPageSize(),
				userList,
				pageInfo.getTotal(),
				pageInfo.getPages());
	}

	@Override
	public User queryByNameAndPassword(String name, String password) {
		
		return userMapper.queryByNameAndPassword(name,password);
	}
	
	
	
}
