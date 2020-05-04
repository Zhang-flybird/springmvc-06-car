package com.etoak.bean;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;


@Data
public class Car {

	private Integer id;
	//品牌
	@NotNull(message = "品牌必填") //not null
	@NotBlank(message = "品牌必填")  //not ""
	@NotEmpty(message = "品牌必填")
	private String brand;
	//车系
	@NotNull(message = "车系必填")
	@NotBlank(message = "车系必填")
	private String series;
	
	@NotNull(message = "价格必填")
	@Min(value = 1, message = "最小是1万")
	@Max(value = 100, message = "不能超过100万")
	private Double price;
	//上牌时间
	private String licensingTime;
	private String level;
	private String gearbox;
	private String outputVolume;
	//里程数
	private Double mileage;
	private String location;
	private String pic;
	private String summary;
	private String createTime;
}
