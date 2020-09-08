/**
 * 初始化优惠券管理详情对话框
 */
var CouponRuleInfoDlg = {
    couponRuleInfoData : {},
		validateFields: {
			couponName: {
		        validators: {
		            notEmpty: {
		                message: '请填写优惠券名称'
		            }
		        }
		    },
		    faceValue: {
		        validators: {
		            notEmpty: {
		                message: '请填写面额'
		            }
		        }
		    },
		    showExtendMode: {
		        validators: {
		            notEmpty: {
		                message: '请选择赠送方式'
		            }
		        }
		    },
		    showUseMode: {
		        validators: {
		            notEmpty: {
		                message: '请选择使用方式'
		            }
		        }
		    },

		    presentMinVal: {
		        validators: {
		            notEmpty: {
		                message: '赠送时最低消费金额不能为空'
		            },
		            regexp: {
		            	//正则验证   赠送时最低消费金额
		                regexp: /^[+]{0,1}(\d+)$/,  
		                message: '请输入正整数'  
		            }
		        }
		    },
		    
		    validMinVal: {
		        validators: {
		            notEmpty: {
		                message: '使用时最低消费金额不能为空'
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
CouponRuleInfoDlg.validate = function () {
    $('#couponRuleInfoForm').data("bootstrapValidator").resetForm();
    $('#couponRuleInfoForm').bootstrapValidator('validate');
    return $("#couponRuleInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 清除数据
 */
CouponRuleInfoDlg.clearData = function() {
    this.couponRuleInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CouponRuleInfoDlg.set = function(key, val) {
    this.couponRuleInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CouponRuleInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CouponRuleInfoDlg.close = function() {
    parent.layer.close(window.parent.CouponRule.layerIndex);
}

/**
 * 收集数据
 */
CouponRuleInfoDlg.collectData = function() {
    this
    .set('id')
    .set('ruleName')
    .set('couponName')
    .set('extendMode')
    .set('useMode')
    .set('presentMinVal')
    .set('faceValue')
    .set('faceRule')
    .set('issueBeginDate')
    .set('issueEndDate')
    .set('validBeginDate')
    .set('validEndDate')
    .set('issueBeginDateTwo')
    .set('issueEndDateTwo')
    .set('validBeginDateTwo')
    .set('validEndDateTwo')
    .set('validMinVal')
    .set('limitTtlQty')
    .set('limitPerQty')
    .set('issuedTtlQty')
    .set('usedTtlQty')
    .set('numPrefix')
    .set('remarks')
    .set('ifOpen')
    .set('status')
    .set('createtime')
    .set('oprtime');
}

/**
 * 提交添加
 */
CouponRuleInfoDlg.addSubmit = function() {

	//赠送方式 赋值
	$("#extendMode").val($("#showExtendMode").val());
	//使用方式
	$("#useMode").val($("#showUseMode").val());
	
	//是否开启
	var valOnline = $('input[name="redioIfOpenCheck"]:checked ').val();
	$("#ifOpen").val(valOnline);

	this.clearData();
    this.collectData();

    //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/couponRule/add", function(data){
        Labi.success("添加成功!");
        window.parent.CouponRule.table.refresh();
        CouponRuleInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.couponRuleInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CouponRuleInfoDlg.editSubmit = function() {

	//赠送方式 赋值
	$("#extendMode").val($("#showExtendMode").val());
	//使用方式
	$("#useMode").val($("#showUseMode").val());
	//是否开启
	var valOnline = $('input[name="redioIfOpenCheck"]:checked ').val();
	$("#ifOpen").val(valOnline);
	
    this.clearData();
    this.collectData();

    //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/couponRule/update", function(data){
        Labi.success("修改成功!");
        window.parent.CouponRule.table.refresh();
        CouponRuleInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.couponRuleInfoData);
    ajax.start();
}

$(function() {

	//radio初始化
    $('input.radio').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
        increaseArea: '20%' // optional
    });
    
   //参数校验
    Labi.initValidator("couponRuleInfoForm", CouponRuleInfoDlg.validateFields);
    
    //赠送方式下拉框数据回显
    var extendModeId = $('#showExtendMode').find('option:eq(0)').data('id');
    $('#showExtendMode option[value='+extendModeId+']').attr('selected',true);
    
   //使用方式下拉框数据回显
    var useModeId = $('#showUseMode').find('option:eq(0)').data('id');
    $('#showUseMode option[value='+useModeId+']').attr('selected',true);
});
