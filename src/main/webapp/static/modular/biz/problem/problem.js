/**
 * 常见问题管理管理初始化
 */
var Problem = {
    id: "ProblemTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Problem.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '排序', field: 'seqNum', visible: true, align: 'center', valign: 'middle'},
        {title: '名称', field: 'problem', visible: true, align: 'center', valign: 'middle'},
        {title: '答案', field: 'answer', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Problem.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Problem.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加常见问题管理
 */
Problem.openAddProblem = function () {
    var index = layer.open({
        type: 2,
        title: '添加常见问题管理',
        area: ['800px', '540px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/problem/problem_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看常见问题管理详情
 */
Problem.openProblemDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '常见问题管理详情',
            area: ['800px', '540px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/problem/problem_update/' + Problem.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除常见问题管理
 */
Problem.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Labi.ctxPath + "/problem/delete", function (data) {
	            Labi.success("删除成功!");
	            Problem.table.refresh();
	        }, function (data) {
	            Labi.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("id",Problem.seItem.id);
	        ajax.start();
    	}
    	Labi.confirm("是否删除?",operation);
    }
};

/**
 * 查询常见问题管理列表
 */
Problem.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Problem.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Problem.initColumn();
    var table = new BSTable(Problem.id, "/problem/list", defaultColunms);
    Problem.table = table.init();
});
