<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>列表</title>
<style>
#app {
  width: 950px;
  margin: 0 auto;
  height: 1100px;
  /* background-color: blue; */
}
#app ul {
  list-style: none;
  width: 1000px;
}

#app ul li {
	float: left;
	width: 220px;
	margin: 0px auto;
	margin-bottom: 50px;
	height: 230px;
	border: 1px;
}

#app ul li img {
	width: 200px;
	height: 200px;
	border-radius: 5px;
	box-shadow: 0px 0px 16px #CCC1C1;
	
}
.top {
	height: 200px;
	width: 930px;
	/* background-color: red; */
	margin: 0px auto;
}
.page {
	clear: both;
}
</style>
</head>
<body>
<div id="app">
	<div class="top">
		<form>
			品牌：<label v-for="(brand, index) in brandList" :key="index">
        	    	<input type="radio" :value="brand" 
        	    	v-model="conditions.brand"
        	    	@change="changeBrand"> {{brand}}
        	     </label> <BR>
        	车系：<label v-for="(series, index) in seriesList" :key="index + 100">
        	    	<input type="radio" :value="series" 
        	    	v-model="conditions.series"
        	    	@change="queryList(1)"> {{series}}
        	     </label> <BR>
      		价格：<label v-for="(price, index) in priceList" :key="index + 1000">
	      	    	<input type="checkbox" :value="price.value" 
	      	    	v-model="conditions.priceRange"
	      	    	@change="queryList(1)"> {{price.name}}
        	     </label> <BR>
        	车龄：<label v-for="(dict, index) in vehicleAgeList" :key="index + 200">
        	    	<input type="radio" :value="dict.value" 
        	    	v-model="conditions.vehicleAge"
        	    	@change="queryList(1)"> {{dict.name}}
        	     </label> <BR>
       	     级别：<select v-model="conditions.level" @change="queryList(1)">
		  	     <option value="">==请选择==</option>
                 <option v-for="(dict,index) in levelList" :value="dict.value" :key="dict.id">
                   {{ dict.name }}
                 </option>
              </select> &nbsp;&nbsp;
                        变速箱：<select  v-model="conditions.gearbox" @change="queryList(1)">
		  	     <option value="">==请选择==</option>
                 <option v-for="(dict,index) in gearboxList" :value="dict.value" :key="dict.id">
                   {{ dict.name }}
                 </option>
              </select> &nbsp;&nbsp;
                        排量：<select  v-model="conditions.outputVolume" @change="queryList(1)">
		  	     <option value="">==请选择==</option>
                 <option v-for="(dict,index) in outputVolumeList" :value="dict.value" :key="dict.id">
                   {{ dict.name }}
                 </option>
              </select>
            <BR/>
                        归属地：<input type="text" v-model="conditions.location" @blur="queryList(1)"> <br>
				<button type="button" @click="reset">重置</button>
		</form>
	</div>
	<ul>
		<li v-for="(car, index) in carList" :key="index">
			<img :src="path + car.pic">
			<p>
				<span>上牌年月：{{car.licensingTime}}</span> <BR>
				<span>价格：{{car.price}}/万元</span>
			</p>
		</li>
	</ul>
	<div class="page">
		<button type="button" @click="queryList(1)" :disabled="pageNum == 1">首页</button>&nbsp;
		<button type="button" @click="queryList(--pageNum)" :disabled="pageNum == 1">上一页</button>&nbsp;
		<button type="button" @click="queryList(++pageNum)" :disabled="pageNum == pageCount ||  pageCount == 0">下一页</button>&nbsp;
		<button type="button" @click="queryList(pageCount)" :disabled="pageNum == pageCount || pageCount == 0">尾页</button>
	</div>
