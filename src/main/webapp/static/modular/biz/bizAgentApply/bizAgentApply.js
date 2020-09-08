/**
 * 分销商管理管理初始化
 */
var BizAgentApply = {
    id: "BizAgentApplyTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
BizAgentApply.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '用户id', field: 'userId', visible: false, align: 'center', valign: 'middle'},
        {title: '分销商姓名', field: 'agentName', visible: true, align: 'center', valign: 'middle'},
        {title: '手机号', field: 'phoneNum', visible: true, align: 'center', valign: 'middle'},
        {title: '余额', field: 'balance', visible: true, align: 'center', valign: 'middle'},
        {title: '累计收益', field: 'totalBalance', visible: true, align: 'center', valign: 'middle'},
        {title: '累计邀请', field: 'totalSubUser', visible: true, align: 'center', valign: 'middle'},
        {title: '状态', field: 'progress', visible: true, align: 'center', valign: 'middle',
            formatter: function(value, row, index) {
                if(value=='wait'){
                    return '待审核';
                }
                if(value=='fail'){
                    return '拒绝';
                }
            }
        },
        {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'}
    ];
};
/**
 * 检查是否选中
 */
BizAgentApply.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Labi.info("请先选中表格中的某一记录！");
        return false;
    } else {
        BizAgentApply.seItem = selected[0];
        return true;
    }
};
/**
 * 打开拒绝页面
 */
BizAgentApply.openAddBizAgentApplyRefuse = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '拒绝原因',
            area: ['800px', '270px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/bizAgentApply/refuse'
        });
        this.layerIndex = index;
    }
};

BizAgentApply.success = function () {
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Labi.ctxPath + "/bizAgentApply/success", function () {
                Labi.success("审核成功!");
                BizAgentApply.table.refresh();
            }, function (data) {
                Labi.error("审核失败!" + data.responseJSON.message + "!");
            });
            ajax.set("agentId",BizAgentApply.seItem.id);
            ajax.start();
        };
        Labi.confirm("确认审核通过?", operation);
    }
};
/**
 * 查询分销商管理列表
 */
BizAgentApply.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BizAgentApply.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = BizAgentApply.initColumn();
    var table = new BSTable(BizAgentApply.id, "/bizAgentApply/list", defaultColunms);
    table.setQueryParams({'progress':'wait'});
    BizAgentApply.table = table.init();
});
