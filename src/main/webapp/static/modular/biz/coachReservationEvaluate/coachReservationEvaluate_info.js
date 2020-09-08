/**
 * 初始化预约记录反馈管理详情对话框 
 */
var CoachReservationEvaluateInfoDlg = {
    CoachReservationEvaluateInfoData : {},
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
CoachReservationEvaluateInfoDlg.validate = function () {
    $('#CoachReservationEvaluateInfoForm').data("bootstrapValidator").resetForm();
    $('#CoachReservationEvaluateInfoForm').bootstrapValidator('validate');
    return $("#CoachReservationEvaluateInfoForm").data('bootstrapValidator').isValid();
}
/**
 * 清除数据
 */
CoachReservationEvaluateInfoDlg.clearData = function() {
    this.CoachReservationEvaluateInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CoachReservationEvaluateInfoDlg.set = function(key, val) {
    this.CoachReservationEvaluateInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CoachReservationEvaluateInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CoachReservationEvaluateInfoDlg.close = function() {
    parent.layer.close(window.parent.CoachReservationEvaluate.layerIndex);
}

/**
 * 收集数据
 */
CoachReservationEvaluateInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('time')
    .set('img')
    .set('userName')
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
CoachReservationEvaluateInfoDlg.addSubmit = function() {

	var val_online = $('input[name="redioCheck"]:checked ').val();
	$("#ifOnline").val(val_online);
	
    this.clearData();
    this.collectData();
    //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/coachReservationEvaluate/add", function(data){
        Labi.success("添加成功!");
        window.parent.CoachReservationEvaluate.table.refresh();
        CoachReservationEvaluateInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.CoachReservationEvaluateInfoData);
    ajax.start();
}

 
 

/**
 * 提交修改
 */
CoachReservationEvaluateInfoDlg.editSubmit = function() {
	
	var val_online = $('input[name="redioCheck"]:checked ').val();
	$("#ifOnline").val(val_online);
	
    this.clearData();
    this.collectData();

    //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/coachReservationEvaluate/update", function(data){
        Labi.success("修改成功!");
        window.parent.CoachReservationEvaluate.table.refresh();
        CoachReservationEvaluateInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.CoachReservationEvaluateInfoData);
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
    Labi.initValidator("CoachReservationEvaluateInfoForm", CoachReservationEvaluateInfoDlg.validateFields);
	// 初始化头像上传
    var avatarUp = new $WebUpload("img",Labi.destDir.CoachReservationEvaluate);
    avatarUp.init();
    
});
