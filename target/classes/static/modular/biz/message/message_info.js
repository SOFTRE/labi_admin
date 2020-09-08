/**
 * 初始化拉比展示分类管理详情对话框
 */
var MessageInfoDlg = {
    messageCatInfoData : {},
	validateFields: {
	    title: {
	        validators: {
	            notEmpty: {
	                message: '标题描述不能为空'
	            }
	        }
	    },
	    templateId: {
	        validators: {
	            notEmpty: {
	                message: '模板ID不能为空'
	            }
	        }
	    },
	    content: {
	        validators: {
	            notEmpty: {
	                message: '模板内容不能为空'
	            }
	        }
	    }
	}
};


MessageInfoDlg.validate = function () {
    $('#messageInfoForm').data("bootstrapValidator").resetForm();
    $('#messageInfoForm').bootstrapValidator('validate');
    return $("#messageInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 清除数据
 */
MessageInfoDlg.clearData = function() {
    this.messageCatInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MessageInfoDlg.set = function(key, val) {
    this.messageCatInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MessageInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框 
 */
MessageInfoDlg.close = function() {
    parent.layer.close(window.parent.Message.layerIndex);
}

/**
 * 收集数据
 */
MessageInfoDlg.collectData = function() {
    this
    .set('id')
    .set('title')
    .set('templateId')
    .set('content')
    .set('createUser')
    .set('createTime');
}

/**
 * 提交添加
 */
MessageInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/message/add", function(data){
        Labi.success("添加成功!");
        window.parent.Message.table.refresh();
        MessageInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.messageCatInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MessageInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/message/update", function(data){
        Labi.success("修改成功!");
        window.parent.Message.table.refresh();
        MessageInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.messageCatInfoData);
    ajax.start();
}

$(function() {

	//参数校验
    Labi.initValidator("messageInfoForm", MessageInfoDlg.validateFields);
    
});
