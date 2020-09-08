/**
 * 订单详情 管理初始化
 */
var ProdOrdersDtl = {
    id: "ProdOrdersDtlTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProdOrdersDtl.initColumn = function () {
    return [
        {field: 'selectItem', radio: false},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '缩略图', field: 'prodThumbImg', visible: true, align: 'center', valign: 'middle',
            formatter: function(value, row, index) {
                if (Labi.noEmpty(value)) {
                    return "<img class='js-img-open' data-value='"+value+"' src="+ Labi.imgPreFix+value+"?x-oss-process=image/resize,w_200>";
                }else
                    return "-"
            }
        },
        {title: '商品名称', field: 'prodName', visible: true, align: 'center', valign: 'middle'},
        {title: '购买单价', field: 'prodSalePrice', visible: true, align: 'center', valign: 'middle'},
        {title: '购买数量', field: 'buyNum', visible: true, align: 'center', valign: 'middle'},
        {title: '属性描述', field: 'prodAttrInfo', visible: true, align: 'center', valign: 'middle'},
        {title: '备注', field: 'remarks', visible: true, align: 'center', valign: 'middle'}
    ];
};


/**
 * 查询订单详情 列表
 */
ProdOrdersDtl.search = function () {
    var queryData = {};
    ProdOrdersDtl.table.refresh({query: queryData});
};
$(function () {
    var defaultColunms = ProdOrdersDtl.initColumn();
    var table = new BSTable(ProdOrdersDtl.id, "/prodOrders/dtl", defaultColunms);
    table.setQueryParams({'prodOrdersId':$('#orderId').val()});
    table.setPaginationType("client");
    ProdOrdersDtl.table = table.init();
});
