/**
 * 教练等级初始化
 */
var CoachGrade = {
    id: "CoachGradeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CoachGrade.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '等级名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '标题', field: 'title', visible: true, align: 'center', valign: 'middle'},
           /* {title: '图片', field: 'img', visible: true, align: 'center', valign: 'middle',
            	formatter: function(value, row, index) {
                    if (Labi.noEmpty(value)) {
                        return "<img class='js-img-open' data-value='"+value+"' src="+ Labi.imgPreFix+value+"?x-oss-process=image/resize,w_200>";
                    }else
                        return "-"
                }
            },*/
            {title: '可预约次数', field: 'coachNum', visible: true, align: 'center', valign: 'middle'},
            {title: '价格(元)', field: 'price', visible: true, align: 'center', valign: 'middle'},
            {title: '等级排序', field: 'seqNum', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createtime', visible: false, align: 'center', valign: 'middle'},
            {title: '操作时间', field: 'oprtime', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
CoachGrade.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CoachGrade.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加教练等级
 */
CoachGrade.openAddCoachGrade = function () {
    var totalRows = $('#' + this.id).bootstrapTable('getOptions').totalRows;
    if(totalRows>=5){
        Labi.info("最多添加五个等级！");
        return false;
    }
    var index = layer.open({
        type: 2,
        title: '教练等级添加',
        area: ['900px', '520px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/coachGrade/coachGrade_add'
    });
    CoachGrade.seItem = null;//添加时清空富文本缓存
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 打开查看教练等级详情
 */
CoachGrade.openCoachGradeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '教练等级详情',
            area: ['900px', '520px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/coachGrade/coachGrade_update/' + CoachGrade.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);
    }
};

/**
 * 删除教练等级
 */
CoachGrade.delete = function () {
    if (this.check()) {
    	var operation = function(){
	        var ajax = new $ax(Labi.ctxPath + "/coachGrade/delete", function (data) {
	            Labi.success("删除成功!");
	            CoachGrade.table.refresh();
	        }, function (data) {
	            Labi.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("coachGradeId",CoachGrade.seItem.id);
	        ajax.start();
    	}
    	Labi.confirm("是否删除?",operation);
    }
};

/**
 * 查询教练管理列表
 */
CoachGrade.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    CoachGrade.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = CoachGrade.initColumn();
    var table = new BSTable(CoachGrade.id, "/coachGrade/list", defaultColunms);
    //table.setPaginationType("client");
    CoachGrade.table = table.init();
    
    //延迟加载
    setTimeout(function(){
    	CoachGrade.search();
    },200);
    
});
