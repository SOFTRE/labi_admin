/**
 * 初始化搜索推荐词管理详情对话框
 */
var SearchKeywordInfoDlg = {
    searchkeywordInfoData : {},
    validateFields: {
        keyword: {
            validators: {
                notEmpty: {
                    message: '问题不能为空'
                }
            }
        }
    }
};

SearchKeywordInfoDlg.validate = function () {
    $('#SearchKeywordInfoForm').data("bootstrapValidator").resetForm();
    $('#SearchKeywordInfoForm').bootstrapValidator('validate');
    return $("#SearchKeywordInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 清除数据
 */
SearchKeywordInfoDlg.clearData = function() {
    this.searchkeywordInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SearchKeywordInfoDlg.set = function(key, val) {
    this.searchkeywordInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SearchKeywordInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SearchKeywordInfoDlg.close = function() {
    parent.layer.close(window.parent.SearchKeyword.layerIndex);
}

/**
 * 收集数据
 */
SearchKeywordInfoDlg.collectData = function() {
    this
    .set('id')
    .set('keyword')

}

/**
 * 提交添加
 */
SearchKeywordInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //参数校验
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/searchkeyword/add", function(data){
        Labi.success("添加成功!");
        window.parent.SearchKeyword.table.refresh();
        SearchKeywordInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.searchkeywordInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SearchKeywordInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

  //参数校验
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/searchkeyword/update", function(data){
        Labi.success("修改成功!");
        window.parent.SearchKeyword.table.refresh();
        SearchKeywordInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.searchkeywordInfoData);
    ajax.start();
}

$(function() {

	//参数校验
    Labi.initValidator("SearchKeywordInfoForm", SearchKeywordInfoDlg.validateFields);
    
});
