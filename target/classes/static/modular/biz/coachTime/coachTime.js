/**
 * 上课时间管理管理初始化
 */
var CoachTime = {
    id: "CoachTimeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CoachTime.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '教练名称', field: 'coachName', visible: true, align: 'center', valign: 'middle'},
            {title: '教练等级', field: 'coachGradeName', visible: true, align: 'center', valign: 'middle'},
            //{title: '开始时间', field: 'startTime', visible: true, align: 'center', valign: 'middle'},
            //{title: '结束时间', field: 'endTime', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
CoachTime.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CoachTime.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加上课时间管理
 */
CoachTime.openAddCoachTime = function () {
    var index = layer.open({
        type: 2,
        title: '添加上课时间管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/coachTime/coachTime_add'
    });
    this.layerIndex = index;
    //layer.full(index);
};

/**
 * 打开查看上课时间管理详情
 */
CoachTime.openCoachTimeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '上课时间详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/coachTime/coachTime_update/' + CoachTime.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);
    }
};

/**
 * 删除上课时间管理
 */
CoachTime.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Labi.ctxPath + "/coachTime/delete", function (data) {
            Labi.success("删除成功!");
            CoachTime.table.refresh();
        }, function (data) {
            Labi.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("CoachTimeId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询上课时间管理列表
 */
CoachTime.search = function () {
    var queryData = {};
    queryData['coachName'] = $("#coachName").val();
    CoachTime.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = CoachTime.initColumn();
    var table = new BSTable(CoachTime.id, "/coachTime/list", defaultColunms);
    //table.setPaginationType("client");
    CoachTime.table = table.init();
    
});
