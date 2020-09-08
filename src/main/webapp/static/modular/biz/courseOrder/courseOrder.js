/**
 * 课程报名管理管理初始化
 */
var CourseOrder = {
    id: "CourseOrderTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CourseOrder.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '订单号', field: 'orderNo', visible: true, align: 'center', valign: 'middle'},
        {title: '付款人手机号', field: 'payUserPhoneNum', visible: true, align: 'center', valign: 'middle'},
        {title: '学员', field: 'userName', visible: true, align: 'center', valign: 'middle'},
        {title: '学员手机', field: 'phoneNum', visible: true, align: 'center', valign: 'middle'},
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
        {title: '上课日期', field: 'adjustListenTime', visible: true, align: 'center', valign: 'middle'},
        {title: '原始上课日期', field: 'listenTime', visible: true, align: 'center', valign: 'middle'},

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
 * 检查是否选中
 */
CourseOrder.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length !== 1){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CourseOrder.seItem = selected[0];
        return true;
    }
};

/**
 * 打开查看课程报名管理详情
 */
CourseOrder.openCourseOrderDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '课程报名管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/courseOrder/courseOrder_update/' + CourseOrder.seItem.id
        });
        this.layerIndex = index;
    }
};


/**
 * 课程签到
 */
CourseOrder.signer = function () {
    var selecteds = $('#' + this.id).bootstrapTable('getSelections');
    if(selecteds.length == 0){
        Labi.info("请先选中一条记录！");
        return false;
    }
    var operation = function (){
        var ids = [];
        for(var i in selecteds){
            ids.push(selecteds[i].id);
        }

        var ajax = new $ax(Labi.ctxPath + "/courseOrder/signer", function (data) {
            Labi.success("签到成功!");
            CourseOrder.table.refresh();
        }, function (data) {
            Labi.error("签到失败!" + data.responseJSON.message + "!");
        });
        ajax.set("courseOrderId",ids.join());
        //ajax.set("ids",ids.join());
        ajax.start();
    }
    Labi.confirm("确认签到?", operation);
};
/**
 * 分成
 */
CourseOrder.divided = function () {
    if (this.check()) {
        if(CourseOrder.seItem.ifDivided=='T'){
            Labi.info("此报名记录已分成！");
            return false;
        }
        var index = layer.open({
            type: 2,
            title: '选择分成分销商',
            area: ['800px', '480px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/bizAgent/choose'
        });
        this.layerIndex = index;
    }
};
/**
 * 查询课程报名管理列表
 */
CourseOrder.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['courseName'] = $("#courseName").val();
    queryData['extendMode'] = $("#SelExtendMode").val();
    queryData['className'] = $("#className").val();
    queryData['ifDivided'] = $("#ifDivided").val();
    CourseOrder.table.refresh({query: queryData});
};
/***
 * 重置
 */
CourseOrder.txtReset = function () {
    $("#condition").val("");//学员姓名
    $("#courseName").val("");//课程名称
    $("#className").val("");//班级名称
    $("#SelExtendMode").val("");//班级名称
};
$(function () {
    var defaultColunms = CourseOrder.initColumn();
    var table = new BSTable(CourseOrder.id, "/courseOrder/list", defaultColunms);
    CourseOrder.table = table.init();
});
