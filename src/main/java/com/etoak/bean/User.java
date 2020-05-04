package com.etoak.bean;

import lombok.Data;

@Data
public class User {

		private Integer id;
		private String name;
		private String password;
		private String gender;
		private Integer age;
		//实际开发中用long
		private String birthday;
}
