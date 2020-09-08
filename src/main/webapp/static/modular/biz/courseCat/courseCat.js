/**
 * 课程分类管理管理初始化
 */
var CourseCat = {
    id: "CourseCatTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CourseCat.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '排序', field: 'seqNum', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
CourseCat.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CourseCat.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加课程分类管理
 */
CourseCat.openAddCourseCat = function () {
    var totalRows = $('#' + this.id).bootstrapTable('getOptions').totalRows;
    console.log(totalRows);
    if(totalRows>=5){
        Labi.info("最多添加五个分类！");
        return false;
    }
    var index = layer.open({
        type: 2,
        title: '添加课程分类管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/courseCat/courseCat_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看课程分类管理详情
 */
CourseCat.openCourseCatDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '课程分类管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/courseCat/courseCat_update/' + CourseCat.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除课程分类管理
 */
CourseCat.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Labi.ctxPath + "/courseCat/delete", function (data) {
	            Labi.success("删除成功!");
	            CourseCat.table.refresh();
	        }, function (data) {
	            Labi.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("courseCatId",CourseCat.seItem.id);
	        ajax.start();
    	}
    	Labi.confirm("是否删除?",operation);
    }
};

/**
 * 查询课程分类管理列表
 */
CourseCat.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    CourseCat.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = CourseCat.initColumn();
    var table = new BSTable(CourseCat.id, "/courseCat/list", defaultColunms);
    //table.setPaginationType("client");
    CourseCat.table = table.init();
});
