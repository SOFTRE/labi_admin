/**
 * 初始化拉比展示管理详情对话框
 */
var ShowInfoDlg = {
    showInfoData : {},
    validateFields: {
    	showCat: {
            validators: {
                notEmpty: {
                    message: '请选择分类'
                }
            }
        },
        title: {
            validators: {
                notEmpty: {
                    message: '请填写标题'
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
ShowInfoDlg.validate = function () {
    $('#showInfoForm').data("bootstrapValidator").resetForm();
    $('#showInfoForm').bootstrapValidator('validate');
    return $("#showInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 清除数据
 */
ShowInfoDlg.clearData = function() {
    this.showInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ShowInfoDlg.set = function(key, val) {
    this.showInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ShowInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ShowInfoDlg.close = function() {
    parent.layer.close(window.parent.Show.layerIndex);
}

/**
 * 收集数据
 */
ShowInfoDlg.collectData = function() {
    this
    .set('id')
    .set('catId')
    .set('img')
    .set('title')
    .set('description')
    .set('desFile')
    .set('info')
    .set('moreImg')
    .set('ifRecommend')
    .set('ifOnline')
    .set('ifVideo')
    .set('videoFile')
    .set('coverFile')
    .set('seqNum')
    .set('status')
    .set('createtime')
    .set('oprtime');
}

/**
 * 提交添加
 */
ShowInfoDlg.addSubmit = function() {

	//拉比展示分类id
	$("#catId").val($("#showCat").val());
	
	//是否推荐
	var valRecommend = $('input[name="redioRecommendCheck"]:checked ').val();
	$("#ifRecommend").val(valRecommend);
	
	//是否上线
	var valOnline = $('input[name="redioOnlineCheck"]:checked ').val();
	$("#ifOnline").val(valOnline);
	
	//是否播放视频
	var valVideo = $('input[name="redioVideoCheck"]:checked ').val();
	$("#ifVideo").val(valVideo);
	
	//富文本获取值
    var info = UE.getEditor('infoEdit').getContent();
    $('#info').val(info);
    
//	//封面图
//	alert($("input[name='coverFile']").val());
//	$("#coverFile").val($("input[name='coverFile']").val()+"");
//	
//	//视频
//	alert($("input[name='videoFile']").val());
//	$("#videoFile").val($("input[name='videoFile']").val()+"");
	
    this.clearData();
    this.collectData();

  //非空验证
    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Labi.ctxPath + "/show/add", function(data){
        Labi.success("添加成功!");
        window.parent.Show.table.refresh();
        ShowInfoDlg.close();
    },function(data){
        Labi.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.showInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ShowInfoDlg.editSubmit = function() {

	//拉比展示分类id
	$("#catId").val($("#showCat").val());
	
	//是否推荐
	var valRecommend = $('input[name="redioRecommendCheck"]:checked ').val();
	$("#ifRecommend").val(valRecommend);
	
	//是否上线
	var valOnline = $('input[name="redioOnlineCheck"]:checked ').val();
	$("#ifOnline").val(valOnline);
	
	//是否播放视频
	var valVideo = $('input[name="redioVideoCheck"]:checked ').val();
	$("#ifVideo").val(valVideo);
	
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
    var ajax = new $ax(Labi.ctxPath + "/show/update", function(data){
        Labi.success("修改成功!");
        window.parent.Show.table.refresh();
        ShowInfoDlg.close();
    },function(data){
        Labi.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.showInfoData);
    ajax.start();
}


$(function() {
	//radio初始化
    $('input.radio').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
        increaseArea: '20%' // optional
    });
    
    //视频上传div 显示 隐藏（单击事件）
    $(document).on("ifChecked","input[name='redioVideoCheck']",function(e){
    	if($(this).val()=='T'){
    		$("#div_video").show();//显示视频div
    		$("#div_img").show();//隐藏图片div
    		$("#div_moreImg").hide();//隐藏多图div
    	}
    	if($(this).val()=='F'){
    		$("#div_video").hide();//隐藏视频上传div
    		$("#div_img").show();//显示图片div
    		$("#div_moreImg").show();//隐藏多图div
    	}
    });
  //参数校验
    Labi.initValidator("showInfoForm", ShowInfoDlg.validateFields);
    
    
    // 初始化单图
    var img = new $WebUpload("img",Labi.destDir.banner);
    img.init();
    
    // 初始化多图
    var moreImg = new $WebMoreUpload("moreImg",Labi.destDir.coach);
    moreImg.init();
    
    // 初始化视频封面图片
    var coverFile = new $WebUpload("coverFile",Labi.destDir.coach);
    coverFile.init();
    
    //初始化视频上传
    var videoUp = new $WebVideoUpload("videoFile","coverFile");
    videoUp.init(videoUp);
    $("#div_video").hide();//隐藏视频上传div
    
    //下拉框数据回显
    var catId = $('#showCat').find('option:eq(0)').data('id');
    $('#showCat option[value='+catId+']').attr('selected',true);
    
  //初始化富文本
	var editor = UE.getEditor('infoEdit');
	editor.ready(function() {
		editor.setHeight(300);
	    
		//富文本set值
		if(parent.Show.seItem){
			$.get(Labi.filePreFix + parent.Show.seItem.desFile, function(data) {
				var start = data.indexOf("<body>"),
				end = data.indexOf('</body>');
				editor.setContent(data.substring(start+6, end));
			});
		} else {
		    //富文本清空
			editor.setContent('');
		}
	});
	
	//修改时 图片 视频判断显示
    if(parent.Show.seItem){
    	if(parent.Show.seItem.ifVideo=='T'){
    		$("#div_video").show();//显示视频div
    		$("#div_moreImg").hide();//隐藏多图div
    	}
    	if(parent.Show.seItem.ifVideo=='F'){
    		$("#div_video").hide();//隐藏视频上传div
    		$("#div_img").show();//显示图片div
    	}
    }
});
