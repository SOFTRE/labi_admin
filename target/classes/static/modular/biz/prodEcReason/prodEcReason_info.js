/**
 * 初始化退货原因管理详情对话框
 */
var ProdEcReasonInfoDlg = {
    prodEcReasonInfoData : {},
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
ProdEcReasonInfoDlg.validate = function () {
    $('#ProdEcReasonInfoForm').data("bootstrapValidator").resetForm();
    $('#ProdEcReasonInfoForm').bootstrapValidator('validate');
    return $("#ProdEcReasonInfoForm").data('bootstrapValidator').isValid();
}
/**
 * 清除数据
 */
ProdEcReasonInfoDlg.clearData = function() {
    this.prodEcReasonInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProdEcReasonInfoDlg.set = function(key, val) {
    this.prodEcReasonInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}


/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProdEcReasonInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProdEcReasonInfoDlg.close = function() {
    parent.layer.close(window.parent.ProdEcReason.layerIndex);
}

/**
 * 收集数据
 */
ProdEcReasonInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('time')
    .set('img')
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
ProdEcReasonInfoDlg.addSubmit = function() {
    this.clearData();
    this.collectData();
    //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/prodEcReason/add", function(data){
        Labi.success("添加成功!");
        window.parent.ProdEcReason.table.refresh();
        ProdEcReasonInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.prodEcReasonInfoData);
    ajax.start();
}

 

/**
 * 提交修改
 */
ProdEcReasonInfoDlg.editSubmit = function() {
    this.clearData();
    this.collectData();

    //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/prodEcReason/update", function(data){
        Labi.success("修改成功!");
        window.parent.ProdEcReason.table.refresh();
        ProdEcReasonInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.prodEcReasonInfoData);
    ajax.start();
}

$(function() {
	//参数校验
    Labi.initValidator("ProdEcReasonInfoForm", ProdEcReasonInfoDlg.validateFields);
});
