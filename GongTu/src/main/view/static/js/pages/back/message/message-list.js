function loadData() {
    $.ajax({
        url: "/pages/back/message/listAjax",
        data: {
            "column": column,
            "keyWord": keyWord,
            "lineSize": lineSize,
            "currentPage": currentPage,
            "parameterName": 'status',
            "parameterValue": status
        },
        type: "post",
        dataType: "json",
        success: function (data) {
            allRecorders = data.allRecorders;
            var allVo = data.allVo;
            var tr1 = $("#row1");
            tr1.nextAll().remove();
            for (var x = 0; x < allVo.length; x++) {
                var m = allVo[x];
                var id = m.messageid;
                var staColor = m.status == '已读' ? 'green' : 'orange';
                tr1.after("<tr id='tr-" + id + "'>\n" +
                    "                         <td style=\"width: 3rem\"><input style=\"min-width: 2.5rem\" class=\"form-control\" type=\"checkbox\"\n" +
                    "                                                       id='check-" + id + "'/></td>\n" +
                    "                        <td style='color: " + staColor + "' id='status-" + id + "'>" + m.status + "</td>\n" +
                    "                   <td>" + m.title + "</td>\n" +
                    "                        <td>" + m.note + "</td>\n" +
                    "                        <td>" + m.sender + "</td>\n" +
                    "                        <td>" + new Date(m.time).format("yyyy-MM-dd hh:mm:ss") + "</td>\n" +
                    "                    </tr>")


            }
            $("[id^=status-]").each(function () {
                if ($(this).text() == '未读') {
                    $(this).html("<span  class='glyphicon glyphicon-eye-open'></span>未读")
                }
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

var status = '未读';
$(function () {
    shoujiOpenOverFlowX();
    loadData();
    $("#selectAll").click(function () {
        var c = this.checked;
        $("[id^=check-]").each(function () {
            this.checked = c;
        })
    });
    $("#plDelete").click(function () {
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

                $.post("/pages/back/message/plDeleteGrMessage", {str: str}, function (res) {
                    if (res) {
                        var ids = str.split('-');
                        for (var x = 0; x < ids.length; x++) {
                            $("#tr-" + ids[x]).remove();
                        }
                        $("#selectAll").get(0).checked = false;
                        showAlert($("#successMsg"), "消息删除成功");
                    } else {
                        showAlert($("#failureMsg"), "消息删除失败");
                    }
                }, "json");
            });

        }


    });
    $("#plyidu").click(function () {
        var str = "";
        $("[id^=check-]").each(function () {
            if (this.checked) {
                str += this.id.split('-')[1] + "-";
            }
        });
        if (str == "") {
            showAlert($("#warningMsg"), "您还未选择任何数据")
        } else {

            $.post("/pages/back/message/plYdMessage", {str: str}, function (res) {
                if (res) {
                    var ids = str.split('-');
                    for (var x = 0; x < ids.length; x++) {
                        var sta=  $("#status-" + ids[x]);
                       sta.text("已读");
                       sta.css({color:'green'})
                    }
                    $("#selectAll").get(0).checked = false;
                } else {
                    showAlert($("#failureMsg"), "消息设置为已读成功失败");
                }
            }, "json");

        }


    });
    enterKeySubmit(loadData);
    //检索按钮绑定事件
    jiansuo(loadData);
    $("#status").change(function () {
        status = this.value;
        loadData();
    })
});



