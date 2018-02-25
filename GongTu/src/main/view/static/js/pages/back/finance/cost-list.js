$(function () {
    y = $("#yyyy").val();
    lineSize=12;
    loadData();
    $("#selectAll").click(function () {
        var m = this.checked;
        $("[id^=check-]").each(function () {
            this.checked = m;
        });
    });
    $("#deleCheked").click(function () {
        var str = '';
        $("[id^=check-]").each(function () {
            if (this.checked) {
                str += this.id.split("-")[1] + "-"
            }
        });
        if (str != '') {
            areYouSure("您确定要删除？", function () {
                $.post("/pages/back/finance/plDeleteCost", {str: str}, function (res) {
                    var ids = str.split('-');
                    if (res) {
                        for (var x = 0; x < ids.length; x++) {
                            $("#tr-" + ids[x]).remove();
                        }
                        showAlert($("#successMsg"), "删除成功");

                    } else {
                        showAlert($("#failureMsg"), "删除失败")
                    }
                }, "json")
            });


        } else {
            showAlert($("#warningMsg"), "您还未选择任何数据")
        }

    });
    enterKeySubmit(loadData);
    //检索按钮绑定事件
    jiansuo(loadData);
    $("#yyyy").change(function () {
        y = this.value;
        loadData();
    });
    $("#mm").change(function () {
        m = this.value;
        loadData();
    })

});
var y = '';
var m = '';

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

function loadData() {
    $.ajax({
        url: "/pages/back/finance/costListAjax",
        data: {
            "column": column,
            "keyWord": keyWord,
            "lineSize": lineSize,
            "currentPage": currentPage,
            "parameterName": 'date',
            "parameterValue": "'%" + y + "-" + getM(m) + "%'"
        },
        type: "post",
        dataType: "json",
        success: function (data) {
            allRecorders = data.allRecorders;
            var allVo = data.allVo;
            var row1 = $("#row1");
            row1.nextAll().remove();
            for (var x = 0; x < allVo.length; x++) {
                var cost = allVo[x];
                var cid = cost.costid;
                row1.after("<tr id='tr-" + cid + "'>" +
                    "<td style=\"width: 3rem\"><input style=\"min-width: 2.5rem\" class=\"form-control\" type=\"checkbox\"\n" +
                    "                                                       id='check-" + cid + "'/></td>\n" +
                    "                        <td>" + new Date(cost.date).format("yyyy-MM") + "</td>\n" +
                    "                        <td style='color: red;font-weight: bold'>" + cost.heji + "</td>\n" +
                    "                        <td>" + cost.fangzu + "</td>\n" +
                    "                        <td>" + cost.shui + "</td>\n" +
                    "                        <td>" + cost.dian + "</td>\n" +
                    "                        <td>" + cost.qiche + "</td>\n" +
                    "                        <td>" + cost.canyin + "</td>\n" +
                    "                        <td>" + cost.haocai + "</td>\n" +
                    "                        <td>" + cost.shebei + "</td>\n" +
                    "                        <td>" + cost.tuiguang + "</td>\n" +
                    "                    </tr>"
                )

            }


            createSplitBar(allRecorders);
            //导航栏应该与此时的内容高度相同
            sameHeight();
        },
        error: function (e) {
        }
    });
}