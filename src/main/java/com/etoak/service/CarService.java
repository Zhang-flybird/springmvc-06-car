package com.etoak.service;

import java.util.List;

import com.etoak.bean.Car;
import com.etoak.vo.CarVo;
import com.etoak.vo.PageVo;

public interface CarService {

		//添加车辆信息
		int addCar(Car car);
		//查询所有品牌
		List<String> queryBrandList();

		//根据品牌查询车系列表
		List<String> querySeriesByBrand(String brand);
		
		//查询车辆列表
		PageVo<CarVo> queryList(int pageNum,int pageSize,CarVo carVo);
}
