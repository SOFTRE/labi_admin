/**
 * 预约反馈管理管理初始化
 */
var CoachAndStudentRelation = {
    id: "CoachAndStudentRelationTable",	//表格id CoachAndStudentRelation
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CoachAndStudentRelation.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '教练名称', field: 'coachName', visible: true, align: 'center', valign: 'middle'},
        {title: '学员名称', field: 'userName', visible: true, align: 'center', valign: 'middle'},
        {title: '教练缩略图', field: 'coachImg', visible: true, align: 'center', valign: 'middle',
            	formatter: function(value, row, index) {
                    if (Labi.noEmpty(value)) {
                        return "<img class='js-img-open' data-value='"+value+"' src="+ Labi.imgPreFix+value+"?x-oss-process=image/resize,w_200>";
                    }else
                        return "-"
                }
            },
       {title: '开始时间', field: 'startTime', visible: true, align: 'center', valign: 'middle'},
       {title: '结束时间', field: 'endTime', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
CoachAndStudentRelation.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CoachAndStudentRelation.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加预约反馈管理
 */
CoachAndStudentRelation.openAddCoachAndStudentRelation = function () {
    var index = layer.open({
        type: 2,
        title: '添加预约反馈管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/coachAndStudentRelation/coachAndStudentRelation_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看预约反馈管理详情
 */
CoachAndStudentRelation.openCoachAndStudentRelationDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '预约反馈管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/coachAndStudentRelation/coachAndStudentRelation_update/' + CoachAndStudentRelation.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除预约反馈管理
 */
CoachAndStudentRelation.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Labi.ctxPath + "/coachAndStudentRelation/delete", function (data) {
            Labi.success("删除成功!");
            CoachAndStudentRelation.table.refresh();
        }, function (data) {
            Labi.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("CoachAndStudentRelationId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询预约反馈管理列表
 */
CoachAndStudentRelation.search = function () {
    var queryData = {};
    queryData['username'] = $("#username").val();//用户名
    queryData['coachname'] = $("#coachname").val();//教练名
    CoachAndStudentRelation.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = CoachAndStudentRelation.initColumn();
    var table = new BSTable(CoachAndStudentRelation.id, "/coachAndStudentRelation/list", defaultColunms);
    //table.setPaginationType("client");
    CoachAndStudentRelation.table = table.init();
    
  //list延迟加载
    setTimeout(function(){
    	CoachAndStudentRelation.search();
    },500);
});
