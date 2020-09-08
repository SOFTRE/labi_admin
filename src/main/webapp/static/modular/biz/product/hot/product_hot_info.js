/**
 * 初始化热推商品管理详情对话框
 */
var ProductHotInfoDlg = {
    ProductHotInfoData : {},
    validateFields: {
    	productType: {
            validators: {
                notEmpty: {
                    message: '链接类型不能为空'
                }
            }
        },
        gotoInfo: {
            validators: {
                notEmpty: {
                    message: '链接产品不能为空'
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
ProductHotInfoDlg.validate = function () {
    $('#productHotInfoForm').data("bootstrapValidator").resetForm();
    $('#productHotInfoForm').bootstrapValidator('validate');
    return $("#productHotInfoForm").data('bootstrapValidator').isValid();
}
/**
 * 清除数据
 */
ProductHotInfoDlg.clearData = function() {
    this.ProductHotInfoData = {};
}

/**
 * ----------------详情跳转（搜索按钮）-----------
 */
ProductHotInfoDlg.jumpSearch = function () {
	//var type = $("#type").find("option:selected").text();
	var productType =$("#productType").val();//类型
	var Keyword=$("#Keyword").val();//检索关键字
	if(Keyword=="" || Keyword==null){
		Labi.error("请输入关键字后查询!");
		return;
	}
	
	if(productType ==null ||  productType==""){
		Labi.success("请选择类型!");
	}else{
		var ajax = new $ax(Labi.ctxPath + "/productHot/geturl", function (data) {
			console.log(data);
            console.log(productType);
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
        ajax.set("productType",productType);
        ajax.set("Keyword",Keyword);
        ajax.start();
	}
	
}

/**
 * 点击选择热推商品
 */
ProductHotInfoDlg.chooseProduct = function () {
    var index = layer.open({
        type: 2,
        title: '选择热推商品',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/productHot/productHot_add'
    });
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProductHotInfoDlg.set = function(key, val) {
    this.ProductHotInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}


/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProductHotInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProductHotInfoDlg.close = function() {
    parent.layer.close(window.parent.ProductHot.layerIndex);
}

/**
 * 收集数据
 */
ProductHotInfoDlg.collectData = function() {
    this
    .set('id')
    .set('productId')
    .set('name')
    .set('productType')
    .set('seqNum')
    .set('createtime')
}

/**
 * 提交添加
 */
ProductHotInfoDlg.addSubmit = function() {

	$("#productId").val($("#gotoInfo").val());//商品id
	$("#name").val($("#gotoInfo").find("option:selected").text());//名称
	
    this.clearData();
    this.collectData();
    //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/productHot/add", function(data){
        Labi.success("添加成功!");
        window.parent.ProductHot.table.refresh();
        ProductHotInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ProductHotInfoData);
    ajax.start();
}



/**
 * 提交修改
 */
ProductHotInfoDlg.editSubmit = function() {
	$("#productId").val($("#gotoInfo").val());//商品id
	$("#name").val($("#gotoInfo").find("option:selected").text());//名称
	
    this.clearData();
    this.collectData();

    //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/productHot/update", function(data){
        Labi.success("修改成功!");
        window.parent.ProductHot.table.refresh();
        ProductHotInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ProductHotInfoData);
    ajax.start();
}

$(function() {
    //radio初始化
    $('input.radio').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
        increaseArea: '20%' // optional
    });
    //$('#coachLeve option[value='+coachGradeId+']').attr('selected',true);
    //参数校验
    Labi.initValidator("productHotInfoForm", ProductHotInfoDlg.validateFields);
    
});
