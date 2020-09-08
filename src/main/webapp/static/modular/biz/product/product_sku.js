/**
 * 初始化广告管理详情对话框
 */
var ProductSkuDlg = {
    productInfoData: {},
    productId: null
};


/**
 * 生成SKU
 * @param catSubId
 */
ProductSkuDlg.loadProdSku = function () {
    $("#js-sku-box").empty();
    var ajax = new $ax(Labi.ctxPath + "/prodSku/attrs", function (data) {
        var arr = data.attrs;
        var attrMap = {};
        for (i in arr) {
            if (!attrMap[arr[i].attrId]) {
                attrMap[arr[i].attrId] = [arr[i]];
            } else {
                attrMap[arr[i].attrId].push(arr[i]);
            }
        }
        var attrArr = [];
        for (key in attrMap) {
            attrArr.push(attrMap[key]);
        }
        if (attrArr.length > 0) {
            var res = ProductSkuDlg.combine(attrArr);
            ProductSkuDlg.createSku(res);
        }
        //回显SKU
        var skus = data.skus;
        if (skus.length > 0) {
            for (i in skus) {
                var sku = skus[i];
                var obj = $('div[data-optionids="' + sku.attrOptionIds + '"]');
                obj.find('.js-sku-id').val(sku.id);
                obj.find('.js-sku-sale-price').val(sku.salePrice);
                obj.find('.js-sku-origin-price').val(sku.originPrice);
                obj.find('.js-sku-left-num').val(sku.leftNum);
                obj.find('.js-sku-provide-price').val(sku.providePrice);
            }
        }
    }, function (data) {
        Labi.error("获取SKU失败!");
    });
    ajax.set('productId', ProductSkuDlg.productId);
    ajax.start();
}

//动态生成SKU列表
ProductSkuDlg.combine = function (arr) {
    var r = [];
    (function f(t, a, n) {
        if (n == 0) {
            return r.push(t);
        }
        for (var i = 0; i < a[n - 1].length; i++) {
            f(t.concat(a[n - 1][i]), a, n - 1);
        }
    })([], arr, arr.length);
    return r;
}
/**
 * 生成SKU
 * @param data
 */
ProductSkuDlg.createSku = function (data) {
    for (var int = 0; int < data.length; int++) {
        var ops = data[int];
        var attrOptionIds = [];
        var optNames = [];
        for (i in ops) {
            attrOptionIds.push(ops[i].attrOptionId);
            optNames.push(ops[i].optionName);
        }
        var optIds = attrOptionIds.join('_');
        if ($('div[data-optionids="' + optIds + '"]').length > 0) {
            continue;
        }
        ProductSkuDlg.createSkuHtml(optIds, optNames.join('-'));
    }
    // if(skus.length>0&&!isComplete){
    //     for(i in skus){
    //         var sku = skus[i];
    //         if(sku.isOld){
    //             createSkuHtml(sku.attrOptionIds,sku.optNames);
    //             var obj = $('div[data-optionids="'+sku.attrOptionIds+'"]');
    //             obj.find('.js-sku-num').val(sku.skuNum);
    //             obj.find('.js-sku-id').val(sku.uuid);
    //             obj.find('.js-sku-sale-price').val(sku.salePrice);
    //             obj.find('.js-sku-origin-price').val(sku.originPrice);
    //             obj.find('.js-sku-left-num').val(sku.leftNum);
    //             obj.find('.js-sku-provide-price').val(sku.providePrice);
    //             if(sku.img){
    //                 obj.find('.skuImgShow').html("<div class='expmpleBox'><img style='height:80px; width:auto;' data-value='" + sku.img + "' src='"+ $common.bucketImgUrl() + sku.img + "'/></div>");
    //             }
    //         }
    //     }
    // }
    // setTimeout(function(){
    //     $('div[class^="skuImg_"]').each(function(i,obj){
    //         $(obj).find('div:eq(1)').css('width','82px').css('height','43px');
    //     });
    // },1500);
}
ProductSkuDlg.createSkuHtml = function (optIds, optNames) {
    var sku_html = [];
    sku_html.push('<div class="form-group js-sku-box no-margin" data-optionids="' + optIds + '">');
    sku_html.push('<div style="margin-bottom: 5px;margin-top: 5px;"><span class="sku-name">' + optNames + '</span></div>');
    sku_html.push('<div style="margin-left: 20px">');
    sku_html.push('<input type="hidden" class="js-sku-opt" value="' + optIds + '"> <input type="hidden" class="js-sku-num"> <input type="hidden" class="js-sku-id"> <input type="hidden" class="js-sku-name" value="' + optNames + '">');
    sku_html.push('<label class="control-label">售价：</label><input class="form-control no-margin js-sku-sale-price" min="0" type="number">');
    sku_html.push('<label class="control-label">原价:</label><input class="form-control no-margin js-sku-origin-price" min="0" type="number">');
    sku_html.push('<label class="control-label">库存:</label><input class="form-control no-margin js-sku-left-num" min="0" type="number">');
    sku_html.push('<label class="control-label">供货价:</label><input class="form-control no-margin js-sku-provide-price" min="0" type="number">');
    sku_html.push('<a class="btn btn-sm btn-primary js-save-sku-btn">保存</a> <a class="btn btn-sm btn-danger js-delete-sku-btn">删除</a>');
    sku_html.push('</div>');
    sku_html.push('</div>');
    $('#js-sku-box').append(sku_html.join(' '));
}

