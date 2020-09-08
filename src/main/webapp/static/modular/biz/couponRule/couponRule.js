/**
 * 优惠券管理管理初始化
 */
var CouponRule = {
    id: "CouponRuleTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CouponRule.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '规则名称', field: 'ruleName', visible: true, align: 'center', valign: 'middle'},
            {title: '优惠券名称', field: 'couponName', visible: true, align: 'center', valign: 'middle'},
            {title: '赠送方式', field: 'extendMode', visible: true, align: 'center', valign: 'middle',
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
            {title: '赠送时的最低消费金额', field: 'presentMinVal', visible: true, align: 'center', valign: 'middle'},
            {title: '使用时最低消费金额', field: 'validMinVal', visible: true, align: 'center', valign: 'middle'},
            {title: '面额', field: 'faceValue', visible: true, align: 'center', valign: 'middle'},
            {title: '面额规则', field: 'faceRule', visible: false, align: 'center', valign: 'middle'},
            {title: '发放开始日期', field: 'issueBeginDate', visible: true, align: 'center', valign: 'middle',
            	formatter : function (value, row, index) {
                    return Labi.getDate(value);
            	}
            },
            {title: '发放结束日期', field: 'issueEndDate', visible: true, align: 'center', valign: 'middle',
            	formatter : function (value, row, index) {
                    return Labi.getDate(value);
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
            {title: '限量发行总张数', field: 'limitTtlQty', visible: true, align: 'center', valign: 'middle'},
            {title: '每人限领张数', field: 'limitPerQty', visible: true, align: 'center', valign: 'middle'},
            {title: '已发行总张数', field: 'issuedTtlQty', visible: false, align: 'center', valign: 'middle'},
            {title: '已使用总张数', field: 'usedTtlQty', visible: false, align: 'center', valign: 'middle'},
            {title: '编号前缀', field: 'numPrefix', visible: false, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remarks', visible: false, align: 'center', valign: 'middle'},
            {title: '是否开启', field: 'ifOpen', visible: true, align: 'center', valign: 'middle',
            	formatter : function (value, row, index) {
            		if (value == 'T') {
                        return '是';
                    }
            		if (value == 'F') {
                        return '否';
                    }
            	}
            },
    ];
};

/**
 * 检查是否选中
 */
CouponRule.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CouponRule.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添优惠券告管理
 */
CouponRule.openAddCouponRule = function () {
    var index = layer.open({
        type: 2,
        title: '添加优惠券管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/couponRule/couponRule_add'
    });
    this.layerIndex = index;
    layer.full(index);//全屏显示
};

/**
 * 打开查看优惠券管理详情
 */
CouponRule.openCouponRuleDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '优惠券管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/couponRule/couponRule_update/' + CouponRule.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);//全屏显示
    }
};

/**
 * 删除优惠券管理
 */
CouponRule.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Labi.ctxPath + "/couponRule/delete", function (data) {
            Labi.success("删除成功!");
            CouponRule.table.refresh();
        }, function (data) {
            Labi.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("couponRuleId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询优惠券管理列表
 */
CouponRule.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['extendMode'] = $("#extendMode").val();
    queryData['useMode'] = $("#useMode").val();
    CouponRule.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = CouponRule.initColumn();
    var table = new BSTable(CouponRule.id, "/couponRule/list", defaultColunms);
    //table.setPaginationType("client");
    CouponRule.table = table.init();
});
