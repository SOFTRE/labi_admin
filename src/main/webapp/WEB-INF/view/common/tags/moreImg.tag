@/*
 	更多图片参数的说明:
    name : 名称
    id : 更多图片的id
@*/
<div class="form-group" id="div_moreImg">
    <label class="col-sm-3 control-label head-scu-label">${name}</label>
    <div class="col-sm-9">
        <div id="${id}PreId" class="inline">
            <img class="upload-img" width="100px" height="100px"
                @if(isEmpty(moreImg)){
                      src="${ctxPath}/static/img/default.png"
                @}
            />
        </div>
        <label>${tip!}</label>
        <div class="head-scu-btn upload-btn" id="${id}BtnId">
            <i class="fa fa-upload"></i>&nbsp;上传更多图片
        </div>
    </div>
    <input type="hidden" id="${id}" value="${moreImg!}"/>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}


