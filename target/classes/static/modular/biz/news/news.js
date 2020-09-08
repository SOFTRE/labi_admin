/**
 * 新闻管理管理初始化
 */
var News = {
    id: "NewsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
News.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '标题', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '缩略图', field: 'img', visible: true, align: 'center', valign: 'middle',
            	formatter: function(value, row, index) {
                    if (Labi.noEmpty(value)) {
                        return "<img class='js-img-open' data-value='"+value+"' src="+ Labi.imgPreFix+value+"?x-oss-process=image/resize,w_200>";
                    }else
                        return "-"
                }
            },
            {title: '简要 ', field: 'description', visible: true, align: 'center', valign: 'middle'},
            {title: '浏览量', field: 'pv', visible: true, align: 'center', valign: 'middle'},
            {title: '链接地址', field: 'hrefUrl', visible: true, align: 'center', valign: 'middle'},
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
            {title: '排序', field: 'seqNum', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
News.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        News.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加新闻管理
 */
News.openAddNews = function () {
    var index = layer.open({
        type: 2,
        title: '添加新闻管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/news/news_add'
    });
    this.layerIndex = index;
    layer.full(index);//全屏显示
    News.seItem = null;
};

/**
 * 打开查看新闻管理详情
 */
News.openNewsDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '新闻管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/news/news_update/' + News.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);//全屏显示
    }
};

/**
 * 删除新闻管理
 */
News.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Labi.ctxPath + "/news/delete", function (data) {
	            Labi.success("删除成功!");
	            News.table.refresh();
	        }, function (data) {
	            Labi.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("newsId",News.seItem.id);
	        ajax.start();
	    	}
    	Labi.confirm("是否删除?",operation);
    }
};

/**
 * 查询新闻管理列表
 */
News.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    News.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = News.initColumn();
    var table = new BSTable(News.id, "/news/list", defaultColunms);
    //table.setPaginationType("client");
    News.table = table.init();
    
  //延迟加载
    setTimeout(function(){
    	News.search();
    },500);
});
