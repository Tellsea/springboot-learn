Highcharts.setOptions({
    global: {
        useUTC: false
    }
});
Highcharts.chart('memory', {
    chart: {
        type: 'spline',
        animation: Highcharts.svg,
        marginRight: 10,
        events: {
            load: function () {
                var series = this.series[0];
                setInterval(function () {
                    //请求后台获取数据
                    var xhr = new XMLHttpRequest();
                    xhr.open('get', '/redis/memory');
                    xhr.send();
                    xhr.onreadystatechange = function() {
                        if (xhr.status == 200) {
                            if (xhr.responseText != "") {
                                var $this = JSON.parse(xhr.responseText);
                                var x = $this.create_time, y = $this.memory / 1024;
                                series.addPoint([x, y], true, true);
                            }
                        }
                    }
                }, 3e3);
            }
        }
    },
    title: {
        text: "Redis 内存实时占用情况",
        style: {
            "font-size": "1.2rem"
        }
    },
    xAxis: {
        type: 'datetime',
        tickPixelInterval: 150
    },
    yAxis: {
        title: {
            text: "kb"
        },
        plotLines: [ {
            value: 0,
            width: 1,
            color: "#808080"
        } ]
    },
    tooltip: {
        formatter: function () {
            return '<b>' + this.series.name + '</b><br/>' +
                Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                Highcharts.numberFormat(this.y, 2);
        }
    },
    legend: {
        enabled: false
    },
    series: [ {
        name: "内存占用（kb）",
        data: function() {
            var data = [], time = new Date().getTime(), i;
            for (i = -19; i <= 0; i++) {
                data.push({
                    x: time + i * 1e3,
                    y: Math.random() * (1e3 - 800) + 800
                });
            }
            return data;
        }()
    } ]
});

Highcharts.chart('dbsize', {
    chart: {
        type: "spline",
        animation: Highcharts.svg,
        marginRight: 10,
        events: {
            load: function() {
                var series = this.series[0];
                setInterval(function() {
                    //请求后台获取数据
                    var xhr = new XMLHttpRequest();
                    xhr.open('get', '/redis/dbsize');
                    xhr.send();
                    xhr.onreadystatechange = function() {
                        if (xhr.status == 200) {
                            if (xhr.responseText != "") {
                                var $this = JSON.parse(xhr.responseText);
                                var x = $this.create_time, y = $this.dbsize;
                                series.addPoint([x, y], true, true);
                            }
                        }
                    }
                }, 3e3);
            }
        }
    },
    title: {
        text: "Redis key的实时数量",
        style: {
            "font-size": "1.2rem"
        }
    },
    xAxis: {
        type: "datetime",
        tickPixelInterval: 150
    },
    yAxis: {
        title: {
            text: ""
        },
        plotLines: [ {
            value: 0,
            width: 1,
            color: "#808080"
        } ]
    },
    tooltip: {
        formatter: function() {
            return "<b>" + this.series.name + "</b><br/>" + Highcharts.dateFormat("%Y-%m-%d %H:%M:%S", this.x) + "<br/>" + Highcharts.numberFormat(this.y, 2);
        }
    },
    credits: {
        enabled: false
    },
    legend: {
        enabled: false
    },
    exporting: {
        enabled: false
    },
    series: [ {
        name: "keys",
        data: function() {
            var data = [], time = new Date().getTime(), i;
            for (i = -19; i <= 0; i++) {
                data.push({
                    x: time + i * 1e3,
                    y: 0
                });
            }
            return data;
        }()
    } ]
});