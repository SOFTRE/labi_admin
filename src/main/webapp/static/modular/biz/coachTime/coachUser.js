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
        {field: 'selectItem', radio: true},
            {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '用户id', field: 'userId', visible: false, align: 'center', valign: 'middle'},
            {title: '头像', field: 'img', visible: true, align: 'center', valign: 'middle',
                formatter: function(value, row, index) {
                    if (Labi.noEmpty(value)) {
                        
                        return "<img class='js-img-open' data-value='"+value+"' src="+ Labi.imgPreFix+value+"?x-oss-process=image/resize,w_200>";
                    }else
                        return "-"
                }},
            {title: '昵称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号', field: 'tetlephone', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'}
    ];
};
/**
 * 关闭此对话框
 */
User.close = function() {
	console.log("-------------------")
    parent.layer.close(window.parent.CoachTimeInfoDlg.layerIndex);
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
 * 选择用户
 */
User.choose = function () {
    if (this.check()) {
        parent.$("#userId").val(User.seItem.userId);
        parent.$("#chooseUserId").val(User.seItem.name);
        User.close();
    }
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
    var table = new BSTable(User.id, "/coach/list", defaultColunms);
    table.setQueryParams({'ifCoach':'F'});
    User.table = table.init();
    //延迟加载
    setTimeout(function(){
        User.search();
    },300);
});
