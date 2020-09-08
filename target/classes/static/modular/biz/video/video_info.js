/**
 * 初始化在线课程管理详情对话框
 */
var ProductInfoDlg = {
    productInfoData : {},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '请填写在线课程名称'
                }
            }
        },
        des: {
            validators: {
                notEmpty: {
                    message: '请填写简介'
                }
            }
        },
        originPrice: {
            validators: {
                notEmpty: {
                    message: '原价不能为空'
                },
                regexp: {
                    //正则验证
                    regexp: /^\d+(\.\d+)?$/,
                    message: '请输入数字'
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
        },
        catId: {
            validators: {
                notEmpty: {
                    message: '请选择分类'
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
    .set('catName')
    .set('name')
    .set('thumbImg')
    .set('moreImg')
    .set('ifShelf')
    .set('ifFree')
    .set('seqNum')
    .set('salePrice')
    .set('originPrice')
    .set('des')
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
    var thumbImg = $('#thumbImg').val();
    if(!thumbImg){
        Labi.error('请上传在线课程缩略图!');
        return false;
    }

    //是否免费
    var ifFree = $('input[name="redioIfFreeCheck"]:checked ').val();
    $("#ifFree").val(ifFree);
    
    //是否上线
    var ifShelf = $('input[name="redioIfShelfCheck"]:checked ').val();
    $("#ifShelf").val(ifShelf);
    var catName = $("#catId").find("option:selected").text();

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
    var ajax = new $ax(Labi.ctxPath + "/video/add", function(data){
        Labi.success("添加成功!");
        window.parent.Product.table.refresh();
        ProductInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.productInfoData);
    ajax.set('catName',catName);
    ajax.start();
}

/**
 * 提交修改
 */
ProductInfoDlg.editSubmit = function() {

    var thumbImg = $('#thumbImg').val();
    if(!thumbImg){
        Labi.error('请上传在线课程缩略图!');
        return false;
    }
    //
    var catName = $("#catId").find("option:selected").text();

    //是否免费
    var ifFree = $('input[name="redioIfFreeCheck"]:checked ').val();
    $("#ifFree").val(ifFree);
    
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
    var ajax = new $ax(Labi.ctxPath + "/video/update", function(data){
        Labi.success("修改成功!");
        window.parent.Product.table.refresh();
        ProductInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.productInfoData);
    ajax.set('catName',catName);
    ajax.start();
}
/**
 * 提交视频添加
 */
ProductInfoDlg.addProdVideoSubmit = function() {
    var name = $('#name').val();
    if(!name){
        Labi.error('请填写名称!');
        return false;
    }
    var videoFile = $('#videoFile').val();
    if(!videoFile){
        Labi.error('请上传视频!');
        return false;
    }
    var seqNum = $('#seqNum').val();
    if(!seqNum){
        Labi.error('请填写排序!');
        return false;
    }
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/video/video/add", function(data){
        Labi.success("添加成功!");
        window.parent.Product.videoTable.refresh();
        ProductInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set('name',name);
    ajax.set('seqNum',seqNum);
    ajax.set('videoFile',videoFile);
    var videoSize = $('#videoSize').val();
    ajax.set('videoSize',videoSize);
    var coverFile = $('#coverFile').val();
    if(coverFile){
        ajax.set('coverFile',coverFile);
    }
    ajax.set('prodId',parent.Product.seItem.id);
    ajax.start();
}
/**
 * 提交视频修改
 */
ProductInfoDlg.editProdVideoSubmit = function() {
    var name = $('#name').val();
    if(!name){
        Labi.error('请填写名称!');
        return false;
    }
    var videoFile = $('#videoFile').val();
    if(!videoFile){
        Labi.error('请上传视频!');
        return false;
    }
    var seqNum = $('#seqNum').val();
    if(!seqNum){
        Labi.error('请填写排序!');
        return false;
    }
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/video/video/update", function(data){
        Labi.success("修改成功!");
        window.parent.Product.videoTable.refresh();
        ProductInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set('id',$('#id').val());
    ajax.set('name',name);
    ajax.set('seqNum',seqNum);
    ajax.set('videoFile',videoFile);
    var videoSize = $('#videoSize').val();
    ajax.set('videoSize',videoSize);
    var coverFile = $('#coverFile').val();
    if(coverFile){
        ajax.set('coverFile',coverFile);
    }
    ajax.start();
}
$(function() {
    if($('#coverFile').length>0){
        // 初始化视频封面图片
        var avatarUp = new $WebUpload("coverFile",Labi.destDir.coach);
        avatarUp.init();
        //初始化视频上传
        var videoUp = new $WebVideoUpload("videoFile","coverFile","videoSize");
        videoUp.init(videoUp);
    } else {
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

        //下拉框数据回显
        var catId = $('#catId').find('option:eq(0)').data('id');
        if(catId){
            $('#catId option[value='+catId+']').attr('selected',true);
        }

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
    }

    var salePrice = $('#salePrice').val();
    if(salePrice){
        $('#salePrice').val(parseFloat(salePrice));
    }
    var originPrice = $('#originPrice').val();
    if(originPrice){
        $('#originPrice').val(parseFloat(originPrice));
    }

    if($('input[name="redioIfFreeCheck"]:checked ').val()=='T'){
        $('#salePrice').attr("disabled",true);
        $('#originPrice').attr("disabled",true);
    }

    $(document).on('ifChecked', 'input[name="redioIfFreeCheck"]', function() {
        if($(this).val() == 'T'){
            $('#salePrice').val(0);
            $('#originPrice').val(0);
            $('#salePrice').attr("disabled",true);
            $('#originPrice').attr("disabled",true);

        } else {
            $('#salePrice').val('');
            $('#salePrice').attr("disabled",false);
            $('#originPrice').val('');
            $('#originPrice').attr("disabled",false);
        }
    });
});
