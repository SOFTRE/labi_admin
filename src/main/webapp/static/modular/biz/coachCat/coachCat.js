/**
 * coachCat管理管理初始化
 */
var coachCat = {
    id: "coachCatTable",	//表格id 
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
coachCat.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '排序', field: 'seqNum', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createtime', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
coachCat.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        coachCat.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加教练分类管理
 */
coachCat.openAddcoachCat = function () {
//    var totalRows = $('#' + this.id).bootstrapTable('getOptions').totalRows;
//    if(totalRows>=5){
//        Labi.info("最多添加五条分类！");
//        return false;
//    }

    var index = layer.open({
        type: 2,
        title: '添加教练分类管理',
        area: ['900px', '520px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/coachCat/coachCat_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看教练分类管理详情
 */
coachCat.openCoachCatDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '教练分类管理详情',
            area: ['900px', '520px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/coachCat/coachCat_update/'+coachCat.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除coachCat管理
 */
coachCat.delete = function () {
    if (this.check()) {
    	
    	var operation = function(){
	        var ajax = new $ax(Labi.ctxPath + "/coachCat/delete", function (data) {
	            Labi.success("删除成功!");
	            coachCat.table.refresh();
	        }, function (data) {
	            Labi.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("coachCatId",coachCat.seItem.id);
	        ajax.start();
    	}
    	Labi.confirm("是否删除?",operation);
    }
};

/**
 * 查询coachCat管理列表
 */
coachCat.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['position'] = $("#position").val();
    queryData['type'] = $("#type").val();
    coachCat.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = coachCat.initColumn();
    var table = new BSTable(coachCat.id, "/coachCat/list", defaultColunms);
    coachCat.table = table.init();
    
    //延迟加载
    setTimeout(function(){
    	coachCat.search();
    },500);
});
