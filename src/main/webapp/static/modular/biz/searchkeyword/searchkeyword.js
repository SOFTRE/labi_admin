/**
 * 搜索推荐词管理管理初始化
 */
var SearchKeyword = {
    id: "SearchKeywordTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SearchKeyword.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '关键词', field: 'keyword', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
SearchKeyword.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SearchKeyword.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加搜索推荐词管理
 */
SearchKeyword.openAddSearchKeyword = function () {
    var index = layer.open({
        type: 2,
        title: '添加搜索推荐词管理',
        area: ['800px', '240px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/searchkeyword/searchkeyword_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看搜索推荐词管理详情
 */
SearchKeyword.openSearchKeywordDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '搜索推荐词管理详情',
            area: ['800px', '240px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/searchkeyword/searchkeyword_update/' + SearchKeyword.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除搜索推荐词管理
 */
SearchKeyword.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Labi.ctxPath + "/searchkeyword/delete", function (data) {
	            Labi.success("删除成功!");
	            SearchKeyword.table.refresh();
	        }, function (data) {
	            Labi.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("id",SearchKeyword.seItem.id);
	        ajax.start();
    	}
    	Labi.confirm("是否删除?",operation);
    }
};

/**
 * 查询搜索推荐词管理列表
 */
SearchKeyword.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    SearchKeyword.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = SearchKeyword.initColumn();
    var table = new BSTable(SearchKeyword.id, "/searchkeyword/list", defaultColunms);
    SearchKeyword.table = table.init();
});
