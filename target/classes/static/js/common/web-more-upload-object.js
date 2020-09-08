/**
 * web-upload 工具类
 * 
 * 约定：
 * 上传按钮的id = 图片隐藏域id + 'BtnId'
 * 图片预览框的id = 图片隐藏域id + 'PreId'
 * 
 * @author lyr
 */
(function() {
	
	var $WebMoreUpload = function(pictureId,destDir) {
		if(!destDir){
            destDir = 'default';
		}
		this.pictureId = pictureId;
		this.uploadBtnId = pictureId + "BtnId";
		this.uploadPreId = pictureId + "PreId";
		this.uploadUrl = Labi.ctxPath + '/file/uploadimg/'+destDir;
		this.fileSizeLimit = 100 * 1024 * 1024;
		this.picWidth = 800;
		this.picHeight = 800;
        this.uploadBarId = null;
        this.hasPreImg = true;
        if(Labi.noEmpty($('#'+pictureId).val())){
            $("#" + this.uploadPreId).empty();
            this.hasPreImg = false;
		}
	};

    $WebMoreUpload.prototype = {
		/**
		 * 初始化webUploader
		 */
		init : function() {
			var uploader = this.create();
            this.setPreImgs();
			this.bindEvent(uploader);
			return uploader;
		},
		
		/**
		 * 创建webuploader对象
		 */
		create : function() {
			var webUploader = WebUploader.create({
				auto : true,
				pick : {
					id : '#' + this.uploadBtnId
				},
				accept : {
					title : 'Images',
					extensions : 'gif,jpg,jpeg,bmp,png',
                    mimeTypes : 'image/gif,image/jpg,image/jpeg,image/bmp,image/png'
				},
				swf : Labi.ctxPath
						+ '/static/css/plugins/webuploader/Uploader.swf',
				disableGlobalDnd : true,
				duplicate : true,
				server : this.uploadUrl,
				fileSingleSizeLimit : this.fileSizeLimit
			});
			
			return webUploader;
		},

		/**
		 * 绑定事件
		 */
		bindEvent : function(bindedObj) {
			var me =  this;
			bindedObj.on('fileQueued', function(file) {
				if(me.hasPreImg){
                    $("#" + me.uploadPreId).empty();
                    me.hasPreImg = false;
				}
                var $img = $('<div class="other-img-d"><div class="other-img-d-d"><i class="fa fa-trash other-img-remove" title="删除"></i></div><img class="upload-img otherImg" width="100px" height="100px"></div>');

				$("#" + me.uploadPreId).append($img);

				// 生成缩略图
				bindedObj.makeThumb(file, function(error, src) {
					if (error) {
						$img.replaceWith('<span>不能预览</span>');
						return;
					}
					$img.find('img').attr('src', src);
				}, me.picWidth, me.picHeight);
			});
			// 文件上传过程中创建进度条实时显示。
			bindedObj.on('uploadProgress', function(file, percentage) {
                Labi.showProgressLoding();
                $('#upload_progress').html(percentage * 100 + "%");
                // $("#"+me.uploadBarId).css("width",percentage * 100 + "%");
			});

			// 文件上传成功，给item添加成功class, 用样式标记上传成功。
			bindedObj.on('uploadSuccess', function(file,response) {
				Labi.success("上传成功");
				// $("#" + me.pictureId).val(response);
                $("#" + me.uploadPreId).find('.other-img-d:last').data('url',response);
                me.setVal(me);
                Labi.hideProgressLoding();
			});

			// 文件上传失败，显示上传出错。
			bindedObj.on('uploadError', function(file) {
				Labi.error("上传失败");
                Labi.hideProgressLoding();
			});

			// 其他错误
			bindedObj.on('error', function(type) {
				if ("Q_EXCEED_SIZE_LIMIT" == type) {
					Labi.error("文件大小超出了限制");
				} else if ("Q_TYPE_DENIED" == type) {
					Labi.error("文件类型不满足");
				} else if ("Q_EXCEED_NUM_LIMIT" == type) {
					Labi.error("上传数量超过限制");
				} else if ("F_DUPLICATE" == type) {
					Labi.error("图片选择重复");
				} else {
					Labi.error("上传过程中出错");
				}
			});

			// 完成上传完了，成功或者失败
			bindedObj.on('uploadComplete', function(file) {
			});

            $(document).on("click", ".other-img-remove", function() {
                var that = this;
                Labi.confirm("确定删除该图片？", function() {
                    $(that).closest('.other-img-d').remove();
                    me.setVal(me);
                });
            });
            // $(document).on("mouseover", ".other-img-d", function() {
            //     $(this).find('.other-img-d-d').slideDown();
            // });
            // $(document).on("mouseout", ".other-img-d", function() {
            //     $(this).find('.other-img-d-d').slideUp();
            // });
		},
        setPreImgs: function(){
			var _this = this;
            $("#" + _this.pictureId).val().split(',').forEach(function(obj) {
            	if(Labi.noEmpty(obj)){
                    var $img = $('<div class="other-img-d"><div class="other-img-d-d"><i class="fa fa-trash other-img-remove" title="删除"></i></div><img class="upload-img otherImg" width="100px" height="100px" src="'+Labi.imgPreFix+obj+'"></div>');
					$img.data('url',obj);
					$("#" + _this.uploadPreId).append($img);
				}
            });
        },
        setVal: function(_this){
            var moreImages = [];
            $("#" + _this.uploadPreId).find('.other-img-d').each(function() {
                moreImages.push($(this).data('url'));
            });
            $("#" + _this.pictureId).val(moreImages.join());
		},
        /**
         * 设置图片上传的进度条的id
         */
        setUploadBarId: function (id) {
            this.uploadBarId = id;
        }
	};

	window.$WebMoreUpload = $WebMoreUpload;

}());