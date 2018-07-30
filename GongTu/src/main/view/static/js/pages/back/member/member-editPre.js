$(function () {
    hasRole('member:总经理-副总')?'':hideXXDivByAction('memberEdit');
    initSimpleDate();
    autoIdCard();
    //前端密码加密
    $("#reUpload").click(function () {
        $("[type=file]").click();
    });
    if (windowNW > 992) {
        $("form label").css({
            width: $("#sureNewPwd").width()
        });
    }
    $("#dept").click(function () {

        var deptid = this.value;
        $.get("/pages/back/dept/getJobsByDeptId", {deptid: deptid}, function (jobs) {
            var jobOne = $("#jobid");
            jobOne.empty();
            for (var x = 0; jobs.length > x; x++) {
                var job = jobs[x];
                jobOne.append($("<option value=" + job.jobid + ">" + job.job + "</option>"))
            }
        }, "json")

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
            name: "请输入用户名",
            phone: "请输入正确的手机号",
            age: "18到155岁之间",
            dept: "请选择部门",
            jobid: "请选择职位",
            idCard: "请输入正确的身份证号", regDate: "入职日期不能为空"

        },
        rules: {
            name: {
                required: true
            },
            age: {
                required: true,
                digits: true,
                range: [18, 155]
            },
            password: {
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
            },
            regdate: {required: true}
            , idCard: {
                required: true,
                remote: {
                    url: "/pages/back/rencai/checkIdCardAlready", // 后台处理程序
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
            phone: {
                required: true,
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

            }
        }
    });
    //为图片添加展示大图事件
});
function hideXXDivByAction() {

    for (var i in arguments){
        console.log(arguments[i]);

        $("[id="+arguments[i]+"]").css({display:'none'});
    }

}


