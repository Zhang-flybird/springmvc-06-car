package com.etoak.service;

import com.etoak.bean.User;
import com.etoak.vo.PageVo;

public interface UserService {

	User queryById(int id);
	
	
	PageVo<User> queryList(int pageNum,int pageSize);


	User queryByNameAndPassword(String name, String password);
}
