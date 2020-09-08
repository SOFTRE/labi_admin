/**
 * 初始化分销商管理详情对话框
 */
var BizAgentApplyRefuse = {
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
BizAgentApplyRefuse.validate = function () {
    $('#bizAgentApplyRefuseForm').data("bootstrapValidator").resetForm();
    $('#bizAgentApplyRefuseForm').bootstrapValidator('validate');
    return $("#bizAgentApplyRefuseForm").data('bootstrapValidator').isValid();
}


/**
 * 关闭此对话框
 */
BizAgentApplyRefuse.close = function() {
    parent.layer.close(window.parent.BizAgentApply.layerIndex);
}

BizAgentApplyRefuse.fail = function () {
    //非空验证
    if (!this.validate()) {
        return;
    }
    var ajax = new $ax(Labi.ctxPath + "/bizAgentApply/fail", function () {
        Labi.success("审核拒绝成功!");
        parent.BizAgentApply.table.refresh();
        BizAgentApplyRefuse.close();
    }, function (data) {
        Labi.error("审核拒绝失败!" + data.responseJSON.message + "!");
    });
    ajax.set("agentId",parent.BizAgentApply.seItem.id);
    ajax.set("remarks",$('#remarks').val());
    ajax.start();
};
$(function() {
    //参数校验
    Labi.initValidator("bizAgentApplyRefuseForm", BizAgentApplyRefuse.validateFields);
});
