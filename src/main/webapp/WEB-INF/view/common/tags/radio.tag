@/*
    è¡¨åä¸­radioæ ç­¾ä¸­åä¸ªåæ°çè¯´æ:

    hidden : input hiddenæ¡çid
    hiddenValue : éè inputçå¼
    name : radioæ¡åç§°
    checked : éä¸­çé¡¹çå¼
    clickFun : ç¹å»äºä»¶çæ¹æ³å
    style : éå çcsså±æ§
@*/
<div class="form-group">
    <label class="col-sm-3 control-label">${title}</label>
    <div class="col-sm-9 radio-inline">
        <input class="radio" type="radio" name="${name}" value="T"
               @if(isNotEmpty(checked)&&checked=="T"){
                    checked="checked"
               @}
               @if(isNotEmpty(clickFun)){
                    onclick="${clickFun}"
               @}
               @if(isNotEmpty(style)){
                     style="${style}"
               @}
        />是
        <input class="radio" type="radio" name="${name}" value="F"
               @if(isNotEmpty(checked)&&checked=="F"){
                    checked="checked"
               @}
               @if(isNotEmpty(clickFun)){
                    onclick="${clickFun}"
               @}
               @if(isNotEmpty(style)){
                    style="${style}"
               @}
        />否
        @if(isNotEmpty(hidden)){
            <input class="form-control" type="hidden" id="${hidden}" name="${hidden}"
                   @if(isNotEmpty(hiddenValue)){
                        value="${hiddenValue}"
                   @}
            />
        @}
    </div>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}


