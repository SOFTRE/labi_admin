@/*
 	è§é¢ä¸ä¼ åæ°çè¯´æ:
    videoId : è§é¢å°åå¯¹åºçåå°æ¥æ¶åæ°å
    imgId : å°é¢å¾å°åå¯¹åºçåå°æ¥æ¶åæ°å
    videoUrl : è§é¢å°å
    imgUrl: å°é¢å¾å°å
@*/
<div class="form-group" id="div_video">
    <label class="col-sm-3 control-label head-scu-label">视频</label>
    <div class="col-sm-9">
        <div>
            <div class="inline">
                <img class="upload-video-img" id="${imgId}VideoPreId"
                @if(isEmpty(videoUrl)){
                     src="${ctxPath}/static/img/default-video.png">
                @}else{
                     src="${ctxPath}/static/img/default-video-success.png">
                @}
                <img class="upload-video-img" id="${imgId}ImgPreId"
                     @if(isEmpty(imgUrl)){
                     src="${ctxPath}/static/img/default.png">
                @}else{
                src="${imgUrl}">
                @}
            </div>
            <label>推荐上传的格式为：mp4,avi 其他允许上传的格式：wmv,mov,webm,mpeg4,ts,mpg,rm,rmvb,mkv
            </label>
        </div>
        <div class="head-scu-btn upload-video-btn webuploader-pick" id="${videoId}VideoBtnId">
            <i class="fa fa-upload"></i>&nbsp;视频
        </div>
        <div class="head-scu-btn upload-video-btn webuploader-pick" id="${imgId}ImgBtnId">
            <i class="fa fa-upload"></i>&nbsp;视频封面
        </div>
        <div class="head-scu-btn upload-video-btn webuploader-pick" id="${videoId}StartId">
            <i class="fa fa-upload"></i>&nbsp;上传
        </div>
    </div>
    <form id="${videoId}Form">
        <input type="file" style="display:none;" id="${videoId}File" accept="video/*"/>
        <input type="file" style="display:none;" id="${imgId}File" accept="image/gif,image/jpg,image/jpeg,image/bmp,image/png"/>
    </form>
    <input type="hidden" id="${videoId}" name="${videoId}" value="${videoUrl!}"/>
    <input type="hidden" id="${imgId}" name="${imgId}" value="${imgUrl!}"/>
    @if(!isEmpty(videoSizeId)){
        <input type="hidden" id="${videoSizeId}" name="${videoSizeId}" value="${videoSize!}"/>
    @}
</div>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}


