/**
 * 视频分类管理管理初始化
 */
var VideoCat = {
    id: "VideoCatTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
VideoCat.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '排序', field: 'seqNum', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
VideoCat.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        VideoCat.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加视频分类管理
 */
VideoCat.openAddVideoCat = function () {
    var totalRows = $('#' + this.id).bootstrapTable('getOptions').totalRows;
    console.log(totalRows);
    if(totalRows>=5){
        Labi.info("最多添加五个分类！");
        return false;
    }
    var index = layer.open({
        type: 2,
        title: '添加视频分类管理',
        area: ['800px', '240px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/videoCat/videoCat_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看视频分类管理详情
 */
VideoCat.openVideoCatDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '视频分类管理详情',
            area: ['800px', '240px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/videoCat/videoCat_update/' + VideoCat.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除视频分类管理
 */
VideoCat.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Labi.ctxPath + "/videoCat/delete", function (data) {
	            Labi.success("删除成功!");
	            VideoCat.table.refresh();
	        }, function (data) {
	            Labi.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("videoCatId",VideoCat.seItem.id);
	        ajax.start();
    	}
    	Labi.confirm("是否删除?",operation);
    }
};

/**
 * 查询视频分类管理列表
 */
VideoCat.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    VideoCat.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = VideoCat.initColumn();
    var table = new BSTable(VideoCat.id, "/videoCat/list", defaultColunms);
    VideoCat.table = table.init();
});
