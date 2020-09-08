/**
 * 预约记录反馈管理管理初始化
 */
var CoachReservationEvaluate = {
    id: "CoachReservationEvaluateTable",	//表格id
    seItem: null,		//选中的条目 
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CoachReservationEvaluate.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '教练名称', field: 'coachName', visible: true, align: 'center', valign: 'middle'},
            {title: '学员名称', field: 'userName',  visible: true,  align: 'center', valign: 'middle'},
            {title: '预约id', field: 'reservationId', visible: false, align: 'center', valign: 'middle'},
            
            {title: '问题', field: 'desQuerName', visible: true, align: 'center', valign: 'middle'},
            {title: '评分', field: 'score', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle',sortable: true},
    ];
};

/**
 * 检查是否选中
 */
CoachReservationEvaluate.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CoachReservationEvaluate.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加预约记录反馈管理
 */
CoachReservationEvaluate.openAddCoachReservationEvaluate = function () {
    var index = layer.open({
        type: 2,
        title: '添加预约记录反馈管理',
        area: ['900px', '520px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/coachReservationEvaluate/coachReservationEvaluate_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看预约记录反馈管理详情
 */
CoachReservationEvaluate.openCoachReservationEvaluateDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '预约记录反馈管理详情',
            area: ['900px', '520px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/coachReservationEvaluate/coachReservationEvaluate_update/' + CoachReservationEvaluate.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除预约记录反馈管理
 */
CoachReservationEvaluate.delete = function () {
    if (this.check()) {
    	
    	var operation = function(){
	        var ajax = new $ax(Labi.ctxPath + "/coachReservationEvaluate/delete", function (data) {
	            Labi.success("删除成功!");
	            CoachReservationEvaluate.table.refresh();
	        }, function (data) {
	            Labi.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("CoachReservationEvaluateId",CoachReservationEvaluate.seItem.id);
	        ajax.start();
    	}
    	Labi.confirm("是否删除?",operation);
    }
};

/**
 * 查询预约记录反馈管理列表
 */
CoachReservationEvaluate.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['userName'] = $("#userName").val();
    CoachReservationEvaluate.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = CoachReservationEvaluate.initColumn();
    var table = new BSTable(CoachReservationEvaluate.id, "/coachReservationEvaluate/list", defaultColunms);
    CoachReservationEvaluate.table = table.init();
    
    //延迟加载
    setTimeout(function(){
    	CoachReservationEvaluate.search();
    },500);
});
