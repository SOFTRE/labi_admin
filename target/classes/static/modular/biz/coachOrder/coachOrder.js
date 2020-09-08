/**
 * 预约缴费管理管理初始化
 */
var CoachOrder = {
    id: "CoachOrderTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CoachOrder.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '订单号', field: 'orderNo', visible: false, align: 'center', valign: 'middle'},
        {title: '学员名称', field: 'userName', visible: true, align: 'center', valign: 'middle'},
        {title: '教练名称', field: 'coachName', visible: true, align: 'center', valign: 'middle',
            formatter: function(value, row, index) {
                if (Labi.noEmpty(value)) {
                    return value;
                }else
                    return "（套餐购买）"
            }},
        {title: '教练等级', field: 'coachGradeName', visible: true, align: 'center', valign: 'middle'},
        {title: '教练可预约次数', field: 'coachNum', visible: true, align: 'center', valign: 'middle'},
        {title: '已预约次数', field: 'useNum', visible: true, align: 'center', valign: 'middle'},
        {title: '总价', field: 'totalPrice', visible: true, align: 'center', valign: 'middle'},
        {title: '订单进度', field: 'progress', visible: true, align: 'center', valign: 'middle',
            formatter : function (value, row, index) {
                if (value == 'waitPay') {
                    return '待支付';
                }
                if (value == 'usein') {
                    return '可预约';
                }
                if (value == 'finish') {
                    return '已完成';
                }
            }
        },
        {title: '支付方式', field: 'payWay', visible: true, align: 'center', valign: 'middle',
            formatter : function (value, row, index) {
                if (value == 'wx') {
                    return '微信支付';
                }
                if (value == 'ali') {
                    return '支付宝';
                }
                if (value == 'unionPay') {
                    return '银联支付';
                }
            }
        },
        {title: '备注信息', field: 'remarks', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
CoachOrder.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CoachOrder.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加预约缴费管理
 */
CoachOrder.openAddCoachOrder = function () {
    var index = layer.open({
        type: 2,
        title: '添加预约缴费管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/coachOrder/coachOrder_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看预约缴费管理详情
 */
CoachOrder.openCoachOrderDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '预约缴费管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/coachOrder/coachOrder_update/' + CoachOrder.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除预约缴费管理
 */
CoachOrder.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Labi.ctxPath + "/coachOrder/delete", function (data) {
            Labi.success("删除成功!");
            CoachOrder.table.refresh();
        }, function (data) {
            Labi.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("coachOrderId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询预约缴费管理列表
 */
CoachOrder.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    CoachOrder.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = CoachOrder.initColumn();
    var table = new BSTable(CoachOrder.id, "/coachOrder/list", defaultColunms);
    //table.setPaginationType("client");
    CoachOrder.table = table.init();
    
    //延迟加载
    setTimeout(function(){
    	CoachOrder.search();
    },500);
    
});
