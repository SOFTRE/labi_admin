/**
 * 会员管理管理初始化
 */
var User = {
    id: "UserTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
User.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '昵称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {
            title: '头像', field: 'headImg', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (Labi.noEmpty(value)) {
                    return "<img class='js-img-open' data-value='" + value + "' src=" + value + ">";
                } else
                    return "-"
            }
        },
        {
            title: '性别', field: 'sex', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value == 1) {
                    return "男";
                }
                if (value == 2) {
                    return "女";
                }
            }
        },
        {title: '手机号', field: 'phoneNum', visible: true, align: 'center', valign: 'middle'},
        {
            title: '是否教练', field: 'ifCoach', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value == 'T') {
                    return "是";
                }
                if (value == 'F') {
                    return "否";
                }
            }
        },
        {title: '行业', field: 'industry', visible: true, align: 'center', valign: 'middle'},
        {title: '职位', field: 'job', visible: true, align: 'center', valign: 'middle'},
        {title: '详细地址', field: 'province', visible: true, align: 'center', valign: 'middle',
            formatter : function (value, row, index) {
                return row.province+'-'+row.city+'-'+row.area+row.addressDetail;
            }
        },
        {
            title: '是否分销商', field: 'agentStatus', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value == 'T') {
                    return "是";
                }
                if (value == 'F') {
                    return "否";
                }
                if (value == 'W') {
                    return "审核中";
                }
            }
        },
        {title: '推荐者id', field: 'refUserId', visible: false, align: 'center', valign: 'middle'},
        // {title: '真实姓名', field: 'realName', visible: true, align: 'center', valign: 'middle'},
        // {title: '身份证号', field: 'cardId', visible: true, align: 'center', valign: 'middle'},
        // {title: '是否冻结', field: 'ifFrozen', visible: true, align: 'center', valign: 'middle',
        //     formatter: function(value, row, index) {
        //         if (value=='T') {
        //             return "冻结";
        //         }
        //         if (value=='F') {
        //             return "正常";
        //         }
        //     }
        // },
        {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
User.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Labi.info("请先选中表格中的某一记录！");
        return false;
    } else {
        User.seItem = selected[0];
        return true;
    }
};


/**
 * 打开查看会员管理详情
 */
User.openUserDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '会员管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/user/userdtl'
        });
        this.layerIndex = index;
        layer.full(index);//全屏显示
    }
};

/**
 * 查询会员管理列表
 */
User.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['ifCoach'] = $("#ifCoach").val();
    queryData['agentStatus'] = $("#agentStatus").val();
    User.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = User.initColumn();
    var table = new BSTable(User.id, "/user/list", defaultColunms);
    User.table = table.init();

    //延迟加载
    setTimeout(function () {
        User.search();
    }, 500);
});
