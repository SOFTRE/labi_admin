/**
 * 初始化商品分类管理详情对话框
 */
var ProdCatAttrInfoDlg = {
    prodCatAttrInfoData : {}
};

/**
 * 清除数据
 */
ProdCatAttrInfoDlg.clearData = function() {
    this.prodCatAttrInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProdCatAttrInfoDlg.set = function(key, val) {
    this.prodCatAttrInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProdCatAttrInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProdCatAttrInfoDlg.close = function() {
    parent.layer.close(window.parent.ProdCat.layerIndex);
}

/**
 * 收集数据
 */
ProdCatAttrInfoDlg.collectData = function() {
    this
    .set('id')
    .set('attrName');
}

/**
 * 提交添加
 */
ProdCatAttrInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/prodCatAttr/add", function(data){
        Labi.success("添加成功!");
        var queryData = {'catId':window.parent.ProdCat.seItem.id};
        parent.$('#'+window.parent.ProdCat.attrId).bootstrapTable('refresh',{query: queryData});
        ProdCatAttrInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.prodCatAttrInfoData);
    ajax.set("catId",window.parent.ProdCat.seItem.id);
    ajax.start();
}

/**
 * 提交修改
 */
ProdCatAttrInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/prodCatAttr/update", function(data){
        Labi.success("修改成功!");
        var queryData = {'catId':window.parent.ProdCat.seItem.id};
        parent.$('#'+window.parent.ProdCat.attrId).bootstrapTable('refresh',{query: queryData});
        ProdCatAttrInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.prodCatAttrInfoData);
    ajax.start();
}

$(function() {
});
