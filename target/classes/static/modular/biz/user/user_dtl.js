/**
 * 用户管理管理初始化
 */
var UserInfo = {
    ProdOrdersTableId: "ProdOrders",	//表格id
    VideoOrdersTableId: "VideoOrders",	//表格id
    CourseOrdersTableId: "CourseOrders",	//表格id
    CoachGradeOrdersTableId: "CoachGradeOrders",	//表格id
    CoachReservationOrdersTableId: "CoachReservationOrders",	//表格id
    layerIndex: -1
};

/**
 * 初始化商城订单表格的列
 */
UserInfo.initProdOrdersColumn = function () {
    return [
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
 * 初始化视频订单表格的列
 */
UserInfo.initVideoOrdersColumn = function () {
    return [
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '订单号', field: 'orderNo', visible: true, align: 'center', valign: 'middle'},
        {title: '缩略图', field: 'prodThumbImg', visible: true, align: 'center', valign: 'middle',
            formatter: function(value, row, index) {
                if (Labi.noEmpty(value)) {
                    return "<img class='js-img-open' data-value='"+value+"' src="+ Labi.imgPreFix+value+"?x-oss-process=image/resize,w_200>";
                }else
                    return "-"
            }
        },
        {title: '视频名称', field: 'prodName', visible: true, align: 'center', valign: 'middle'},
        {title: '售价', field: 'prodSalePrice', visible: true, align: 'center', valign: 'middle'},
        {title: '用户id', field: 'userId', visible: false, align: 'center', valign: 'middle'},
        {title: '用户名', field: 'userName', visible: true, align: 'center', valign: 'middle'},
        {title: '订单总价', field: 'totalPrice', visible: true, align: 'center', valign: 'middle'},
        /*{title: '运费', field: 'freight', visible: true, align: 'center', valign: 'middle'},*/
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
 * 初始化报名订单表格的列
 */
UserInfo.initCourseOrdersColumn = function () {
    return [
        {title: '订单号', field: 'orderNo', visible: false, align: 'center', valign: 'middle'},
        {title: '学员', field: 'userName', visible: true, align: 'center', valign: 'middle'},
        {title: '行业', field: 'industry', visible: true, align: 'center', valign: 'middle'},
        {title: '职位', field: 'job', visible: true, align: 'center', valign: 'middle'},
        {title: '详细地址', field: 'province', visible: true, align: 'center', valign: 'middle',
            formatter : function (value, row, index) {
                return row.province+'-'+row.city+'-'+row.area+row.addressDetail;
            }
        },
        {title: '推荐人', field: 'refName', visible: true, align: 'center', valign: 'middle'},
        {title: '课程分类', field: 'courseCatName', visible: true, align: 'center', valign: 'middle'},
        {title: '课程名称', field: 'courseName', visible: true, align: 'center', valign: 'middle'},
        {title: '课程简介', field: 'courseDes', visible: false, align: 'center', valign: 'middle'},
        {title: '班级名称', field: 'className', visible: true, align: 'center', valign: 'middle'},
        {title: '上课日期', field: 'listenTime', visible: true, align: 'center', valign: 'middle'},
        {title: '调整后上课日期', field: 'adjustListenTime', visible: true, align: 'center', valign: 'middle'},

        {title: '总价', field: 'totalPrice', visible: true, align: 'center', valign: 'middle'},
        {title: '订单进度', field: 'progress', visible: true, align: 'center', valign: 'middle',
            formatter : function (value, row, index) {
                if (value == 'waitPay') {
                    return '待支付';
                }
                if (value == 'waitListen') {
                    return '待听课';
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
        {title: '备注信息', field: 'remarks', visible: false, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createtime', visible: false, align: 'center', valign: 'middle'},
    ];
};
/**
 * 初始化预约缴费订单表格的列
 */
UserInfo.initCoachGradeOrdersColumn = function () {
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
 * 初始化预约记录表格的列
 */
UserInfo.initCoachReservationOrdersColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '学员名称', field: 'userName', visible: true, align: 'center', valign: 'middle'},
        {title: '教练名称', field: 'coachName', visible: true, align: 'center', valign: 'middle'},
        {title: '教练缩略图', field: 'coachImg', visible: true, align: 'center', valign: 'middle',
            formatter: function(value, row, index) {
                if (Labi.noEmpty(value)) {
                    return "<img class='js-img-open' data-value='"+value+"' src="+ Labi.imgPreFix+value+"?x-oss-process=image/resize,w_200>";
                }else
                    return "-"
            }
        },
        // {title: '评价星级', field: 'judgeLevel', visible: true, align: 'center', valign: 'middle'},-->
        {title: '是否完成', field: 'ifFinish', visible: true, align: 'center', valign: 'middle',
            formatter : function (value, row, index) {
                if (value == 'F') {
                    return '否';
                }
                if (value == 'T') {
                    return '是';
                }
            }
        },
        {title: '是否取消', field: 'ifCancel', visible: true, align: 'center', valign: 'middle',
            formatter : function (value, row, index) {
                if (value == 'F') {
                    return '否';
                }
                if (value == 'T') {
                    return '是';
                }
            }
        },
        {title: '是否反馈', field: 'ifFeedback', visible: true, align: 'center', valign: 'middle',
            formatter : function (value, row, index) {
                if (value == 'F') {
                    return '否';
                }
                if (value == 'T') {
                    return '是';
                }
            }
        },
        {title: '开始时间', field: 'startTime', visible: true, align: 'center', valign: 'middle'},
        {title: '结束时间', field: 'endTime', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createtime', visible: false, align: 'center', valign: 'middle'},
    ];
};
$(function () {
    var userId = window.parent.User.seItem.id;
    var prodOrdersTable = new BSTable(UserInfo.ProdOrdersTableId, "/prodOrders/list", UserInfo.initProdOrdersColumn());
    prodOrdersTable.setQueryParams({'userId':userId});
    prodOrdersTable.setHeight(390);
    prodOrdersTable.setSearch(true);
    prodOrdersTable.init();

    var videoOrdersTable = new BSTable(UserInfo.VideoOrdersTableId, "/videoOrders/list", UserInfo.initVideoOrdersColumn());
    videoOrdersTable.setQueryParams({'userId':userId});
    videoOrdersTable.setHeight(390);
    videoOrdersTable.setSearch(true);
    videoOrdersTable.init();

    var courseOrdersTable = new BSTable(UserInfo.CourseOrdersTableId, "/courseOrder/list", UserInfo.initCourseOrdersColumn());
    courseOrdersTable.setQueryParams({'userId':userId});
    courseOrdersTable.setHeight(390);
    courseOrdersTable.setSearch(true);
    courseOrdersTable.init();

    var coachGradeOrdersTable = new BSTable(UserInfo.CoachGradeOrdersTableId, "/coachOrder/list", UserInfo.initCoachGradeOrdersColumn());
    coachGradeOrdersTable.setQueryParams({'userId':userId});
    coachGradeOrdersTable.setHeight(390);
    coachGradeOrdersTable.setSearch(true);
    coachGradeOrdersTable.init();

    var coachReservationOrdersTable = new BSTable(UserInfo.CoachReservationOrdersTableId, "/coachReservation/list", UserInfo.initCoachReservationOrdersColumn());
    coachReservationOrdersTable.setQueryParams({'userId':userId});
    coachReservationOrdersTable.setHeight(390);
    coachReservationOrdersTable.setSearch(true);
    coachReservationOrdersTable.init();
});
