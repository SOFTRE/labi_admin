/**
 * 系统管理--用户管理的单例对象
 */
var MgrUser = {
    id: "managerTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptid: 0
};

/**
 * 初始化表格的列
 */
MgrUser.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '账号', field: 'account', align: 'center', valign: 'middle', sortable: true},
        {title: '姓名', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '性别', field: 'sexName', align: 'center', valign: 'middle', sortable: true},
        {title: '角色', field: 'roleName', align: 'center', valign: 'middle', sortable: true},
        {title: '公司', field: 'deptName', align: 'center', valign: 'middle', sortable: true},
        {title: '邮箱', field: 'email', align: 'center', valign: 'middle', sortable: true},
        {title: '电话', field: 'phone', align: 'center', valign: 'middle', sortable: true},
        {
            title: '是否客服', field: 'ifCustomer', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value == 'T') {
                    return "是";
                }
                if (value == 'F') {
                    return "否";
                }
            }
        },
        {title: '创建时间', field: 'createtime', align: 'center', valign: 'middle', sortable: true},
        {title: '状态', field: 'statusName', align: 'center', valign: 'middle', sortable: true}];
    return columns;
};

/**
 * 检查是否选中
 */
MgrUser.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Labi.info("请先选中表格中的某一记录！");
        return false;
    } else {
        MgrUser.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加管理员
 */
MgrUser.openAddMgr = function () {
    var index = layer.open({
        type: 2,
        title: '添加管理员',
        area: ['800px', '560px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/mgr/user_add'
    });
    this.layerIndex = index;
};

/**
 * 点击修改按钮时
 * @param userId 管理员id
 */
MgrUser.openChangeUser = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '编辑管理员',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/mgr/user_edit/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 点击角色分配
 * @param
 */
MgrUser.roleAssign = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '角色分配',
            area: ['300px', '400px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/mgr/role_assign/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除用户
 */
MgrUser.delMgrUser = function () {
    if (this.check()) {

        var operation = function () {
            var userId = MgrUser.seItem.id;
            var ajax = new $ax(Labi.ctxPath + "/mgr/delete", function () {
                Labi.success("删除成功!");
                MgrUser.table.refresh();
            }, function (data) {
                Labi.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("userId", userId);
            ajax.start();
        };

        Labi.confirm("是否删除用户" + MgrUser.seItem.account + "?", operation);
    }
};

/**
 * 冻结用户账户
 * @param userId
 */
MgrUser.freezeAccount = function () {
    if (this.check()) {
        var userId = this.seItem.id;
        var ajax = new $ax(Labi.ctxPath + "/mgr/freeze", function (data) {
            Labi.success("冻结成功!");
            MgrUser.table.refresh();
        }, function (data) {
            Labi.error("冻结失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userId", userId);
        ajax.start();
    }
};

/**
 * 解除冻结用户账户
 * @param userId
 */
MgrUser.unfreeze = function () {
    if (this.check()) {
        var userId = this.seItem.id;
        var ajax = new $ax(Labi.ctxPath + "/mgr/unfreeze", function (data) {
            Labi.success("解除冻结成功!");
            MgrUser.table.refresh();
        }, function (data) {
            Labi.error("解除冻结失败!");
        });
        ajax.set("userId", userId);
        ajax.start();
    }
}

/**
 * 重置密码
 */
MgrUser.resetPwd = function () {
    if (this.check()) {
        var userId = this.seItem.id;
        parent.layer.confirm('是否重置密码为111111？', {
            btn: ['确定', '取消'],
            shade: false //不显示遮罩
        }, function () {
            var ajax = new $ax(Labi.ctxPath + "/mgr/reset", function (data) {
                Labi.success("重置密码成功!");
            }, function (data) {
                Labi.error("重置密码失败!");
            });
            ajax.set("userId", userId);
            ajax.start();
        });
    }
};
/**
 * 设置客服
 * @param userId
 */
MgrUser.setCustomer = function () {
    if (this.check()) {
        var userId = this.seItem.id;
        var ajax = new $ax(Labi.ctxPath + "/mgr/customer", function (data) {
            Labi.success("设置成功!");
            MgrUser.table.refresh();
        }, function (data) {
            Labi.error("设置失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userId", userId);
        ajax.start();
    }
};
/**
 * 点击设置客服
 * @param userId 管理员id
 */
MgrUser.setCustomer = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '设置客服业务类型',
            area: ['800px', '300px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/mgr/user_customer_type_edit/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};
MgrUser.resetSearch = function () {
    $("#name").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    $('#ifCustomer option[value=""]').attr('selected',true);
    MgrUser.search();
}

MgrUser.search = function () {
    var queryData = {};

    queryData['deptid'] = MgrUser.deptid;
    queryData['name'] = $("#name").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    queryData['ifCustomer'] = $("#ifCustomer").val();

    MgrUser.table.refresh({query: queryData});
}

MgrUser.onClickDept = function (e, treeId, treeNode) {
    MgrUser.deptid = treeNode.id;
    MgrUser.search();
};

$(function () {
    var defaultColunms = MgrUser.initColumn();
    var table = new BSTable("managerTable", "/mgr/list", defaultColunms);
    table.setPaginationType("client");
    MgrUser.table = table.init();
    var ztree = new $ZTree("deptTree", "/dept/tree");
    ztree.bindOnClick(MgrUser.onClickDept);
    ztree.init();
});
