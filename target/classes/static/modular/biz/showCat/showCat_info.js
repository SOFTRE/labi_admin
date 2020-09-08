/**
 * 初始化拉比展示分类管理详情对话框
 */
var ShowCatInfoDlg = {
    showCatInfoData : {},
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


ShowCatInfoDlg.validate = function () {
    $('#showCatfoForm').data("bootstrapValidator").resetForm();
    $('#showCatfoForm').bootstrapValidator('validate');
    return $("#showCatfoForm").data('bootstrapValidator').isValid();
}

/**
 * 清除数据
 */
ShowCatInfoDlg.clearData = function() {
    this.showCatInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ShowCatInfoDlg.set = function(key, val) {
    this.showCatInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ShowCatInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ShowCatInfoDlg.close = function() {
    parent.layer.close(window.parent.ShowCat.layerIndex);
}

/**
 * 收集数据
 */
ShowCatInfoDlg.collectData = function() {
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
ShowCatInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/showCat/add", function(data){
        Labi.success("添加成功!");
        window.parent.ShowCat.table.refresh();
        ShowCatInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.showCatInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ShowCatInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/showCat/update", function(data){
        Labi.success("修改成功!");
        window.parent.ShowCat.table.refresh();
        ShowCatInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.showCatInfoData);
    ajax.start();
}

$(function() {

	//参数校验
    Labi.initValidator("showCatfoForm", ShowCatInfoDlg.validateFields);
    
});
