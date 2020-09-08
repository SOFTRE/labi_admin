/**
 * 商品分类推荐管理管理初始化
 */
var ProdCatRecommend = {
    id: "ProdCatRecommendTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProdCatRecommend.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '分类名称', field: 'catName', visible: true, align: 'center', valign: 'middle'},
        {title: '推荐图', field: 'recommendImg', visible: true, align: 'center', valign: 'middle',
            formatter: function(value, row, index) {
                if (Labi.noEmpty(value)) {
                    return "<img class='js-img-open' data-value='"+value+"' src="+ Labi.imgPreFix+value+"?x-oss-process=image/resize,w_200>";
                }else
                    return "-"
            }
        },
        {title: '推荐商品名称', field: 'recommendProdName', visible: true, align: 'center', valign: 'middle'},
        {title: '排序', field: 'seqNum', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ProdCatRecommend.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProdCatRecommend.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加商品分类推荐管理
 */
ProdCatRecommend.openAddProdCatRecommend = function () {
    var index = layer.open({
        type: 2,
        title: '添加商品分类推荐',
        area: ['800px', '570px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/prodCatRecommend/prodCatRecommend_add'
    });
    ProdCatRecommend.seItem = null;//添加时清空富文本缓存
    this.layerIndex = index;
    // layer.full(index);//全屏显示
};

/**
 * 打开查看商品分类推荐管理详情
 */
ProdCatRecommend.openProdCatRecommendDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '商品分类推荐详情',
            area: ['800px', '570px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/prodCatRecommend/prodCatRecommend_update/' + ProdCatRecommend.seItem.id
        });
        this.layerIndex = index;
        // layer.full(index);//全屏显示
    }
};

/**
 * 删除商品分类推荐
 */
ProdCatRecommend.delete = function () {
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Labi.ctxPath + "/prodCatRecommend/delete", function (data) {
                Labi.success("删除成功!");
                ProdCatRecommend.table.refresh();
            }, function (data) {
                Labi.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id",ProdCatRecommend.seItem.id);
            ajax.start();
        }
        Labi.confirm("是否删除?",operation);
    }
};

/**
 * 查询商品分类推荐管理列表
 */
ProdCatRecommend.search = function () {
    var queryData = {};
    // queryData['condition'] = $("#condition").val();
    ProdCatRecommend.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ProdCatRecommend.initColumn();
    var table = new BSTable(ProdCatRecommend.id, "/prodCatRecommend/list", defaultColunms);
    //table.setPaginationType("client");
    ProdCatRecommend.table = table.init();

    //延迟加载
    setTimeout(function(){
        ProdCatRecommend.search();
    },500);
});
