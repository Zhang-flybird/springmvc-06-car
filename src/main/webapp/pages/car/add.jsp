<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>车辆信息添加</title>
</head>
<body>
<div id="app">
	<form>
		品牌：<input type="text" v-model="form.brand"> <br>
		车系：<input type="text" v-model="form.series"> <br>
		价格：<input type="number" v-model="form.price"> <br>
		上牌时间：<input type="text" id="licensingTime" v-model="form.licensingTime"> <br>
		级别：<select  v-model="form.level">
			<option value="">==请选择==</option>
              <option :value="dict.value" v-for="(dict,index) in levelList" :key="dict.id">
                        {{ dict.name }}
                    </option>
              </select>
              <BR/>

         变速箱: <label v-for="(item, index) in gearboxList" :key="index">
        		<input type="radio" 
        			   :value="item.value" 
        		       v-model="form.gearbox">{{ item.name }}
        		</label> <BR>
        
       	排量：<select  v-model="form.outputVolume">
       		<option value="">==请选择==</option>
                 <option :value="dict.value" v-for="(dict,index) in outpuVolumeList" :key="dict.id">
                        {{ dict.name }}
                    </option>
              </select>
              <BR/>
        里程：<input type="number" v-model="form.mileage"> <br>
		归属地：<input type="text" v-model="form.location"> <br>
		图片：<input type="file" id="pic"  name="file">&nbsp;
			<input type="button" value="上传" @click="upload"> <br>   
		概述：<input type="text" v-model="form.summary"> <br>
		<input type="button" value="添加" @click="addCar">
	</form>


</div>
<script src="${pageContext.request.contextPath }/static/js/vue/vue.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/static/js/axios/axios.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/static/js/jquery/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/static/js/jquery/ajaxfileupload.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/static/js/layer/layer.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/static/js/laydate/laydate.js" type="text/javascript"></script>
<script type="text/javascript">
	const path = "${pageContext.request.contextPath}";
	//当月日期
	var day = new Date();
	day.setTime(day.getTime());
	var currentDate=day.getFullYear()+"-"+(day.getMonth()+1);
	//年月选择器
	laydate.render({
	  elem: '#licensingTime',
	  type: 'month',
	  max:currentDate,
	  done: function(value, date, endDate){
		 vm.form.licensingTime = value;
	  }
	});
	
	let vm = new Vue({
		el: '#app',
		data: {
			form: {
				brand: '',
				series: '',
				price: '',
				licensingTime: '',
				level: '',
				gearbox: '',
				outputVolume: '',
				mileage: '',
				location: '',
				pic: '',
				summary: ''

			},
			levelList: [],
			gearboxList: [],
			outpuVolumeList: []

		},
		 methods:{
			 
			upload(){
				let _this = this;
				$.ajaxFileUpload({
					fileElementId:'pic',//文件选择框的ID属性
					url:path+'/car/upload',
					type:"POST",
					data:'',//传到后台的参数，可以不写
					secureuri:false,//是否启安全提交 默认false
					dataType:'json',//服务器返回格式
					async:false,//同步的方式 true的话为异步
					success:function(data){
						_this.form.pic = data.data;
						layer.msg('添加成功',{icon: 6})
					},
					error:function(data,status,e){
						layer.msg('添加失败',{icon: 5})
					}
				});
			},
		
			//查询字典项
			queryDict(groupId){
				//如果传入为空 则默认查询level
				groupId = groupId ||'level'
				let url= path +'/dict/' + groupId
				//这个this是一个vue实例
				let _this = this;
				
				axios.get(url)
				.then(function(res){
					if(groupId =='level'){
						_this.levelList = res.data
					}else if(groupId =='gearbox'){
						_this.gearboxList = res.data
					}else{
						_this.outpuVolumeList = res.data
					}	
				}).catch(function(error){
					console.log(error)
				})
			},
			addCar() {
				let url =path +'/car'
				axios.post(url,this.form)
				.then(res =>{
					if(res.status === 200 && res.data.code === 200){
						layer.msg('添加成功',{icon: 6})
					}else{
						layer.msg('添加失败' + res.data.data,{icon: 5})
					}
				}).catch(error =>{
					console.log(error)
					layer.msg('添加失败',{icon: 5})
				}).finally(() =>{
					this.form =  {
							brand: '',
							series: '',
							price: '',
							licensingTime: '',
							level: '',
							gearbox: '',
							outputVolume: '',
							mileage: '',
							location: '',
							pic: '',
							summary: ''

						}
				});
			}
		},
		created(){
			this.queryDict('level');
			this.queryDict('gearbox');
			this.queryDict('output_volume');
		}
		
	});
</script>
</body>
</html>
