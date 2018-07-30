function addRow(type) {
    var row = $("<div class=\"row\">" +
        "<div class='col-sm-4' >\n" +
        "                        <input class=\"form-control\" type=\"text\" placeholder=\"请输入项目名称\" name='" + type + "-name' " +
        "                               id='" + type + "-name'/>\n" +
        "                    </div>\n" +
        "                    <div class='col-sm-2'>\n" +
        "                        <input class=\"form-control\" type=\"text\" placeholder=\"请输入项目金额\" name='" + type + "-cost' " +
        "                               id='cost-'/>\n" +
        "                    </div>\n" +
        "                    <div class=\"col-sm-2\">\n" +
        "                        <select class=\"form-control\" name='" + type + "-executive'" +
        "                                id='executive'>\n" +
        "                        </select>\n" +
        "                    </div><button class=\"btn btn-danger\" style=\"margin-top: 0.1rem\" name='" + type + "DeleRow'>删除</button>\n" +
        "                </div>");
    return row;
}

//为下拉列表增加选项
function selectExcutive(type) {
    $("[name=" + type + "-executive").each(function () {
        // $(this).empty();
        var allNames = $("#allNames").clone();

        if ($(this).children().length == 0) {
            $(this).append(allNames.children());
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

function getAllCost() {
    $("[id=cost-]").each(function () {
        $(this).change(function () {
            var allCost = 0;
            $("[id=cost-]").each(function () {
                if (this.value != "") {
                    allCost += parseInt(this.value);
                }
            });
            $("#totalCost").text("总金额：" + allCost);
            $("#allCost").val(allCost);
        });
        $(this).blur(function () {
            var allCost = 0;
            $("[id=cost-]").each(function () {
                if (this.value != "") {
                    allCost += parseInt(this.value);
                }
            });
            $("#totalCost").text("总金额：" + allCost);
            $("#allCost").val(allCost);
            console.log(allCost)
        })
    });
}

function addOrRemoveRow(type) {
    deleteRow(type);
    $("#" + type + "AddRow").click(function () {
        $("#" + type + "RowLast").before(addRow(type));
        selectExcutive(type);
        deleteRow(type);
        getAllCost();
    });
    getAllCost();
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
    addOrRemoveRow('zz');
    addOrRemoveRow('gk');
    addOrRemoveRow('dp');
    addOrRemoveRow('qt');
    addOrRemoveRow('px');


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
            // form.submit(); // 提交表单
            showLoadingData();
            $.post("/pages/back/contract/edit", $(form).serializeArray(), function (res) {
                hideLoadingData();
                res ? showAlert($("#successMsg"), "合同修改成功！") : showAlert($("#failureMsg"), "合同修改失败");
            }, "json");
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
            companyName: {
                required: true
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