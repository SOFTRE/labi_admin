/**
 * 商品管理管理初始化
 */
var Product = {
    id: "ProductTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Product.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '商品名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {
            title: '缩略图', field: 'thumbImg', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (Labi.noEmpty(value)) {
                    return "<img class='js-img-open' data-value='" + value + "' src=" + Labi.imgPreFix + value + "?x-oss-process=image/resize,w_200>";
                } else
                    return "-"
            }
        },
        {title: '类目名称', field: 'catName', visible: true, align: 'center', valign: 'middle'},
        {title: '累计销量', field: 'saleNum', visible: true, align: 'center', valign: 'middle', sortable: true},
        {
            title: '是否上架', field: 'ifShelf', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value == 'T') {
                    return "上架";
                }
                if (value == 'F') {
                    return "下架";
                }
            }
        },
        {
            title: '是否推荐', field: 'ifRecommend', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
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
 * 检查是否选中
 */
Product.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length !== 1) {
        Labi.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Product.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加商品管理
 */
Product.openAddProduct = function () {
    var index = layer.open({
        type: 2,
        title: '添加商品管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/product/product_add'
    });
    Product.seItem = null;//添加时清空富文本缓存
    this.layerIndex = index;
    layer.full(index);//全屏显示
};
/**
 * 点击添加商品SKU管理
 */
Product.openSkus = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '商品SKU管理',
            area: ['1000px', '620px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/product/skus'
        });
        this.layerIndex = index;
    }
};

/**
 * 打开查看商品管理详情
 */
Product.openProductDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '商品管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Labi.ctxPath + '/product/product_update/' + Product.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);//全屏显示
    }
};

/**
 * 删除商品管理
 */
Product.delete = function () {
    var selecteds = $('#' + this.id).bootstrapTable('getSelections');
    if (selecteds.length == 0) {
        Labi.info("请先选中要删除的记录！");
        return false;
    }
    var ids = [];
    for (var i in selecteds) {
        ids.push(selecteds[i].id);
    }

    var operation = function () {
        var ajax = new $ax(Labi.ctxPath + "/product/delete", function (data) {
            Labi.success("删除成功!");
            Product.table.refresh();
        }, function (data) {
            Labi.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("ids", ids.join());
        ajax.start();
    }
    Labi.confirm("确认删除选中的商品吗?", operation);
};
/**
 * 上下架商品管理
 */
Product.shelf = function () {
    if (this.check()) {
        var shelf, msg;
        if (Product.seItem.ifShelf == 'T') {
            shelf = 'F';
            msg = '下架';
        }
        if (Product.seItem.ifShelf == 'F') {
            shelf = 'T';
            msg = '上架';
        }
        var operation = function () {
            var ajax = new $ax(Labi.ctxPath + "/product/shelf", function (data) {
                Labi.success(msg + "成功!");
                Product.table.refresh();
            }, function (data) {
                Labi.error(msg + "失败!" + data.responseJSON.message + "!");
            });
            ajax.set("productId", Product.seItem.id);
            ajax.set("shelf", shelf);
            ajax.start();
        }
        Labi.confirm("确认" + msg + "?", operation);
    }
};
/**
 * 推荐视频
 */
Product.recommend = function () {
    if (this.check()) {
        var ifRecommend, msg;
        if (Product.seItem.ifRecommend == 'T') {
            ifRecommend = 'F';
            msg = '取消推荐';
        }
        if (Product.seItem.ifRecommend == 'F') {
            ifRecommend = 'T';
            msg = '推荐';
        }
        var operation = function () {
            var ajax = new $ax(Labi.ctxPath + "/product/recommend", function (data) {
                Labi.success(msg + "成功!");
                Product.table.refresh();
            }, function (data) {
                Labi.error(msg + "失败!" + data.responseJSON.message + "!");
            });
            ajax.set("productId", Product.seItem.id);
            ajax.set("ifRecommend", ifRecommend);
            ajax.start();
        }
        Labi.confirm("确认" + msg + "?", operation);
    }
};
/**
 * 查询商品管理列表
 */
Product.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['ifRecommend'] = $("#ifRecommend").val();
    var catSubId = $('#catSubId').val();
    var catId = $('#catId').val();
    if (catSubId) {
        queryData['cartIds'] = catSubId;
    } else {
        if (catId) {
            var ids = [];
            ids.push(catId)
            $('#catSubId').find('option:gt(0)').each(function (i, obj) {
                ids.push(obj.value)
            })
            queryData['cartIds'] = ids.join();
        } else {
            queryData['cartIds'] = "";
        }h
    }
    Product.table.refresh({query: queryData});
};
/**
 * 行点击事件
 */
Product.clickRow = function (row) {
    setTimeout(function () {
        var selected = $('#' + Product.id).bootstrapTable('getSelections');
        if (selected.length === 1) {
            if (selected[0].ifShelf == 'T') {
                $('#shelf_btn').html('<i class="fa fa-arrow-down"></i> 下架');
            } else {
                $('#shelf_btn').html('<i class="fa fa-arrow-up"></i> 上架');
            }
            if (selected[0].ifRecommend == 'T') {
                $('#recommend_btn').html('<i class="fa fa-arrow-down"></i> 取消推荐');
            } else {
                $('#recommend_btn').html('<i class="fa fa-arrow-up"></i> 推荐');
            }
        }
    }, 500)
};
/**
 * 生成二级分类
 * @param catSubId
 */
Product.changeCat = function () {
    $("#catSubId").empty();
    var catId = $("#catId").val();
    if (catId) {
        var ajax = new $ax(Labi.ctxPath + "/prodCat/list", function (data) {
            var data = data.records;
            $("#catSubId").append('<option value="">全部</option>');
            for (var key in data) {
                $("#catSubId").append('<option value="' + data[key].id + '">' + data[key].catName + '</option>');
            }
        }, function (data) {
            Labi.error("获取子分类失败!");
        });
        ajax.set('catId', catId);
        ajax.start();
    } else {
        $("#catSubId").append('<option value="">全部</option>');
    }
}
$(function () {
    var defaultColunms = Product.initColumn();
    var table = new BSTable(Product.id, "/product/list", defaultColunms);
    table.setClickRow(Product.clickRow);
    Product.table = table.init();
});
