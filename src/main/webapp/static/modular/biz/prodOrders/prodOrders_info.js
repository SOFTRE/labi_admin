/**
 * 初始化订单发货管理详情对话框
 */
var ProdOrdersInfoDlg = {
    prodOrdersInfoData : {},
    validateFields: {
        logisticsNo: {
            validators: {
                notEmpty: {
                    message: '快递单号不能为空'
                }
            }
        }
    }
};
/**
 * 验证数据是否为空
 */
ProdOrdersInfoDlg.validate = function () {
    $('#ProdOrdersInfoForm').data("bootstrapValidator").resetForm();
    $('#ProdOrdersInfoForm').bootstrapValidator('validate');
    return $("#ProdOrdersInfoForm").data('bootstrapValidator').isValid();
}
/**
 * 清除数据
 */
ProdOrdersInfoDlg.clearData = function() {
    this.prodOrdersInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProdOrdersInfoDlg.set = function(key, val) {
    this.prodOrdersInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProdOrdersInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProdOrdersInfoDlg.close = function() {
    parent.layer.close(window.parent.ProdOrders.layerIndex);
}

/**
 * 收集数据
 */
ProdOrdersInfoDlg.collectData = function() {
    this
    .set('orderId')
    .set('logisticsNo')
}

/**
 * 提交添加
 */
ProdOrdersInfoDlg.deliverySubmit = function() {
    //非空验证
    if (!this.validate()) {
        return;
    }
    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/prodOrders/delivery", function(data){
        Labi.success("添加成功!");
        window.parent.ProdOrders.table.refresh();
        ProdOrdersInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    var logisticsCode=$("input[name='wuliu'][checked]").val();
    var logisticsName=$("input[name='wuliu'][checked='checked']").parent().next().text();
    ajax.set(this.prodOrdersInfoData);
    ajax.set('logisticsCode',logisticsCode);
    ajax.set('logisticsName',logisticsName);
    ajax.start();

}

$(function() {
    $('input.radio').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
        increaseArea: '20%' // optional
    });
    $(document).on('ifChecked', 'input.radio',function(event){
        $("input[name='"+$(this).attr('name')+"']").removeAttr("checked");
        $(this).attr("checked","checked");
    });

    //参数校验
    Labi.initValidator("ProdOrdersInfoForm", ProdOrdersInfoDlg.validateFields);
});
