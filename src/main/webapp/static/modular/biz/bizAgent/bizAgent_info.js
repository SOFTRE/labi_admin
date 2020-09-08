/**
 * 初始化分销商管理详情对话框
 */
var BizAgentInfoDlg = {
    bizAgentInfoData : {},
    userTableId: "BizUserTable",	//表格id
    tableUser:null,
    validateFields: {
        agentName: {
            validators: {
                notEmpty: {
                    message: '分销商名称不能为空'
                }
            }
        },
        bankName: {
            validators: {
                notEmpty: {
                    message: '银行名称不能为空'
                }
            }
        },
        bankNo: {
            validators: {
                notEmpty: {
                    message: '银行卡号不能为空'
                }
            }
        },
        beginDate: {
            validators: {
                notEmpty: {
                    message: '合约开始时间'
                }
            }
        },
        endDate: {
            validators: {
                notEmpty: {
                    message: '合约结束时间'
                }
            }
        },
        chooseUserId: {
            validators: {
                notEmpty: {
                    message: '请选择分销商所属会员'
                }
            }
        },
        phoneNum: {
            validators: {
                notEmpty: {
                    message: '请先绑定手机号码'
                }
            }
        }
    }
};
/**
 * 验证数据是否为空
 */
BizAgentInfoDlg.validate = function () {
    $('#bizAgentInfoForm').data("bootstrapValidator").resetForm();
    $('#bizAgentInfoForm').bootstrapValidator('validate');
    return $("#bizAgentInfoForm").data('bootstrapValidator').isValid();
}
/**
 * 清除数据
 */
BizAgentInfoDlg.clearData = function() {
    this.bizAgentInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BizAgentInfoDlg.set = function(key, val) {
    this.bizAgentInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BizAgentInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BizAgentInfoDlg.close = function() {
    parent.layer.close(window.parent.BizAgent.layerIndex);
}

/**
 * 收集数据
 */
BizAgentInfoDlg.collectData = function() {
    this
        .set('id')
        .set('userId')
        .set('beginDate')
        .set('endDate')
        .set('agentName')
        .set('bankNo')
        .set('bankName')
        .set('bankSubname')
}

/**
 * 提交添加
 */
BizAgentInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    //非空验证
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/bizAgent/add", function(data){
        Labi.success("添加成功!");
        window.parent.BizAgent.table.refresh();
        BizAgentInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bizAgentInfoData);
    ajax.start();
}
/**
 * 点击选择会员
 */
BizAgentInfoDlg.chooseUser = function () {
    var index = layer.open({
        type: 2,
        title: '选择会员',
        area: ['800px', '480px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/user/choose'
    });
    this.layerIndex = index;
    layer.full(index);//全屏显示
};

/**
 * 提交修改
 */
BizAgentInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    //非空验证
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/bizAgent/update", function(data){
        Labi.success("修改成功!");
        window.parent.BizAgent.table.refresh();
        BizAgentInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bizAgentInfoData);
    ajax.start();
}
/**
 * 冻结/解冻
 */
$(function() {
    //参数校验
    Labi.initValidator("bizAgentInfoForm", BizAgentInfoDlg.validateFields);
});
