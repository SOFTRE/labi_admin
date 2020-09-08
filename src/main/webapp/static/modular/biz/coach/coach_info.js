/**
 * 初始化教练管理详情对话框
 */
var CoachInfoDlg = {
    coachInfoData : {},
    validateFields: {
        selUserId: {
            validators: {
                notEmpty: {
                    message: '请选择用户'
                }
            }
        },
    	name: {
            validators: {
                notEmpty: {
                    message: '姓名不能为空'
                }
            }
        },
        coachLeve: {
            validators: {
                notEmpty: {
                    message: '请选择教练等级'
                }
            }
        },
        coachCatLeve: {
            validators: {
                notEmpty: {
                    message: '请选择教练分类'
                }
            }
        },
        coachNum: {
            validators: {
                notEmpty: {
                    message: '可预约次数不能为空'
                },
                regexp: {
                	//正则验证  
                    regexp: /^[+]{0,1}(\d+)$/,  
                    message: '请输入正整数'  
                }
            }
        },
        price: {
            validators: {
                notEmpty: {
                    message: '价格不能为空'
                },
                regexp: {
                    //正则验证
                    regexp: /^(0|[1-9][0-9]{0,9})(\.[0-9]{1,3})?$/,
                    message: '请输入正确价格'
                }
            }
        },
        telephone: {
            validators: {
                notEmpty: {
                    message: '内部电话不能为空'
                },
                regexp: {
                	//正则验证  
                    regexp: /^[+]{0,1}(\d+)$/,  
                    message: '请输入正整数'  
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
 * 验证数据是否为空
 */
CoachInfoDlg.validate = function () {
    $('#coachInfoForm').data("bootstrapValidator").resetForm();
    $('#coachInfoForm').bootstrapValidator('validate');
    return $("#coachInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 清除数据
 */
CoachInfoDlg.clearData = function() {
    this.coachInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CoachInfoDlg.set = function(key, val) {
    this.coachInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CoachInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CoachInfoDlg.close = function() {
    parent.layer.close(window.parent.Coach.layerIndex);
}

/**
 * 收集数据
 */
CoachInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('name')
    .set('coachCatId')
    .set('coachCatName')
    .set('img')
    .set('moreImg')
    .set('desFile')
    .set('des')
    .set('info')
    .set('coachGradeId')
    .set('coachGradeName')
    .set('coachNum')
    .set('price')
    .set('telephone')
    .set('seqNum')
    .set('ifRecommend')
    .set('ifFrozen')
    .set('createtime')
    .set('oprtime');
}

/**
 * 提交添加
 */
CoachInfoDlg.addSubmit = function() {
	
	//是否推荐
	var ifRecommend = $('input[name="redRecommendCheck"]:checked ').val();
	$("#ifRecommend").val(ifRecommend);
	
	//是否冻结
	var if_frozen = $('input[name="redFrozenioCheck"]:checked ').val();
	$("#ifFrozen").val(if_frozen);
	
	//教练等级id
	$("#coachGradeId").val($("#coachLeve").val());
	
	//教练等级名称
	//var name = $("#coachLeve").find("option:selected").text();
	//$("#coachGradeName").val(name);

	//教练分类id
	$("#coachCatId").val($("#coachCatLeve").val());
	
	//富文本获取值
    var info = UE.getEditor('infoEdit').getContent();
    $('#info').val(info);
    
	this.clearData();
    this.collectData();

    //非空验证
    if (!this.validate()) {
        return;
    }
    
    //电话是否重复校验
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/coach/add", function(data){
        Labi.success("添加成功!");
        window.parent.Coach.table.refresh();
        CoachInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.coachInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CoachInfoDlg.editSubmit = function() {
	
	//是否推荐
	var ifRecommend = $('input[name="redRecommendCheck"]:checked ').val();
	$("#ifRecommend").val(ifRecommend);
	
	//是否冻结
	var if_frozen = $('input[name="redFrozenioCheck"]:checked ').val();
	$("#ifFrozen").val(if_frozen);
	
	//教练等级id
	$("#coachGradeId").val($("#coachLeve").val());
	
	//是否冻结
	var if_frozen = $('input[name="redFrozenioCheck"]:checked ').val();
	$("#ifFrozen").val(if_frozen);
	
	//教练分类id
	$("#coachCatId").val($("#coachCatLeve").val());
	
	//富文本获取值
    var info = UE.getEditor('infoEdit').getContent();
    $('#info').val(info);
    
    this.clearData();
    this.collectData();

    //非空验证
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/coach/update", function(data){
        Labi.success("修改成功!");
        window.parent.Coach.table.refresh();
        CoachInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.coachInfoData);
    ajax.start();
}
/**
 * 点击选择会员
 */
CoachInfoDlg.chooseUser = function () {
    var index = layer.open({
        type: 2,
        title: '选择会员',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Labi.ctxPath + '/user/choosecoach'
    });
    this.layerIndex = index;
    layer.full(index);
};


$(function() {
	//radio初始化
    $('input.radio').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
        increaseArea: '20%' // optional
    });
    //参数校验
    Labi.initValidator("coachInfoForm", CoachInfoDlg.validateFields);
	// 初始化缩略图
    var avatarUp = new $WebUpload("img",Labi.destDir.coach);
    avatarUp.init();
    
    // 初始化多图
    var moreImg = new $WebMoreUpload("moreImg",Labi.destDir.coach);
    moreImg.init();

    //价格末尾去0
    var price = $('#price').val();
    if(price){
        $('#price').val(parseFloat(price));
    }
    
    //修改时教练等级下拉框数据回显
    var coachGradeId = $('#coachLeve').find('option:eq(0)').data('id');
    $('#coachLeve option[value='+coachGradeId+']').attr('selected',true);
    
   //修改时教练分类下拉框数据回显
    var coachCatId = $('#coachCatLeve').find('option:eq(0)').data('id');
    $('#coachCatLeve option[value='+coachCatId+']').attr('selected',true);
    
	   //初始化富文本
		var editor = UE.getEditor('infoEdit');
		editor.ready(function() {
			editor.setHeight(300);
			//富文本set值
			if(parent.Coach.seItem){
				$.get(Labi.filePreFix + parent.Coach.seItem.desFile, function(data) {
					var start = data.indexOf("<body>"),
					end = data.indexOf('</body>');
					editor.setContent(data.substring(start+6, end));
				});
			} else {
			    //富文本清空
				editor.setContent('');
			}
		});
});
