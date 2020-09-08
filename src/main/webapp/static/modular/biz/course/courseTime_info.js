/**
 * 初始化课程时间管理详情对话框
 */
var CourseTimeInfoDlg = {
    courseTimeInfoData : {},
    validateFields: {
    	 startTime: {
             validators: {
                 notEmpty: {
                     message: '请选择开始时间'
                 }
             }
         }
    }
};


CourseTimeInfoDlg.validate = function () {
    $('#CourseTimeInfoForm').data("bootstrapValidator").resetForm();
    $('#CourseTimeInfoForm').bootstrapValidator('validate');
    return $("#CourseTimeInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 清除数据
 */
CourseTimeInfoDlg.clearData = function() {
    this.courseTimeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CourseTimeInfoDlg.set = function(key, val) {
    this.courseTimeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CourseTimeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CourseTimeInfoDlg.close = function() {
    parent.layer.close(window.parent.CourseTime.layerIndex);
}

/**
 * 收集数据
 */
CourseTimeInfoDlg.collectData = function() {
    this
    .set('id')
    .set('courseId')
    .set('startTime');
}

/**
 * 提交添加
 */
CourseTimeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    //非空验证
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/courseTime/add", function(data){
        Labi.success("添加成功!");
        window.parent.CourseTime.table.refresh();
        CourseTimeInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.courseTimeInfoData);
    ajax.set('courseId',window.parent.CourseTime.courseId);
    ajax.start();
}

/**
 * 提交修改
 */
CourseTimeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/courseTime/update", function(data){
        Labi.success("修改成功!");
        window.parent.CourseTime.table.refresh();
        CourseTimeInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.courseTimeInfoData);
    ajax.start();
}

$(function() {

	//参数校验
    Labi.initValidator("CourseTimeInfoForm", CourseTimeInfoDlg.validateFields);
    
});
