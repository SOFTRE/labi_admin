/**
 * 初始化用户优惠券管理详情对话框
 */
var CouponListInfoDlg = {
    couponListInfoData : {}
};

/**
 * 清除数据
 */
CouponListInfoDlg.clearData = function() {
    this.couponListInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CouponListInfoDlg.set = function(key, val) {
    this.couponListInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CouponListInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CouponListInfoDlg.close = function() {
    parent.layer.close(window.parent.CouponList.layerIndex);
}

/**
 * 收集数据
 */
CouponListInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('userName')
    .set('ruleId')
    .set('couponName')
    .set('extendMode')
    .set('useMode')
    .set('ecCode')
    .set('faceValue')
    .set('ifUsed')
    .set('ifExpired')
    .set('validBeginDate')
    .set('validEndDate')
    .set('validMinVal')
    .set('useTime')
    .set('remarks')
    .set('createtime');
}

/**
 * 提交添加
 */
CouponListInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/couponList/add", function(data){
        Labi.success("添加成功!");
        window.parent.CouponList.table.refresh();
        CouponListInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.couponListInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CouponListInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/couponList/update", function(data){
        Labi.success("修改成功!");
        window.parent.CouponList.table.refresh();
        CouponListInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.couponListInfoData);
    ajax.start();
}

$(function() {

});
