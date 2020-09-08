/**
 * 课程班级管理管理初始化
 */
var CourseClass = {
    id: "CourseClassTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CourseClass.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        	{title: '班级名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '所属课程', field: 'courseName', visible: true, align: 'center', valign: 'middle'},
            {title: '编号', field: 'code', visible: true, align: 'center', valign: 'middle'},
            {title: '最大报名数', field: 'maxNum', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
CourseClass.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CourseClass.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加课程班级管理
 */
CourseClass.openAddCourseClass = function () {
    var index = layer.open({
        type: 2,
        title: '添加班级管理',
        area: ['900px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/courseClass/courseClass_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看课程班级管理详情
 */
CourseClass.openCourseClassDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '班级管理详情',
            area: ['900px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/courseClass/courseClass_update/' + CourseClass.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除班级管理
 */
CourseClass.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Labi.ctxPath + "/courseClass/delete", function (data) {
            Labi.success("删除成功!");
            CourseClass.table.refresh();
        }, function (data) {
            Labi.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("courseClassId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询班级管理列表
 */
CourseClass.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    CourseClass.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = CourseClass.initColumn();
    var table = new BSTable(CourseClass.id, "/courseClass/list", defaultColunms);
    //table.setPaginationType("client");
    CourseClass.table = table.init();
});
