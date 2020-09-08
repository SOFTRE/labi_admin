/**
 * 拉比展示管理管理初始化
 */
var Show = {
    id: "ShowTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Show.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        /**
            {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '分类id', field: 'catId', visible: true, align: 'center', valign: 'middle'},**/
        	{title: '标题', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '分类名称', field: 'catName', visible: true, align: 'center', valign: 'middle'},
            {title: '缩略图', field: 'img', visible: true, align: 'center', valign: 'middle',
            	formatter: function(value, row, index) {
                    if (Labi.noEmpty(value)) {
                        return "<img class='js-img-open' data-value='"+value+"' src="+ Labi.imgPreFix+value+"?x-oss-process=image/resize,w_200>";
                    }else
                        return "-"
                }
            },
            {title: '简要 ', field: 'description', visible: true, align: 'center', valign: 'middle'},
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
            {title: '是否上线', field: 'ifOnline', visible: true, align: 'center', valign: 'middle',
            	formatter : function (value, row, index) {
            		if (value == 'T') {
                        return '是';
                    }
            		if (value == 'F') {
                        return '否';
                    }
            	}
            },
            {title: '是否播放视频', field: 'ifVideo', visible: true, align: 'center', valign: 'middle',
            	formatter : function (value, row, index) {
            		if (value == 'T') {
                        return '是';
                    }
            		if (value == 'F') {
                        return '否';
                    }
            	}
            },
            {title: '排序', field: 'seqNum', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Show.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Show.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加拉比展示管理
 */
Show.openAddShow = function () {
    var index = layer.open({
        type: 2,
        title: '添加拉比展示',
        area: ['800px', '620px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/show/show_add'
    });
    Show.seItem = null;//添加时清空富文本缓存
    this.layerIndex = index;
    layer.full(index);//全屏显示
};

/**
 * 打开查看拉比展示管理详情
 */
Show.openShowDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '拉比展示详情',
            area: ['800px', '620px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/show/show_update/' + Show.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);//全屏显示
    }
};

/**
 * 删除拉比展示
 */
Show.delete = function () {
    if (this.check()) {
    	var operation = function(){
        var ajax = new $ax(Labi.ctxPath + "/show/delete", function (data) {
            Labi.success("删除成功!");
            Show.table.refresh();
        }, function (data) {
            Labi.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("showId",Show.seItem.id);
        ajax.start();
    	}
    	Labi.confirm("是否删除?",operation);
    }
};

/**
 * 查询拉比展示管理列表
 */
Show.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['ifRecommend'] = $("#ifRecommend").val();
    queryData['ifOnline'] = $("#ifOnline").val();
    queryData['catId'] = $("#showCat").val();
    Show.table.refresh({query: queryData});
};

/**
 * 重置
 */
Show.txtRest = function () {
	$("#condition").val("");//标题
    $("#showCat").val("");//分类
    $("#ifRecommend").val("");//是否推荐
    $("#ifOnline").val("");//是否上线
};

$(function () {
    var defaultColunms = Show.initColumn();
    var table = new BSTable(Show.id, "/show/list", defaultColunms);
    //table.setPaginationType("client");
    Show.table = table.init();
    
  //延迟加载
    setTimeout(function(){
    	Show.search();
    },500);
});
