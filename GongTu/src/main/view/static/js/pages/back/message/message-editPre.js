$(function () {
    $("#selectAll").click(function () {
        var s = this.checked;
        $("[id^=check-]").each(function () {
            this.checked = s;
        })
    });
    $("#add").click(function () {
        x = false;
    });
    $("#form").validate({
        debug: true, // 取消表单的提交操作
        submitHandler: function (form) {
            var str = '';
            $("[id^=check-]").each(function () {
                if (this.checked) {
                    str += this.id.split('-')[1] + '-';
                }
            });
            if (x) {//修改并发送

                if (str == '') {
                    showAlert($("#warningMsg"), '您还未选择任何收件人');
                } else {
                    showLoadingData();
                    $.post("/pages/back/message/editAndSend", {
                        str: str, messageid: $("#messageid").val(),
                        note: $("#note").val(),
                        title: $("#title1").val()
                    }, function (res) {
                        hideLoadingData();
                        if (res) {
                            showAlert($("#successMsg"), "消息发送成功！");
                            setTimeout(function () {
                                window.location="/pages/back/message/mlist"
                            },1500)
                        } else {
                            showAlert($("#failureMsg"), "消息发送失败！")
                        }
                    }, "json")
                }

            } else {//只是修改
                x = true;//改变标记
                $.post("/pages/back/message/edit", {
                    note: $("#note").val(),
                    str: str, messageid: $("#messageid").val(),
                    title: $("#title1").val()
                }, function (res) {
                    hideLoadingData();
                    if (res) {
                        showAlert($("#successMsg"), "消息修改成功！");
                        $("[type=checkbox]").each(function () {
                            this.checked = false;
                        });
                    } else {
                        showAlert($("#failureMsg"), "消息修改失败！")
                    }
                }, "json")
            }

            //出现数据加载
        },
        errorPlacement: function (error, element) {
            $("#" + $(element).attr("id") + "Msg").append(error);
        },
        highlight: function (element, errorClass) {
            $(element).fadeOut(1, function () {
                $(element).fadeIn(1, function () {
                    $(element).css({
                        "box-shadow": "0 0 10px rgba(255,0,0,1)"
                    })
                });

            })
        },
        unhighlight: function (element, errorClass) {
            $(element).fadeOut(1, function () {
                $(element).fadeIn(1, function () {
                    $(element).css({
                        "box-shadow": "0 0 10px rgba(0,255,0,1)"
                    });
                });
            })
        },
        errorClass: "text-danger",

        rules: {
            title: {
                required: true
            },
            note: {
                required: true
            }


        }
    });
});
var x = true;