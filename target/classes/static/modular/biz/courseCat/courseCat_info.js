/**
 * 初始化广告管理详情对话框
 */
var CourseCatInfoDlg = {
    courseCatInfoData : {},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '名称不能为空'
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
        }
    }
};

CourseCatInfoDlg.validate = function () {
    $('#CouresCatInfoForm').data("bootstrapValidator").resetForm();
    $('#CouresCatInfoForm').bootstrapValidator('validate');
    return $("#CouresCatInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 清除数据
 */
CourseCatInfoDlg.clearData = function() {
    this.courseCatInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CourseCatInfoDlg.set = function(key, val) {
    this.courseCatInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CourseCatInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CourseCatInfoDlg.close = function() {
    parent.layer.close(window.parent.CourseCat.layerIndex);
}

/**
 * 收集数据
 */
CourseCatInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('seqNum')
    .set('status')
    .set('createtime')
    .set('oprtime');
}

/**
 * 提交添加
 */
CourseCatInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //参数校验
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/courseCat/add", function(data){
        Labi.success("添加成功!");
        window.parent.CourseCat.table.refresh();
        CourseCatInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.courseCatInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CourseCatInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

  //参数校验
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/courseCat/update", function(data){
        Labi.success("修改成功!");
        window.parent.CourseCat.table.refresh();
        CourseCatInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.courseCatInfoData);
    ajax.start();
}

$(function() {

	//参数校验
    Labi.initValidator("CouresCatInfoForm", CourseCatInfoDlg.validateFields);
    
});
