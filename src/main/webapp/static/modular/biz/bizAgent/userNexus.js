$(function () {
    $('#container').css('height',$(window).height()-50);
    var dom = document.getElementById("container");
    var myChart = echarts.init(dom);
    myChart.showLoading();
    var ajax = new $ax(Labi.ctxPath + "/bizAgent/ajax/usernexus", function(data){
        success(data);
    },function(data){
        Labi.error("获取信息!");
    });
    ajax.set("agentId",$('#agentId').val());
    ajax.start();
    function success(data) {
        myChart.hideLoading();
        var option = {
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: false, readOnly: false},
                    restore : {show: false},
                    saveAsImage : {show: true}
                }
            },
            series : [
                {
                    name:'树图',
                    type:'tree',
                    orient: 'horizontal',  // vertical horizontal
                    rootLocation: {x: 100, y: 'center'}, // 根节点位置  {x: 100, y: 'center'}
                    nodePadding: 8,
                    layerPadding: 200,
                    hoverable: false,
                    roam: true,
                    symbolSize: 12,
                    itemStyle: {
                        normal: {
                            color: '#4883b4',
                            label: {
                                show: true,
                                position: 'right',
                                formatter: "{b}",
                                textStyle: {
                                    color: '#000',
                                    fontSize: 12
                                }
                            },
                            lineStyle: {
                                color: '#ccc',
                                type: 'curve' // 'curve'|'broken'|'solid'|'dotted'|'dashed'

                            }
                        },
                        emphasis: {
                            color: '#4883b4',
                            label: {
                                show: false
                            },
                            borderWidth: 0
                        }
                    },

                    data: [data]
                }
            ]
        };

        // var option = {
        //     tooltip: {
        //         trigger: 'item',
        //         triggerOn: 'mousemove'
        //     },
        //     series: [
        //         {
        //             type: 'tree',
        //
        //             data: [data],
        //
        //             top: '1%',
        //             left: '7%',
        //             bottom: '1%',
        //             right: '20%',
        //
        //             symbolSize: 7,
        //
        //             label: {
        //                 normal: {
        //                     position: 'left',
        //                     verticalAlign: 'middle',
        //                     align: 'right',
        //                     fontSize: 9
        //                 }
        //             },
        //
        //             leaves: {
        //                 label: {
        //                     normal: {
        //                         position: 'right',
        //                         verticalAlign: 'middle',
        //                         align: 'left'
        //                     }
        //                 }
        //             },
        //
        //             expandAndCollapse: true,
        //             animationDuration: 550,
        //             animationDurationUpdate: 750
        //         }
        //     ]
        // };
        myChart.clear();
        myChart.setOption(option, true);
    }
});
