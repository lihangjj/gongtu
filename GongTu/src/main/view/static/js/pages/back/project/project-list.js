//


function loadData() {
    $.ajax({
        url: "/pages/back/project/listAjax",
        data: {
            "column": column,
            "keyWord": keyWord,
            "lineSize": lineSize,
            "currentPage": currentPage,
            "parameterName": 'type',
            "parameterValue": type + "-status=" + status
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
                var statusColor = c.status == "进行中" ? "red" : c.status == "暂停" ? "orange" : "green";
                var status = c.status;
                var signingDate = c.signingDate;
                var expireDate = c.expireDate;
                var type;
                var logs = p.logs;

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
                    "                        <td style='text-align: left' ><a style='text-decoration: none;cursor: pointer' href='/pages/back/contract/editPre?contractid=" + c.contractid + "'>" + companyName + "</a>-" + p.name + "</td>\n" +
                    "                        <td>" + type + "</td>\n" +
                    "                        <td>" + p.cost + "</td>\n" +
                    "                        <td id='p-" + pid + "' style='color: " + logColor + ";cursor: pointer;'><div style='color: black' class=\"dropdown\">\n" +
                    "                                    <span id='pp-" + pid + "' class=\"dropdown-toggle\" data-toggle='dropdown' style='color: " + logColor + ";cursor: pointer;'>" + log + "</span>\n" +
                    "                                    <div class=\"dropdown-menu\" style='width: 50rem;margin-left: 8rem;margin-top: -10rem'>\n" +
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
                    "                        <td style='color: " + statusColor + "'>" + status + "</td>\n" +
                    "                    </tr>"));
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

                more.get(0).m=logs;
                more.click(function () {
                    var id = this.id.split('-')[1];
                    var allLogs=this.m;
                    $("#tr1-"+id).nextAll().remove();
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
                logs.length>4? $("#logtb-" + pid).append(more):"";

            }
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
var status = '进行中';
var type = '';

