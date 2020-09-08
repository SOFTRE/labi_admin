/**
 * 用户管理管理初始化
 */
var AgentMoneyRecord = {
    id: "AgentMoneyRecordTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AgentMoneyRecord.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '金额', field: 'balance', visible: true, align: 'center', valign: 'middle'},
        {title: '之后余额', field: 'leftBalance', visible: true, align: 'center', valign: 'middle'},
        {
            title: '类型', field: 'type', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value == 'income') {
                    return "收入"
                }
                if (value == 'expend') {
                    return "支出"
                }
            }
        },
        {title: '昵称', field: 'remarks', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'}
    ];
};
/**
 * 关闭此对话框
 */
AgentMoneyRecord.close = function () {
    parent.layer.close(window.parent.BizAgent.layerIndex);
}


/**
 * 查询用户管理列表
 */
AgentMoneyRecord.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['type'] = $("#type").val();
    AgentMoneyRecord.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AgentMoneyRecord.initColumn();
    var table = new BSTable(AgentMoneyRecord.id, "/bizAgent/moneyRecord/" + window.parent.BizAgent.seItem.id, defaultColunms);
    AgentMoneyRecord.table = table.init();
});
