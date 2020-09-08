/**
 * 初始化课程班级管理详情对话框
 */
var CourseClassInfoDlg = {
    courseClassInfoData : {},
    validateFields: {
    	name: {
            validators: {
                notEmpty: {
                    message: '请填写班级名称'
                }
            }
        },
        code: {
            validators: {
                notEmpty: {
                    message: '请填写编号'
                }
            }
        },
        maxNum: {
            validators: {
                notEmpty: {
                    message: '最大报名数不能为空'
                },
                regexp: {
                	//正则验证  
                    regexp: /^[+]{0,1}(\d+)$/,  
                    message: '请输入正整数'  
                }
            }
        },
        showCourse: {
            validators: {
                notEmpty: {
                    message: '请选择所属课程'
                }
            }
        }
        
    }
};


/**
 * 验证数据是否为空
 */
CourseClassInfoDlg.validate = function () {
    $('#courseInfoForm').data("bootstrapValidator").resetForm();
    $('#courseInfoForm').bootstrapValidator('validate');
    return $("#courseInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 清除数据
 */
CourseClassInfoDlg.clearData = function() {
    this.courseClassInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CourseClassInfoDlg.set = function(key, val) {
    this.courseClassInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CourseClassInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CourseClassInfoDlg.close = function() {
    parent.layer.close(window.parent.CourseClass.layerIndex);
}

/**
 * 收集数据
 */
CourseClassInfoDlg.collectData = function() {
    this
    .set('id')
    .set('courseId')
    .set('code')
    .set('name')
    .set('maxNum')
    .set('status')
    .set('createtime')
    .set('oprtime');
}

/**
 * 提交添加
 */
CourseClassInfoDlg.addSubmit = function() {

	 
	//所属课程id
	$("#courseId").val($("#showCourse").val());
	
    this.clearData();
    this.collectData();
    
    //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/courseClass/add", function(data){
        Labi.success("添加成功!");
        window.parent.CourseClass.table.refresh();
        CourseClassInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.courseClassInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CourseClassInfoDlg.editSubmit = function() {

	//所属课程id
	$("#courseId").val($("#showCourse").val());
	
    this.clearData();
    this.collectData();

    //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/courseClass/update", function(data){
        Labi.success("修改成功!");
        window.parent.CourseClass.table.refresh();
        CourseClassInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.courseClassInfoData);
    ajax.start();
}

$(function() {

	//参数校验
    Labi.initValidator("courseInfoForm", CourseClassInfoDlg.validateFields);
    
    //修改页面下拉框数据回显
    var catId = $('#showCourse').find('option:eq(0)').data('id');
    $('#showCourse option[value='+catId+']').attr('selected',true);
    
});
