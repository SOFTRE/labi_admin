/**
 * banner管理管理初始化
 */
var Banner = {
    id: "BannerTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Banner.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '图片', field: 'img', visible: true, align: 'center', valign: 'middle',
                formatter: function(value, row, index) {
                    if (Labi.noEmpty(value)) {
                        return "<img class='js-img-open' data-value='"+value+"' src="+ Labi.imgPreFix+value+"?x-oss-process=image/resize,w_200>";
                    }else
                        return "-"
                }
            },
            {title: '模块', field: 'position', visible: true, align: 'center', valign: 'middle',
            	formatter : function (value, row, index) {
                    if (value == 1) {
                        return '首页';
                    }
                	if (value == 2) {
                        return '课程报名';
                    }
                	if (value == 3) {
                        return '教练预约';
                    }
                	if (value == 4) {
                        return '拉比展示';
                    }
                	if (value == 5) {
                        return '在线直播';
                    }if (value == 6) {
                        return '拉比商城';
                    }
                }
            
            },
            { title: '类型', field: 'type',  visible: true,  align: 'center', valign: 'middle',
                formatter : function (value, row, index) {
                    if (value == 'course') {
                        return '课程报名';
                    }
                	if (value == 'product') {
                        return '商品';
                    }
                	if (value == 'video') {
                        return '网上课程';
                    }
                	if (value == 'coach') {
                        return '教练';
                    }if (value == 'show') {
                        return '拉比展示';
                    }
                }
            },
           // {title: '跳转的地址', field: 'gotoInfo', visible: true, align: 'center', valign: 'middle'},
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
           
            {title: 'banner位置', field: 'indexes', visible: true, align: 'center', valign: 'middle',
            	 formatter : function (value, row, index) {
                     if (value == 1) {
                         return '顶部';
                     }
                 	if (value == 2) {
                         return '首页-最新课程';
                     }
                 	if (value == 3) {
                         return '首页-拉比展示推荐';
                     }
                     if (value == 4) {
                         return '首页-网上课程推荐';
                     }
                     if (value == 5) {
                         return '首页-热点推荐';
                     }
                 }
            },
            {title: '排序', field: 'seqNum', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createtime', visible: false, align: 'center', valign: 'middle'},
            {title: '操作时间', field: 'oprtime', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Banner.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Banner.seItem = selected[0];
        return true;
    }
};


/**
 * 重置
 */
Banner.txtRest = function () {
	$("#condition").val("");//标题
    $("#position").val("");//模块
    $("#type").val("");//类型
};
/**
 * 点击添加banner管理
 */
Banner.openAddBanner = function () {
    var index = layer.open({
        type: 2,
        title: '添加banner管理',
        area: ['900px', '520px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/banner/banner_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看banner管理详情
 */
Banner.openBannerDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'banner管理详情',
            area: ['900px', '520px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/banner/banner_update/' + Banner.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除banner管理
 */
Banner.delete = function () {
    if (this.check()) {
    	
    	var operation = function(){
	        var ajax = new $ax(Labi.ctxPath + "/banner/delete", function (data) {
	            Labi.success("删除成功!");
	            Banner.table.refresh();
	        }, function (data) {
	            Labi.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("bannerId",Banner.seItem.id);
	        ajax.start();
    	}
    	Labi.confirm("是否删除?",operation);
    }
};

/**
 * 查询banner管理列表
 */
Banner.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['position'] = $("#position").val();
    queryData['type'] = $("#type").val();
    Banner.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Banner.initColumn();
    var table = new BSTable(Banner.id, "/banner/list", defaultColunms);
    Banner.table = table.init();
    
    //延迟加载
    setTimeout(function(){
    	Banner.search();
    },1000);
});
