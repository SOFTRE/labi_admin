/**
 * 初始化coachCat管理详情对话框
 */
var coachCatInfoDlg = {
    coachCatInfoData : {},
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
                    message: '排序号不能为空'
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
coachCatInfoDlg.validate = function () {
    $('#coachCatInfoForm').data("bootstrapValidator").resetForm();
    $('#coachCatInfoForm').bootstrapValidator('validate');
    return $("#coachCatInfoForm").data('bootstrapValidator').isValid();
}
/**
 * 清除数据
 */
coachCatInfoDlg.clearData = function() {
    this.coachCatInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
coachCatInfoDlg.set = function(key, val) {
    this.coachCatInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
coachCatInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
coachCatInfoDlg.close = function() {
    parent.layer.close(window.parent.coachCat.layerIndex);
}

/**
 * 收集数据
 */
coachCatInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('seqNum')
    .set('createtime')
    .set('oprtime');
}

/**
 * 提交添加
 */
coachCatInfoDlg.addSubmit = function() {

	var val_online = $('input[name="redioCheck"]:checked ').val();
	$("#ifOnline").val(val_online);
	
    this.clearData();
    this.collectData();
    //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/coachCat/add", function(data){
        Labi.success("添加成功!");
        window.parent.coachCat.table.refresh();
        coachCatInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.coachCatInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
coachCatInfoDlg.editSubmit = function() {
	
    this.clearData();
    this.collectData();

    //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/coachCat/update", function(data){
        Labi.success("修改成功!");
        window.parent.coachCat.table.refresh();
        coachCatInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.coachCatInfoData);
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
    Labi.initValidator("coachCatInfoForm", coachCatInfoDlg.validateFields);
	// 初始化头像上传
    var avatarUp = new $WebUpload("img",Labi.destDir.banner);
    avatarUp.init();
    
});
