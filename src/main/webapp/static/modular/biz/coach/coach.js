/**
 * 教练管理管理初始化
 */
var Coach = {
    id: "CoachTable",	// 表格id
    seItem: null,		// 选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Coach.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '姓名', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '缩略图', field: 'img', visible: true, align: 'center', valign: 'middle',
            formatter: function(value, row, index) {
                if (Labi.noEmpty(value)) {
                    return "<img class='js-img-open' data-value='"+value+"' src="+ Labi.imgPreFix+value+"?x-oss-process=image/resize,w_200>";
                }else
                    return "-"
            }
        },
        {title: '价格', field: 'price', visible: true, align: 'center', valign: 'middle'},
        {title: '等级名称', field: 'coachGradeName', visible: true, align: 'center', valign: 'middle'},
        {title: '教练分类', field: 'coachCatName', visible: true, align: 'center', valign: 'middle'},
        {title: '评分', field: 'score', visible: true, align: 'center', valign: 'middle'},
        {title: '被评价次数', field: 'scoreNum', visible: true, align: 'center', valign: 'middle'},
        {title: '已预约次数', field: 'saleNum', visible: true, align: 'center', valign: 'middle'},
        {title: '内部电话', field: 'telephone', visible: true, align: 'center', valign: 'middle'},
        {title: '排序', field: 'seqNum', visible: false, align: 'center', valign: 'middle'},
        {title: '是否冻结', field: 'ifFrozen', visible: true, align: 'center', valign: 'middle',
            formatter : function (value, row, index) {
                if (value == 'T') {
                    return '是';
                }
                if (value == 'F') {
                    return '否';
                }
            }
        },
        {title: '是否推荐', field: 'ifRecommend', visible: true, align: 'center', valign: 'middle',
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
Coach.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Coach.seItem = selected[0];
        return true;
    }
};

/***
 * 教练历史评分详情
 */
Coach.evaluate = function () {
	if (this.check()) {
	    var index = layer.open({
	        type: 2,
	        title: '历史评分详情',
            area: ['1200px', '600px'], //宽高
	        fix: false, //不固定
	        maxmin: true,
	        content: Labi.ctxPath + '/coach/coach_evaluate/'+ Coach.seItem.id
	    });
	    this.layerIndex = index;
	}
};

/**
 * 点击添加教练管理
 */
Coach.openAddCoach = function () {
    var index = layer.open({
        type: 2,
        title: '教练添加',
        area: ['1000px', '520px'], // 宽高
        fix: false, // 不固定
        maxmin: true,
        content: Labi.ctxPath + '/coach/coach_add'
    });
    Coach.seItem = null;//添加时清空富文本缓存
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 打开查看教练管理详情
 */
Coach.openCoachDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '教练管理详情',
            area: ['1000px', '520px'], // 宽高
            fix: false, // 不固定
            maxmin: true,
            content: Labi.ctxPath + '/coach/coach_update/' + Coach.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);
    }
};

/**
 * 删除教练
 */
Coach.delete = function () {
    if (this.check()) {
    	
    	var operation = function(){
	        var ajax = new $ax(Labi.ctxPath + "/coach/delete", function (data) {
	            Labi.success("删除成功!");
	            Coach.table.refresh();
	        }, function (data) {
	            Labi.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("coachId",Coach.seItem.id);
	        ajax.start();
    	};
    	Labi.confirm("是否删除?",operation);
    }
};

/**
 * 行点击事件
 */
Coach.clickRow = function (row) {
    setTimeout(function () {
        var selected = $('#' + Coach.id).bootstrapTable('getSelections');
        if (selected.length === 1) {
        	//冻结、解冻
            if (selected[0].ifFrozen == 'T') {
                $('#but_frozen').html('<i class="fa fa-arrow-down"></i> 解冻');
            } else {
                $('#but_frozen').html('<i class="fa fa-arrow-up"></i> 冻结');
            }
            
            //推荐、取消推荐
            if (selected[0].ifRecommend == 'T') {
                $('#but_recommend').html('<i class="fa fa-arrow-down"></i> 取消推荐');
            } else {
                $('#but_recommend').html('<i class="fa fa-arrow-up"></i> 推荐');
            }
        }
    }, 500)
};

/**
 * 冻结/解冻
 */
Coach.frozen = function () {
    if (this.check()) {
    	var frozen=null;
    	//判断该条数据冻结状态
    	if(Coach.seItem.ifFrozen=="T"){
    		frozen="F"
    	}else{
    		frozen="T"
    	}
    	var operation = function(){
	        var ajax = new $ax(Labi.ctxPath + "/coach/frozen", function (data) {
	            Labi.success("冻结成功!");
	            Coach.table.refresh();
	        }, function (data) {
	            Labi.error("冻结失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("coachId",Coach.seItem.id);
	        ajax.set("frozen",frozen);
	        ajax.start();
    	};
    	Labi.confirm("是否确认操作?",operation);
    }
};

/**
 * 推荐/取消推荐
 */
Coach.recommend = function () {
    if (this.check()) {
    	var recommend=null;
    	//判断该条数据冻结状态
    	if(Coach.seItem.ifRecommend=="T"){
    		recommend="F"
    	}else{
    		recommend="T"
    	}
    	var operation = function(){
	        var ajax = new $ax(Labi.ctxPath + "/coach/recommend", function (data) {
	            Labi.success("推荐成功!");
	            Coach.table.refresh();
	        }, function (data) {
	            Labi.error("推荐失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("coachId",Coach.seItem.id);
	        ajax.set("recommend",recommend);
	        ajax.start();
    	};
    	Labi.confirm("是否确认操作?",operation);
    }
};

/**
 * 查询教练管理列表
 */
Coach.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['telephone'] = $("#telephone").val();
    queryData['gradeId'] = $("#gradeId").val();
    queryData['catId'] = $("#catId").val();
    Coach.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Coach.initColumn();
    var table = new BSTable(Coach.id, "/coach/list", defaultColunms);
    // table.setPaginationType("client");
    table.setClickRow(Coach.clickRow);
    Coach.table = table.init();
    
    //延迟加载
    setTimeout(function(){
    	Coach.search();
    },500);
    
});
