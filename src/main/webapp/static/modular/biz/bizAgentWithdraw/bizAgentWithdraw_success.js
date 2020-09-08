/**
 * 初始化提现拒绝对话框
 */
var BizAgentWithdrawSuccess = {
    validateFields: {
        exchangeNum: {
            validators: {
                notEmpty: {
                    message: '请输入交易流水号'
                }
            }
        }
    }
};
/**
 * 验证数据是否为空
 */
BizAgentWithdrawSuccess.validate = function () {
    $('#bizAgentWithdrawSuccessForm').data("bootstrapValidator").resetForm();
    $('#bizAgentWithdrawSuccessForm').bootstrapValidator('validate');
    return $("#bizAgentWithdrawSuccessForm").data('bootstrapValidator').isValid();
}


/**
 * 关闭此对话框
 */
BizAgentWithdrawSuccess.close = function() {
    parent.layer.close(window.parent.BizAgentWithdraw.layerIndex);
}

BizAgentWithdrawSuccess.success = function () {
    //非空验证
    if (!this.validate()) {
        return;
    }
    var ajax = new $ax(Labi.ctxPath + "/bizAgentWithdraw/success", function () {
        Labi.success("提现审核成功!");
        parent.BizAgentWithdraw.table.refresh();
        BizAgentWithdrawSuccess.close();
    }, function (data) {
        Labi.error("提现审核失败!" + data.responseJSON.message + "!");
    });
    ajax.set("id",parent.BizAgentWithdraw.seItem.id);
    ajax.set("exchangeNum",$('#exchangeNum').val());
    ajax.set("exchangeBak",$('#exchangeBak').val());
    ajax.start();
};
$(function() {
    //参数校验
    Labi.initValidator("bizAgentWithdrawSuccessForm", BizAgentWithdrawSuccess.validateFields);
});
