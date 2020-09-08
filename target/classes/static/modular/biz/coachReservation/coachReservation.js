/**
 * 预约反馈管理管理初始化
 */
var CoachReservation = {
    id: "CoachReservationTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列 
 */
CoachReservation.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'ID', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '学员名称', field: 'userName', visible: true, align: 'center', valign: 'middle'},
            {title: '教练名称', field: 'coachName', visible: true, align: 'center', valign: 'middle'},
            {title: '教练缩略图', field: 'coachImg', visible: true, align: 'center', valign: 'middle',
            	formatter: function(value, row, index) {
                    if (Labi.noEmpty(value)) {
                        return "<img class='js-img-open' data-value='"+value+"' src="+ Labi.imgPreFix+value+"?x-oss-process=image/resize,w_200>";
                    }else
                        return "-"
                }
            },
           // {title: '评价星级', field: 'judgeLevel', visible: true, align: 'center', valign: 'middle'},-->
            {title: '是否完成', field: 'ifFinish', visible: true, align: 'center', valign: 'middle',
            	formatter : function (value, row, index) {
                    if (value == 'F') {
                        return '否';
                    }
                	if (value == 'T') {
                        return '是';
                    }
                }
            },
            {title: '是否取消', field: 'ifCancel', visible: true, align: 'center', valign: 'middle',
            	formatter : function (value, row, index) {
                    if (value == 'F') {
                        return '否';
                    }
                	if (value == 'T') {
                        return '是';
                    }
                }
            },
            {title: '是否反馈', field: 'ifFeedback', visible: true, align: 'center', valign: 'middle',
            	formatter : function (value, row, index) {
                    if (value == 'F') {
                        return '否';
                    }
                	if (value == 'T') {
                        return '是';
                    }
                }
            },
            {title: '开始时间', field: 'startTime', visible: true, align: 'center', valign: 'middle'},
            {title: '结束时间', field: 'endTime', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createtime', visible: false, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
CoachReservation.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CoachReservation.seItem = selected[0];
        console.log(selected[0])
        return true;
    }
};


/**
 * 点击选择会员
 */
CoachReservation.evaluateInfo = function () {
	if (this.check()) {
	    var index = layer.open({
	        type: 2,
	        title: '预约反馈详情',
	        area: ['1200px', '600px'], //宽高
	        fix: false, //不固定
	        maxmin: true,
	        content: Labi.ctxPath + '/coachReservation/coachReservation_evaluate/'+ CoachReservation.seItem.id
	    });
	}
    this.layerIndex = index;
};

/**
 * 点击添加预约反馈管理
 */
CoachReservation.openAddCoachReservation = function () {
    var index = layer.open({
        type: 2,
        title: '添加预约反馈管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/coachReservation/coachReservation_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看预约反馈管理详情
 */
CoachReservation.openCoachReservationDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '预约反馈管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/coachReservation/coachReservation_update/' + CoachReservation.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除预约反馈管理
 */
CoachReservation.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Labi.ctxPath + "/coachReservation/delete", function (data) {
            Labi.success("删除成功!");
            CoachReservation.table.refresh();
        }, function (data) {
            Labi.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("coachReservationId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询预约反馈管理列表
 */
CoachReservation.search = function () {
    var queryData = {};
    queryData['username'] = $("#username").val();//用户名
    queryData['coachname'] = $("#coachname").val();//教练名
    CoachReservation.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = CoachReservation.initColumn();
    var table = new BSTable(CoachReservation.id, "/coachReservation/list", defaultColunms);
    //table.setPaginationType("client");
    CoachReservation.table = table.init();
    
  //list延迟加载
    setTimeout(function(){
    	CoachReservation.search();
    },500);
});
