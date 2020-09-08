/**
 * 订单管理初始化
 */
var ProdOrders = {
    id: "ProdOrdersTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProdOrders.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '订单号', field: 'orderNo', visible: true, align: 'center', valign: 'middle'},
        {title: '用户id', field: 'userId', visible: false, align: 'center', valign: 'middle'},
        {title: '用户名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
        {title: '订单总价', field: 'totalPrice', visible: true, align: 'center', valign: 'middle'},
        /*{title: '运费', field: 'freight', visible: true, align: 'center', valign: 'middle'},*/
        {title: '配送地址信息', field: 'shipInfo', visible: true, align: 'center', valign: 'middle'},
        {
            title: '订单状态', field: 'progress', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                //订单状态：waitPay()、waitDelivery(待发货)、waitTake(待收货)、waitJudge(待评价)、judged(已评价)、canceled(已取消)
                switch (value) {
                    case 'waitPay':
                        return '待支付'
                    case 'waitDelivery':
                        return '待发货'
                    case 'waitTake':
                        return '待收货'
                    case 'waitJudge':
                        return '已完成'
                    case 'judged':
                        return '已完成'
                    case 'canceled':
                        return '已取消'
                }
            }
        },
        {
            title: '支付方式', field: 'payWay', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                //wx（微信支付）、ali（支付宝）、unionPay(银联支付)
                switch (value) {
                    case 'wx':
                        return '微信支付'
                    case 'ali':
                        return '支付宝'
                    case 'unionPay':
                        return '银联支付'
                }
            }
        },
        {title: '备注', field: 'remarks', visible: true, align: 'center', valign: 'middle'},
        {title: '下单时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ProdOrders.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Labi.info("请先选中表格中的某一记录！");
        return false;
    } else {
        ProdOrders.seItem = selected[0];
        return true;
    }
};
/**
 * 打开订单发货
 */
ProdOrders.openProdOrdersDelivery = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '订单发货',
            area: ['800px', '270px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/prodOrders/prodOrders_delivery/' + ProdOrders.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 打开查看订单详情
 */
ProdOrders.openProdOrdersDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '订单详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/prodOrders/prodOrders_dtl/' + ProdOrders.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);//全屏显示
    }
};


/**
 * 查询订单列表
 */
ProdOrders.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['progress'] = $("#progress").val();
    ProdOrders.table.refresh({query: queryData});
};
/**
 * 行点击事件
 */
ProdOrders.clickRow = function (row) {
    if(row.progress=='waitDelivery'){
        $('#delivery_btn').prop("disabled",false);
    } else {
        $('#delivery_btn').prop("disabled",true);
    }
};
$(function () {
    var defaultColunms = ProdOrders.initColumn();
    var table = new BSTable(ProdOrders.id, "/prodOrders/list", defaultColunms);
    table.setClickRow(ProdOrders.clickRow);
    ProdOrders.table = table.init();
});
