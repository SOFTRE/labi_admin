/**
 * 初始化商品管理详情对话框
 */
var ProductInfoDlg = {
    productInfoData : {},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '请填写商品名称'
                }
            }
        },
        'js-cat-select': {
            validators: {
                notEmpty: {
                    message: '请选择分类'
                }
            }
        },
        seqNum: {
            validators: {
                notEmpty: {
                    message: '排序不能为空'
                },
                regexp: {
                    //正则验证
                    regexp: /^[+]{0,1}(\d+)$/,
                    message: '请输入正整数'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
ProductInfoDlg.clearData = function() {
    this.productInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProductInfoDlg.set = function(key, val) {
    this.productInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProductInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProductInfoDlg.close = function() {
    parent.layer.close(window.parent.Product.layerIndex);
}

/**
 * 收集数据
 */
ProductInfoDlg.collectData = function() {
    this
    .set('id')
    .set('catId')
    .set('name')
    .set('des')
    .set('thumbImg')
    .set('moreImg')
    .set('ifShelf')
    .set('seqNum')
    .set('info');
}
/**
 * 验证数据是否为空
 */
ProductInfoDlg.validate = function () {
    $('#productInfoForm').data("bootstrapValidator").resetForm();
    $('#productInfoForm').bootstrapValidator('validate');
    return $("#productInfoForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
ProductInfoDlg.addSubmit = function() {
    //销售属性
    var ok = true;
    var thumbImg = $('#thumbImg').val();
    if(!thumbImg){
        Labi.error('请上传商品缩略图!');
        return false;
    }
    var attrValueArr = [];
    $('select.attr-select-option').each(function() {
        var attrId = $(this).data('attrid');
        var attrName = $(this).data('attrname');
        var valueId = $(this).val();
        if(!attrId ||!valueId){
            ok = false;
            return false;
        } else {
            for(i in valueId){
                var optionName = $('.attr-select-option option[value='+valueId[i]+']').text();
                attrValueArr.push({attrId:attrId,valueId:valueId[i],attrName:attrName,optionName:optionName});
            }
        }
    });
    var attrValueArrStr = JSON.stringify(attrValueArr);

    if(!ok){
        Labi.error('请确认所有参数都填写完整!');
        return false;
    }

    //分类id
    var catId = $("#js-cat-sub-select").val();
    var catName;
    if(!catId){
        catId = $("#js-cat-select").val();
        catName = $("#js-cat-select").find("option:selected").text();
    } else {
        catName = $("#js-cat-sub-select").find("option:selected").text();
    }
    $("#catId").val(catId);

    //是否上线
    var ifShelf = $('input[name="redioIfShelfCheck"]:checked ').val();
    $("#ifShelf").val(ifShelf);

    //富文本获取值
    var info = UE.getEditor('infoEdit').getContent();
    $('#info').val(info);

    this.clearData();
    this.collectData();
    //非空验证
    if (!this.validate()) {
        return false;
    }

    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/product/add", function(data){
        Labi.success("添加成功!");
        window.parent.Product.table.refresh();
        ProductInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.productInfoData);
    ajax.set('attrValueArrStr',attrValueArrStr);
    ajax.set('catName',catName);
    ajax.start();
}

/**
 * 提交修改
 */
ProductInfoDlg.editSubmit = function() {

    //销售属性
    var ok = true;
    var thumbImg = $('#thumbImg').val();
    if(!thumbImg){
        Labi.error('请上传商品缩略图!');
        return false;
    }
    var attrValueArr = [];
    $('select.attr-select-option').each(function() {
        var attrId = $(this).data('attrid');
        var attrName = $(this).data('attrname');
        var valueId = $(this).val();
        if(!attrId ||!valueId){
            ok = false;
            return false;
        } else {
            for(i in valueId){
                var optionName = $('.attr-select-option option[value='+valueId[i]+']').text();
                attrValueArr.push({attrId:attrId,valueId:valueId[i],attrName:attrName,optionName:optionName});
            }
        }
    });
    var attrValueArrStr = JSON.stringify(attrValueArr);

    if(!ok){
        Labi.error('请确认所有参数都填写完整!');
        return false;
    }

    //分类id
    var catId = $("#js-cat-sub-select").val();
    var catName;
    if(!catId){
        catId = $("#js-cat-select").val();
        catName = $("#js-cat-select").find("option:selected").text();
    } else {
        catName = $("#js-cat-sub-select").find("option:selected").text();
    }
    $("#catId").val(catId);

    //是否上线
    var ifShelf = $('input[name="redioIfShelfCheck"]:checked ').val();
    $("#ifShelf").val(ifShelf);

    //富文本获取值
    var info = UE.getEditor('infoEdit').getContent();
    $('#info').val(info);

    this.clearData();
    this.collectData();
    //非空验证
    if (!this.validate()) {
        return false;
    }

    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/product/update", function(data){
        Labi.success("修改成功!");
        window.parent.Product.table.refresh();
        ProductInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.productInfoData);
    ajax.set('attrValueArrStr',attrValueArrStr);
    ajax.set('catName',catName);
    ajax.start();
}
ProductInfoDlg.catSelectChange = function(){
    ProductInfoDlg.createCatSub();
}
/**
 * 生成子分类
 * @param catSubId
 */
ProductInfoDlg.createCatSub = function (catSubId){
    $("#js-cat-sub-select").empty();
    var catId = $("#js-cat-select").val();
    if(catId){
        var ajax = new $ax(Labi.ctxPath + "/prodCat/list", function(data){
            var data = data.records;
            $("#js-cat-sub-select").append('<option value="">请选择</option>');
            for (var key in data) {
                $("#js-cat-sub-select").append('<option value="'+data[key].id+'">'+data[key].catName+'</option>');
            }
            if(catSubId){
                $("#js-cat-sub-select option[value='"+catSubId+"']").attr("selected","selected");
            }
            //创建属性
            ProductInfoDlg.createAttr();
        },function(data){
            Labi.error("获取子分类失败!");
        });
        ajax.set('catId',catId);
        ajax.start();
    } else {
        $("#js-cat-sub-select").append('<option value="">请选择</option>');
    }
}
/**
 * 生成属性
 * @param catSubId
 */
ProductInfoDlg.createAttr = function (attrs){
    var catId = $("#js-cat-sub-select").val();
    if(!catId){
        catId = $("#js-cat-select").val();
    }

    $("#goods-attr").empty();
    if(catId){
        var ajax = new $ax(Labi.ctxPath + "/prodCatAttr/attrs", function(data){
            for (var key in data) {
                var $select =
                    $('<select class="selectpicker form-control" multiple data-live-search="true" disabled=true data-attrid="'+data[key].id+'" data-attrname="'+data[key].attrName+'" style="margin:0;width: 150px;display: inline-block;"></select>');
                for (var i in data[key].options) {
                    var option = data[key].options[i];
                    $select.append('<option value="' + option.id + '">' + option.optionName + '</option>');
                }
                var div =
                    '<div class="form-group" style="display: inline-block;margin-right: 25px;">'+
                    '<a class="form-control attr-a" data-attrid="'+data[key].id+'">'+data[key].attrName+
                    '<i class="fa fa-check-square hide"></i>'+
                    '</a>'+
                    '</div>';
                $("#goods-attr").append($(div).append($select));
            }
            $('.selectpicker').selectpicker({
                'selectedText': 'cat'
            });
            //设置属性选中
            for(key in attrs){
                var $a = $('a[data-attrid='+attrs[key].attrId+']');
                $a.find('i').removeClass('hide');
                $a.addClass('attr-check');
                var $select = $('select[data-attrid='+attrs[key].attrId+']');
                $select.find('option[value='+attrs[key].attrOptionId+']').attr("selected","selected");
                $select.prop('disabled',false);
                $select.selectpicker('refresh');
                $select.addClass('attr-select-option');
            }
        },function(data){
            Labi.error("获取分类属性失败!");
        });
        ajax.set('catId',catId);
        ajax.start();
    }
}
/**
 * 修改时属性回显
 * @param catSubId
 */
ProductInfoDlg.loadProdAttr = function (){
    var ajax = new $ax(Labi.ctxPath + "/product/attrs", function(attrs){
        ProductInfoDlg.createAttr(attrs);
    },function(data){
        Labi.error("获取商品属性失败!");
    });
    ajax.set('productId',parent.Product.seItem.id);
    ajax.start();
}

/**
 * 绑定事件
 */
ProductInfoDlg.bindEvent = function (){
    //销售属性选择
    $(document).on("click", ".attr-a", function() {
        $(this).toggleClass('attr-check');
        var $select = $('select[data-attrid='+$(this).data('attrid')+']');
        if($(this).hasClass('attr-check')){
            $(this).find('i').removeClass('hide');
            $select.prop('disabled', false);
            $select.selectpicker('refresh');
            $select.addClass('attr-select-option');
        } else {
            $(this).find('i').addClass('hide');
            $select.prop('disabled', true);
            $select.selectpicker('refresh');
            $select.removeClass('attr-select-option');
        }
    });
}


$(function() {

    //参数校验
    Labi.initValidator("productInfoForm", ProductInfoDlg.validateFields);
    //radio初始化
    $('input.radio').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
        increaseArea: '20%' // optional
    });
    // 初始化图片
    var imgUp = new $WebUpload("thumbImg",Labi.destDir.product);
    imgUp.init();
    // 初始化多上传
    var moreImgUp = new $WebMoreUpload("moreImg",Labi.destDir.product);
    moreImgUp.init();

    //初始化富文本
    var editor = UE.getEditor('infoEdit');

    editor.ready(function() {
        editor.setHeight(500);

        //富文本set值
        if(parent.Product.seItem){
            $.get(Labi.filePreFix + parent.Product.seItem.desFile, function(data) {
                var start = data.indexOf("<body>"),
                    end = data.indexOf('</body>');
                editor.setContent(data.substring(start+6, end));
            });
        } else {
            //富文本清空
            editor.setContent('');
        }
    });
    //下拉框数据回显
    var catId = $('#js-cat-select').find('option:eq(0)').data('id');
    $('#js-cat-select option[value='+catId+']').attr('selected',true);
    var catSubId = $('#js-cat-sub-select').find('option:eq(0)').data('id');
    $('#js-cat-sub-select option[value='+catSubId+']').attr('selected',true);
    //商品属性回显
    if(parent.Product.seItem){
        ProductInfoDlg.loadProdAttr();
    }
    //绑定事件
    ProductInfoDlg.bindEvent();

});
