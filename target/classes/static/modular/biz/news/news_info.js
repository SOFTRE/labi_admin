/**
 * 初始化新闻管理详情对话框
 */
var NewsInfoDlg = {
    newsInfoData : {},
    validateFields: {
    	title: {
            validators: {
                notEmpty: {
                    message: '标题不能为空'
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
        description: {
            validators: {
                notEmpty: {
                    message: '请填写简要'
                }
            }
        }
        
    }
};

/**
 * 验证数据是否为空
 */
NewsInfoDlg.validate = function () {
    $('#newsInfoForm').data("bootstrapValidator").resetForm();
    $('#newsInfoForm').bootstrapValidator('validate');
    return $("#newsInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 清除数据
 */
NewsInfoDlg.clearData = function() {
    this.newsInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
NewsInfoDlg.set = function(key, val) {
    this.newsInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
NewsInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
NewsInfoDlg.close = function() {
    parent.layer.close(window.parent.News.layerIndex);
}

/**
 * 收集数据
 */
NewsInfoDlg.collectData = function() {
    this
    .set('id')
    .set('img')
    .set('smallImg')
    .set('moreImg')
    .set('title')
    .set('description')
    .set('desFile')
    .set('info')
    .set('pv')
    .set('hrefUrl')
    .set('type')
    .set('ifOnline')
    .set('seqNum')
    .set('createtime')
    .set('oprtime');
}

/**
 * 提交添加
 */
NewsInfoDlg.addSubmit = function() {

	//是否上线
	var valOnline = $('input[name="redioNewsCheck"]:checked ').val();
	$("#ifOnline").val(valOnline);
	
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
    var ajax = new $ax(Labi.ctxPath + "/news/add", function(data){
        Labi.success("添加成功!");
        window.parent.News.table.refresh();
        NewsInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.newsInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
NewsInfoDlg.editSubmit = function() {
	
	//是否上线
	var valOnline = $('input[name="redioNewsCheck"]:checked ').val();
	$("#ifOnline").val(valOnline);
	
	//富文本获取值
    var info = UE.getEditor('infoEdit').getContent();
    $('#info').val(info);
    
    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/news/update", function(data){
        Labi.success("修改成功!");
        window.parent.News.table.refresh();
        NewsInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.newsInfoData);
    ajax.start();
}

$(function() {
	
	//参数校验
    Labi.initValidator("newsInfoForm", NewsInfoDlg.validateFields);
    
	//radio初始化
    $('input.radio').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
        increaseArea: '20%' // optional
    });
    
	// 初始化新闻缩略图
    var avatarUp = new $WebUpload("img",Labi.destDir.banner);
    avatarUp.init();
    var avatarSmallUp = new $WebUpload("smallImg",Labi.destDir.banner);
    avatarSmallUp.init();

    // 初始化多图
    //var moreImg = new $WebMoreUpload("moreImg",Labi.destDir.coach);
    //moreImg.init();
    
  //初始化富文本
    setTimeout(function(){
    	var editor = UE.getEditor('infoEdit');
    	editor.ready(function() {
    		editor.setHeight(300);
    		//富文本set值
    		if(parent.News.seItem&&parent.News.seItem.desFile){
    			$.get(Labi.filePreFix + parent.News.seItem.desFile, function(data) {
    				var start = data.indexOf("<body>"),
    				end = data.indexOf('</body>');
    				editor.setContent(data.substring(start+6, end));
    			});
    		} else {
    			//富文本清空
    			editor.setContent('');
    		}
    	});
    },500)
});
