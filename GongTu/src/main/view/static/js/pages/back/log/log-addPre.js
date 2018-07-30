$(function () {
    $("[name=type]").click(function () {
        var se=$("#projectid");
        if (this.value == '其他') {
            se.attr("disabled", "")
        } else if (this.value == '配合') {
            se.attr("disabled", false);
            se.empty();
            se.append($("#allSe").children().clone());
        } else {
            se.attr("disabled", false);
            se.empty();
            se.append($("#mySe").children().clone());
        }
    });

    $("#formDiv").validate({
        debug: true, // 取消表单的提交操作
        submitHandler: function (form) {
            $.post("/pages/back/log/add", $(form).serializeArray(), function (res) {
                if (res) {
                    $("#note").val("");
                    showAlert($("#successMsg"), "日志添加成功！");
                } else {
                    showAlert($("#failureMsg"), "日志添加失败！");

                }
            }, "json")


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
        messages: {
            note: "内容不能为空"

        },
        rules: {
            note: {
                required: true
            }

        }
    });

})


