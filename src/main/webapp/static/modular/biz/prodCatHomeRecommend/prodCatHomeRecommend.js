/**
 * 商品分类首页推荐管理管理初始化
 */
var ProdCatHomeRecommend = {
    id: "ProdCatHomeRecommendTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProdCatHomeRecommend.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '分类名称', field: 'catName', visible: true, align: 'center', valign: 'middle'},
        {title: '首页推荐图', field: 'recommendHomeImg', visible: true, align: 'center', valign: 'middle',
            formatter: function(value, row, index) {
                if (Labi.noEmpty(value)) {
                    return "<img class='js-img-open' data-value='"+value+"' src="+ Labi.imgPreFix+value+"?x-oss-process=image/resize,w_200>";
                }else
                    return "-"
            }
        },
        {title: '首页推荐商品名称', field: 'recommendHomeProdName', visible: true, align: 'center', valign: 'middle'},
        {title: '排序', field: 'seqNum', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ProdCatHomeRecommend.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProdCatHomeRecommend.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加商品分类首页推荐管理
 */
ProdCatHomeRecommend.openAddProdCatHomeRecommend = function () {
    var index = layer.open({
        type: 2,
        title: '添加商品分类首页推荐',
        area: ['800px', '570px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/prodCatHomeRecommend/prodCatHomeRecommend_add'
    });
    ProdCatHomeRecommend.seItem = null;//添加时清空富文本缓存
    this.layerIndex = index;
    // layer.full(index);//全屏显示
};

/**
 * 打开查看商品分类首页推荐管理详情
 */
ProdCatHomeRecommend.openProdCatHomeRecommendDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '商品分类首页推荐详情',
            area: ['800px', '570px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/prodCatHomeRecommend/prodCatHomeRecommend_update/' + ProdCatHomeRecommend.seItem.id
        });
        this.layerIndex = index;
        // layer.full(index);//全屏显示
    }
};

/**
 * 删除商品分类首页推荐
 */
ProdCatHomeRecommend.delete = function () {
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Labi.ctxPath + "/prodCatHomeRecommend/delete", function (data) {
                Labi.success("删除成功!");
                ProdCatHomeRecommend.table.refresh();
            }, function (data) {
                Labi.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id",ProdCatHomeRecommend.seItem.id);
            ajax.start();
        }
        Labi.confirm("是否删除?",operation);
    }
};

/**
 * 查询商品分类首页推荐管理列表
 */
ProdCatHomeRecommend.search = function () {
    var queryData = {};
    // queryData['condition'] = $("#condition").val();
    ProdCatHomeRecommend.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ProdCatHomeRecommend.initColumn();
    var table = new BSTable(ProdCatHomeRecommend.id, "/prodCatHomeRecommend/list", defaultColunms);
    //table.setPaginationType("client");
    ProdCatHomeRecommend.table = table.init();

    //延迟加载
    setTimeout(function(){
        ProdCatHomeRecommend.search();
    },500);
});
