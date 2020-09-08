/**
 * 初始化商品分类管理详情对话框
 */
var ProdCatAttrOptionInfoDlg = {
    prodCatAttrOptionInfoData : {}
};

/**
 * 清除数据
 */
ProdCatAttrOptionInfoDlg.clearData = function() {
    this.prodCatAttrOptionInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProdCatAttrOptionInfoDlg.set = function(key, val) {
    this.prodCatAttrOptionInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProdCatAttrOptionInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProdCatAttrOptionInfoDlg.close = function() {
    parent.layer.close(window.parent.ProdCat.layerIndex);
}

/**
 * 收集数据
 */
ProdCatAttrOptionInfoDlg.collectData = function() {
    this
    .set('id')
    .set('optionName');
}

/**
 * 提交添加
 */
ProdCatAttrOptionInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/prodCatAttrOption/add", function(data){
        Labi.success("添加成功!");
        var queryData = {'attrId':window.parent.ProdCat.seItemAttr.id};
        parent.$('#'+window.parent.ProdCat.attrOptionId).bootstrapTable('refresh',{query: queryData});
        ProdCatAttrOptionInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.prodCatAttrOptionInfoData);
    ajax.set("attrId",window.parent.ProdCat.seItemAttr.id);
    ajax.start();
}

/**
 * 提交修改
 */
ProdCatAttrOptionInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/prodCatAttrOption/update", function(data){
        Labi.success("修改成功!");
        var queryData = {'attrId':window.parent.ProdCat.seItemAttr.id};
        parent.$('#'+window.parent.ProdCat.attrOptionId).bootstrapTable('refresh',{query: queryData});
        ProdCatAttrOptionInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.prodCatAttrOptionInfoData);
    ajax.start();
}

$(function() {
});
