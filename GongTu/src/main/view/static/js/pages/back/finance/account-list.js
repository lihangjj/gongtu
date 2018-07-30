$(function () {
    $("#addAcc").click(function () {
        $(this).text('一次添加一个');
        $(this).get(0).disabled = true;
        var addTr = $(" <tr  >\n" +
            "                        <td style='width: 3rem'></td>\n" +
            "                        <td ><input type='text' class='form-control' id='accountid' placeholder='请输入账户[8-19位数字' name='accountid'/><span id='accountidMsg'></span> </td>\n" +
            "                        <td  ><input class='form-control' type='text' id='name' placeholder='请输入户主姓名' name='name'/><span id='nameMsg'></span></td>\n" +
            "                        <td><div style='display: inline-block;position: relative;'>" +
            "<select id='bankSelect' name='banck' class='form-control' " +
            "      style='width: 22rem;display: inline-block;'>    " +
            "<option>中国工商银行</option>\n" +
            "                                <option>中国农业银行</option>\n" +
            "                                <option>交通银行</option>\n" +
            "                                <option>中国建设银行</option>\n" +
            "                                <option>中国邮政银行</option>\n" +
            "                                <option>招商银行</option>\n" +
            "                                <option>重庆银行</option>\n" +
            "                                <option>民生银行</option>" +
            "   </select>        <input style='width: 20rem;position: absolute;border-radius: 5px 0 0 5px;z-index:2;border-right: none;top: 0'     " +
            "     type='text' id='bankInput'  name='bank' class='form-control'/><span id='bankMsg'></span> " +
            "</div>" +
            "" +
            "" +
            "<button id='sureAdd' class='btn btn-success' type='submit'>确认添加</button><button style='margin-left: 1rem' id='cancel' class='btn btn-info' type='button'>取消</button></td>\n" +
            "                    </tr>");

        $("#dntb").append(addTr);

        $("#bankSelect").change(function () {
            $("#bankInput").val(this.value)
        });
        $("#cancel").click(function () {
            location.reload();
        });
        $("#formDiv").validate({
            debug: true, // 取消表单的提交操作
            submitHandler: function (form) {

                //出现数据加载
                $.post("/pages/back/finance/addAccount", {
                    accountid: $("#accountid").val(),
                    name: $("#name").val(),
                    bank: $("#bankInput").val()
                }, function (res) {
                    if (res) {
                        showAlert($("#successMsg"), "账户添加成功")
                    } else {
                        showAlert($("#failureMsg"), "账户添加失败");
                    }
                    $("input").val("")

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
                name: "请输入姓名", accountid: {remote: "已有此账户！", digits: "请输入整数", minlength: "最小长度为8", maxlength: "最大长度为19"}
            },
            rules: {
                name: {
                    required: true
                }, accountid: {
                    required: true,
                    digits: true, minlength: 8, maxlength: 19,
                    remote: {
                        url: "/pages/back/finance/checkAccount", // 后台处理程序
                        type: "post", // 数据发送方式
                        dataType: "json", // 接受数据格式
                        data: { // 要传递的数据
                            accountid: function () {//必须用过这样的方式取得表单数据
                                return $("#accountid").val();
                            }
                        },
                        dataFilter: function (data) {
                            return data;
                        }
                    }
                }, bank: {
                    required: true
                }


            }
        });
    });
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
            ;

        });
        if (str != '') {

            areYouSure("您确定要删除？", function () {
                $.post("/pages/back/finance/plDeleteAccount", {str: str}, function (res) {
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

    $("[id^=editYue-]").click(function () {
        var accid = this.id.split('-')[1];
        var val = $("#val-" + accid).val();
        var qichuYue = $("#qichu-" + accid).val();
        areYouSure("您确定要修改当前账户余额吗？", function () {
            $.post("/pages/back/finance/editYue", {accountid: accid, yue: val,qichuYue:qichuYue}, function (res) {
                if (res) {
                    showAlert($("#successMsg"), '余额修改成功！', '/pages/back/finance/accountList');
                } else {
                    showAlert($("#failureMsg"), '余额修改失败！');

                }

            }, "json")
        });

    });
    shoujiOpenOverFlowX();

});

