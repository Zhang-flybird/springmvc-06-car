package com.etoak.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.etoak.bean.Car;
import com.etoak.exception.ParamException;
import com.etoak.service.impl.CarServiceImpl;
import com.etoak.vo.CarVo;
import com.etoak.vo.PageVo;
import com.etoak.vo.ResultVo;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/car")
@Slf4j
@PropertySource(value = "classpath:upload.properties")//读取配置文件
public class CarController {

	@Value("${file.upload}") // d:/upload
	private String uploadDir;

	@Value("${file.mapping}") // /pics/
	private String picMapping;
	@Autowired
	CarServiceImpl carService;
	
	
	//添加页面
	@RequestMapping("/toAdd")
	public String toAdd() {
		return "car/add";
	}
	
	
	
	@RequestMapping("/upload")
	@ResponseBody
	public ResultVo upload(MultipartFile file) throws IllegalStateException, IOException {
		// 跨服务器上传; 或者文件可能传到分布式文件系统：阿里的FastDFS, GlusterFS, 七牛云...
		// 这里直接上传到本地文件系统
		//originalFilename：原来的文件名字
		String originalFilename = file.getOriginalFilename();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		String newFilename = uuid + "_" + originalFilename;

		// 创建一个文件夹
		File fileDir = new File(uploadDir);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}

		// 创建一个文件
		File newFile = new File(fileDir, newFilename);

		// 执行上传
		file.transferTo(newFile);

		// 图片的访问路径: /pics/xxx.jpg
		String path = picMapping + newFilename;
		return ResultVo.success(path);
	}

	//添加车辆信息 Rest方式 
	@PostMapping("")
	@ResponseBody
    @CrossOrigin(allowCredentials = "true", //
    allowedHeaders = "*", //
    origins = "*", //
    maxAge = 1)

	public ResultVo addCar(@Valid @RequestBody Car car,BindingResult bindingResult) {
		
		// 获取所有的校验错误信息
		List<ObjectError> allErrors = bindingResult.getAllErrors();
		if (!CollectionUtils.isEmpty(allErrors)) {
		    // 走到这里说明验证未通过
		    StringBuffer buf = new StringBuffer();
		    for (ObjectError error : allErrors) {
		        // 校验的消息
		        String message = error.getDefaultMessage();
		        buf.append(message).append(";");
		    }
		    //return ResultVo.failed(buf.toString());
		    //校验之后抛出异常
		    throw new ParamException("参数校验异常" + buf.toString());
		}
		
		carService.addCar(car);
		return ResultVo.success("");
	}
	
	//查询品牌列表
	@GetMapping("/queryBrand")
	@ResponseBody
	public ResultVo queryBrand(){
		return ResultVo.success(carService.queryBrandList());
	}
	
	//根据品牌查车系
	@GetMapping("/querySeries")
	@ResponseBody
	public ResultVo querySeries(String brand) {
		return ResultVo.success(carService.querySeriesByBrand(brand));
	}
	
	@GetMapping("/list")
	@ResponseBody
	public PageVo<CarVo> list(
			@RequestParam(required =false,defaultValue ="1") int pageNum,
			@RequestParam(required =false,defaultValue ="10") int pageSize,
			CarVo carVo){
		log.info("pageNum{},pageSize{},carVo{}",pageNum,pageSize,carVo);
		return carService.queryList(pageNum, pageSize, carVo);
	}
	@RequestMapping("/toList")
	public String toList() {
		return "car/list";
	} 
	
	
}
