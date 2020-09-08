/**
 * 初始化课程管理详情对话框
 */
var CourseInfoDlg = {
    courseInfoData : {},
    validateFields: {
    	name: {
            validators: {
                notEmpty: {
                    message: '请填写名称'
                }
            }
        },
        classPrefix: {
            validators: {
                notEmpty: {
                    message: '请填班级前缀'
                }
            }
        },
        des: {
            validators: {
                notEmpty: {
                    message: '请填写课程简介'
                }
            }
        },
        courseCatLeve: {
            validators: {
                notEmpty: {
                    message: '请选择课程分类'
                }
            }
        },
        price: {
            validators: {
                notEmpty: {
                    message: '课程价格不能为空'
                },
                regexp: {
                	//正则验证  
                    regexp: /^(0|[1-9][0-9]{0,9})(\.[0-9]{1,3})?$/,  
                    message: '请输入正确价格'
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
        maxNum: {
            validators: {
                notEmpty: {
                    message: '最大报名人数不能为空'
                },
                regexp: {
                	//正则验证  
                    regexp: /^[+]{0,1}(\d+)$/,  
                    message: '请输入正整数'  
                }
            }
        },
    }
};

CourseInfoDlg.validate = function () {
    $('#couresInfoForm').data("bootstrapValidator").resetForm();
    $('#couresInfoForm').bootstrapValidator('validate');
    return $("#couresInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 清除数据
 */
CourseInfoDlg.clearData = function() {
    this.courseInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CourseInfoDlg.set = function(key, val) {
    this.courseInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CourseInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CourseInfoDlg.close = function() {
    parent.layer.close(window.parent.Course.layerIndex);
}

/**
 * 收集数据
 */
CourseInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('classPrefix')
    .set('des')
    .set('img')
    .set('moreImg')
    .set('info')
    .set('desFile')
    .set('courseCatId')
    .set('courseCatName')
    .set('price')
    .set('seqNum')
    .set('maxNum')
    .set('saleNum')
    .set('ifRecommend')
    .set('ifShelf')
    .set('status')
    .set('createtime')
    .set('oprtime');
}

/**
 * 提交添加
 */
CourseInfoDlg.addSubmit = function() {

	//是否推荐
	var val_recommend = $('input[name="redioIfRecommendCheck"]:checked ').val();
	$("#ifRecommend").val(val_recommend);
	
	//是否上架
	var val_ifShelf = $('input[name="redioIfShelfCheck"]:checked ').val();
	$("#ifShelf").val(val_ifShelf);
	
	//课程分类id
	$("#courseCatId").val($("#courseCatLeve").val());
	//课程分类名称
	var name = $("#courseCatLeve").find("option:selected").text();
	$("#courseCatName").val(name);
	
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
    var ajax = new $ax(Labi.ctxPath + "/course/add", function(data){
        Labi.success("添加成功!");
        window.parent.Course.table.refresh();
        CourseInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.courseInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CourseInfoDlg.editSubmit = function() {

	//是否推荐
	var val_recommend = $('input[name="redioIfRecommendCheck"]:checked ').val();
	$("#ifRecommend").val(val_recommend);
	
	//是否上架
	var val_ifShelf = $('input[name="redioIfShelfCheck"]:checked ').val();
	$("#ifShelf").val(val_ifShelf);
	
	//课程分类id
	$("#courseCatId").val($("#courseCatLeve").val());
	//课程分类名称
	var name = $("#courseCatLeve").find("option:selected").text();
	$("#courseCatName").val(name);
	
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
    var ajax = new $ax(Labi.ctxPath + "/course/update", function(data){
        Labi.success("修改成功!");
        window.parent.Course.table.refresh();
        CourseInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.courseInfoData);
    ajax.start();
}

$(function() {
	//radio初始化
    $('input.radio').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
        increaseArea: '20%' // optional
    });
    
	//参数校验
    Labi.initValidator("couresInfoForm", CourseInfoDlg.validateFields);
    
    // 初始化单图
    var avatarUp = new $WebUpload("img",Labi.destDir.coach);
    avatarUp.init();
    
    // 初始化多图
    var moreImg = new $WebMoreUpload("moreImg",Labi.destDir.coach);
    moreImg.init();
    
  //教练等级下拉框数据回显
    var coachGradeId = $('#courseCatLeve').find('option:eq(0)').data('id');
    $('#courseCatLeve option[value='+coachGradeId+']').attr('selected',true);
    
    //价格末尾去0
    var price = $('#price').val();
    if(price){
        $('#price').val(parseFloat(price));
    }
    
  //初始化富文本
	var editor = UE.getEditor('infoEdit');
	editor.ready(function() {
		editor.setHeight(300);
		//富文本set值
		if(parent.Course.seItem){
			$.get(Labi.filePreFix + parent.Course.seItem.desFile, function(data) {
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
