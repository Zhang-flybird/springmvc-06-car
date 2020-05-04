package com.etoak.vo;

import java.util.List;
import java.util.Map;

import com.etoak.bean.Car;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CarVo extends Car{
	
	private String levelName;
	private String gearboxName;
	private String outputVolumeName;
	//接受前端的价格范围参数
	@JsonIgnore  //carVo作为json对象返回前端是，不包含这个字段
	private List<String> priceList;
	
	//接受前端车龄的参数
	@JsonIgnore
	private String vehicleAge;
	@JsonIgnore
	private String startDate;
	@JsonIgnore
	private String endDate;
	
	@JsonIgnore
	private List<Map<String,Double>> priceMapList;
	
}
