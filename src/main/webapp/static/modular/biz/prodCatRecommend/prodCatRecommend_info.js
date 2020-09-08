/**
 * 初始化商品分类推荐管理详情对话框
 */
var ProdCatRecommendInfoDlg = {
    prodCatRecommendInfoData : {},
    validateFields: {
        id: {
            validators: {
                notEmpty: {
                    message: '请选择分类'
                }
            }
        },
        recommendProdId: {
            validators: {
                notEmpty: {
                    message: '请选择推荐商品'
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
ProdCatRecommendInfoDlg.clearData = function() {
    this.prodCatRecommendInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProdCatRecommendInfoDlg.set = function(key, val) {
    this.prodCatRecommendInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProdCatRecommendInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProdCatRecommendInfoDlg.close = function() {
    parent.layer.close(window.parent.ProdCatRecommend.layerIndex);
}

/**
 * 收集数据
 */
ProdCatRecommendInfoDlg.collectData = function() {
    this
    .set('id')
    .set('recommendProdId')
    .set('recommendProdName')
    .set('recommendImg')
    .set('seqNum');
}

/**
 * 提交添加
 */
ProdCatRecommendInfoDlg.addSubmit = function() {
    var recommendImg = $('#recommendImg').val();
    if(!recommendImg){
        Labi.error('请上传推荐位图片!');
        return false;
    }
    this.clearData();
    this.collectData();
    //非空验证
    if (!this.validate()) {
        return false;
    }
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/prodCatRecommend/update", function(data){
        Labi.success("添加成功!");
        window.parent.ProdCatRecommend.search();
        ProdCatRecommendInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.prodCatRecommendInfoData);
    ajax.set('recommendProdName',$("#recommendProdId").find("option:selected").text());
    ajax.start();
}

/**
 * 提交修改
 */
ProdCatRecommendInfoDlg.editSubmit = function() {
    var recommendImg = $('#recommendImg').val();
    if(!recommendImg){
        Labi.error('请上传推荐位图片!');
        return false;
    }
    this.clearData();
    this.collectData();
    //非空验证
    if (!this.validate()) {
        return false;
    }
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/prodCatRecommend/update", function(data){
        Labi.success("修改成功!");
        window.parent.ProdCatRecommend.search();
        ProdCatRecommendInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.prodCatRecommendInfoData);
    ajax.set('recommendProdName',$("#recommendProdId").find("option:selected").text());
    ajax.start();
}
/**
 * 查询商品
 */
ProdCatRecommendInfoDlg.jumpSearch = function () {
    var Keyword=$("#Keyword").val();//检索关键字

    if(Keyword=="" || Keyword==null){
        Labi.error("请输入关键字后查询!");
        return;
    }
    var ajax = new $ax(Labi.ctxPath + "/banner/geturl", function (data) {
        $("#recommendProdId").empty();
        //检测数据源是否为空
        if(data.length==0){
            $("#recommendProdId").append($("<option/>").text("暂无数据").attr("value",""));

        }else{
            $.each(data, function(i, item){
                $("#recommendProdId").append($("<option/>").text(item.name).attr("value",item.id));
            });
        }

    }, function (data) {
        Labi.error("查询异常!" + data.responseJSON.message + "!");
    });
    ajax.set("type","product");
    ajax.set("Keyword",Keyword);
    ajax.start();
}
/**
 * 验证数据是否为空
 */
ProdCatRecommendInfoDlg.validate = function () {
    $('#ProdCatRecommendInfoForm').data("bootstrapValidator").resetForm();
    $('#ProdCatRecommendInfoForm').bootstrapValidator('validate');
    return $("#ProdCatRecommendInfoForm").data('bootstrapValidator').isValid();
}
$(function() {
    //参数校验
    Labi.initValidator("ProdCatRecommendInfoForm", ProdCatRecommendInfoDlg.validateFields);

    var imgUp = new $WebUpload("recommendImg",Labi.destDir.prodCat);
    imgUp.init();

    //下拉框数据回显
    var id = $('#catId').val();
    if(id){
        $('#id option[value='+id+']').attr('selected',true);
    }
});
