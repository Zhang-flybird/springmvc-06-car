package com.etoak.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.etoak.bean.Car;
import com.etoak.mapper.CarMapper;
import com.etoak.service.CarService;
import com.etoak.vo.CarVo;
import com.etoak.vo.PageVo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class CarServiceImpl implements CarService {

	@Autowired
	CarMapper carMapper;
	
	
	@Override
	public int addCar(Car car) {
		
		return carMapper.addCar(car);
	}


	@Override
	public List<String> queryBrandList() {
		return carMapper.queryBrandList();
	}


	@Override
	public List<String> querySeriesByBrand(String brand) {
		return carMapper.querySeriesByBrand(brand);
	}


	@Override
	public PageVo<CarVo> queryList(int pageNum, int pageSize, CarVo carVo) {
		//处理价格范围
		this.handlePriceList(carVo);
		//处理车龄
		this.handleVehicleAge(carVo);
		//设置分页条件
		PageHelper.startPage(pageNum,pageSize);
		//执行查询
		List<CarVo> carList =carMapper.queryList(carVo);
		//创建pageInfo
		PageInfo<CarVo> pageInfo =new PageInfo<>(carList);
		//返回结果
		return new PageVo<CarVo>(pageInfo.getPageNum(),
				pageInfo.getPageSize(),
				carList,
				pageInfo.getTotal(),
				pageInfo.getPages());
	}
	//处理车龄
	private void handleVehicleAge(CarVo carVo) {
		String vehicleAge =carVo.getVehicleAge();
		if(StringUtils.isNotEmpty(vehicleAge)) {
			String[] vehicleAgeArray =vehicleAge.split("-");
			//1-3年为例子 
			//startData 是3 就是应该大于等于3年前
			//endDate 是1 就是应该小于等于1年前
			if(!StringUtils.equals("0", vehicleAgeArray[0])) {
				carVo.setEndDate(vehicleAgeArray[0]);
			}
			if(!StringUtils.equals("0", vehicleAgeArray[1])) {
				carVo.setStartDate(vehicleAgeArray[1]);
			}	
		}	
	}


	//处理价格范围
	private void handlePriceList(CarVo carVo) {
	    // ["0-3", "15-50"] => [{start=0, end=3}, {start=15, end=50}]
	    
	    List<Map<String, Double>> priceMapList = new ArrayList<>();
	    carVo.setPriceMapList(priceMapList);
	    
	    if (!CollectionUtils.isEmpty(carVo.getPriceList())) {
	        for (String priceRange : carVo.getPriceList()) {
	            String[] price = priceRange.split("-");
	            Map<String, Double> priceMap = new HashMap<>();
	            priceMap.put("start", Double.parseDouble(price[0]));
	            priceMap.put("end", Double.parseDouble(price[1]));
	            priceMapList.add(priceMap);
	        }
	        log.info("priceMapList{}",priceMapList);
	    }
	}

}

