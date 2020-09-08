/**
 * 初始化预约反馈管理详情对话框
 */
var CoachReservationInfoDlg = {
    coachReservationInfoData : {}
};

/**
 * 清除数据
 */
CoachReservationInfoDlg.clearData = function() {
    this.coachReservationInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CoachReservationInfoDlg.set = function(key, val) {
    this.coachReservationInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CoachReservationInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CoachReservationInfoDlg.close = function() {
    parent.layer.close(window.parent.CoachReservation.layerIndex);
}

/**
 * 收集数据
 */
CoachReservationInfoDlg.collectData = function() {
    this
    .set('id')
    .set('orderId')
    .set('userId')
    .set('userName')
    .set('coachId')
    .set('startTime')
    .set('endTime')
    .set('coachName')
    .set('coachImg')
    .set('judgeLevel')
    .set('feedbackContent')
    .set('ifFinish')
    .set('ifFeedback')
    .set('createtime')
    .set('oprtime');
}


/**
 * 提交添加
 */
CoachReservationInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/coachReservation/add", function(data){
        Labi.success("添加成功!");
        window.parent.CoachReservation.table.refresh();
        CoachReservationInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.coachReservationInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CoachReservationInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/coachReservation/update", function(data){
        Labi.success("修改成功!");
        window.parent.CoachReservation.table.refresh();
        CoachReservationInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.coachReservationInfoData);
    ajax.start();
}

$(function() {

});
