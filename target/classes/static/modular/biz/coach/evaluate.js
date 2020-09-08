/**
 * 用户管理管理初始化
 */
var Evaluate = {
    id: "evaluateTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Evaluate.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '用户ID', field: 'userId', visible: false, align: 'center', valign: 'middle'},
            {title: '教练ID', field: 'evaluateId', visible: false, align: 'center', valign: 'middle'},
            {title: '学员姓名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
            {title: '问题', field: 'evaluateName', visible: true, align: 'center', valign: 'middle'},
            {title: '分数', field: 'score', visible: true, align: 'center', valign: 'middle'},
            {title: '评价时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
    ];
};
/**
 * 关闭此对话框
 */
Evaluate.close = function() {
    parent.layer.close(window.parent.CoachInfoDlg.layerIndex);
}
/**
 * 检查是否选中
 */
Evaluate.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	CoachReservation.seItem = selected[0];
        return true;
    }
};

/**
 * 选择用户
 */
Evaluate.chooseUser = function () {
    if (this.check()) {
        parent.$("#userId").val(CoachReservation.seItem.userId);
        parent.$("#evaluateId").val(CoachReservation.seItem.evaluateId);
        Evaluate.close();
    }
};

/**
 * 查询反馈列表
 */
Evaluate.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Evaluate.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Evaluate.initColumn();
    var table = new BSTable(Evaluate.id, "/coach/evaluate_info", defaultColunms);
    //table.setQueryParams({'ifCoach':'F'});
    Evaluate.table = table.init();
    //延迟加载
    setTimeout(function(){
    	Evaluate.search();
    },300);
});
