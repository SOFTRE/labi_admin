/**
 * 用户管理管理初始化
 */
var User = {
    id: "UserTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
User.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
            {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '头像', field: 'headImg', visible: true, align: 'center', valign: 'middle',
                formatter: function(value, row, index) {
                    if (Labi.noEmpty(value)) {
                        return "<img class='js-img-open' src="+value+">";
                    }else
                        return "-"
                }},
            {title: '昵称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号', field: 'phoneNum', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'}
    ];
};
/**
 * 关闭此对话框
 */
User.close = function() {
    parent.layer.close(window.parent.CoachInfoDlg.layerIndex);
}
/**
 * 检查是否选中
 */
User.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        User.seItem = selected[0];
        return true;
    }
};

/**
 * 用户选择短信发送
 * 
 */
User.userSendAlone = function () {
		
		//模板选择校验
		var sendmodel = $("#sendMode").val();
		if(sendmodel=="" || sendmodel==null){
			Labi.info("请先选择发送模板！");
	        return false;
		}
	
		var selecteds = $('#' + this.id).bootstrapTable('getSelections');
	    if(selecteds.length == 0){
	        Labi.info("请先选中一条记录！");
	        return false;
	    }
    
	    var ids = [];
        for(var i in selecteds){
            ids.push(selecteds[i].id);
        }
        alert(ids);
        
		var templateId=$("#sendMode").val();
        var ajax = new $ax(Labi.ctxPath + "/messageSend/usermsgsend", function (data) {
            Labi.success("发送成功!");
            MessageSend.table.refresh();
        }, function (data) {
            Labi.error("发送失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userId",ids.join());
        ajax.set("templateId",templateId);
        ajax.start();
	
};

/**
 * 查询用户管理列表
 */
User.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    User.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = User.initColumn();
    var table = new BSTable(User.id, "/messageSend/seluserlist", defaultColunms);
    //table.setQueryParams({'ifCoach':'F'});
    User.table = table.init();
    //延迟加载
    setTimeout(function(){
        User.search();
    },300);
});
