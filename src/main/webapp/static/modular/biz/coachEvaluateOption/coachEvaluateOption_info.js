/**
 * 初始化拉比展示管理详情对话框
 */
var CoachEvaluateOptionInfoDlg = {
    CoachEvaluateOptionInfoData : {},
    validateFields: {
    	 
        name: {
            validators: {
                notEmpty: {
                    message: '请填写问题名称'
                }
            }
        },
        seqNum: {
            validators: {
                notEmpty: {
                    message: '排序不能为空'
                },
                regexp: {
                	//正则验证  
                    regexp: /^[+]{0,1}(\d+)$/,  
                    message: '请输入正整数'  
                }
            }
        },
    }
};

/**
 * 验证数据是否为空
 */
CoachEvaluateOptionInfoDlg.validate = function () {
    $('#CoachEvaluateOptionInfoForm').data("bootstrapValidator").resetForm();
    $('#CoachEvaluateOptionInfoForm').bootstrapValidator('validate');
    return $("#CoachEvaluateOptionInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 清除数据
 */
CoachEvaluateOptionInfoDlg.clearData = function() {
    this.CoachEvaluateOptionInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CoachEvaluateOptionInfoDlg.set = function(key, val) {
    this.CoachEvaluateOptionInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CoachEvaluateOptionInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CoachEvaluateOptionInfoDlg.close = function() {
    parent.layer.close(window.parent.CoachEvaluateOption.layerIndex);
}

/**
 * 收集数据
 */
CoachEvaluateOptionInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('seqNum')
    .set('type')
    .set('status')
    .set('isStart')
    .set('createtime')
    .set('oprtime');
}

/**
 * 提交添加
 */
CoachEvaluateOptionInfoDlg.addSubmit = function() {

	//是否上线
	var isStart = $('input[name="redioIsStartCheck"]:checked ').val();
	$("#isStart").val(isStart);
	
     
    this.clearData();
    this.collectData();

  //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/coachEvaluateOption/add", function(data){
        Labi.success("添加成功!");
        window.parent.CoachEvaluateOption.table.refresh();
        CoachEvaluateOptionInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.CoachEvaluateOptionInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CoachEvaluateOptionInfoDlg.editSubmit = function() {

	//是否上线
	var isStart = $('input[name="redioIsStartCheck"]:checked ').val();
	$("#isStart").val(isStart);
	
    this.clearData();
    this.collectData();

  //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/coachEvaluateOption/update", function(data){
        Labi.success("修改成功!");
        window.parent.CoachEvaluateOption.table.refresh();
        CoachEvaluateOptionInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.CoachEvaluateOptionInfoData);
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
    Labi.initValidator("CoachEvaluateOptionInfoForm", CoachEvaluateOptionInfoDlg.validateFields);
  
});
