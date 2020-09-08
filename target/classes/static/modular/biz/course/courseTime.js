/**
 * 课程时间管理管理初始化
 */
var CourseTime = {
    id: "CourseTimeTable",	//表格id
    seItem: null,		//选中的条目
    courseId:null,
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CourseTime.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '开始时间', field: 'startTime', visible: true, align: 'center', valign: 'middle',
            formatter : function (value, row, index) {
                return Labi.getDate(value)
            }
        }
    ];
};

/**
 * 检查是否选中
 */
CourseTime.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Labi.info("请先选中表格中的某一记录！");
        return false;
    } else {
        CourseTime.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加课程时间管理
 */
CourseTime.openAddCourseTime = function () {
    var index = layer.open({
        type: 2,
        title: '添加课程时间管理',
        area: ['800px', '320px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/courseTime/courseTime_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看课程时间管理详情
 */
CourseTime.openCourseTimeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '课程时间详情',
            area: ['800px', '320px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/courseTime/courseTime_update/' + CourseTime.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除课程时间管理
 */
CourseTime.delete = function () {
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Labi.ctxPath + "/courseTime/delete", function (data) {
                Labi.success("删除成功!");
                CourseTime.table.refresh();
            }, function (data) {
                Labi.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("courseTimeId", CourseTime.seItem.id);
            ajax.start();
        }
        Labi.confirm("是否删除?",operation);
    }

};

/**
 * 查询课程时间管理列表
 */
CourseTime.search = function () {
    var queryData = {};
    // queryData['courseName'] = $("#courseName").val();
    CourseTime.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = CourseTime.initColumn();
    var table = new BSTable(CourseTime.id, "/courseTime/list", defaultColunms);
    table.setQueryParams({'courseId': window.parent.Course.seItem.id});
    CourseTime.table = table.init();

    CourseTime.courseId = window.parent.Course.seItem.id;
});
