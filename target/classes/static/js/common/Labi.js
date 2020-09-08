var Labi = {
    ctxPath: "",
    imgPreFix: "",
    filePreFix: "",
    destDir: {
        banner: "banner",
        coach: "coach",
        prodCat: "prodCat",
        product: "product"
    },
    addCtx: function (ctx) {
        if (this.ctxPath == "") {
            this.ctxPath = ctx;
        }
    },
    addCloud: function (img, file) {
        if (this.imgPreFix == "") {
            this.imgPreFix = img;
        }
        if (this.filePreFix == "") {
            this.filePreFix = file;
        }
    },
    confirm: function (tip, ensure) {//询问框
        parent.layer.confirm(tip, {
            btn: ['确定', '取消']
        }, function (index) {
            ensure();
            parent.layer.close(index);
        }, function (index) {
            parent.layer.close(index);
        });
    },
    log: function (info) {
        console.log(info);
    },
    alert: function (info, iconIndex) {
        parent.layer.msg(info, {
            icon: iconIndex
        });
    },
    info: function (info) {
        Labi.alert(info, 0);
    },
    success: function (info) {
        Labi.alert(info, 1);
    },
    error: function (info) {
        Labi.alert(info, 2);
    },
    infoDetail: function (title, info) {
        var display = "";
        if (typeof info == "string") {
            display = info;
        } else {
            if (info instanceof Array) {
                for (var x in info) {
                    display = display + info[x] + "<br/>";
                }
            } else {
                display = info;
            }
        }
        parent.layer.open({
            title: title,
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['950px', '600px'], //宽高
            content: '<div style="padding: 20px;">' + display + '</div>'
        });
    },
    writeObj: function (obj) {
        var description = "";
        for (var i in obj) {
            var property = obj[i];
            description += i + " = " + property + ",";
        }
        layer.alert(description, {
            skin: 'layui-layer-molv',
            closeBtn: 0
        });
    },
    showInputTree: function (inputId, inputTreeContentId, leftOffset, rightOffset) {
        var onBodyDown = function (event) {
            if (!(event.target.id == "menuBtn" || event.target.id == inputTreeContentId || $(event.target).parents("#" + inputTreeContentId).length > 0)) {
                $("#" + inputTreeContentId).fadeOut("fast");
                $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
            }
        };

        if (leftOffset == undefined && rightOffset == undefined) {
            var inputDiv = $("#" + inputId);
            var inputDivOffset = $("#" + inputId).offset();
            $("#" + inputTreeContentId).css({
                left: inputDivOffset.left + "px",
                top: inputDivOffset.top + inputDiv.outerHeight() + "px"
            }).slideDown("fast");
        } else {
            $("#" + inputTreeContentId).css({
                left: leftOffset + "px",
                top: rightOffset + "px"
            }).slideDown("fast");
        }

        $("body").bind("mousedown", onBodyDown);
    },
    baseAjax: function (url, tip) {
        var ajax = new $ax(Labi.ctxPath + url, function (data) {
            Labi.success(tip + "成功!");
        }, function (data) {
            Labi.error(tip + "失败!" + data.responseJSON.message + "!");
        });
        return ajax;
    },
    changeAjax: function (url) {
        return Labi.baseAjax(url, "修改");
    },
    zTreeCheckedNodes: function (zTreeId) {
        var zTree = $.fn.zTree.getZTreeObj(zTreeId);
        var nodes = zTree.getCheckedNodes();
        var ids = "";
        for (var i = 0, l = nodes.length; i < l; i++) {
            ids += "," + nodes[i].id;
        }
        return ids.substring(1);
    },
    eventParseObject: function (event) {//获取点击事件的源对象
        event = event ? event : window.event;
        var obj = event.srcElement ? event.srcElement : event.target;
        return $(obj);
    },
    sessionTimeoutRegistry: function () {
        $.ajaxSetup({
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            complete: function (XMLHttpRequest, textStatus) {
                //通过XMLHttpRequest取得响应头，sessionstatus，
                var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
                if (sessionstatus == "timeout") {
                    //如果超时就处理 ，指定要跳转的页面
                    window.location = Labi.ctxPath + "/global/sessionError";
                }
            }
        });
    },
    initValidator: function (formId, fields) {
        $('#' + formId).bootstrapValidator({
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: fields,
            live: 'enabled',
            message: '该字段不能为空'
        });
    },
    noEmpty: function (value) {
        if (value == null || value == '' || value == undefined) {
            return false;
        }
        return true;
    },
    getFileUrl: function (sourceId) {
        var url;
        if (navigator.userAgent.indexOf("MSIE") >= 1) { // IE
            url = document.getElementById(sourceId).value;
        } else if (navigator.userAgent.indexOf("Firefox") > 0) { // Firefox
            url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
        } else if (navigator.userAgent.indexOf("Chrome") > 0) { // Chrome
            url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
        }
        return url;
    },
    preImg: function (sourceId, targetId) {
        var url = this.getFileUrl(sourceId);
        var imgPre = document.getElementById(targetId);
        imgPre.src = url;
    },
    getDate: function (dateTime) {
        if (dateTime) {
            return dateTime.substring(0, 10);
        }
    },
    frozenFormatter: function (val) {
        if (val == 'F')
            return '<i class="fa fa-check fa-lg" aria-hidden="true" title="激活" style="color: #00cc00;"></i>';
        if (val == 'T')
            return '<i class="fa fa-ban fa-lg" aria-hidden="true" title="冻结" style="color: #ff0066;"></i>';
    },
    showLoding: function () {
        if ($('#LodingDiv').length == 0) {
            $("body").append('<div id="LodingDiv"><div class="loding" style="display: block; margin-top: -124px;"><h2>请稍后<div class="sk-spinner sk-spinner-three-bounce"style="display: inline-block;"><div class="sk-bounce1"></div><div class="sk-bounce2"></div><div class="sk-bounce3"></div></div></h2></div><div class="loding-backdrop"></div></div>');
        }
    },
    showProgressLoding: function () {
        if ($('#LodingProgressDiv').length == 0) {
            $("body").append('<div id="LodingProgressDiv"><div class="loding" style="display: block; margin-top: -124px;"><h2>正在上传<span id="upload_progress"></span><div class="sk-spinner sk-spinner-three-bounce"style="display: inline-block;"><div class="sk-bounce1"></div><div class="sk-bounce2"></div><div class="sk-bounce3"></div></div></h2></div><div class="loding-backdrop"></div></div>');
        }
    },
    hideLoding: function () {
        $("#LodingDiv").remove();
    },
    hideProgressLoding: function () {
        $("#LodingProgressDiv").remove();
    },
    captureImage: function (videoFileId) {
        var video = document.getElementById(videoFileId);
        var canvas = document.createElement("canvas");
        var scale = 0.2;
        canvas.width = video.videoWidth * scale;
        canvas.height = video.videoHeight * scale;
        canvas.getContext('2d').drawImage(video, 0, 0, canvas.width, canvas.height);
        return canvas.toDataURL("image/png");
    },
    /*
     * formatMoney(s,type)
     * 功能：金额按千位逗号分隔，负号用括号
     * 参数：s，需要格式化的金额数值.
     * 参数：type,判断格式化后的金额是否需要小数位.
     * 返回：返回格式化后的数值字符串.
     */
    formatMoney: function (s, type) {
        var result = s;
        if (s < 0)
            s = 0 - s;
        if (/[^0-9\.]/.test(s))
            return "0.00";
        if (s == null || s == "null" || s == "")
            return "0.00";
        if (type > 0)
            s = new Number(s).toFixed(type);
        s = s.toString().replace(/^(\d*)$/, "$1.");
        s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1");
        s = s.replace(".", ",");
        var re = /(\d)(\d{3},)/;
        while (re.test(s))
            s = s.replace(re, "$1,$2");
        s = s.replace(/,(\d\d)$/, ".$1");
        if (type == 0) {
            var a = s.split(".");
            if (a[1] == "00") {
                s = a[0];
            }
        }
        if (result < 0)
            result = "(" + s + ")";
        else
            result = s;
        return result;
    },
    subtractDay: function (num) {
        var date2 = new Date();
        date2.setDate(date2.getDate() - num);
        var month = (date2.getMonth() + 1) + "";
        month = month.length === 1 ? ("0" + month) : +month;
        var day = date2.getDate() + "";
        day = day.length === 1 ? ("0" + day) : day;
        var time2 = date2.getFullYear() + "-" + month + "-" + day;
        return time2;
    },
    subtractMonth: function (num) {
        var d = new Date();
        d.setDate(1);
        var i = d.getMonth() - num;
        d.setMonth(i);
        var month = (d.getMonth() + 1) + "";
        month = month.length === 1 ? ("0" + month) : month
        var time2 = d.getFullYear() + "-" + month;
        return time2;
    },
    loadNow: function () {
        var date2 = new Date();
        var month = (date2.getMonth() + 1) + "";
        month = month.length === 1 ? ("0" + month) : +month;
        var day = date2.getDate() + "";
        day = day.length === 1 ? ("0" + day) : day;
        var time2 = date2.getFullYear() + "-" + month + "-" + day;
        return time2;
    },
    bytesToSize: function (bytes) {
        if (bytes === 0) return '0 B';
        var k = 1024;
        var sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
        var i = Math.floor(Math.log(bytes) / Math.log(k));
        return (bytes / Math.pow(k, i)).toFixed(2) + ' ' + sizes[i];
        //size(3) 后面保留一位小数，如1.0GB                                                                                                                  //return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];  
    }
};
$(document)
    .ajaxStart(function (event) {
        if (document.isLoading !== false) {
            Labi.showLoding();
        } else {
            Labi.hideLoding();
        }
    })
    .ajaxComplete(function () {
        Labi.hideLoding();
    });