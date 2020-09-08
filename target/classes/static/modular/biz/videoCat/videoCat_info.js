/**
 * 初始化视频分类管理详情对话框
 */
var VideoCatInfoDlg = {
    videoCatInfoData : {},
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

VideoCatInfoDlg.validate = function () {
    $('#VideoCatInfoForm').data("bootstrapValidator").resetForm();
    $('#VideoCatInfoForm').bootstrapValidator('validate');
    return $("#VideoCatInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 清除数据
 */
VideoCatInfoDlg.clearData = function() {
    this.videoCatInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
VideoCatInfoDlg.set = function(key, val) {
    this.videoCatInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
VideoCatInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
VideoCatInfoDlg.close = function() {
    parent.layer.close(window.parent.VideoCat.layerIndex);
}

/**
 * 收集数据
 */
VideoCatInfoDlg.collectData = function() {
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
VideoCatInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //参数校验
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/videoCat/add", function(data){
        Labi.success("添加成功!");
        window.parent.VideoCat.table.refresh();
        VideoCatInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.videoCatInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
VideoCatInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

  //参数校验
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/videoCat/update", function(data){
        Labi.success("修改成功!");
        window.parent.VideoCat.table.refresh();
        VideoCatInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.videoCatInfoData);
    ajax.start();
}

$(function() {

	//参数校验
    Labi.initValidator("VideoCatInfoForm", VideoCatInfoDlg.validateFields);
    
});
