/**
 * 初始化商品分类管理详情对话框
 */
var ProdCatInfoDlg = {
    prodCatInfoData : {}
};

/**
 * 清除数据
 */
ProdCatInfoDlg.clearData = function() {
    this.prodCatInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProdCatInfoDlg.set = function(key, val) {
    this.prodCatInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProdCatInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProdCatInfoDlg.close = function() {
    parent.layer.close(window.parent.ProdCat.layerIndex);
}

/**
 * 收集数据
 */
ProdCatInfoDlg.collectData = function() {
    this
    .set('id')
    .set('catName')
    .set('img')
    .set('parentId')
    .set('seqNum');
}

/**
 * 提交添加
 */
ProdCatInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/prodCat/add", function(data){
        Labi.success("添加成功!");
        window.parent.ProdCat.search();
        ProdCatInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.prodCatInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ProdCatInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/prodCat/update", function(data){
        Labi.success("修改成功!");
        window.parent.ProdCat.search();
        ProdCatInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.prodCatInfoData);
    ajax.start();
}

$(function() {
    var imgUp = new $WebUpload("img",Labi.destDir.prodCat);
    imgUp.init();

    //下拉框数据回显
    var parentId = $('#parentId').find('option:eq(0)').data('parentid');
    if(parentId){
        $('#parentId option[value='+parentId+']').attr('selected',true);
    }
});
