@/*
    id : textarea id
    name : textarea name
    value:textarea value
@*/
<div class="form-group">
    <label class="col-sm-3 control-label">${name}</label>
    <div class="col-sm-9">
    
    <textarea class="form-control" id="${id}" name="${id}" style="height: 200px"
     @if(isNotEmpty(rows)){
                    rows="${rows}"
     @}
    @if(isNotEmpty(cols)){
                    cols="${cols}"
     @}
    
    >@if(isNotEmpty(value)){
${value}
     @}
     </textarea>
    </div>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}


