/**
 * 初始化提现拒绝对话框
 */
var BizAgentWithdrawRefuse = {
    validateFields: {
        remarks: {
            validators: {
                notEmpty: {
                    message: '请输入拒绝原因'
                }
            }
        }
    }
};
/**
 * 验证数据是否为空
 */
BizAgentWithdrawRefuse.validate = function () {
    $('#bizAgentWithdrawRefuseForm').data("bootstrapValidator").resetForm();
    $('#bizAgentWithdrawRefuseForm').bootstrapValidator('validate');
    return $("#bizAgentWithdrawRefuseForm").data('bootstrapValidator').isValid();
}


/**
 * 关闭此对话框
 */
BizAgentWithdrawRefuse.close = function() {
    parent.layer.close(window.parent.BizAgentWithdraw.layerIndex);
}

BizAgentWithdrawRefuse.fail = function () {
    //非空验证
    if (!this.validate()) {
        return;
    }
    var ajax = new $ax(Labi.ctxPath + "/bizAgentWithdraw/fail", function () {
        Labi.success("审核拒绝成功!");
        parent.BizAgentWithdraw.table.refresh();
        BizAgentWithdrawRefuse.close();
    }, function (data) {
        Labi.error("审核拒绝失败!" + data.responseJSON.message + "!");
    });
    ajax.set("id",parent.BizAgentWithdraw.seItem.id);
    ajax.set("exchangeBak",$('#exchangeBak').val());
    ajax.start();
};
$(function() {
    //参数校验
    Labi.initValidator("bizAgentWithdrawRefuseForm", BizAgentWithdrawRefuse.validateFields);
});
