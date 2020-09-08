/**
 *教练评价选项管理管理初始化
 */
var CoachEvaluateOption = {
    id: "CoachEvaluateOptionTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CoachEvaluateOption.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        	{title: '名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '排序', field: 'seqNum', visible: true, align: 'center', valign: 'middle'},
            {title: '是否启用', field: 'isStart', visible: true, align: 'center', valign: 'middle',
            	formatter : function (value, row, index) {
            		if (value == 'T') {
                        return '是';
                    }
            		if (value == 'F') {
                        return '否';
                    }
            	}
            },
    ];
};

/**
 * 检查是否选中
 */
CoachEvaluateOption.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CoachEvaluateOption.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加教练评价选项管理
 */
CoachEvaluateOption.openAddCoachEvaluateOption = function () {
    var index = layer.open({
        type: 2,
        title: '添加教练评价选项',
        area: ['800px', '620px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/coachEvaluateOption/coachEvaluateOption_add'
    });
    CoachEvaluateOption.seItem = null;//添加时清空富文本缓存
    this.layerIndex = index;
    //layer.full(index);//全屏显示
};

/**
 * 打开查看教练评价选项管理详情
 */
CoachEvaluateOption.openCoachEvaluateOptionDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '教练评价选项详情',
            area: ['800px', '620px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/coachEvaluateOption/coachEvaluateOption_update/' + CoachEvaluateOption.seItem.id
        });
        this.layerIndex = index;
        //layer.full(index);//全屏显示
    }
};

/**
 * 删除教练评价选项
 */
CoachEvaluateOption.delete = function () {
    if (this.check()) {
    	var operation = function(){
        var ajax = new $ax(Labi.ctxPath + "/coachEvaluateOption/delete", function (data) {
            Labi.success("删除成功!");
            CoachEvaluateOption.table.refresh();
        }, function (data) {
            Labi.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("coachEvaluateId",CoachEvaluateOption.seItem.id);
        ajax.start();
    	}
    	Labi.confirm("是否删除?",operation);
    }
};

/**
 * 查询教练评价选项管理列表
 */
CoachEvaluateOption.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['ifRecommend'] = $("#ifRecommend").val();
    queryData['ifOnline'] = $("#ifOnline").val();
    CoachEvaluateOption.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = CoachEvaluateOption.initColumn();
    var table = new BSTable(CoachEvaluateOption.id, "/coachEvaluateOption/list", defaultColunms);
    //table.setPaginationType("client");
    CoachEvaluateOption.table = table.init();
    
  //延迟加载
    setTimeout(function(){
    	CoachEvaluateOption.search();
    },500);
});
