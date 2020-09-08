/**
 * 视频管理管理初始化
 */
var Product = {
    id: "ProductTable",	//表格id
    videoTableId: "ProdVideoTable",
    seItem: null,		//选中的条目
    seVideoItem: null,
    table: null,
    videoTable:null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Product.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '课程名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '缩略图', field: 'thumbImg', visible: true, align: 'center', valign: 'middle',
                formatter: function(value, row, index) {
                    if (Labi.noEmpty(value)) {
                        return "<img class='js-img-open' data-value='"+value+"' src="+ Labi.imgPreFix+value+"?x-oss-process=image/resize,w_200>";
                    }else
                        return "-"
                }
            },
            {title: '类目名称', field: 'catName', visible: true, align: 'center', valign: 'middle'},
            {title: '售价', field: 'salePrice', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '累计销量', field: 'saleNum', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '是否免费', field: 'ifFree', visible: true, align: 'center', valign: 'middle',
                formatter: function(value, row, index) {
                    if (value=='T') {
                        return "是";
                    }
                    if (value=='F') {
                        return "否";
                    }
                }
            },
            {title: '是否上架', field: 'ifShelf', visible: true, align: 'center', valign: 'middle',
                formatter: function(value, row, index) {
                    if (value=='T') {
                        return "上架";
                    }
                    if (value=='F') {
                        return "下架";
                    }
                }
            },
            {title: '是否推荐', field: 'ifRecommend', visible: true, align: 'center', valign: 'middle',
                formatter : function (value, row, index) {
                    if (value == 'T') {
                        return '是';
                    }
                    if (value == 'F') {
                        return '否';
                    }
                }
            },
            {title: '排序', field: 'seqNum', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
    ];
};
/**
 * 初始化属性表格的列
 */
Product.initVideoColumn = function () {
    return [
        {field: 'selectItem1', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '视频名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {
            title: '视频大小', field: 'videoSize', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value) {
                    return Labi.bytesToSize(value);
                }
                return '-';
            }
        }
    ];
};
/**
 * 检查是否选中
 */
Product.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Product.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加视频管理
 */
Product.openAddProduct = function () {
    var index = layer.open({
        type: 2,
        title: '添加在线课程',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/video/video_add'
    });
    Product.seItem = null;//添加时清空富文本缓存
    this.layerIndex = index;
    layer.full(index);//全屏显示
};

/**
 * 打开查看视频管理详情
 */
Product.openProductDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '在线课程详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/video/video_update/' + Product.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);//全屏显示
    }
};

/**
 * 删除视频管理
 */
Product.delete = function () {
    if (this.check()) {
        var operation = function (){
            var ajax = new $ax(Labi.ctxPath + "/product/delete", function (data) {
                Labi.success("删除成功!");
                Product.table.refresh();
            }, function (data) {
                Labi.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("productId",Product.seItem.id);
            ajax.start();
        }
        Labi.confirm("确认删除该课程?", operation);
    }
};
/**
 * 上下架视频管理
 */
Product.shelf = function () {
    if (this.check()) {
        var shelf,msg;
        if(Product.seItem.ifShelf=='T'){
            shelf = 'F';
            msg = '下架';
        }
        if(Product.seItem.ifShelf=='F'){
            shelf = 'T';
            msg = '上架';
        }
        var operation = function (){
            var ajax = new $ax(Labi.ctxPath + "/product/shelf", function (data) {
                Labi.success(msg+"成功!");
                Product.table.refresh();
            }, function (data) {
                Labi.error(msg+"失败!" + data.responseJSON.message + "!");
            });
            ajax.set("productId",Product.seItem.id);
            ajax.set("shelf",shelf);
            ajax.start();
        }
        Labi.confirm("确认"+msg+"?", operation);
    }
};
/**
 * 推荐视频
 */
Product.recommend = function () {
    if (this.check()) {
        var ifRecommend,msg;
        if(Product.seItem.ifRecommend=='T'){
            ifRecommend = 'F';
            msg = '取消推荐';
        }
        if(Product.seItem.ifRecommend=='F'){
            ifRecommend = 'T';
            msg = '推荐';
        }
        var operation = function (){
            var ajax = new $ax(Labi.ctxPath + "/product/recommend", function (data) {
                Labi.success(msg+"成功!");
                Product.table.refresh();
            }, function (data) {
                Labi.error(msg+"失败!" + data.responseJSON.message + "!");
            });
            ajax.set("productId",Product.seItem.id);
            ajax.set("ifRecommend",ifRecommend);
            ajax.start();
        }
        Labi.confirm("确认"+msg+"?", operation);
    }
};
/**
 * 查询视频管理列表
 */
Product.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['catId'] = $("#catId").val();
    queryData['ifRecommend'] = $("#ifRecommend").val();
    Product.table.refresh({query: queryData});
};
Product.searchVideo = function () {
    var queryData = {};
    queryData['condition'] = $("#video_condition").val();
    Product.videoTable.refresh({query: queryData});
};

/**
 * 行点击事件
 */
Product.clickRow = function (row) {
    if(row.ifShelf=='T'){
        $('#shelf_btn').html('<i class="fa fa-arrow-down"></i> 下架');
    } else {
        $('#shelf_btn').html('<i class="fa fa-arrow-up"></i> 上架');
    }
    if(row.ifRecommend=='T'){
        $('#recommend_btn').html('<i class="fa fa-arrow-down"></i> 取消推荐');
    } else {
        $('#recommend_btn').html('<i class="fa fa-arrow-up"></i> 推荐');
    }
    var queryData = {};
    queryData['productId'] = row.id;
    Product.videoTable.refresh({query: queryData});
};
/*************************视频*****************************/
/**
 * 点击添加视频
 */
Product.openAddVideo = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '添加在线课程视频',
            area: ['1000px', '520px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/video/prod_video_add'
        });
        Product.seVideoItem = null;
        this.layerIndex = index;
    }
};
/**
 * 检查是否选中
 */
Product.checkVideo = function () {
    var selected = $('#' + this.videoTableId).bootstrapTable('getSelections');
    if(selected.length == 0){
        Labi.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Product.seVideoItem = selected[0];
        return true;
    }
};
/**
 * 打开查看视频详情
 */
Product.openProductInfoDlgVideoDetail = function () {
    if (this.checkVideo()) {
        var index = layer.open({
            type: 2,
            title: '修改在线课程视频',
            area: ['800px', '520px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/video/prod_video_edit/' + Product.seVideoItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除视频
 */
Product.deleteVideo = function () {
    if (this.checkVideo()) {
        var operation = function (){
            var ajax = new $ax(Labi.ctxPath + "/video/video/delete", function (data) {
                Labi.success("删除成功!");
                Product.videoTable.refresh();
            }, function (data) {
                Labi.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("videoId",Product.seVideoItem.id);
            ajax.start();
        }
        Labi.confirm("确认删除该视频?", operation);
    }
};
$(function () {
    var defaultColunms = Product.initColumn();
    var table = new BSTable(Product.id, "/video/list", defaultColunms);
    table.setClickRow(Product.clickRow);
    Product.table = table.init();

    var videoTable = new BSTable(Product.videoTableId, "/video/videos", Product.initVideoColumn());
    videoTable.setShowExport(false);
    videoTable.setShowColumns(false);
    videoTable.setShowRefresh(false);
    Product.videoTable = videoTable.init();

});
