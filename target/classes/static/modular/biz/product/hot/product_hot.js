/**
 * 热推商品管理管理初始化
 */
var ProductHot = {
    id: "ProductHotTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProductHot.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
            {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '商品名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '链接类型', field: 'productType', visible: true, align: 'center', valign: 'middle',
                formatter: function(value, row, index) {
                    if (value=='news') {
                        return "新闻";
                    }
                    if (value=='coach') {
                        return "教练";
                    }
                    if (value=='product') {
                        return "商品";
                    }
                    if (value=='course') {
                        return "课程";
                    }
                    if (value=='video') {
                        return "视频";
                    }
                    if (value=='show') {
                        return "拉比展示";
                    }
                }
            },
            {title: '排序', field: 'seqNum', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
ProductHot.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length !== 1){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProductHot.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加热推商品管理
 */
ProductHot.openAddProductHot = function () {
    var index = layer.open({
        type: 2,
        title: '添加热推商品管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/productHot/hot_add'
    });
    ProductHot.seItem = null;//添加时清空富文本缓存
    this.layerIndex = index;
    layer.full(index);//全屏显示
};
 

/**
 * 打开查看热推商品详情
 */
ProductHot.openProductHotDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '热推修改',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/productHot/productHot_update/' + ProductHot.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);//全屏显示
    }
};

/**
 * 删除热推商品
 */
ProductHot.delete = function () {
    var selecteds = $('#' + this.id).bootstrapTable('getSelections');
    if(selecteds.length == 0){
        Labi.info("请先选中要删除的记录！");
        return false;
    }
    
    var ids = [];
    for(var i in selecteds){
        ids.push(selecteds[i].id);
    }

    
    var operation = function (){
        var ajax = new $ax(Labi.ctxPath + "/productHot/delete", function (data) {
            Labi.success("删除成功!");
            ProductHot.table.refresh();
        }, function (data) {
            Labi.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("ids",ids.join());
        ajax.start();
    }
    Labi.confirm("确认删除选中的商品吗?", operation);
};
 
/**
 * 查询商品管理列表
 */
ProductHot.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['productType'] = $("#productType").val();
    ProductHot.table.refresh({query: queryData});
};
 

$(function () {
    var defaultColunms = ProductHot.initColumn();
    var table = new BSTable(ProductHot.id, "/productHot/list", defaultColunms);
    //table.setClickRow(ProductHot.clickRow);
    ProductHot.table = table.init();
    
  //延迟加载
//    setTimeout(function(){
//    	ProductHot.search();
//    },1000);
});
