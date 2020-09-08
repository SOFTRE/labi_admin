/**
 * 初始化教练等级管理详情对话框
 */
var CoachGradeInfoDlg = {
    coachGradeInfoData : {},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '名称不能为空'
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
    }
};

CoachGradeInfoDlg.validate = function () {
    $('#CoachGradeInfoForm').data("bootstrapValidator").resetForm();
    $('#CoachGradeInfoForm').bootstrapValidator('validate');
    return $("#CoachGradeInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 清除数据
 */
CoachGradeInfoDlg.clearData = function() {
    this.coachGradeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CoachGradeInfoDlg.set = function(key, val) {
    this.coachGradeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CoachGradeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CoachGradeInfoDlg.close = function() {
    parent.layer.close(window.parent.CoachGrade.layerIndex);
}

/**
 * 收集数据
 */
CoachGradeInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('title')
    .set('des')
    .set('info')
    .set('img')
    .set('moreImg')
    .set('coachNum')
    .set('price')
    .set('seqNum')
    .set('status')
    .set('createtime')
    .set('oprtime');
}

/**
 * 提交添加
 */
CoachGradeInfoDlg.addSubmit = function() {
    var img = $('#img').val();
    if(!img){
        Labi.error('请上传教练等级缩略图!');
        return false;
    }
	//富文本获取值
    var info = UE.getEditor('infoEdit').getContent();
    $('#info').val(info);
    
    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/coachGrade/add", function(data){
        Labi.success("添加成功!");
        window.parent.CoachGrade.table.refresh();
        CoachGradeInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.coachGradeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CoachGradeInfoDlg.editSubmit = function() {
    var img = $('#img').val();
    if(!img){
        Labi.error('请上传教练等级缩略图!');
        return false;
    }
	//富文本获取值
    var info = UE.getEditor('infoEdit').getContent();
    $('#info').val(info);
    
    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/coachGrade/update", function(data){
        Labi.success("修改成功!");
        window.parent.CoachGrade.table.refresh();
        CoachGradeInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.coachGradeInfoData);
    ajax.start();
}

$(function() {
	
	//参数校验
    Labi.initValidator("CoachGradeInfoForm", CoachGradeInfoDlg.validateFields);
    
    // 初始化图片上传
    var avatarUp = new $WebUpload("img",Labi.destDir.coach);
    avatarUp.init();
    
   // 初始化多图
    //var moreImg = new $WebMoreUpload("moreImg",Labi.destDir.coach);
    //moreImg.init();
    
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
		if(parent.CoachGrade.seItem){
			$.get(Labi.filePreFix + parent.CoachGrade.seItem.desFile, function(data) {
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
