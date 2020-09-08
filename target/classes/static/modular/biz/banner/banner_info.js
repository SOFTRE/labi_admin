/**
 * 初始化banner管理详情对话框
 */
var BannerInfoDlg = {
    bannerInfoData : {},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '名称不能为空'
                }
            }
        },
        type: {
            validators: {
                notEmpty: {
                    message: '类型不能为空'
                }
            }
        },
        position: {
            validators: {
                notEmpty: {
                    message: '所属模块不能为空'
                }
            }
        },
        indexes: {
            validators: {
                notEmpty: {
                    message: 'banner位置不能为空'
                },
                regexp: {
                	//正则验证  
                    regexp: /^[+]{0,1}(\d+)$/,  
                    message: '请输入正整数'  
                }
            }
        },
        seqNum: {
            validators: {
                notEmpty: {
                    message: '排序号不能为空'
                },
                regexp: {
                	//正则验证  
                    regexp: /^[+]{0,1}(\d+)$/,  
                    message: '请输入正整数'  
                }
            }
        }
    }
};
/**
 * 验证数据是否为空
 */
BannerInfoDlg.validate = function () {
    $('#bannerInfoForm').data("bootstrapValidator").resetForm();
    $('#bannerInfoForm').bootstrapValidator('validate');
    return $("#bannerInfoForm").data('bootstrapValidator').isValid();
}
/**
 * 清除数据
 */
BannerInfoDlg.clearData = function() {
    this.bannerInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BannerInfoDlg.set = function(key, val) {
    this.bannerInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * banner位置显示 隐藏
 */
BannerInfoDlg.bannerStatus = function(key, val) {
    var position = $("#position").find("option:selected").text();
    if(position=="首页"){
    	$("#div_index").show();
    }else{
    	$("#div_index").hide();
    }
}
/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BannerInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BannerInfoDlg.close = function() {
    parent.layer.close(window.parent.Banner.layerIndex);
}

/**
 * 收集数据
 */
BannerInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('time')
    .set('img')
    .set('type')
    .set('gotoInfo')
    .set('ifOnline')
    .set('position')
    .set('indexes')
    .set('seqNum')
    .set('createtime')
    .set('oprtime');
}

/**
 * 提交添加
 */
BannerInfoDlg.addSubmit = function() {

	var val_online = $('input[name="redioCheck"]:checked ').val();
	$("#ifOnline").val(val_online);
	
    this.clearData();
    this.collectData();
    //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/banner/add", function(data){
        Labi.success("添加成功!");
        window.parent.Banner.table.refresh();
        BannerInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bannerInfoData);
    ajax.start();
}

/**
 * ----------------详情跳转（搜索按钮）-----------
 */
BannerInfoDlg.jumpSearch = function () {
	//var type = $("#type").find("option:selected").text();
	var type =$("#type").val();//类型
	var Keyword=$("#Keyword").val();//检索关键字
	
	if(Keyword=="" || Keyword==null){
		Labi.error("请输入关键字后查询!");
		return;
	}
	if(type ==null ||  type==""){
		Labi.success("请选择类型!");
	}else{
		var ajax = new $ax(Labi.ctxPath + "/banner/geturl", function (data) {
			console.log(data);
            console.log(type);
            $("#gotoInfo").empty(); 
            //检测数据源是否为空
            if(data.length==0){
            		$("#gotoInfo").append($("<option/>").text("暂无数据").attr("value",""));
               
            }else{
            	$.each(data, function(i, item){
            		if(item.name!="" && item.name!=null){
            			//教练、商品、课程、视频
            			$("#gotoInfo").append($("<option/>").text(item.name).attr("value",item.id));
            		}else{
            			//拉比展示
            			$("#gotoInfo").append($("<option/>").text(item.title).attr("value",item.id));
            		}
               }); 
            }
            
        }, function (data) {
            Labi.error("查询异常!" + data.responseJSON.message + "!");
        });
        ajax.set("type",type);
        ajax.set("Keyword",Keyword);
        ajax.start();
		//Labi.success("chaxun!");
	}
	
}

/**
 * 提交修改
 */
BannerInfoDlg.editSubmit = function() {
	
	var val_online = $('input[name="redioCheck"]:checked ').val();
	$("#ifOnline").val(val_online);
	
    this.clearData();
    this.collectData();

    //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/banner/update", function(data){
        Labi.success("修改成功!");
        window.parent.Banner.table.refresh();
        BannerInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bannerInfoData);
    ajax.start();
}

$(function() {
    //radio初始化
    $('input.radio').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
        increaseArea: '20%' // optional
    });
    
    //修改时如果所属模块为首页 banner位置隐藏
    var positionId = $('#position').find('option:eq(0)').data('id');
    if(positionId !=1){
    	$("#div_index").hide()
    }
    //$('#coachLeve option[value='+coachGradeId+']').attr('selected',true);
    
    //参数校验
    Labi.initValidator("bannerInfoForm", BannerInfoDlg.validateFields);
	// 初始化头像上传
    var avatarUp = new $WebUpload("img",Labi.destDir.banner);
    avatarUp.init();
    
});
