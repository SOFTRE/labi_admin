/**
 * 短信发送管理管理初始化
 */
var MessageSend = {
    id: "MessageSendTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MessageSend.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '学员名称', field: 'userName', visible: true, align: 'center', valign: 'middle'},
            {title: '课程名称', field: 'courseName', visible: true, align: 'center', valign: 'middle'},
            {title: '课程分类', field: 'courseCatName', visible: true, align: 'center', valign: 'middle'},
            {title: '上课日期', field: 'listenTime', visible: true, align: 'center', valign: 'middle'},
            {title: '调整后上课日期', field: 'adjustListenTime', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 单发 检查是否选中
 */
MessageSend.check = function () {
	var sendmodel = $("#sendMode").val();
	if(sendmodel=="" || sendmodel==null){
		Labi.info("请先选择发送模板！");
        return false;
	}
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MessageSend.seItem = selected[0];
        return true;
    }
};

 
/**
 * 短信单发
 * 
 */
MessageSend.sendAlone = function () {
	if (this.check()) {
		var templateId=$("#sendMode").val();
        var ajax = new $ax(Labi.ctxPath + "/messageSend/alone", function (data) {
            Labi.success("发送成功!");
            MessageSend.table.refresh();
        }, function (data) {
            Labi.error("发送失败!" + data.responseJSON.message + "!");
        });
        ajax.set("courseOrderId",MessageSend.seItem.id);
        ajax.set("userId",MessageSend.seItem.userId);
        ajax.set("templateId",templateId);
        ajax.start();
	}
};

/**
 * 短信群发
 * 
 */
MessageSend.sendGroup = function () {
	if (this.check()) {
		var templateId=$("#sendMode").val();//模板id
		var condition=$("#condition").val();//学员名称
		var courseName=$("#courseName").val();//学员名称
		var courseCatId=$("#SelExtendMode").val();//学员名称
		
        var ajax = new $ax(Labi.ctxPath + "/messageSend/group", function (data) {
            Labi.success("发送成功!");
            MessageSend.table.refresh();
        }, function (data) {
            Labi.error("发送失败!" + data.responseJSON.message + "!");
        });
        //ajax.set("courseOrderId",MessageSend.seItem.id);//订单
        //ajax.set("userId",MessageSend.seItem.userId);//用户
        ajax.set("templateId",templateId);//模板
        ajax.set("condition",condition);//学员名称
        ajax.set("courseName",courseName);//课程名称
        ajax.set("courseCatId",courseCatId);//课程分类
        ajax.start();
	}
	
};

/**
 * 查询短信发送管理列表
 */
MessageSend.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['courseName'] = $("#courseName").val();
    queryData['extendMode'] = $("#SelExtendMode").val();
    queryData['listenTime'] = $("#listenTime").val();
    MessageSend.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = MessageSend.initColumn();
    var table = new BSTable(MessageSend.id, "/messageSend/list", defaultColunms);
    //table.setPaginationType("client");
    MessageSend.table = table.init();
});
