$(function () {
    $("#formDiv").validate({
        debug: true, // 取消表单的提交操作
        submitHandler: function (form) {
            //出现数据加载
            showLoadingData();
            $.post("/pages/back/dept/add", {
                dname: $("#dname").val()
            }, function (res) {
                if (res) {
                    showAlert($("#successMsg"), "部门增加成功！");
                    $("#dname").val("")
                } else {
                    showAlert($("#failureMsg"), "部门增加失败！")
                }
                hideLoadingData();

            }, "json");

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
            dname: {
                required: "请输入部门名称",
                remote: "该部门已存在"
            }
        },
        rules: {
            dname: {
                required: true,
                remote: {
                    url: "/pages/back/dept/checkDname", // 后台处理程序
                    type: "post", // 数据发送方式
                    dataType: "json", // 接受数据格式
                    data: { // 要传递的数据
                        dname: function () {//必须用过这样的方式取得表单数据
                            return $("#dname").val();
                        }
                    },
                    dataFilter: function (data) {
                        return data;
                    }
                }
            }

        }
    });

})


