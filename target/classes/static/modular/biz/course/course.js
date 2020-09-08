/**
 * 课程管理管理初始化
 */
var Course = {
    id: "CourseTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Course.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '班级前缀名', field: 'classPrefix', visible: false, align: 'center', valign: 'middle'},
            {title: '缩略图', field: 'img', visible: true, align: 'center', valign: 'middle',
            	formatter: function(value, row, index) {
                    if (Labi.noEmpty(value)) {
                        return "<img class='js-img-open' data-value='"+value+"' src="+ Labi.imgPreFix+value+"?x-oss-process=image/resize,w_200>";
                    }else
                        return "-"
                }
            },

            {title: '分类名称', field: 'courseCatName', visible: true, align: 'center', valign: 'middle'},
            {title: '课程价格', field: 'price', visible: true, align: 'center', valign: 'middle'},
            {title: '排序', field: 'seqNum', visible: true, align: 'center', valign: 'middle'},
            {title: '最大报名数', field: 'maxNum', visible: true, align: 'center', valign: 'middle'},
            {title: '已报名人数', field: 'saleNum', visible: true, align: 'center', valign: 'middle'},
            {title: '是否推荐', field: 'ifRecommend', visible: true, align: 'center', valign: 'middle',
            	formatter : function (value, row, index) {
            		if (value == 'T') {
                        return '是';
                    }
            		if (value == 'F') {
                        return '否';
                    }
            	}
            },
            {title: '是否上架', field: 'ifShelf', visible: true, align: 'center', valign: 'middle',
            	formatter : function (value, row, index) {
            		if (value == 'T') {
                        return '是';
                    }
            		if (value == 'F') {
                        return '否';
                    }
            	}
            }
    ];
};

/**
 * 检查是否选中
 */
Course.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Course.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加课程管理
 */
Course.openAddCourse = function () {
    var index = layer.open({
        type: 2,
        title: '添加课程管理',
        area: ['900px', '520px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/course/course_add'
    });
    Course.seItem = null;//添加时清空富文本缓存
    this.layerIndex = index;
    layer.full(index);//全屏显示
};

/**
 * 打开查看课程管理详情
 */
Course.openCourseDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '课程管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/course/course_update/' + Course.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);//全屏显示
    }
};
/**
 * 打开课程时间设定
 */
Course.setTime = function () {
    var height = (document.body.offsetHeight-48-35-40-14-7) + "px";
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '课程时间设定',
            area: ['800px', height], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/courseTime'
        });
        this.layerIndex = index;
        layer.full(index);//全屏显示
    }
};
/**
 * 删除课程管理
 */
Course.delete = function () {
    if (this.check()) {
    	
    	var operation = function(){
	        var ajax = new $ax(Labi.ctxPath + "/course/delete", function (data) {
	            Labi.success("删除成功!");
	            Course.table.refresh();
	        }, function (data) {
	            Labi.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("courseId",Course.seItem.id);
	        ajax.start();
    	}
    	Labi.confirm("是否删除?",operation);
    }
};

/**
 * 查询课程管理列表
 */
Course.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Course.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Course.initColumn();
    var table = new BSTable(Course.id, "/course/list", defaultColunms);
    //table.setPaginationType("client");
    Course.table = table.init();
    
  //延迟加载
    setTimeout(function(){
    	Course.search();
    },500);
});
