@/*
    表单中input框标签中各个参数的说明:

@*/
<div class="form-group">
    <label class="col-sm-3 control-label">${name}</label>
    <div class="col-sm-9">
        <div class="col-sm-10" style="padding: 0;">
            <input class="form-control" id="${id}" name="${id}"
                   @if(isNotEmpty(maxLength)){
                        maxLength="${maxLength}"
                   @}
                   @if(isNotEmpty(placeholder)){
                        placeholder="${placeholder}"
                   @}
                   @if(isNotEmpty(value)){
                        value="${tool.dateType(value)}"
                   @}
                   @if(isNotEmpty(style)){
                        style="${style}"
                   @}
            />
        </div>
        <div class="col-sm-2">
            <span class="input-group-btn">
                <button type="button" class="btn btn-primary"
                    @if(isNotEmpty(clickFun)){
                    onclick="${clickFun}"
                    @}
                >搜索</button>
            </span>
        </div>
    </div>

</div>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}


