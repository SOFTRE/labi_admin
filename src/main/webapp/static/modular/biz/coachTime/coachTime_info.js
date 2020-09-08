/**
 * 初始化预约缴费管理详情对话框
 */
var CoachTimeInfoDlg = {
    coachTimeInfoData : {},
    validateFields: {
    	 startTime: {
             validators: {
                 notEmpty: {
                     message: '请选择开始时间'
                 }
             }
         },
         endTime: {
             validators: {
                 notEmpty: {
                     message: '请选择结束时间'
                 }
             }
         },
    }
};


CoachTimeInfoDlg.validate = function () {
    $('#CoachTimeInfoForm').data("bootstrapValidator").resetForm();
    $('#CoachTimeInfoForm').bootstrapValidator('validate');
    return $("#CoachTimeInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 清除数据
 */
CoachTimeInfoDlg.clearData = function() {
    this.coachTimeInfoData = {};
}

/**
 * 点击选择会员
 */
CoachTimeInfoDlg.selUser = function () {
    var index = layer.open({
        type: 2,
        title: '评价详情',
        area: ['800px', '480px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/coachTime/coaclist'
    });
    this.layerIndex = index;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CoachTimeInfoDlg.set = function(key, val) {
    this.coachTimeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CoachTimeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CoachTimeInfoDlg.close = function() {
    parent.layer.close(window.parent.CoachTime.layerIndex);
}

/**
 * 收集数据
 */
CoachTimeInfoDlg.collectData = function() {
    this
    .set('id')
    .set('coachId')
    .set('userId')
    .set('coachName')
    .set('startTime')
    .set('endTime')
    .set('createtime');
}

/***
 * 教练时间添加删除
 * day 日期
 * index 下标
 */
CoachTimeInfoDlg.timtAdd= function(day,index){
		var sTime="";
		var eTime="";
		
	    var str =day+"-"+index;//当前按钮id
	    var cssflg = $("#"+str).attr("cssflg");//当前按钮自定义属性
	    
	    var front=day+"-"+(index-1);//前一个按钮id
	    var frontflg = $("#"+front).attr("cssflg");//前一个按钮自定义属性
	    
		var next=day+"-"+(index+1);//后一个按钮id
		var nextflg = $("#"+next).attr("cssflg");//后一个按钮自定义属性
		
		//alert(frontflg + "= "+cssflg+" =" +nextflg);
		//判断当前时间是否可选
		if(frontflg!="0" && nextflg!="0" && cssflg=="0"){
			 Labi.success("当前时间不可选!");
			 return false;
		}
	    
	    if(cssflg=="0"){
	    	//向后渲染
			$("#"+str).removeClass("btn-white");
			$("#"+str).addClass("btn-danger");
			
			var str2=day+"-"+(index+1);
			$("#"+str2).removeClass("btn-white");
			$("#"+str2).addClass("btn-danger");
	
			sTime=$("#"+str).text();
			eTime=$("#"+str2).text();
			sTime=day+" "+sTime.substring(0,5)+":00"
			eTime =day+" "+eTime.substring(6,11)+":00"
			//alert(sTime +"==="+eTime);
			
			//$("#dayTime").val(day);
			$("#shhTime").val(sTime);
			$("#ehhTime").val(eTime);
			
			//自定义属性赋值
			$("#"+str).attr("cssflg",str);
			$("#"+str2).attr("cssflg",str);
	    }else if(cssflg=="1"){
	    	//教练时间修改时处理逻辑
	    	//清除当前按钮样式
			$("#"+str).removeClass("btn-danger");
			$("#"+str).addClass("btn-white");
			$("#"+str).attr("cssflg","0");
			
	    	//连续性 情况
	    	if(frontflg=="1" && nextflg=="1"){
	    		 var btntxt=$("#"+str).text();
	    		 var mm=btntxt.substring(3,5)
	    		 //alert(btntxt +"      "+mm );
	    		//如果默认选中 判断是整点还是半点
	    		if(mm=="00"){
	    			//如果是整点 向后
	    			//清除之后样式
					$("#"+next).removeClass("btn-danger");
					$("#"+next).addClass("btn-white");
					$("#"+next).attr("cssflg","0");
	    			
					sTime=$("#"+str).text().substring(0,5)+":00";
					eTime=$("#"+next).text().substring(6,11)+":00";
					//alert("连续行   后  "+ " sTime: "+sTime+"  eTime: "+eTime);
	    		}else{
	    			//如果是半点向前
	    			//清除之前样式
					$("#"+front).removeClass("btn-danger");
					$("#"+front).addClass("btn-white");
					$("#"+front).attr("cssflg","0");
					
					sTime=$("#"+front).text().substring(0,5)+":00";
					eTime=$("#"+str).text().substring(6,11)+":00";
					//alert("连续行    前  "+ " sTime: "+sTime+"  eTime: "+eTime);
	    		}
	    	}else{
	    		//非连续性情况
		    	if(frontflg=="1"){
		    		//清除之前样式
					$("#"+front).removeClass("btn-danger");
					$("#"+front).addClass("btn-white");
					$("#"+front).attr("cssflg","0");
					
					sTime=$("#"+front).text().substring(0,5)+":00";
					eTime=$("#"+str).text().substring(6,11)+":00";
					//alert("非连续行  前  "+ " sTime: "+sTime+"  eTime: "+eTime);
		    	}
		    	
		    	if(nextflg=="1"){
		    		//中间有间隔的情况 清除之后样式 str front
					$("#"+next).removeClass("btn-danger");
					$("#"+next).addClass("btn-white");
					$("#"+next).attr("cssflg","0");
					
					sTime=$("#"+str).text().substring(0,5)+":00";
					eTime=$("#"+next).text().substring(6,11)+":00";
					//alert("非连续行  后  "+ " sTime: "+sTime+"  eTime: "+eTime);
		    	}
	    	}
	    	  
	    	$("#shhTime").val(day+" "+sTime);
			$("#ehhTime").val(day+" "+eTime);
	    }else{
	    	//清除当前按钮样式
			$("#"+str).removeClass("btn-danger");
			$("#"+str).addClass("btn-white");
			//自定义属性赋值
			$("#"+str).attr("cssflg","0");
			
			//判断结束时间是在 之前 还是之后
			//var fontstr =day+"-"+(index-1);//前一个按钮id
		    //var fontcssflg = $("#"+fontstr).attr("cssflg");//当前按钮自定义属性
			if(cssflg==frontflg){
				//之前样式
				$("#"+front).removeClass("btn-danger");
				$("#"+front).addClass("btn-white");
				//自定义属性赋值
				$("#"+front).attr("cssflg","0");
				sTime=$("#"+next).text();
				eTime=$("#"+str).text();
			}
			
			//var nextstr =day+"-"+(index+1);//当前按钮id
		    //var nextcssflg = $("#"+nextstr).attr("cssflg");//当前按钮自定义属性
			if(cssflg==nextflg ){
				//之后样式
				$("#"+next).removeClass("btn-danger");
				$("#"+next).addClass("btn-white");
				//自定义属性赋值
				$("#"+next).attr("cssflg","0");
				sTime=$("#"+str).text();
				eTime=$("#"+next).text();
				//alert("之后");
			}
			//alert(frontflg + "="+(cssflg+"-"+index));
			sTime= day+" "+sTime.substring(0,5)+":00";
			eTime =day+" "+eTime.substring(6,11)+":00";
			//alert(sTime +"||||"+eTime);
			$("#shhTime").val(sTime);
			$("#ehhTime").val(eTime);

	    }

	var coachId= $("#coachId").val();//教练id
	var startTime =$("#shhTime").val();//开始时间
	var endTime = $("#ehhTime").val();//结束时间
	
	console.log("coachId "+coachId +"startTime:"+startTime +"endTime:"+endTime+" cssflg:"+cssflg);
    
    var ajax = new $ax(Labi.ctxPath + "/coachTime/addtime", function(data){
        Labi.success("操作成功!");
        console.log(data);
        //window.parent.CoachTime.table.refresh();
        //CoachTimeInfoDlg.close();
    },function(data){
        Labi.error("操作失败!" + data.responseJSON.message + "!");
    });
    ajax.set("coachId",coachId);//教练id
    ajax.set("startTime",startTime);//开始时间
    ajax.set("endTime",endTime);//结束时间
    ajax.set("cssflg",cssflg);//0添加、 1删除
    ajax.start();
}
/**
 * 提交添加
 */
CoachTimeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/coachTime/add", function(data){
        Labi.success("添加成功!");
        window.parent.coachTime.table.refresh();
        CoachTimeInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.coachTimeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CoachTimeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/coachTime/update", function(data){
        Labi.success("修改成功!");
        window.parent.CoachTime.table.refresh();
        CoachTimeInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.coachTimeInfoData);
    ajax.start();
}

$(function() {

	//参数校验
    Labi.initValidator("CoachTimeInfoForm", CoachTimeInfoDlg.validateFields);
    
});
