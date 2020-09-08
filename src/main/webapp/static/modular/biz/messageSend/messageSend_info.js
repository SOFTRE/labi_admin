/**
 * 初始化短信发送管理详情对话框
 */
var MessageSendInfoDlg = {
    courseOrderInfoData : {}
};

/**
 * 清除数据
 */
MessageSendInfoDlg.clearData = function() {
    this.courseOrderInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MessageSendInfoDlg.set = function(key, val) {
    this.courseOrderInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MessageSendInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MessageSendInfoDlg.close = function() {
    parent.layer.close(window.parent.CourseOrder.layerIndex);
}

/**
 * 收集数据
 */
MessageSendInfoDlg.collectData = function() {
    this
    .set('id')
    .set('orderNo')
    .set('userId')
    .set('userName')
    .set('courseName')
    .set('courseDes')
    .set('courseImg')
    .set('listenTime')
    .set('adjustListenTime')
    .set('courseCatId')
    .set('templateId')
    .set('courseCatName')
    .set('totalPrice')
    .set('progress')
    .set('payWay')
    .set('remarks')
    .set('createtime')
    .set('oprtime');
}


 

$(function() {

});
