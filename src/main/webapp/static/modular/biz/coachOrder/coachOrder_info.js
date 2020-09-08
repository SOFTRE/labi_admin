/**
 * 初始化预约缴费管理详情对话框
 */
var CoachOrderInfoDlg = {
    coachOrderInfoData : {}
};

/**
 * 清除数据
 */
CoachOrderInfoDlg.clearData = function() {
    this.coachOrderInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CoachOrderInfoDlg.set = function(key, val) {
    this.coachOrderInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CoachOrderInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CoachOrderInfoDlg.close = function() {
    parent.layer.close(window.parent.CoachOrder.layerIndex);
}

/**
 * 收集数据
 */
CoachOrderInfoDlg.collectData = function() {
    this
    .set('id')
    .set('orderNo')
    .set('userId')
    .set('userName')
    .set('coachGradeId')
    .set('coachGradeName')
    .set('coachGradeImg')
    .set('coachNum')
    .set('useNum')
    .set('totalPrice')
    .set('progress')
    .set('payWay')
    .set('remarks')
    .set('createtime')
    .set('oprtime');
}

/**
 * 提交添加
 */
CoachOrderInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/coachOrder/add", function(data){
        Labi.success("添加成功!");
        window.parent.CoachOrder.table.refresh();
        CoachOrderInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.coachOrderInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CoachOrderInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/coachOrder/update", function(data){
        Labi.success("修改成功!");
        window.parent.CoachOrder.table.refresh();
        CoachOrderInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.coachOrderInfoData);
    ajax.start();
}

$(function() {

	// 初始化列表图片
    var avatarUp = new $WebUpload("img",Labi.destDir.coach);
    avatarUp.init();
    
});
