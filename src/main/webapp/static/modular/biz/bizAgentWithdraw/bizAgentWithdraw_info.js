/**
 * 初始化分销商提现管理详情对话框
 */
var BizAgentWithdrawInfoDlg = {
    bizAgentWithdrawInfoData : {}
};

/**
 * 清除数据
 */
BizAgentWithdrawInfoDlg.clearData = function() {
    this.bizAgentWithdrawInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BizAgentWithdrawInfoDlg.set = function(key, val) {
    this.bizAgentWithdrawInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BizAgentWithdrawInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BizAgentWithdrawInfoDlg.close = function() {
    parent.layer.close(window.parent.BizAgentWithdraw.layerIndex);
}

/**
 * 收集数据
 */
BizAgentWithdrawInfoDlg.collectData = function() {
    this
    .set('id')
    .set('agentId')
    .set('agentName')
    .set('totalAmount')
    .set('exchangeNum')
    .set('exchangeBak')
    .set('bankNo')
    .set('bankInfo')
    .set('progress')
    .set('createtime')
    .set('oprtime');
}

/**
 * 提交添加
 */
BizAgentWithdrawInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/bizAgentWithdraw/add", function(data){
        Labi.success("添加成功!");
        window.parent.BizAgentWithdraw.table.refresh();
        BizAgentWithdrawInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bizAgentWithdrawInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
BizAgentWithdrawInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/bizAgentWithdraw/update", function(data){
        Labi.success("修改成功!");
        window.parent.BizAgentWithdraw.table.refresh();
        BizAgentWithdrawInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bizAgentWithdrawInfoData);
    ajax.start();
}

$(function() {

});
