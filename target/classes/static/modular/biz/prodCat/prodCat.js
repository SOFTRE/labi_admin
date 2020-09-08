/**
 * 商品分类管理初始化
 */
var ProdCat = {
    id: "ProdCatTable",	//表格id
    attrId: "ProdCatAttrTable",	//表格id
    attrOptionId: "ProdCatAttrOptionTable",	//表格id
    seItem: null,		//选中的条目
    seItemAttr: null,		//选中的条目
    seItemAttrOption: null,		//选中的条目
    table: null,
    attrTable: null,
    attrOptionTable: null,
    layerIndex: -1,
    dataCatId:null,
    dataCatAttrId:null
};

/**
 * 初始化表格的列
 */
ProdCat.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '分类名称', field: 'catName', visible: true, align: 'left', valign: 'middle'},
        {title: '图片', field: 'img', visible: true, align: 'center', valign: 'middle',
            formatter: function(value, row, index) {
                if (Labi.noEmpty(value)) {
                    return "<img class='js-img-open' data-value='"+value+"' src="+ Labi.imgPreFix+value+"?x-oss-process=image/resize,w_200>";
                }else
                    return "-"
            }
        }
    ];
};
/**
 * 初始化属性表格的列
 */
ProdCat.initAttrColumn = function () {
    return [
        {field: 'selectItem1', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '属性名称', field: 'attrName', visible: true, align: 'center', valign: 'middle'}
    ];
};
/**
 * 初始化属性可选项表格的列
 */
ProdCat.initAttrOptionColumn = function () {
    return [
        {field: 'selectItem2', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '属性值名称', field: 'optionName', visible: true, align: 'center', valign: 'middle'}
    ];
};
/**
 * 检查是否选中
 */
ProdCat.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中分类表格中的某一记录！");
        return false;
    }else{
        ProdCat.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加商品分类
 */
ProdCat.openAddProdCat = function () {
    var index = layer.open({
        type: 2,
        title: '添加商品分类',
        fix: false, //不固定
        area: ['800px', '470px'], //宽高
        maxmin: true,
        content: Labi.ctxPath + '/prodCat/prodCat_add'
    });
    this.layerIndex = index;
    //layer.full(index);
};

/**
 * 打开查看商品分类详情
 */
ProdCat.openProdCatDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '商品分类详情',
            area: ['800px', '470px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/prodCat/prodCat_update/' + ProdCat.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除商品分类
 */
ProdCat.delete = function () {
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Labi.ctxPath + "/prodCat/delete", function (data) {
                Labi.success("删除成功!");
                ProdCat.search();
                ProdCat.attrTable.btInstance.bootstrapTable('removeAll');
                ProdCat.attrOptionTable.btInstance.bootstrapTable('removeAll');
            }, function (data) {
                Labi.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id",ProdCat.seItem.id);
            ajax.start();
        }
        Labi.confirm("是否删除该分类?", operation);
    }
};
/**
 * 删除商品分类属性
 */
ProdCat.deleteCatAttr = function () {
    if (this.checkAttr()) {
        var operation = function(){
            var ajax = new $ax(Labi.ctxPath + "/prodCatAttr/delete", function (data) {
                Labi.success("删除成功!");
                ProdCat.attrTable.refresh();
                ProdCat.attrOptionTable.btInstance.bootstrapTable('removeAll');
            }, function (data) {
                Labi.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id",ProdCat.seItemAttr.id);
            ajax.start();
        }
        Labi.confirm("是否删除该分类属性?", operation);
    }
};
/**
 * 删除商品分类属性可选项
 */
ProdCat.deleteCatAttrOption = function () {
    if (this.checkAttrOption()) {
        var operation = function(){
            var ajax = new $ax(Labi.ctxPath + "/prodCatAttrOption/delete", function (data) {
                Labi.success("删除成功!");
                ProdCat.attrOptionTable.refresh();
            }, function (data) {
                Labi.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id",ProdCat.seItemAttrOption.id);
            ajax.start();
        }
        Labi.confirm("是否删除该分类属性可选项?", operation);
    }
};

/**
 * 分类行单击事件
 */
ProdCat.catClickRow = function (row) {
    var queryData = {};
    queryData['catId'] = row.id;
    ProdCat.attrTable.refresh({query: queryData});
    ProdCat.attrOptionTable.btInstance.bootstrapTable('removeAll');
    ProdCat.dataCatId = row.id;
};
/**
 * 分类属性行单击事件
 */
ProdCat.catAttrClickRow = function (row) {
    var queryData = {};
    queryData['attrId'] = row.id;
    ProdCat.attrOptionTable.refresh({query: queryData});
    ProdCat.dataCatAttrId = row.id;
};
/**
 * 查询商品分类管理列表
 */
ProdCat.search = function () {
    var queryData = {};
    $('#'+ProdCat.id).bootstrapTable('refresh',{query: queryData});
};
/**
 * 查询商品分类管理列表
 */
ProdCat.initCatTable = function () {
    ProdCat.table = $('#'+ProdCat.id).bootstrapTable({
        url: Labi.ctxPath + "/prodCat/list",         //请求后台的URL（*）
        method: 'post',                      //请求方式（*）
        toolbar: '#ProdCatTableToolbar',     //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: false,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        showExport: true,                     //是否显示导出
        sortName:'addTime',
        clickToSelect:true,
        sortOrder:'desc',
        queryParamsType: 'limit', 	//默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
        treeView: true,
        treeId: "id",
        treeField: "catName",
        treeRootLevel: 1,
        exportDataType: "all",              //basic', 'all', 'selected'.
        sidePagination: "server",
        pageNumber:1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: true,
        showExport:false,
        showRefresh: true,                //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        height: document.body.offsetHeight-48-35-40-14-7,                  //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: ProdCat.initColumn()[1]["field"],                     //每一行的唯一标识，一般为主键列
        undefinedText:"-",
        showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        showColumns:true,
        minimumCountColumns:2,
        columns: ProdCat.initColumn(),
        responseHandler : function(res) {
            return {
                total : res.total,
                rows : res.records
            };
        },
        onClickRow: ProdCat.catClickRow,
        onLoadSuccess: function(){
            $('.js-img-open').on('mouseover', function(e){
                var imgUrl = e.target.src;
                toolTip("<img src='" + imgUrl + "' />");
            });
            $('.js-img-open').on('mouseout', function(e){
                toolTip();
            });
        }
    });
};
/**
 * 检查是否选中
 */
ProdCat.checkAttr = function () {
    var selected = $('#' + this.attrId).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中属性表格中的某一记录！");
        return false;
    }else{
        ProdCat.seItemAttr = selected[0];
        return true;
    }
};
/**
 * 点击添加商品分类属性
 */
ProdCat.openAddProdCatAttr = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '添加商品分类属性',
            fix: false, //不固定
            area: ['800px', '270px'], //宽高
            maxmin: true,
            content: Labi.ctxPath + '/prodCatAttr/add'
        });
        this.layerIndex = index;
        //layer.full(index);
    }
};

