function loadData() {
    $.ajax({
        url: "/pages/back/message/mListAjax",
        data: {
            "column": column,
            "keyWord": keyWord,
            "lineSize": lineSize,
            "currentPage": currentPage,
            "parameterName": 'sender',
            "parameterValue": name
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
                var status = m.flag == 1 ? '已发送' : '未发送';
                var staColor = m.flag == 1 ? 'green' : 'red';
                tr1.after("<tr id='tr-" + id + "'>\n" +
                    "                         <td style=\"width: 3rem\"><input style=\"min-width: 2.5rem\" class=\"form-control\" type=\"checkbox\"\n" +
                    "                                                       id='check-" + id + "'/></td>\n" +
                    "                        <td style='color: " + staColor + "'>" + status + "</td>\n" +
                    "                   <td id='tit-" + m.flag + "'> <a href='/pages/back/message/editPre?messageid=" + id + "' style='text-decoration: none' >" + m.title + "</a></td>\n" +
                    "                        <td>" + m.note + "</td>\n" +
                    "                        <td>" + new Date(m.time).format("yyyy-MM-dd hh:mm:ss") + "</td>\n" +
                    "                    </tr>")


            }
            $("[id^=tit-]").each(function () {
                    var  s=$(this).children().text();
                if (this.id.split('-')[1] == 1) {
                    $(this).html(s)
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


$(function () {
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

                $.post("/pages/back/message/plDeleteMessage", {str: str}, function (res) {
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
    enterKeySubmit(loadData);
    //检索按钮绑定事件
    jiansuo(loadData);

});



