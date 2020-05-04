package com.etoak.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoak.bean.Dict;
import com.etoak.service.DictService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/dict")
@Slf4j
public class DictController {

	@Autowired
	DictService dictService;
	
	@RequestMapping("/{groupId}")
	public List<Dict> queryList(@PathVariable("groupId") String groupId){
		log.info("groupId{}",groupId);
		return dictService.queryList(groupId);
	}
}
