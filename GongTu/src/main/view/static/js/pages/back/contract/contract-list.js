function showType(id, zzdropDown, zz, type) {
    $("#show" + type + "-" + id).append("<div class=\"dropdown \" style=\"cursor: pointer\">" +
        "<span class=\"dropdown-toggle\" data-toggle='" + zzdropDown + "'>" + zz + "</span>" +
        "<div class=\"dropdown-menu \" style='width: 40rem;margin-left: -16rem;'>" +
        "<table id='more" + type + "-" + id + "'  class=\"table\" style=\"text-align: center\"></table>" +
        "</div>" +
        "</div>");
}

function showMore(id, type, typeMap) {
    if ($("[id^=show" + type + "-]").find(".dropdown-toggle").text() != "无") {
        var morezz = $("#more" + type + "-" + id);
        morezz.append(" <tr style='color: black'>\n" +
            "                        <td><label>项目名称</label></td>\n" +
            "                        <td><label>金额</label></td>\n" +
            "                        <td><label>执行人</label></td>\n" +
            "                        <td><label>添加时间</label></td>\n" +
            "                    </tr>");
        var list = typeMap[id + "-" + type + ""];
        for (var y = 0; y < list.length; y++) {
            var project = list[y];
            morezz.append(" <tr style='color:black;border: dashed #b0ccff 0.1px'>\n" +
                "                        <td>" + project.name + "</td>\n" +
                "                        <td>" + project.cost + "</td>\n" +
                "                        <td>" + project.executive + "</td>\n" +
                "                        <td>" + new Date(project.addDate).format('yyyy-MM-dd hh:mm:ss') + "</td>\n" +
                "                    </tr>");
        }
    }
    return {morezz: morezz, list: list, y: y, project: project};
}

//
function loadData() {
    $.ajax({
        url: "/pages/back/contract/listAjax",
        data: {
            "column": column,
            "keyWord": keyWord,
            "lineSize": lineSize,
            "currentPage": currentPage,
            "parameterName": 'status',
            "parameterValue": parameterValue
        },
        type: "post",
        dataType: "json",
        success: function (data) {
            allRecorders = data.allRecorders;
            var allVo = data.allVo;
            var typeMap = data.typeProjectMap;
            console.log(typeMap);
            for (var key in typeMap) {
                console.log("属性：" + key + ",值：" + typeMap[key]);
            }
            var dntb = $("#dntb");
            $("#row1").nextAll().remove();
            for (var x = 0; x < allVo.length; x++) {
                var contract = allVo[x];
                var id = contract.contractid;
                var zz = typeMap[id + "-zz"] == "" ? "无" : "查看";
                var zzdropDown = typeMap[id + "-zz"] == "" ? "" : "dropdown";

                var zzColor = typeMap[id + "-zz"] == "" ? "red" : "green";
                var px = typeMap[id + "-px"] == "" ? "无" : "查看";
                var pxColor = typeMap[id + "-px"] == "" ? "red" : "green";
                var pxdropDown = typeMap[id + "-px"] == "" ? "" : "dropdown";

                var qt = typeMap[id + "-qt"] == "" ? "无" : "查看";
                var qtColor = typeMap[id + "-qt"] == "" ? "red" : "green";
                var qtdropDown = typeMap[id + "-qt"] == "" ? "" : "dropdown";

                var dp = typeMap[id + "-dp"] == "" ? "无" : "查看";
                var dpColor = typeMap[id + "-dp"] == "" ? "red" : "green";
                var dpdropDown = typeMap[id + "-dp"] == "" ? "" : "dropdown";

                var gk = typeMap[id + "-gk"] == "" ? "无" : "查看";
                var gkColor = typeMap[id + "-gk"] == "" ? "red" : "green";
                var gkdropDown = typeMap[id + "-gk"] == "" ? "" : "dropdown";

                var statusColor = contract.status == "进行中" ? "red" : contract.status == "暂停" ? "orange" : "green";

                var signingDate=contract.signingDate==null?"":contract.signingDate;
                var expireDate=contract.expireDate==null?"":contract.expireDate;
                dntb.append($("<tr id='row-" + id + "'>\n" +
                    " <td><input class='form-control' style='min-width: 2.5rem' type='checkbox'  id='check-" + id + "' value='" + id + "' /></td>\n" +
                    " <td><a href='/pages/back/contract/editPre?contractid=" + id + "' style='text-decoration: none;cursor: pointer'>" + contract.companyName + "</a></td>\n" +
                    " <td>" + contract.contact + "-" + contract.phone + "-" + contract.qq + "</td>\n" +
                    " <td id='showzz-" + id + "' style='color: " + zzColor + "'></td>\n" +
                    " <td id='showgk-" + id + "' style='color: " + gkColor + "'></td>\n" +
                    " <td id='showdp-" + id + "' style='color: " + dpColor + "'></td>\n" +
                    " <td id='showpx-" + id + "' style='color: " + pxColor + "'></td>\n" +
                    " <td  id='showqt-" + id + "' style='color: " + qtColor + "'></td>\n" +
                    " <td >" + contract.note + "</td>\n" +
                    " <td  style='color: " + statusColor + "' >" + contract.status + "</td>\n" +
                    " <td>" + signingDate + "</td>\n" +
                    " <td>" + expireDate + "</td>\n" +
                    "  </tr>"));
                showType(id, zzdropDown, zz, 'zz');
                showType(id, pxdropDown, px, 'px');
                showType(id, gkdropDown, gk, 'gk');
                showType(id, dpdropDown, dp, 'dp');
                showType(id, qtdropDown, qt, 'qt');
                showMore(id, 'zz', typeMap);
                showMore(id, 'px', typeMap);
                showMore(id, 'gk', typeMap);
                showMore(id, 'qt', typeMap);
                showMore(id, 'dp', typeMap);

            }

            if (windowNW > 992) {
                $("[id^=show]").each(function () {
                    if ($(this).find(".dropdown-toggle").text() != "无") {
                        $(this).hover(function () {
                            $(this).find(".dropdown-toggle").dropdown("toggle")
                        }, function () {
                            $(this).find(".dropdown-toggle").dropdown("toggle")
                        })
                    }

                });
            }
            $(".dropdown-menu").click(function (e) {
                e.stopPropagation();
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

                $.post("/pages/back/contract/plDeleteContract", {str: str}, function (res) {
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
    $("#status").change(function () {
        parameterValue = this.value;
        loadData();
    })

});


