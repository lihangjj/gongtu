

function loadData() {
    $.ajax({
        url: "/pages/back/log/listAjax",
        data: {
            "column": column,
            "keyWord": keyWord,
            "lineSize": lineSize,
            "currentPage": currentPage,
            "parameterName": 'condition',
            "parameterValue": "dflag=0 and time like '%" + y + "-" + getM(m) + getD(d) + "%' " + getName(name) + getStatus(st)
        },
        type: "post",
        dataType: "json",
        success: function (data) {
            var allVo = data.allVo;
            allRecorders = data.allRecorders;
            var dntb = $("#logtb");
            $("#row1").nextAll().remove();
            var d = new Date();
            for (var x = 0; x < allVo.length; x++) {
                var log = allVo[x];
                var logid = log.logid;
                var p = log.project;
                var c;
                var companyName;
                var type;
                var cid;
                var pname;
                var sta;
                var staColor;
                if (p == null) {
                    companyName = "";
                    pname = "";
                    type = "其他类型日志";
                    sta = "";
                    staColor = "";

                } else {
                    c = p.contract;
                    cid = c.contractid;
                    companyName = c.companyName;
                    pname = p.name;
                    sta = p.status;
                    staColor = sta == '进行中' ? 'red' : sta == '暂停' ? 'orange' : sta == '完结' ? 'green' : '';
                    switch (p.type) {
                        case "px":
                            type = "培训";
                            break;
                        case "qt":
                            type = "其他";
                            break;
                        case "zz":
                            type = "资质";
                            break;
                        case "gk":
                            type = "挂靠";
                            break;
                        case "dp":
                            type = "代评";
                            break;
                    }
                }
                var s = (d.getTime() - log.time) / (1000 * 60 * 60);
                console.log(d.getTime() + 'd');
                console.log(log.time);
                console.log(s + '@111111111111')
                var status = memberid == spmid ? "" : s > 24 ? "disabled" : "";
                var color = type == "其他类型日志" ? "red" : "";
                var yesColor = log.type == "配合" ? "red" : "";

                dntb.append($("<tr id='row-" + logid + "' >\n" +
                    "                        <td ><input class=\"form-control\" type=\"checkbox\" id='check-" + logid + "'/></td>\n" +
                    "                        <td style='text-align: left;color: " + color + "' ><a id='alog-"+logid+"' style='text-decoration: none;cursor: pointer' href='/pages/back/contract/editPre?contractid=" + cid + "'>" + companyName + "</a>-" + pname + "-" + type + "-<span style='color: " + staColor + "'>" + sta + "</span></td>\n" +
                    "                        <td ><textarea id='coordination-" + logid + "' name='" + log.memberid + "' class='form-control'>" + log.coordination + "</textarea></td>\n" +
                    "                        <td   style='color: " + yesColor + "' >" + log.type + "</td>\n" +
                    "                        <td ><textarea id='log-" + logid + "' name='" + log.memberid + "' class='form-control'>" + log.note + "</textarea></td>\n" +
                    "                        <td >" + new Date(log.time).format("yyyy-M-dd hh:mm") + "<br/>" + log.member.name + "</td>\n" +
                    "                        <td><button class='btn btn-primary' id='bt-" + logid + "' " + status + " >修改</button> </td>\n" +
                    "                    </tr>"));

            }
            hasRole('contract:总经理-副总-财务主管')?'':$("[id^=alog-]").removeAttr('href');
            windowNW < 768 ? $("[id^=log-]").css({
                'min-width': '12rem',
                'min-height': '20rem'
            }) : $("[id^=log-]").css({'min-width': '40rem'});
            $("[id^=bt-]").each(function () {
                $(this).click(function () {
                    var id = this.id.split('-')[1];
                    $.post("/pages/back/log/edit", {
                            logid: id, coordination: $("#coordination-" + id).val(),
                            note: $("#log-" + id).val(),
                            memberid:
                            $("#log-" + id).get(0).name
                        },

                        function (res) {
                            var re = res.split(":")[0];
                            var str = res.split(":")[1];
                            if ("true" == re) {
                                showAlert($("#successMsg"), str)
                            } else {
                                showAlert($("#failureMsg"), str)
                            }

                        }
                        ,
                        "text"
                    )
                });

            });

            createSplitBar(allRecorders);
            //导航栏应该与此时的内容高度相同
            sameHeight();
        }
        ,
        error: function (e) {
        }
    });
}


// /*]]>*/
$(function () {
    shoujiOpenOverFlowX();
    //这里需要复写parameterName;
    y = $("#y").val();
    m = $("#m").val();
    loadData();
    $("#checkAll").click(function () {
        var c = this.checked;
        $("[id^=check-]").each(function () {
            this.checked = c;
        })
    });
    $("#pldele").click(function () {
        var str = "";
        $("[id^=check-]").each(function () {
            if (this.checked) {
                str += this.id.split('-')[1] + "-";
            }
        });
        if (str == "") {
            showAlert($("#warningMsg"), "您还未选择任何数据")
        } else {
            areYouSure("您确定要删除选中数据？", function () {

                $.post("/pages/back/log/plDeleteLog", {str: str}, function (res) {
                    if (res) {
                        var ids = str.split('-');
                        for (var x = 0; x < ids.length; x++) {
                            $("#row-" + ids[x]).remove();
                        }
                        $("#checkAll").get(0).checked = false;
                        showAlert($("#successMsg"), "日志删除成功");
                    } else {
                        showAlert($("#failureMsg"), "日志删除失败");
                    }
                }, "json");
            });

        }


    });
    enterKeySubmit(loadData);
    //检索按钮绑定事件
    jiansuo(loadData);
    $("#y").change(function () {
        currentPage = 1;
        y = this.value;
        loadData();
    });
    $("#m").change(function () {
        currentPage = 1;
        m = this.value;
        loadData();
    });
    $("#d").change(function () {
        currentPage = 1;
        d = this.value;
        loadData();
    });
    $("#author").change(function () {
        currentPage = 1;
        name = this.value;
        $("#authorInput").val($(this).find("option:selected").text());
        loadData();
    });
    $("#status").change(function () {
        currentPage = 1;
        st = this.value;
        loadData();

    });
    $("#authorInput").blur(function () {
        currentPage = 1;
        var val = this.value;
        if ('' == val || '所有' == val) {
            name = ''
        } else {
            name = $("#author option:contains(" + val + ")").val();

        }
        loadData();
    })

});
var name = '';
var y = '';
var m = '';
var d = '';
var st = '';

function getStatus(st) {
    if (st == '') {
        return ''
    } else {
        if (st == '其他') {
            return " and type='" + st + "'"
        }
        return ' split status=' + st;
    }

}

//得到月份05格式
function getM(m) {
    if (m == '') {
        return '';
    } else {
        if (m < 10) {
            return "0" + m + "-";
        } else {
            return m + "-";
        }
    }
}

function getD(d) {
    if (d == '') {
        return '';
    } else {
        if (d < 10) {
            return "0" + d;
        } else {
            return d;
        }
    }
}

function getName(name) {
    if (name == '') {
        return '';
    } else {
        return " and memberid='" + name + "'";
    }
}



