$(function () {
    // $('#container').css('height',$(window).height()-50);
    createChart('day');
    createMoneyCake();
    createOrderCake()
    loadTotalMoney("");
    loadTotalNumber("");
    loadUserTotalCount("");
});

function loadTotalMoney(type, _this) {
    var ajax = new $ax(Labi.ctxPath + "/blackboard/totalmoney", function (data) {
        $('#totalMoney').html(Labi.formatMoney(data.totalMoney, 2));
        if (parseInt(data.proportion) >= 100) {
            $('#totalMoneyChange').addClass('text-info').removeClass('text-danger');
            $('#totalMoneyChange').html(parseInt(data.proportion) + "% <i class='fa fa-level-up'>");
        } else {
            $('#totalMoneyChange').removeClass('text-info').addClass('text-danger');
            $('#totalMoneyChange').html((100 - parseInt(data.proportion)) + "% <i class='fa fa-level-down'>");
        }
        if (_this) {
            $(_this).parent().find('button').removeClass("active").removeClass("btn-success").addClass("btn-white");
            $(_this).addClass("active").removeClass("btn-white").addClass("btn-success");
        }
        if (type == "day") {
            $('#moneyTitle').html('昨日收入');
        }
        if (type == "month") {
            $('#moneyTitle').html('本月收入');
        }
        if (type == "year") {
            $('#moneyTitle').html('本年收入');
        }
        if (type == "") {
            $('#moneyTitle').html('总收入');
        }
    }, function () {
        Labi.error("获取信息失败!");
    });
    ajax.set("type", type);
    ajax.start();
}

function loadTotalNumber(type, _this) {
    var ajax = new $ax(Labi.ctxPath + "/blackboard/totalnumber", function (data) {
        $('#totalNumber').html(Labi.formatMoney(data.totalNumber, 2));
        if (parseInt(data.proportion) >= 100) {
            $('#totalNumberChange').addClass('text-info').removeClass('text-danger');
            $('#totalNumberChange').html(parseInt(data.proportion) + "% <i class='fa fa-level-up'>");
        } else {
            $('#totalNumberChange').removeClass('text-info').addClass('text-danger');
            $('#totalNumberChange').html((100 - parseInt(data.proportion)) + "% <i class='fa fa-level-down'>");
        }
        if (_this) {
            $(_this).parent().find('button').removeClass("active").removeClass("btn-success").addClass("btn-white");
            $(_this).addClass("active").removeClass("btn-white").addClass("btn-success");
        }
        if (type == "day") {
            $('#orderTitle').html('昨日订单');
        }
        if (type == "month") {
            $('#orderTitle').html('本月订单');
        }
        if (type == "year") {
            $('#orderTitle').html('本年订单');
        }
        if (type == "") {
            $('#orderTitle').html('总订单');
        }
    }, function () {
        Labi.error("获取信息失败!");
    });
    ajax.set("type", type);
    ajax.start();
}

function loadUserTotalCount(type, _this) {
    var ajax = new $ax(Labi.ctxPath + "/blackboard/totaluser", function (data) {
        $('#userTotalCount').html(Labi.formatMoney(data.totalCount, 2));
        if (parseInt(data.proportion) >= 100) {
            $('#userTotalCountChange').addClass('text-info').removeClass('text-danger');
            $('#userTotalCountChange').html(parseInt(data.proportion) + "% <i class='fa fa-level-up'>");
        } else {
            $('#userTotalCountChange').removeClass('text-info').addClass('text-danger');
            $('#userTotalCountChange').html((100 - parseInt(data.proportion)) + "% <i class='fa fa-level-down'>");
        }
        if (_this) {
            $(_this).parent().find('button').removeClass("active").removeClass("btn-success").addClass("btn-white");
            $(_this).addClass("active").removeClass("btn-white").addClass("btn-success");
        }
        if (type == "day") {
            $('#userTitle').html('昨日用户');
        }
        if (type == "month") {
            $('#userTitle').html('本月用户');
        }
        if (type == "year") {
            $('#userTitle').html('本年用户');
        }
        if (type == "") {
            $('#userTitle').html('总用户');
        }
    }, function () {
        Labi.error("获取信息失败!");
    });
    ajax.set("type", type);
    ajax.start();
}

