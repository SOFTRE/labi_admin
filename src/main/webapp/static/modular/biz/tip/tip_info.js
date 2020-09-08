/**
 * 初始化提示管理详情对话框
 */
var TipInfoDlg = {
    tipInfoData : {},
    validateFields: {
        title: {
            validators: {
                notEmpty: {
                    message: '请填写提示标题'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
TipInfoDlg.clearData = function() {
    this.tipInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TipInfoDlg.set = function(key, val) {
    this.tipInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TipInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TipInfoDlg.close = function() {
    parent.layer.close(window.parent.Tip.layerIndex);
}

/**
 * 收集数据
 */
TipInfoDlg.collectData = function() {
    this
    .set('id')
    .set('title')
    .set('info');
}
/**
 * 验证数据是否为空
 */
TipInfoDlg.validate = function () {
    $('#tipInfoForm').data("bootstrapValidator").resetForm();
    $('#tipInfoForm').bootstrapValidator('validate');
    return $("#tipInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 提交修改
 */
TipInfoDlg.editSubmit = function() {

    //富文本获取值
    var info = UE.getEditor('infoEdit').getContent();
    $('#info').val(info);

    this.clearData();
    this.collectData();
    //非空验证
    if (!this.validate()) {
        return false;
    }

    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/tip/update", function(data){
        Labi.success("修改成功!");
        window.parent.Tip.table.refresh();
        TipInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.tipInfoData);
    ajax.start();
}
$(function() {
    //参数校验
    Labi.initValidator("tipInfoForm", TipInfoDlg.validateFields);
    //初始化富文本
    var editor = UE.getEditor('infoEdit');

    editor.ready(function() {
        editor.setHeight(500);
        //富文本set值
        if(parent.Tip.seItem){
            $.get(Labi.filePreFix + parent.Tip.seItem.desFile, function(data) {
                var start = data.indexOf("<body>"),
                    end = data.indexOf('</body>');
                editor.setContent(data.substring(start+6, end));
            });
        } else {
            //富文本清空
            editor.setContent('');
        }
    });
});
