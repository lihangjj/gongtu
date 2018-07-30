$(function () {
    if (windowNW > 992) {
        $("form label").css({
            width: $("#surePwd").width()
        });
    }
    autoIdCard();
    initSimpleDate();
    $("#dept").change(function () {
        var deptid = this.value;
        $.get("/pages/back/dept/getJobsByDeptId", {deptid: deptid}, function (jobs) {
            var jobOne = $("#jobOne");
            jobOne.nextAll().remove();
            for (var x = 0; jobs.length > x; x++) {
                var job = jobs[x];
                jobOne.after($("<option value=" + job.jobid + ">" + job.job + "</option>"))
            }
        }, "json")

    });

    $("#reUpload").click(function () {
        $("[type=file]").click();
    });
    $("[type=file]").change(
        function () {
            $("#reUpload").next().empty();
            $("#reUpload").next().append(this.value);
            imgPreview(this);
        }
    );
    $("#formDiv").validate({
        debug: true, // 取消表单的提交操作
        submitHandler: function (form) {
            form.submit(); // 提交表单
            //出现数据加载
            showLoadingData();
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
            name: "请输入姓名",
            phone: "请输入正确的手机号",
            bgPhone: "请输入正确的手机号",
            age: "18到155岁之间",
            dept: "请选择部门",
            jobid: "请选择职位",
            memberid: {
                required: "请输入用户名",
                remote: "该用户已存在"
            }, idCard: "请输入正确的身份证！"
        },
        rules: {
            memberid: {
                required: true,
                remote: {
                    url: "/pages/back/member/checkMemberid", // 后台处理程序
                    type: "post", // 数据发送方式
                    dataType: "json", // 接受数据格式
                    data: { // 要传递的数据
                        phone: function () {//必须用过这样的方式取得表单数据
                            return $("#memberid").val();
                        }
                    },
                    dataFilter: function (data) {
                        return data;
                    }
                }
            }, idCard:{
                required: true,
                remote: {
                    url: "/pages/back/rencai/checkIdCard", // 后台处理程序
                    type: "post", // 数据发送方式
                    dataType: "json", // 接受数据格式
                    data: { // 要传递的数据
                        data: function () {//必须用过这样的方式取得表单数据
                            return $("#idCard").val();
                        }
                    },
                    dataFilter: function (data) {
                        var res = data.split(':')[0];
                        var msg = data.split(':')[1];
                        if (res == 'true') {
                            return true;
                        } else {
                            messages = msg;
                            return false

                        }
                    }
                }
            },

            name: {
                required: true
            },
            age: {
                required: true,
                digits: true,
                range: [18, 155]
            },
            password: {
                required: true,
                equalTo: $("#password2")
            },
            password2: {
                equalTo: $("#password")
            }
            , dept: {
                required: true
            }
            , jobid: {
                required: true
            }
            ,
            phone: {
                digits: true,
                rangelength: [11, 11],
                remote: {
                    url: "/checkCellPhoneNumber", // 后台处理程序
                    type: "post", // 数据发送方式
                    dataType: "json", // 接受数据格式
                    data: { // 要传递的数据
                        phone: function () {//必须用过这样的方式取得表单数据
                            return $("#phone").val();
                        }
                    },
                    dataFilter: function (data) {
                        return data;
                    }
                }

            },
            bgPhone: {
                digits: true,
                rangelength: [11, 11],
                remote: {
                    url: "/checkCellPhoneNumber", // 后台处理程序
                    type: "post", // 数据发送方式
                    dataType: "json", // 接受数据格式
                    data: { // 要传递的数据
                        phone: function () {//必须用过这样的方式取得表单数据
                            return $("#bgPhone").val();
                        }
                    },
                    dataFilter: function (data) {
                        return data;
                    }
                }

            }
        }
    });

});