</div>
<script src="${pageContext.request.contextPath }/static/js/vue/vue.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/static/js/axios/axios.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/static/js/jquery/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/static/js/layer/layer.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/static/js/laydate/laydate.js" type="text/javascript"></script>
<script type="text/javascript">
	const path = "${pageContext.request.contextPath}";
	
	let vm = new Vue({
		el: '#app',
		data: {
			path: path,
			carList: [], // 存储结果数据
			pageCount: 0, // 保存总页数
			pageNum: 1, // 页码
			pageSize: 12, // 每页显示多少数据
			
			brandList: [], // 保存品牌结果 
			seriesList: [], // 保存车系
			levelList: [],
			gearboxList:[],
			outputVolumeList:[],
			priceList: [{name: '20万以下', value: '0-20'}, 
					    {name: '20万~40万', value: '20-40'}, 
					    {name: '40万~80万', value: '40-80'},
					    {name: '80万以上', value: '80-100'}],
		    vehicleAgeList:[],
			conditions: { // 查询条件
				brand: '', 
				series: '',
				level: '',
				gearbox:'',
				outputVolume: '',
				location: '',
				priceRange: [],
				vehicleAge:'',
			}
		},
		methods: {
			reset(){
				this.conditions = { // 查询条件
					brand: '', 
					series: '',
					level: '',
					gearbox:'',
					outputVolume: '',
					location: '',
					priceRange: [],
					vehicleAge:'',
				}
			},
			// 查询级别、变速箱和排量
			queryDict(groupId) {
				axios.get(path + '/dict/' + groupId).then(res => {
					if(groupId === 'level') {
						this.levelList = res.data
					} else if(groupId === 'gearbox') {
						this.gearboxList = res.data
					} else if(groupId === 'output_volume'){
						this.outputVolumeList = res.data
					}else{
						this.vehicleAgeList = res.data
					}
				}).catch(error => {
					console.log(error)
				})
			},
			
			// 切换品牌，同时查询“车系和车辆列表数据”
			changeBrand() {
				this.conditions.series = '' // 切换品牌的时候，需要将上一次选择车系清空
				this.querySeries();
				this.queryList(1); 
			},
			// 查询车系
			querySeries() {
				axios.get(path + '/car/querySeries?brand=' + this.conditions.brand)
				.then(res => {
					// res = {"status":200, "data": {"code": 200, "msg":"success", "data": ["a", "b"]}, ...}
					this.seriesList = res.data.data
				}).catch(error => {
					console.log(error)
				})
			},
			// 查询品牌
			queryBrand() {
				let _this = this
				$.ajax({
					url: path + '/car/queryBrand',
					type: 'GET',
					dataType: 'json',
					success: res => {
						// res={"code": 200, "msg":"success", "data": ["a", "b"]}
						_this.brandList = res.data
					}
				});
			},
			
			queryList(pageNum, pageSize) {
				pageNum = pageNum || this.pageNum
				pageSize = pageSize || this.pageSize
				axios.get(path + '/car/list', {
					params: {
						pageNum: pageNum,
						pageSize: pageSize,
						brand: this.conditions.brand,
						series: this.conditions.series,
						level: this.conditions.level,
						gearbox: this.conditions.gearbox,
						outputVolume: this.conditions.outputVolume,
						location: this.conditions.location,
						priceList: this.conditions.priceRange.join(','),
						vehicleAge:this.conditions.vehicleAge
					}
				}).then(res => {
					// 给carList赋值
					this.carList = res.data.rows // rows
					this.pageNum = res.data.pageNum // 当前页码
					this.pageCount = res.data.pageCount // 总页数
				}).catch(error => {
					console.log(error)
				})
			}
		},
		// vue实例化后执行
		created() {
			this.queryBrand() // 查询品牌
			this.querySeries() // 查询车系
			this.queryList() // 查询车辆列表
			this.queryDict('level') //查询级别列表
			this.queryDict('gearbox') //查询变速箱列表
			this.queryDict('output_volume') //查询排量列表
			this.queryDict('vehicle_age') //查询车龄列表
		}
	});
	
</script>	
</body>
</html>

