/**
 * 退货原因管理管理初始化
 */
var ProdEcOrders = {
    id: "ProdEcOrdersTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProdEcOrders.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '订单号', field: 'orderNo', visible: true, align: 'center', valign: 'middle'},
            {title: '用户名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
            {title: '用户电话', field: 'userPhoneNum', visible: true, align: 'center', valign: 'middle'},
            {title: '退货联系电话', field: 'phoneNum', visible: true, align: 'center', valign: 'middle'},
            {title: '缩略图', field: 'prodThumbImg', visible: true, align: 'center', valign: 'middle',
            	formatter: function(value, row, index) {
                    if (Labi.noEmpty(value)) {
                        return "<img class='js-img-open' data-value='"+value+"' src="+ Labi.imgPreFix+value+"?x-oss-process=image/resize,w_200>";
                    }else
                        return "-"
                }
            },
            {title: '商品名称', field: 'prodName', visible: true, align: 'center', valign: 'middle'},
            {title: '退款总额 [元]', field: 'totalPrice', visible: true, align: 'center', valign: 'middle'},
            {title: '实际退款金额 [元]', field: 'realPrice', visible: true, align: 'center', valign: 'middle'},
            {title: '退款状态', field: 'progress', visible: true, align: 'center', valign: 'middle',
            	formatter : function (value, row, index) {
                    if (value == 'waitReview') {
                        return '待审核';
                    }
                	if (value == 'waitDelivery') {
                        return '等待买家发货';
                    }
                	if (value == 'waitTake') {
                        return '待收货';
                    }
                	if (value == 'refuse') {
                        return '已拒绝';
                    }
                	if (value == 'canceled') {
                        return '已取消';
                    }if (value == 'finish') {
                        return '已完成';
                    }
                }
            },
            {title: '退款原因', field: 'reasonName', visible: true, align: 'center', valign: 'middle'},
            {title: '用户退款备注', field: 'remarks', visible: true, align: 'center', valign: 'middle'},
            {title: '拒绝退款备注', field: 'refuseRemark', visible: true, align: 'center', valign: 'middle'},
            {title: '物流公司', field: 'logisticsName', visible: true, align: 'center', valign: 'middle'},
            {title: '物流单号', field: 'logisticsNo', visible: true, align: 'center', valign: 'middle'},
            {title: '申请时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
            {title: '操作时间', field: 'oprtime', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ProdEcOrders.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProdEcOrders.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加退货问题管理
 */
ProdEcOrders.openAddProdEcOrders = function () {
    var index = layer.open({
        type: 2,
        title: '添加退货原由',
        area: ['900px', '520px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/prodEcOrders/prodEcOrders_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看退货原因管理详情
 */
ProdEcOrders.openProdEcOrdersDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '退款审核',
            area: ['900px', '520px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/prodEcOrders/prodEcOrders_update/' + ProdEcOrders.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 点击设置客服
 */
ProdEcOrders.complete = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '完成退款',
            area: ['800px', '300px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/prodEcOrders/complete/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 查询退货原因管理列表
 */
ProdEcOrders.search = function () {
    var queryData = {};
    queryData['orderNo'] = $("#orderNo").val();
    queryData['userName'] = $("#userName").val();
    queryData['progress'] = $("#progress").val();
    queryData['logisticsNo'] = $("#logisticsNo").val();
    ProdEcOrders.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ProdEcOrders.initColumn();
    var table = new BSTable(ProdEcOrders.id, "/prodEcOrders/list", defaultColunms);
    ProdEcOrders.table = table.init();
    
  //延迟加载
    setTimeout(function(){
    	ProdEcOrders.search();
    },500);
});