/**
 * 绑定事件
 */
ProductSkuDlg.bindEvent = function () {
    /*保存修改sku信息*/
    $(document).on("click", ".js-save-sku-btn", function (e) {
        var _this = this;
        var parent = $(this).parent(),
            id = parent.find('.js-sku-id').val(),
            opt_ids = parent.find('.js-sku-opt').val(),
            sku_num = parent.find('.js-sku-num').val(),
            opt_name = parent.find('.js-sku-name').val(),
            spricte = parent.find('.js-sku-sale-price').val(),
            oprice = parent.find('.js-sku-origin-price').val(),
            leftNum = parent.find('.js-sku-left-num').val(),
            providePrice = parent.find('.js-sku-provide-price').val();
        if (!spricte || !oprice || !leftNum || !providePrice) {
            Labi.error('请确认所有参数都填写完整!');
            return false;
        }
        var data = {
            id: id,
            prodId: ProductSkuDlg.productId,
            attrOptionIds: opt_ids,
            attrOptionNames: opt_name,
            salePrice: spricte,
            originPrice: oprice,
            leftNum: leftNum,
            providePrice: providePrice
        };
        var ajax = new $ax(Labi.ctxPath + "/prodSku/add", function (data) {
            $(_this).closest('.js-sku-box').find('.js-sku-id').val(data);
            Labi.success("商品sku数据保存成功");
        }, function (data) {
            Labi.error("商品sku数据保存失败!");
        });
        ajax.setData(data);
        ajax.start();
    });
    //删除SKU
    $(document).on("click", ".js-delete-sku-btn", function (e) {
        var _this = this;
        var skuId = $(_this).closest('.js-sku-box').find('.js-sku-id').val();
        if (skuId) {
            Labi.confirm("是否删除SKU?", function () {
                var data = {
                    prodSkuId: skuId
                };
                var ajax = new $ax(Labi.ctxPath + "/prodSku/delete", function (data) {
                    Labi.success("商品sku数据删除成功");
                    ProductSkuDlg.loadProdSku();
                }, function (data) {
                    Labi.error("商品sku数据删除失败!");
                });
                ajax.setData(data);
                ajax.start();
            });
        }
    });
}

$(function () {
    if (parent.Product.seItem) {
        ProductSkuDlg.productId = parent.Product.seItem.id;
        ProductSkuDlg.loadProdSku();
        $('#js-sku-product-name').html(parent.Product.seItem.name);
    }
    //绑定事件
    ProductSkuDlg.bindEvent();
});
