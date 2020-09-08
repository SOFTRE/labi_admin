/**
 * 初始化课程报名管理详情对话框
 */
var CourseOrderInfoDlg = {
    courseOrderInfoData : {},
    validateFields: {
    	selectadjustListenT: {
            validators: {
                notEmpty: {
                    message: '请选择上课时间'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
CourseOrderInfoDlg.clearData = function() {
    this.courseOrderInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CourseOrderInfoDlg.set = function(key, val) {
    this.courseOrderInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 验证数据是否为空
 */
CourseOrderInfoDlg.validate = function () {
    $('#couresInfoForm').data("bootstrapValidator").resetForm();
    $('#couresInfoForm').bootstrapValidator('validate');
    return $("#couresInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CourseOrderInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CourseOrderInfoDlg.close = function() {
    parent.layer.close(window.parent.CourseOrder.layerIndex);
}

/**
 * 收集数据
 */
CourseOrderInfoDlg.collectData = function() {
    this
    .set('id')
    .set('orderNo')
    .set('userId')
    .set('userName')
    .set('courseName')
    .set('courseDes')
    .set('courseImg')
    .set('listenTime')
    .set('adjustListenTime')
    .set('listenT')
    .set('adjustListenT')
    .set('courseCatId')
    .set('courseCatName')
    .set('totalPrice')
    .set('progress')
    .set('payWay')
    .set('remarks')
    .set('createtime')
    .set('oprtime');
}

/**
 * 提交添加
 */
CourseOrderInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/courseOrder/add", function(data){
        Labi.success("添加成功!");
        window.parent.CourseOrder.table.refresh();
        CourseOrderInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.courseOrderInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CourseOrderInfoDlg.editSubmit = function() {
	//调整后的上课时间
	var adjustListenT = $("#selectadjustListenT").find("option:selected").text();
	$("#adjustListenT").val(adjustListenT);
	
    this.clearData();
    this.collectData();

  //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/courseOrder/update", function(data){
        Labi.success("修改成功!");
        window.parent.CourseOrder.table.refresh();
        CourseOrderInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.courseOrderInfoData);
    ajax.start();
}

$(function() {

	 //参数校验
    Labi.initValidator("couresInfoForm", CourseOrderInfoDlg.validateFields);
    
});
