/**
 * 分销商管理管理初始化
 */
var BizAgent = {
    id: "BizAgentTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
BizAgent.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '分销商姓名', field: 'agentName', visible: true, align: 'center', valign: 'middle'},
        {title: '银行名称', field: 'bankName', visible: true, align: 'center', valign: 'middle'},
        {title: '支行', field: 'bankSubname', visible: true, align: 'center', valign: 'middle'},
        {title: '银行卡号', field: 'bankNo', visible: true, align: 'center', valign: 'middle'},
        {
            title: '合约开始时间', field: 'beginDate', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return Labi.getDate(value);
            }
        },
        {
            title: '合约结束时间', field: 'endDate', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return Labi.getDate(value);
            }
        },
        {title: '手机号', field: 'phoneNum', visible: true, align: 'center', valign: 'middle'},
        {title: '余额', field: 'balance', visible: true, align: 'center', valign: 'middle'},
        {title: '累计收益', field: 'totalBalance', visible: true, align: 'center', valign: 'middle'},
        {title: '累计邀请', field: 'totalSubUser', visible: true, align: 'center', valign: 'middle'},
        {
            title: '状态', field: 'ifFrozen', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return Labi.frozenFormatter(value)
            }
        },
        {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'}
    ];
};
/**
 * 检查是否选中
 */
BizAgent.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Labi.info("请先选中表格中的某一记录！");
        return false;
    } else {
        BizAgent.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加分销商管理
 */
BizAgent.openAddBizAgent = function () {
    var index = layer.open({
        type: 2,
        title: '添加分销商管理',
        area: ['800px', '550px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/bizAgent/bizAgent_add'
    });
    this.layerIndex = index;
    layer.full(index);//全屏显示
};

/**
 * 打开查看分销商管理详情
 */
BizAgent.openBizAgentDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '分销商管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/bizAgent/bizAgent_update/' + BizAgent.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);//全屏显示
    }
};
/**
 * 打开查看分销商下级
 */
BizAgent.openUserNexus = function () {
    if (this.check()) {
        var height = (document.body.offsetHeight-48-35-40-14-7) + "px";
        var index = layer.open({
            type: 2,
            title: '查看分销商下级',
            area: ['800px', height], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/bizAgent/usernexus/' + BizAgent.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);//全屏显示
    }
};
/**
 * 查看收支明细
 */
BizAgent.openMoneyRecord = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '查看分销商收支明细',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/bizAgent/moneyRecord'
        });
        this.layerIndex = index;
        layer.full(index);//全屏显示
    }
};

BizAgent.frozen = function (ifFrozen) {
    var msg = ifFrozen == 'T' ? '冻结' : '解冻';
    if (this.check()) {
        var operation = function () {
            var ajax = new $ax(Labi.ctxPath + "/bizAgent/frozen", function () {
                Labi.success(msg + "成功!");
                BizAgent.table.refresh();
            }, function (data) {
                Labi.error(msg + "失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", BizAgent.seItem.id);
            ajax.set("ifFrozen", ifFrozen);
            ajax.start();
        };
        Labi.confirm("是否" + msg + "该分销商?", operation);
    }
};
/**
 * 查询分销商管理列表
 */
BizAgent.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['beginDate'] = $("#beginDate").val();
    queryData['endDate'] = $("#endDate").val();
    BizAgent.table.refresh({query: queryData});
};
/**
 * 行单击事件
 */
BizAgent.clickRow = function (row) {
    if (row.ifFrozen == 'T') {
        $('#frozen_btn').prop("disabled", true);
        $('#no_frozen_btn').prop("disabled", false);
    } else {
        $('#frozen_btn').prop("disabled", false);
        $('#no_frozen_btn').prop("disabled", true);
    }
};
$(function () {
    var defaultColunms = BizAgent.initColumn();
    var table = new BSTable(BizAgent.id, "/bizAgent/list", defaultColunms);
    table.setClickRow(BizAgent.clickRow);
    BizAgent.table = table.init();
});
