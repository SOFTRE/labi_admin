/**
 * 视频管理管理初始化
 */
var Tip = {
    id: "TipTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化属性表格的列
 */
Tip.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '提示标题', field: 'title', visible: true, align: 'center', valign: 'middle'},
        {title: '提示CODE', field: 'code', visible: true, align: 'center', valign: 'middle'},

    ];
};
/**
 * 检查是否选中
 */
Tip.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Tip.seItem = selected[0];
        return true;
    }
};

/**
 * 打开查看视频管理详情
 */
Tip.openTipDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '提示详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/tip/tip_update/' + Tip.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);//全屏显示
    }
};

/**
 * 查询视频管理列表
 */
Tip.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Tip.table.refresh({query: queryData});
};
Tip.searchTip = function () {
    var queryData = {};
    queryData['condition'] = $("#tip_condition").val();
    Tip.tipTable.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Tip.initColumn();
    var table = new BSTable(Tip.id, "/tip/list", defaultColunms);
    Tip.table = table.init();

});
