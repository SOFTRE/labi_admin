/**
 * 广告管理管理初始化
 */
var CouponList = {
    id: "CouponListTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CouponList.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '用户姓名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
            {title: '优惠券名称', field: 'couponName', visible: true, align: 'center', valign: 'middle'},
            {title: '发放方式', field: 'extendMode', visible: true, align: 'center', valign: 'middle',
            	formatter : function (value, row, index) {
            		if (value == 'product') {
                        return '商城消费赠送';
                    }
            		if (value == 'coach') {
                        return '预约教练赠送';
                    }
            		if (value == 'course') {
                        return '课程报名赠送';
                    }
            	}
            },
            {title: '使用方式', field: 'useMode', visible: true, align: 'center', valign: 'middle',
            	formatter : function (value, row, index) {
            		if (value == 'all') {
                        return '通用';
                    }
            		if (value == 'product') {
                        return '商城消费';
                    }
            		if (value == 'coach') {
                        return '预约教练';
                    }
            		if (value == 'course') {
                        return '课程报名';
                    }
            	}
            },
            {title: '电子券代码', field: 'ecCode', visible: true, align: 'center', valign: 'middle'},
            {title: '面额', field: 'faceValue', visible: true, align: 'center', valign: 'middle'},
            {title: '是否使用', field: 'ifUsed', visible: true, align: 'center', valign: 'middle',
            	formatter : function (value, row, index) {
            		if (value == 'T') {
                        return '是';
                    }
            		if (value == 'F') {
                        return '否';
                    }
            	}	
            },
            {title: '是否过期', field: 'ifExpired', visible: true, align: 'center', valign: 'middle',
            	formatter : function (value, row, index) {
            		if (value == 'T') {
                        return '是';
                    }
            		if (value == 'F') {
                        return '否';
                    }
            	}
            },
            {title: '使用开始日期', field: 'validBeginDate', visible: true, align: 'center', valign: 'middle',
            	formatter : function (value, row, index) {
                    return Labi.getDate(value);
            	}
            },
            {title: '使用结束日期', field: 'validEndDate', visible: true, align: 'center', valign: 'middle',
            	formatter : function (value, row, index) {
                    return Labi.getDate(value);
            	}
            },
            {title: '使用时最低消费金额', field: 'validMinVal', visible: true, align: 'center', valign: 'middle'},
            {title: '使用时间', field: 'useTime', visible: false, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remarks', visible: false, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
CouponList.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CouponList.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加广告管理
 */
CouponList.openAddCouponList = function () {
    var index = layer.open({
        type: 2,
        title: '添加广告管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/couponList/couponList_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看广告管理详情
 */
CouponList.openCouponListDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '广告管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/couponList/couponList_update/' + CouponList.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除广告管理
 */
CouponList.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Labi.ctxPath + "/couponList/delete", function (data) {
            Labi.success("删除成功!");
            CouponList.table.refresh();
        }, function (data) {
            Labi.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("couponListId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询广告管理列表
 */
CouponList.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['couponName'] = $("#couponName").val();
    queryData['extendMode'] = $("#extendMode").val();
    queryData['useMode'] = $("#useMode").val();
    CouponList.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = CouponList.initColumn();
    var table = new BSTable(CouponList.id, "/couponList/list", defaultColunms);
    //table.setPaginationType("client");
    CouponList.table = table.init();
});
