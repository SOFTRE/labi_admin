/**
 * 分销商管理管理初始化
 */
var Agent = {
    id: "AgentTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Agent.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '分销商姓名', field: 'agentName', visible: true, align: 'center', valign: 'middle'},
        {title: '银行名称', field: 'bankName', visible: true, align: 'center', valign: 'middle'},
        {title: '支行', field: 'bankSubname', visible: true, align: 'center', valign: 'middle'},
        {title: '银行卡号', field: 'bankNo', visible: true, align: 'center', valign: 'middle'},
        {title: '手机号', field: 'phoneNum', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'}
    ];
};
/**
 * 关闭此对话框
 */
Agent.close = function() {
    parent.layer.close(window.parent.CourseOrder.layerIndex);
}
/**
 * 检查是否选中
 */
Agent.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Agent.seItem = selected[0];
        return true;
    }
};

/**
 * 选择分销商后
 */
Agent.choose = function () {
    if (this.check()) {
        var operation = function (){
            var ajax = new $ax(Labi.ctxPath + "/courseOrder/divided", function (data) {
                Labi.success("分成成功!");
                parent.CourseOrder.table.refresh();
                Agent.close();
            }, function (data) {
                Labi.error("分成失败!" + data.responseJSON.message + "!");
            });
            ajax.set("courseOrderId",parent.CourseOrder.seItem.id);
            ajax.set("agentId",Agent.seItem.id);
            ajax.start();
        }
        Labi.confirm("确认分成给分销商:"+Agent.seItem.agentName+"吗?", operation);
    }
};

/**
 * 查询分销商管理列表
 */
Agent.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Agent.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Agent.initColumn();
    var table = new BSTable(Agent.id, "/bizAgent/list", defaultColunms);
    Agent.table = table.init();
    //延迟加载
    setTimeout(function(){
        Agent.search();
    },300);
});