function createChart(type,_this) {
    var dom = document.getElementById("container");
    var myChart = echarts.init(dom, 'macarons');
    myChart.showLoading();
    var xAxisData = [];
    if ('day' == type) {
        for (var i = 7; i > 0; i--) {
            xAxisData.push(Labi.subtractDay(i));
        }
    }
    if ('month' == type) {
        for (var i = 5; i >= 0; i--) {
            xAxisData.push(Labi.subtractMonth(i));
        }
    }
    var ajax = new $ax(Labi.ctxPath + "/blackboard/chartdata", function (data) {
        var legendData = [];
        var seriesData = [];
        for (var i in data) {
            legendData.push(data[i]['simplename']);


            var reportsData = {};
            for (var j in data[i]['reports']) {
                reportsData[data[i]['reports'][j]['time']] = data[i]['reports'][j]['totalPrice'];
            }
            var seriesObjData = [];
            for (var j in xAxisData) {
                var totalPrice = reportsData[xAxisData[j]];
                totalPrice = !totalPrice ? 0 : totalPrice;
                seriesObjData.push(totalPrice)
            }
            var seriesObj = {
                name: data[i]['simplename'],
                type: 'bar',
                data: seriesObjData
            }
            seriesData.push(seriesObj);
        }
        legendData.push('平均值');
        var averageData = [];
        for (var i in xAxisData) {
            var total = 0;
            var max = data.length;
            while (max > 0) {
                total += seriesData[--max]['data'][i];
            }
            averageData[i] = parseFloat(total/data.length).toFixed(2);
        }
        seriesData.push({
            name: '平均值',
            type: 'line',
            data: averageData
        });
        var option = {
            tooltip: {
                trigger: 'axis'
            },
            toolbox: {
                show: false,
                y: 'bottom',
                feature: {
                    mark: {show: false},
                    dataView: {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            legend: {
                data: legendData
            },
            xAxis: [
                {
                    type: 'category',
                    splitLine: {show: false},
                    data: xAxisData
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    position: 'right'
                }
            ],
            series: seriesData
        };
        myChart.clear();
        myChart.setOption(option, true);
        myChart.hideLoading();
        if (_this) {
            $(_this).parent().find('button').removeClass("active").removeClass("btn-success").addClass("btn-white");
            $(_this).addClass("active").removeClass("btn-white").addClass("btn-success");
        }
    }, function () {
        Labi.error("获取信息失败!");
    });
    ajax.set("limit", 20);
    ajax.set("offset", 1);
    ajax.set("type", type);
    ajax.set("endDate", Labi.loadNow());
    ajax.start();
}


function createMoneyCake() {
    var dom = document.getElementById("moneyCake");
    var myChart = echarts.init(dom, 'macarons');
    myChart.showLoading();
    var ajax = new $ax(Labi.ctxPath + "/blackboard/chartdata", function (data) {

        var legendData = [];
        var seriesData = [];
        for (var i in data) {
            legendData.push(data[i]['simplename']);
            seriesData.push({
                name:data[i]['simplename'],
                value:data[i]['reports'][0]['totalPrice']
            })

        }
        var option = {
            title : {
                text: '各公司收入比例',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient : 'vertical',
                x : 'left',
                data:legendData
            },
            calculable : true,
            series : [
                {
                    name:'收入比例',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:seriesData
                }
            ]
        };
        myChart.clear();
        myChart.setOption(option, true);
        myChart.hideLoading();
    }, function () {
        Labi.error("获取信息失败!");
    });
    ajax.set("limit", 20);
    ajax.set("offset", 1);
    ajax.set("type", "all");
    ajax.start();
}
function createOrderCake() {
    var dom = document.getElementById("orderCake");
    var myChart = echarts.init(dom, 'macarons');
    myChart.showLoading();
    var ajax = new $ax(Labi.ctxPath + "/blackboard/chartdata", function (data) {

        var legendData = [];
        var seriesData = [];
        for (var i in data) {
            legendData.push(data[i]['simplename']);
            seriesData.push({
                name:data[i]['simplename'],
                value:data[i]['reports'][0]['num']
            })

        }
        var option = {
            title : {
                text: '各公司订单数量比例',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient : 'vertical',
                x : 'left',
                data:legendData
            },
            calculable : true,
            series : [
                {
                    name:'订单数量比例',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:seriesData
                }
            ]
        };
        myChart.clear();
        myChart.setOption(option, true);
        myChart.hideLoading();
    }, function () {
        Labi.error("获取信息失败!");
    });
    ajax.set("limit", 20);
    ajax.set("offset", 1);
    ajax.set("type", "all");
    ajax.start();
}
