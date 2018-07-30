var allName;

function getRow(type) {

    var row = $("<div class=\"row\"><div class='col-sm-4' >\n" +
        "                        <input class=\"form-control\" type=\"text\" placeholder=\"请输入项目名称\" name='" + type + "-name' " +
        "                               id='" + type + "-name'/>\n" +
        "                    </div>\n" +
        "                    <div class='col-sm-2'>\n" +
        "                        <input class=\"form-control\" type=\"text\" placeholder=\"请输入项目金额\" name='" + type + "-cost' " +
        "                               id=\"cost-\"/>\n" +
        "                    </div>\n" +
        "                    <div class=\"col-sm-2\">\n" +
        "                        <select class=\"form-control\" name='" + type + "-executive'" +
        "                                id='executive-" + type + "'>\n" +
        "                        </select>\n" +
        "                    </div><button class=\"btn btn-danger\" style=\"margin-top: 0.1rem\" name='" + type + "DeleRow'>删除</button>\n" +
        "                </div>");


    return row;
}

//为下拉列表增加选项
function selectExcutive() {
    $("select").each(function () {
        $(this).empty();
        $(this).append($("<option value=''>请选择执行人</option>"));
        for (var x = 0; x < allName.length; x++) {
            $(this).append($("<option>" + allName[x] + "</option>"))
        }
    });
}

//删除项目行
function deleteRow(type) {
    $("[name=" + type + "DeleRow]").each(function () {
        $(this).click(function () {
            $(this).parent().remove();
        })
    });
}

function addOrRemoveRow(type) {
    for (var x = 0; x < 2; x++) {
        $("#" + type + "RowLast").before(getRow(type));

    }
    deleteRow(type);
    $("#" + type + "AddRow").click(function () {
        $("#" + type + "RowLast").before(getRow(type));
        $("[id^=executive-" + type + "]").each(function () {
            if ($(this).children().length == 0) {
                $(this).append($("<option value=''>请选择执行人</option>"));
                for (var x = 0; x < allName.length; x++) {
                    $(this).append($("<option>" + allName[x] + "</option>"))
                }
            }
        });
        deleteRow(type);
        $("[id=cost-]").each(function () {
                $(this).change(function () {
                    getAllCost();
                });

            }
        );

    });

    selectExcutive();
    $("[id=cost-]").each(function () {
            $(this).change(function () {
                getAllCost();
            });
        }
    );
}

function getAllCost() {
    var allCost = 0;
    $("[id=cost-]").each(function () {
        if (this.value != "") {
            allCost += parseInt(this.value);
        }
    });
    $("#totalCost").text("总金额：" + allCost);
    $("#allCost").val(allCost);
}

//设置到期时间
function setExpiredDate() {
    var signingDate = $("#signingDate").val();
    var x = $("#period").val();

    if (signingDate != "" && x != "") {
        var d = $("#datetimepicker1").data("datetimepicker").getDate();
        d.setMonth(d.getMonth() + parseInt(x));
        $("#expireDate").val(d.format("yyyy-MM-dd"))
    } else {
        $("#expireDate").val("");
    }
}


$(function () {
    if (windowNW > 768) {
        $("form label").css({
            width: $("#phone").width()
        });
    }
    $.get("/pages/back/member/getAllNames", {}, function (names) {
        allName = names;
        addOrRemoveRow('zz');
        addOrRemoveRow('px');
        addOrRemoveRow('gk');
        addOrRemoveRow('dp');
        addOrRemoveRow('qt');
    }, "json");


    $("#period").change(function () {
        setExpiredDate();
    });
    $("#signingDate").change(function () {
        setExpiredDate();
    });
    initSimpleDate();

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
            companyName: {
                required: '请填写公司名称',
                remote: "名称已存在，请换一个"
            }
        },

        rules: {
            companyName: {
                required: true,
                remote: {
                    url: "/pages/back/contract/checkCompanyName", // 后台处理程序
                    type: "post", // 数据发送方式
                    dataType: "json", // 接受数据格式
                    data: { // 要传递的数据
                        companyName: function () {//必须用过这样的方式取得表单数据
                            return $("#companyName").val();
                        }
                    },
                    dataFilter: function (data) {
                        return data;
                    }
                }
            },
            period: {
                digits: true
            },
            "zz-cost": {
                digits: true
            }, "dp-cost": {
                digits: true
            }, "qt-cost": {
                digits: true
            }, "px-cost": {
                digits: true
            }, "gk-cost": {
                digits: true
            }

        }
    });

});
