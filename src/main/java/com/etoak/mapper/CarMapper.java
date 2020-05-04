package com.etoak.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.etoak.bean.Car;
import com.etoak.vo.CarVo;

public interface CarMapper {
	
	//添加车辆信息
	int addCar(Car car);
	
	//查询所有品牌
	List<String> queryBrandList();

	//根据品牌查询车系列表
	List<String> querySeriesByBrand(@Param("brand") String brand);
	
	//查询车辆列表
	List<CarVo> queryList(CarVo carVo); 
}
