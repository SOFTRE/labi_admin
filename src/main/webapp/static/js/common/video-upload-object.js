/**
 * web-upload 工具类
 *
 * 约定：
 * 上传视频按钮的videoId = 视频隐藏域id + 'VideoBtnId'
 * 上传封面按钮的imgId = 封面隐藏域id + 'ImgBtnId'
 * 图片预览框的id = 图片隐藏域imgId + 'VideoPreId'
 * videoId : 视频隐藏域id
 * imgId : 封面隐藏域id
 * 视频隐藏域id + StartId : 开始上传
 *
 * @author lyr
 */
(function () {

    var $WebVideoUpload = function (videoId, imgId,videoSizeId) {
        this.videoId = videoId;
        this.videoSizeId = videoSizeId;
        this.formId = videoId + "Form";
        this.imgId = imgId;
        this.uploadVideoBtnId = videoId + "VideoBtnId";
        this.uploadImgBtnId = imgId + "ImgBtnId";
        this.uploadPreId = imgId + "VideoPreId";
        this.uploadImgPreId = imgId + "ImgPreId";
        this.startId = videoId + "StartId";
        this.videoPlayId = videoId + "VideoPlayId";
        this.videoFileId = videoId + 'File';
        this.imgFileId = imgId + 'File';
        this.fileSizeLimit = 1 * 1024 * 1024 * 1024;
        this.uploadBarId = null;
        this.videoFile = null;
        this.imgFile = null;
    };

    $WebVideoUpload.prototype = {
        /**
         * 初始化WebVideoUpload
         */
        init: function (obj) {
            this.bindEvent(obj);
        },
        /**
         * 绑定事件
         */
        bindEvent: function (obj) {

            $('#' + obj.uploadVideoBtnId).on('click', function () {
                $('#' + obj.videoFileId).click();
            });
            $('#' + obj.videoFileId).on('change', function (e) {
                var videoFile = this.files[0];
                if (videoFile.size > obj.fileSizeLimit) {
                    Labi.error('最大支持视频大小1G，请重新上传');
                    return false;
                }
                obj.videoFile = videoFile;
                var imgPre = document.getElementById(obj.uploadPreId);
                imgPre.src = Labi.ctxPath + '/static/img/default-video-success.png';
                // console.log(Labi.captureImage(obj.videoFileId));
                Labi.success('视频已选择');
            });
            $('#' + obj.uploadImgBtnId).on('click', function () {
                $('#' + obj.imgFileId).click();
            });
            $('#' + obj.imgFileId).on('change', function (e) {
                var coverFile = this.files[0];
                obj.imgFile = coverFile;
                Labi.preImg(obj.imgFileId, obj.uploadImgPreId);
                Labi.success('封面已选择');
            });
            $('#' + obj.startId).on('click', function (e) {
                if (obj.videoFile.size > obj.fileSizeLimit) {
                    Labi.error('最大支持视频大小1G，请重新上传');
                    return false;
                }
                obj['videoFileSize'] = obj.videoFile.size;
                obj.startUploader(obj);
                $('#' + obj.formId)[0].reset();
                obj.videoFile = '';
            });

        },
        getSignature: function (callback) {
            $.ajax({
                url: Labi.ctxPath + "/cloud/signature",
                data: JSON.stringify({
                    "Action": "GetVodSignatureV2"
                }),
                type: 'POST',
                dataType: 'json',
                success: function (res) {
                    if (res) {
                        callback(res);
                    } else {
                        return '获取签名失败';
                    }

                }
            });
        },
        startUploader: function (obj) {
            if (obj.videoFile) {
                Labi.showProgressLoding();
                $('#upload_progress').html('0%');
                var resultMsg = qcVideo.ugcUploader.start({
                    videoFile: obj.videoFile,
                    coverFile: obj.imgFile,
                    getSignature: obj.getSignature,
                    allowAudio: 1,
                    success: function (result) {
                        if (result.type == 'video') {
                            Labi.success('视频上传成功');
                        } else if (result.type == 'cover') {

                        }
                    },
                    error: function (result) {
                        if (result.type == 'video') {
                            Labi.error('上传失败>>' + result.msg);
                        } else if (result.type == 'cover') {
                            Labi.error('上传失败>>' + result.msg);
                        }
                    },
                    finish: function (result) {
                        // console.log(result);
                        $('input[name="' + obj.videoId + '"]').val(result.videoUrl);
                        $('input[name="' + obj.imgId + '"]').val(result.coverUrl);
                        if(obj.videoSizeId){
                            $('input[name="' + obj.videoSizeId + '"]').val(obj.videoFileSize);
                        }
                        Labi.hideProgressLoding();

                    },
                    progress: function (result) {
                        Labi.showProgressLoding();
                        if (result.type == 'video') {
                            // console.log(result.name);
                            // console.log(Math.floor(result.shacurr*100)+'%');
                            // console.log(Math.floor(result.curr*100)+'%');
                            $('#upload_progress').html(Math.floor(result.curr * 100) + '%');
                            // console.log('taskId', result.taskId);
                        } else if (result.type == 'cover') {
                            // console.log(result.name);
                            // console.log(Math.floor(result.shacurr*100)+'%');
                            // console.log(Math.floor(result.curr*100)+'%');
                        }
                    }
                });
                if (resultMsg) {
                    Labi.info(resultMsg);
                }
            } else {
                Labi.error('请选择视频!');
            }

        },
        /**
         * 设置上传的进度条的id
         */
        setUploadBarId: function (id) {
            this.uploadBarId = id;
        }
    };
    window.$WebVideoUpload = $WebVideoUpload;
}());