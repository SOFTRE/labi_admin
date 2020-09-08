/**
 * 初始化常见问题管理详情对话框
 */
var ProblemInfoDlg = {
    problemInfoData : {},
    validateFields: {
        problem: {
            validators: {
                notEmpty: {
                    message: '问题不能为空'
                }
            }
        },
        answer: {
            validators: {
                notEmpty: {
                    message: '答案不能为空'
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

ProblemInfoDlg.validate = function () {
    $('#ProblemInfoForm').data("bootstrapValidator").resetForm();
    $('#ProblemInfoForm').bootstrapValidator('validate');
    return $("#ProblemInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 清除数据
 */
ProblemInfoDlg.clearData = function() {
    this.problemInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProblemInfoDlg.set = function(key, val) {
    this.problemInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProblemInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProblemInfoDlg.close = function() {
    parent.layer.close(window.parent.Problem.layerIndex);
}

/**
 * 收集数据
 */
ProblemInfoDlg.collectData = function() {
    this
    .set('id')
    .set('problem')
    .set('answer')
    .set('seqNum')

}

/**
 * 提交添加
 */
ProblemInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //参数校验
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/problem/add", function(data){
        Labi.success("添加成功!");
        window.parent.Problem.table.refresh();
        ProblemInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.problemInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ProblemInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

  //参数校验
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/problem/update", function(data){
        Labi.success("修改成功!");
        window.parent.Problem.table.refresh();
        ProblemInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.problemInfoData);
    ajax.start();
}

$(function() {

	//参数校验
    Labi.initValidator("ProblemInfoForm", ProblemInfoDlg.validateFields);
    
});
