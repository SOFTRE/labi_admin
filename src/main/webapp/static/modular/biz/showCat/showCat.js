/**
 * 拉比展示分类管理管理初始化
 */
var ShowCat = {
    id: "ShowCatTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ShowCat.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '分类名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '排序', field: 'seqNum', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createtime', visible: false, align: 'center', valign: 'middle'},
            {title: '操作时间', field: 'oprtime', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ShowCat.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ShowCat.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加拉比展示分类管理
 */
ShowCat.openAddShowCat = function () {
    var index = layer.open({
        type: 2,
        title: '添加拉比展示分类',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/showCat/showCat_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看拉比展示分类详情
 */
ShowCat.openShowCatDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '拉比展示分类详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/showCat/showCat_update/' + ShowCat.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除拉比展示分类
 */
ShowCat.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Labi.ctxPath + "/showCat/delete", function (data) {
	            Labi.success("删除成功!");
	            ShowCat.table.refresh();
	        }, function (data) {
	            Labi.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("showCatId",ShowCat.seItem.id);
	        ajax.start();
    	}
    	Labi.confirm("是否删除?",operation);
    }
};

/**
 * 查询拉比展示分类列表
 */
ShowCat.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ShowCat.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ShowCat.initColumn();
    var table = new BSTable(ShowCat.id, "/showCat/list", defaultColunms);
    //table.setPaginationType("client");
    ShowCat.table = table.init();
});
