@/*
 	å¤´ååæ°çè¯´æ:
    name : åç§°
    id : å¤´åçid
@*/
<div class="form-group" id="div_img">
    <label class="col-sm-3 control-label head-scu-label">${name}</label>
    <div class="col-sm-9">
        <div id="${id}PreId">
            <div class="inline"><img class="upload-img" width="100px" height="100px"
                @if(isEmpty(avatarImg)){
                      src="${ctxPath}/static/img/default.png"></div>
                @}else{
                      src="${imgPreFix}/${avatarImg}"></div>
                @}
        <label>${tip!}</label>
        </div>
        <div class="head-scu-btn upload-btn" id="${id}BtnId">
            <i class="fa fa-upload"></i>上传图片
        </div>
    </div>
    <input type="hidden" id="${id}" value="${avatarImg!}"/>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}


