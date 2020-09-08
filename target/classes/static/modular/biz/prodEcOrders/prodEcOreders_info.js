/**
 * 初始化退货原因管理详情对话框 
 */
var ProdEcOrdersInfoDlg = {
    ProdEcOrdersInfoData : {},
    validateFields: {
    	refuseRemark: {
            validators: {
                notEmpty: {
                    message: '请填写拒绝原因'
                }
            }
        }
    },
    validateFields1: {
        realPrice: {
            validators: {
                notEmpty: {
                    message: '请填写实际实际退款金额'
                },
                regexp: {
                    //正则验证
                    regexp: /^[0-9]+([.]{1}[0-9]{1,2})?$/,
                    message: '请输入数字'
                }
            }
        }
    }
};
/**
 * 验证数据是否为空
 */
ProdEcOrdersInfoDlg.validate = function () {
    $('#ProdEcOrdersInfoForm').data("bootstrapValidator").resetForm();
    $('#ProdEcOrdersInfoForm').bootstrapValidator('validate');
    return $("#ProdEcOrdersInfoForm").data('bootstrapValidator').isValid();
}
ProdEcOrdersInfoDlg.validate1 = function () {
    $('#ProdEcOrdersFinishForm').data("bootstrapValidator").resetForm();
    $('#ProdEcOrdersFinishForm').bootstrapValidator('validate');
    return $("#ProdEcOrdersFinishForm").data('bootstrapValidator').isValid();
}
/**
 * 清除数据
 */
ProdEcOrdersInfoDlg.clearData = function() {
    this.ProdEcOrdersInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProdEcOrdersInfoDlg.set = function(key, val) {
    this.ProdEcOrdersInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}


/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProdEcOrdersInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProdEcOrdersInfoDlg.close = function() {
    parent.layer.close(window.parent.ProdEcOrders.layerIndex);
}

/**
 * 收集数据
 */
ProdEcOrdersInfoDlg.collectData = function() {
    this
    .set('id')
    .set('refuseRemark')
    .set('progress')
}

/**
 * 提交添加
 */
/**
ProdEcOrdersInfoDlg.addSubmit = function() {
    this.clearData();
    this.collectData();
    //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/prodEcOrders/add", function(data){
        Labi.success("添加成功!");
        window.parent.ProdEcOrders.table.refresh();
        ProdEcOrdersInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ProdEcOrdersInfoData);
    ajax.start();
}**/

 

/**
 * 提交修改
 */
ProdEcOrdersInfoDlg.editSubmit = function() {
	
	var progress = $('input[name="redioProgress"]:checked ').val();
	$("#progress").val(progress);
	
    this.clearData();
    this.collectData();

    //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/prodEcOrders/update", function(data){
        Labi.success("修改成功!");
        window.parent.ProdEcOrders.table.refresh();
        ProdEcOrdersInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ProdEcOrdersInfoData);
    ajax.start();
}
/**
 * 完成退款
 */
ProdEcOrdersInfoDlg.finishSubmit = function () {

    if(window.parent.ProdEcOrders.seItem.progress!="waitTake"){
        Labi.error("只有待收货的订单才可以完成退款!");
        return false;
    }
    if (!this.validate1()) {
        return;
    }

    var ajax = new $ax(Labi.ctxPath + "/prodEcOrders/complete", function (data) {
        Labi.success("成功!");
        window.parent.ProdEcOrders.table.refresh();
        ProdEcOrdersInfoDlg.close();
    }, function (data) {
        Labi.error("失败!" + data.responseJSON.message + "!");
    });
    ajax.set("id",$('#id').val());
    ajax.set("realPrice",$('#realPrice').val());
    ajax.start();
};
$(function() {
	//radio初始化
	$('input.radio').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
        increaseArea: '20%' // optional
    });
	
	$(document).on("ifChecked","input[name='redioProgress']",function(e){
    	if($(this).val()=='F'){
    		$("#div_refuseRemark").show();//显示视频div
    	}
    	if($(this).val()=='T'){
    		$("#div_refuseRemark").hide();//显示视频div
    	}
    });
	
	// 初始化单图
    var img = new $WebUpload("img",Labi.destDir.banner);
    img.init();
    
	//参数校验
    Labi.initValidator("ProdEcOrdersInfoForm", ProdEcOrdersInfoDlg.validateFields);
    Labi.initValidator("ProdEcOrdersFinishForm", ProdEcOrdersInfoDlg.validateFields1);


});
