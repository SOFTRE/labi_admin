/**
 * 退货原因管理管理初始化
 */
var ProdEcReason = {
    id: "ProdEcReasonTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProdEcReason.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
           // {title: '跳转的地址', field: 'gotoInfo', visible: true, align: 'center', valign: 'middle'},
            {title: '排序', field: 'seqNum', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createtime', visible: false, align: 'center', valign: 'middle'},
            {title: '操作时间', field: 'oprtime', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ProdEcReason.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProdEcReason.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加退货问题管理
 */
ProdEcReason.openAddProdEcReason = function () {
    var index = layer.open({
        type: 2,
        title: '添加退货原由',
        area: ['900px', '520px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/prodEcReason/prodEcReason_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看退货原因管理详情
 */
ProdEcReason.openProdEcReasonDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '退货原因管理详情',
            area: ['900px', '520px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/prodEcReason/prodEcReason_update/' + ProdEcReason.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除退货原因管理
 */
ProdEcReason.delete = function () {
    if (this.check()) {
    	
    	var operation = function(){
	        var ajax = new $ax(Labi.ctxPath + "/prodEcReason/delete", function (data) {
	            Labi.success("删除成功!");
	            ProdEcReason.table.refresh();
	        }, function (data) {
	            Labi.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("prodEcReasonId",ProdEcReason.seItem.id);
	        ajax.start();
    	}
    	Labi.confirm("是否删除?",operation);
    }
};

/**
 * 查询退货原因管理列表
 */
ProdEcReason.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ProdEcReason.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ProdEcReason.initColumn();
    var table = new BSTable(ProdEcReason.id, "/prodEcReason/list", defaultColunms);
    ProdEcReason.table = table.init();
});
