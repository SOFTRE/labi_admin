/**
 * 分销商提现管理管理初始化
 */
var BizAgentWithdraw = {
    id: "BizAgentWithdrawTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
BizAgentWithdraw.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '用户id', field: 'agentId', visible: false, align: 'center', valign: 'middle'},
            {title: '分销商姓名', field: 'agentName', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号', field: 'phoneNum', visible: true, align: 'center', valign: 'middle'},
            {title: '提现金额', field: 'totalAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '转账交易流水号', field: 'exchangeNum', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'exchangeBak', visible: true, align: 'center', valign: 'middle'},
            {title: '银行卡号', field: 'bankNo', visible: true, align: 'center', valign: 'middle'},
            {title: '银行卡信息', field: 'bankInfo', visible: true, align: 'center', valign: 'middle'},
            {title: '进度', field: 'progress', visible: true, align: 'center', valign: 'middle',
                formatter: function(value, row, index) {
                    if(value=='wait'){
                        return '待审核';
                    }
                    if(value=='success'){
                        return '成功';
                    }
                    if(value=='fail'){
                        return '拒绝';
                    }
                }
            },
            {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
BizAgentWithdraw.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        BizAgentWithdraw.seItem = selected[0];
        return true;
    }
};
/**
 * 打开拒绝页面
 */
BizAgentWithdraw.openBizAgentWithdrawRefuse = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '拒绝原因',
            area: ['800px', '270px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/bizAgentWithdraw/torefuse'
        });
        this.layerIndex = index;
    }
};

/**
 * 打开审核同意页面
 */
BizAgentWithdraw.openBizAgentWithdrawSuccess = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '提现成功',
            area: ['800px', '370px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/bizAgentWithdraw/tosuccess'
        });
        this.layerIndex = index;
    }
};
/**
 * 查询分销商提现管理列表
 */
BizAgentWithdraw.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['progress'] = $("#progress").val();
    BizAgentWithdraw.table.refresh({query: queryData});
};
/**
 * 行单击事件
 */
BizAgentWithdraw.clickRow = function (row) {
    if(row.progress!='wait'){
        $('#success_btn').prop("disabled",true);
        $('#fail_btn').prop("disabled",true);
    } else {
        $('#success_btn').prop("disabled",false);
        $('#fail_btn').prop("disabled",false);
    }
};
$(function () {
    var defaultColunms = BizAgentWithdraw.initColumn();
    var table = new BSTable(BizAgentWithdraw.id, "/bizAgentWithdraw/list", defaultColunms);
    table.setQueryParams({'progress':'wait'});
    table.setClickRow(BizAgentWithdraw.clickRow);
    BizAgentWithdraw.table = table.init();
});
