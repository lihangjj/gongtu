
    $(function () {
        $("#formDiv").validate({
            debug: true, // 取消表单的提交操作
            submitHandler: function (form) {
                //出现数据加载
                showLoadingData();
                $.post("/pages/back/job/add", {
                    job: $("#job").val(),
                    deptid:$("#deptid").val()
                }, function (res) {
                    if (res) {
                        showAlert($("#successMsg"), "职位增加成功！")
                        $("#job").val("");
                        $("#deptid").val("")
                    } else {
                        showAlert($("#failureMsg"), "职位增加失败！")
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
            messages:{
                deptid:"请选择部门！"
            },
            rules: {
                name: {required: true},
                deptid: {required: true}
            }
        });

    })


