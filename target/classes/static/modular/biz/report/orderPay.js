/**
 * 报表管理初始化
 */
var OrderPay = {
    id: "OrderPayTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
OrderPay.initColumn = function () {
    return [
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {
            title: '时间', field: 'time', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return value.toString();
            }
        },
        {
            title: '业务类型', field: 'businessType', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                var businessType = $("#businessType").val();
                switch (businessType) {
                    case "":
                        return '全部';
                    case "coach":
                        return '教练预约';
                    case "course":
                        return '课程报名';
                    case "prod":
                        return '商城';
                    case "video":
                        return '视频';
                    default:
                        return '-';
                }
            }
        },
        {title: '订单数量', field: 'num', visible: true, align: 'center', valign: 'middle'},
        {title: '总收入', field: 'totalPrice', visible: true, align: 'center', valign: 'middle'}
    ];
};


/**
 * 查询报表列表
 */
OrderPay.search = function () {
    var queryData = {};
    queryData['type'] = $("#type").val();
    queryData['businessType'] = $("#businessType").val();
    queryData['deptId'] = $("#deptId").val();
    queryData['beginDate'] = $("#beginDate").val();
    queryData['endDate'] = $("#endDate").val();
    OrderPay.table.refresh({query: queryData});
};
$(function () {
    var defaultColunms = OrderPay.initColumn();
    var table = new BSTable(OrderPay.id, "/report/orderpay/list", defaultColunms);
    table.setQueryParams({'type': 'all'});
    OrderPay.table = table.init();
});
