var status = '进行中';
var type = '';

function getStatus(status) {
    if (status == '') {
        return '';
    } else {
        return " AND contractid IN(SELECT contractid FROM contract WHERE status='" + status + "')"

    }
}

function getColumnAndKeyWord(column, keyWord) {
    if (keyWord == '') {
        return ''
    } else {
        if (column == 'companyName') {//查询公司名字
            return " AND contractid IN(SELECT contractid FROM contract WHERE companyName like '%" + keyWord + "%') "
        } else {
            return " AND " + column + " LIKE '%" + keyWord + "%' "
        }
    }

}

function getType(type) {
    if (type == '') {
        return '';
    } else {
        return " AND type='" + type + "' "
    }
}


function loadData() {
    $.ajax({
        url: "/pages/back/project/listAjax",
        data: {
            "column": column,
            "keyWord": keyWord,
            "lineSize": lineSize,
            "currentPage": currentPage,
            "parameterName": 'type',
            "parameterValue": getType(type) + getColumnAndKeyWord(column, keyWord) + getStatus(status)
        },
        type: "post",
        dataType: "json",
        success: function (data) {
            var allVo = data.allVo;
            allRecorders = data.allRecorders;
            var dntb = $("#dntb");
            $("#row1").nextAll().remove();
            for (var x = 0; x < allVo.length; x++) {
                var p = allVo[x];
                var pid = p.projectid;
                var c = p.contract;
                var companyName = c.companyName;
                var statusColor = p.status == "进行中" ? "red" : p.status == "暂停" ? "orange" : "green";
                var status = p.status;
                var signingDate = c.signingDate == null ? '' : c.signingDate;
                var expireDate = c.expireDate == null ? '' : c.expireDate;
                var type;
                var logs = p.logs;
                var rencaiText = p.rencais.length > 0 ? '查看' : '无';
                var rencaiColor = p.rencais.length > 0 ? 'green' : '无';

                allLogs = logs;
                var log = p.logs.length == 0 ? "无" : "查看";
                var logColor = p.logs.length == 0 ? "" : "green";
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
                dntb.append($("<tr id='row-" + pid + "' >\n" +
                    "                        <td ><input class=\"form-control\" type=\"checkbox\" id='check-" + pid + "'/></td>\n" +
                    "                        <td style='text-align: left' ><a id='a' style='text-decoration: none;cursor: pointer' href='/pages/back/contract/editPre?contractid=" + c.contractid + "'>" + companyName + "</a>-" + p.name + "</td>" +
                    "                       <td>" + type + "</td>\n" +
                    "                        <td id='xuanBt'><button id='xuan-" + pid + "' class='btn btn-success'>选人才</button></td>\n" +
                    "                        <td id='rcTD-" + pid + "' style='cursor: pointer'>" +
                    "<div class='dropdown'>" +
                    "<span style='color:" + rencaiColor + ";' data-toggle='dropdown-toggle'>" + rencaiText + "</span>" +
                    "<div class='dropdown-menu' id='rcMenu-" + pid + "' style='margin-left: -45rem;'>" +
                    "<table style='text-align: center;width:45rem' id='rcTB-" + pid + "' class='table'></table>" +
                    "</div>" +
                    "</div>" +
                    "</td>\n" +
                    "                        <td id='p-" + pid + "' style='color: " + logColor + ";cursor: pointer;'><div style='color: black' class=\"dropdown\">\n" +
                    "                                    <span id='pp-" + pid + "' class=\"dropdown-toggle\" data-toggle='dropdown' style='color: " + logColor + ";cursor: pointer;'>" + log + "</span>\n" +
                    "                                    <div class=\"dropdown-menu\" style='width: 50rem;margin-left: 7rem;margin-top: -10rem'>\n" +
                    "                                        <table id='logtb-" + pid + "' class=\"table\" style='text-align: center'>\n" +
                    "                                            <tr id='tr1-" + pid + "' style='font-weight: bold'>\n" +
                    "                                                <td>类型</td>\n" +
                    "                                                <td>协调</td>\n" +
                    "                                                <td style='width: 20rem'>内容</td>\n" +
                    "                                                <td>时间-作者</td>\n" +
                    "                                            </tr>\n" +
                    "                                        </table>\n" +
                    "                                    </div>\n" +
                    "                                </div></td>\n" +
                    "                        <td>" + p.executive + "</td>\n" +
                    "                        <td>" + signingDate + "</td>\n" +
                    "                        <td>" + expireDate + "</td>\n" +
                    "                        <td style='color: " + statusColor + "'><select id='select-" + pid + "'>\n" +
                    "                                            <option value='进行中' id='jinxingzhong'>进行中</option>\n" +
                    "                                            <option value='暂停' id='zanting'>暂停</option>\n" +
                    "                                            <option id='wanjie' value='完结'>完结</option>\n" +
                    "                                        </select></td>\n" +
                    "                    </tr>"));
                $("#select-" + pid).children("option").each(function () {
                    if (status == this.value) {
                        this.selected = true;
                    }
                });

                var rcTB = $("#rcTB-" + pid);
                rcTB.append("<tr style='font-weight: bold'>" +
                    "<td style='width: 2rem;padding-left: 1rem'></td>" +
                    "<td>照片</td>" +
                    "<td>姓名</td>" +
                    "<td>类别</td>" +
                    "<td>专业-等级</td>" +
                    "</tr>");
                for (var m = 0; m < p.rencais.length; m++) {
                    var rc = p.rencais[m];
                    var xuhao = m + 1;
                    var zsdengji = rc.zsdengji == null ? '' : rc.zsdengji;
                    rcTB.append("<tr style='border: dashed 0.1px #ff7f19'><td style='width:2rem;padding-left: 1rem'>" + xuhao + "</td>" +
                        "<td><img style='width: 5rem;border-radius: 5px' src=" + rc.photo + " name='" + rc.bigPhoto + "'></td>" +
                        "<td>" + rc.name + "</td>" +
                        "<td>" + rc.rcType + "</td>" +
                        "<td>" + rc.zszhuanye + "-" + zsdengji + "</td>" +
                        "</tr>");
                }
                for (var y = (logs.length > 4 ? logs.length - 5 : 0); y < logs.length; y++) {
                    var log = logs[y];
                    $("#tr1-" + pid).after("<tr >\n" +
                        "<td>" + log.type + "</td>\n" +
                        "<td>" + log.coordination + "</td>\n" +
                        "<td>" + log.note + "</td>\n" +
                        " <td>" + new Date(log.time).format("yyyy-M-dd hh:mm:ss") + "-" + log.member.name + "</td>\n" +
                        " </tr>");
                }
                var more = $("<tr id='more-" + pid + "' >\n" +
                    "<td colspan='4' style='color: green' >更多</td>\n" +
                    " </tr>");

                more.get(0).m = logs;
                more.click(function () {
                    var id = this.id.split('-')[1];
                    var allLogs = this.m;
                    $("#tr1-" + id).nextAll().remove();
                    for (var m = 0; m < allLogs.length; m++) {
                        var log = allLogs[m];
                        $("#tr1-" + id).after("<tr >\n" +
                            "<td>" + log.type + "</td>\n" +
                            "<td>" + log.coordination + "</td>\n" +
                            "<td>" + log.note + "</td>\n" +
                            " <td>" + new Date(log.time).format("yyyy-M-dd hh:mm:ss") + "-" + log.member.name + "</td>\n" +
                            " </tr>");
                    }
                });
                logs.length > 4 ? $("#logtb-" + pid).append(more) : "";

            }
            $("[id^=select-]").change(function () {
                var id = this.id.split('-')[1];
                $.post("/pages/back/project/editProjectStatus", {
                    projectid: id,
                    status: this.value
                }, function (res) {
                    loadData();
                    if (res) {
                        showAlert($("#successMsg"), "项目状态修改成功！")

                    }

                }, "json")
            });
            hasJob('contract:总经理-财务主管') ? '' : $("[id=a]").removeAttr('href');
            hasJob('project:总经理-财务主管-人才主管-副总') ? '' : $("[id=xuanBt]").remove();
            createSplitBar(allRecorders);
            if (windowNW > 992) {
                $("[id^=p-]").each(function () {
                    if (this.style.color == "green") {
                        var id = this.id.split("-")[1];
                        $(this).hover(function () {
                            $("#pp-" + id).next().show();
                        }, function () {
                            $("#pp-" + id).next().hide();

                        })
                    }
                });
            }
            $(".dropdown-menu").click(function (e) {
                e.stopPropagation();
            });

            $("[id^=rcTD-]").hover(function () {
                var id = this.id.split('-')[1];
                var spanText = $(this).find("span").text();
                spanText == '无' ? "" : $("#rcMenu-" + id).show();

            }, function () {
                var id = this.id.split('-')[1];
                var spanText = $(this).find("span").text();
                spanText == '无' ? "" : $("#rcMenu-" + id).hide();
            });
            $("[id^=xuan-]").click(function () {
                var projectid = this.id.split('-')[1];
                window.location = "/pages/back/rencai/list?projectid=" + projectid;
            });
            //导航栏应该与此时的内容高度相同
            sameHeight();
        }
        ,
        error: function (e) {
        }
    });
}

var allLogs;

// /*]]>*/
$(function () {
    //这里需要复写parameterName;
    loadData();
    shoujiOpenOverFlowX();
    $("#selectAll").click(function () {
        var c = this.checked;
        $("[id^=check-]").each(function () {
            this.checked = c;
        })
    });
    $("#deleCheked").click(function () {
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

                $.post("/pages/back/project/plDeleteProject", {str: str}, function (res) {
                    if (res) {
                        var ids = str.split('-');
                        for (var x = 0; x < ids.length; x++) {
                            $("#row-" + ids[x]).remove();
                        }
                        $("#selectAll").get(0).checked = false;
                        showAlert($("#successMsg"), "客户删除成功");
                    } else {
                        showAlert($("#failureMsg"), "客户删除失败");
                    }
                }, "json");
            });

        }


    });
    enterKeySubmit(loadData);
    //检索按钮绑定事件
    jiansuo(loadData);
    $("#selectType").change(function () {
        currentPage = 1;
        type = this.value;
        loadData();
    });
    $("#status").change(function () {
        currentPage = 1;
        status = this.value;
        loadData();
    })

});