/**
 * 打开查看商品分类属性详情
 */
ProdCat.openProdCatAttrDetail = function () {
    if (this.checkAttr()&&this.check()) {
        var index = layer.open({
            type: 2,
            title: '商品分类属性详情',
            area: ['800px', '270px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/prodCatAttr/update/' + ProdCat.seItemAttr.id
        });
        this.layerIndex = index;
    }
};
/**
 * 检查是否选中
 */
ProdCat.checkAttrOption = function () {
    var selected = $('#' + this.attrOptionId).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中属性可选项表格中的某一记录！");
        return false;
    }else{
        ProdCat.seItemAttrOption = selected[0];
        return true;
    }
};
/**
 * 点击添加商品分类属性可选项
 */
ProdCat.openAddProdCatAttrOption = function () {
    if (this.checkAttr()) {
        var index = layer.open({
            type: 2,
            title: '添加商品分类属性',
            fix: false, //不固定
            area: ['800px', '270px'], //宽高
            maxmin: true,
            content: Labi.ctxPath + '/prodCatAttrOption/add'
        });
        this.layerIndex = index;
        //layer.full(index);
    }
};

/**
 * 打开查看商品分类属性可选项详情
 */
ProdCat.openProdCatAttrOptionDetail = function () {
    if (this.checkAttrOption()&&this.checkAttr()) {
        var index = layer.open({
            type: 2,
            title: '商品分类属性详情',
            area: ['800px', '270px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/prodCatAttrOption/update/' + ProdCat.seItemAttrOption.id
        });
        this.layerIndex = index;
    }
};
$(function () {
    //初始化分类表格
    ProdCat.initCatTable();
    //属性表格
    var attrColumn = ProdCat.initAttrColumn();
    var attrOptionColumn = ProdCat.initAttrOptionColumn();
    // var table = new BSTable(ProdCat.id, "/prodCat/list", defaultColunms);
    //属性可选项表格
    var attrTable = new BSTable(ProdCat.attrId, "/prodCatAttr/list", attrColumn);
    var attrOptionTable = new BSTable(ProdCat.attrOptionId, "/prodCatAttrOption/list", attrOptionColumn);
    //初始化属性表格
    attrTable.setPaginationType("client");
    attrTable.setShowColumns(false);
    attrTable.setShowRefresh(false);
    attrTable.setPagination(false);
    attrTable.setShowExport(false);
    attrTable.setClickRow(ProdCat.catAttrClickRow);
    //初始化属性表格
    attrOptionTable.setPaginationType("client");
    attrOptionTable.setShowColumns(false);
    attrOptionTable.setShowRefresh(false);
    attrOptionTable.setPagination(false);
    attrOptionTable.setShowExport(false);

    //ProdCat.table = table.init();
    ProdCat.attrTable = attrTable.init();
    ProdCat.attrOptionTable = attrOptionTable.init();

    //延迟加载
    setTimeout(function(){
        ProdCat.search();
    },500);
});
