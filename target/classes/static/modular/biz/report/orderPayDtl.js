/**
 * 报表管理初始化
 */
var OrderPayDtl = {
    id: "OrderPayDtlTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
OrderPayDtl.initColumn = function () {
    return [
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '用户名称', field: 'userName', visible: true, align: 'center', valign: 'middle'},
        {title: '用户手机号', field: 'phoneNum', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if(!value){
                    return "-";
                }
                return value;
            }
        },
        {title: '支付订单编号', field: 'pay_no', visible: true, align: 'center', valign: 'middle'},
        {
            title: '业务类型', field: 'type', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                switch (value) {
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
        {title: '公司名称', field: 'deptName', visible: true, align: 'center', valign: 'middle'},
        {title: '支付金额', field: 'total_price', visible: true, align: 'center', valign: 'middle'},
        {title: '支付方式', field: 'pay_way', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                switch (value) {
                    case "wx":
                        return '微信支付';
                    default:
                        return '-';
                }
            }
        },
        {title: '交易流水号', field: 'business_number', visible: true, align: 'center', valign: 'middle'},
        {title: '支付时间', field: 'pay_time', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return value.toString();
            }
        },
    ];
};


/**
 * 查询报表列表
 */
OrderPayDtl.search = function () {
    var queryData = {};
    queryData['businessType'] = $("#businessType").val();
    queryData['deptId'] = $("#deptId").val();
    queryData['beginDate'] = $("#beginDate").val();
    queryData['endDate'] = $("#endDate").val();
    OrderPayDtl.table.refresh({query: queryData});
};
$(function () {
    var defaultColunms = OrderPayDtl.initColumn();
    var table = new BSTable(OrderPayDtl.id, "/reportdtl/list", defaultColunms);
    OrderPayDtl.table = table.init();
});
