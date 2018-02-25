function loadData() {
    $.ajax({
        url: "/pages/back/contract/listAjax",
        data: {
            "column": column,
            "keyWord": keyWord,
            "lineSize": lineSize,
            "currentPage": currentPage,
            "parameterName": 'status',
            "parameterValue": parameterValue
        },
        type: "post",
        dataType: "json",
        success: function (data) {
            allRecorders = data.allRecorders;
            var allVo = data.allVo;
            var dntb = $("#dntb");
            var paylogMap = data.paylogMap;
            console.log(paylogMap);
            for (var key in paylogMap) {
                console.log("属性：" + key + ",值：" + paylogMap[key]);
            }
            $("#row1").nextAll().remove();
            for (var x = 0; x < allVo.length; x++) {
                var contract = allVo[x];
                var id = contract.contractid;
                var statusColor = contract.status == "进行中" ? "red" : contract.status == "暂停" ? "orange" : "green";
                dntb.append($("<tr id='row-" + id + "'>\n" +
                    " <td><input class='form-control ' style='min-width: 2.5rem' type='checkbox'  id='check-" + id + "' value='" + id + "' /></td>\n" +
                    " <td><a href='/pages/back/contract/editPre?contractid=" + id + "' style='text-decoration: none;cursor: pointer'>" + contract.companyName + "</a></td>\n" +
                    " <td>" + contract.principal + "-" + contract.principalPhone + "-" + contract.principalQQ + "</td>\n" +
                    " <td >" + contract.allCost + "</td>\n" +
                    " <td id='drop-"+id+"'  ><div class=\"dropdown\" >\n" +
                    "            <span id='alreadyPay-" + id + "' style='cursor: pointer' data-toggle=\"dropdown\">" + contract.alreadyPay + "</span>\n" +
                    "            <div class=\"dropdown-menu\" style='width: 50rem;margin-left: -50rem;'>\n" +
                    "                <table class='table' id='paylog-" + id + "' style='text-align: center'></table>\n" +
                    "            </div>\n" +
                    "        </div></td>\n" +
                    " <td id='noPay-" + id + "'>" + (contract.allCost - contract.alreadyPay) + "</td>\n" +
                    " <td  style='color: " + statusColor + ";font-weight: bold' >" + contract.status + "</td>\n" +
                    " <td><button class='btn btn-warning ' style='margin: 0.5rem' id='pay-" + id + "-" + contract.allCost + "' >收款</button  ><button class='btn btn-info' id='mingxi-" + id + "' name='" + contract.companyName + "' style='margin: 0.5rem'>明细</button></td>\n" +
                    "  </tr>"));
                var tb = $("#paylog-" + id);
                var paylogs = paylogMap[id];
                tb.append("<tr>" +
                    "<td colspan='4' style='color: green'>付款记录</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td >金额</td>" +
                    "<td>方式</td>" +
                    "<td >付款账户</td>" +
                    "<td >时间</td>" +
                    "</tr>");
                for (var p = 0; p < paylogs.length; p++) {
                    var paylog = paylogs[p];
                    var payTime = paylog.time == null ? "" : new Date(paylog.time).format("yyyy-M-dd hh:mm:ss");
                    tb.append($("<tr style='border: dashed  #b0ccff 0.1px' >" +
                        "<td  >" + paylog.cost + "</td>" +
                        "<td>" + paylog.payway + "</td>" +
                        "<td>" + paylog.payaccount + "</td>" +
                        "<td>" + payTime + "</td>" +
                        "</tr>"))

                }

            }
            if (windowNW>992){//不这样手机上不现实


                $("[id^=drop]").hover(function () {
                    var id=this.id.split('-')[1];
                    var alreadyPay=$("#alreadyPay-"+id);
                    if (alreadyPay.text()!="0"){
                        alreadyPay.next().show();
                    }
                },function () {
                    var id=this.id.split('-')[1];
                    var alreadyPay=$("#alreadyPay-"+id);
                    if (alreadyPay.text()!="0"){
                        alreadyPay.next().hide();
                    }
                });
            }
            $(".dropdown-menu").click(function (e) {
                e.stopPropagation();
            });

            $("[id^=pay-]").each(function () {
                $(this).click(function () {
                    var id = this.id.split('-')[1];
                    allCost = this.id.split('-')[2];
                    var alreadyPay = $("#alreadyPay-" + id).text();
                    var noPa = allCost - alreadyPay;
                    $("#allCost").text("总金额：" + allCost);
                    $("#alreadyPay").text("已付款：" + alreadyPay);
                    $("#noPay").text("未付款：" + noPa);
                    $("#contractid").val(id);
                    noPay = noPa;
                    alReadyPay = alreadyPay;
                    $("#payWindow").modal("show");
                })
            });
            $("[id^=mingxi-]").each(function () {
                $(this).click(function () {
                    var id = this.id.split('-')[1];
                    var name = this.name;
                    $("#mingxititle").html("<span style='color: red'>" + name + "</span>" + "收款明细");
                    $.get("/pages/back/finance/getPaylogsByContractId", {contractid: id}, function (allPaylog) {
                        var oneRow = $("#mingxiRow1");
                        oneRow.nextAll().remove();
                        var oneTb = $("#mingxiShouji");
                        oneTb.nextAll().remove();
                        for (var x = 0; x < allPaylog.length; x++) {
                            var paylog = allPaylog[x];
                            var invoiceCost = paylog.invoiceCost == null ? "" : paylog.invoiceCost;
                            var wkjCost = paylog.wkjCost == null ? "" : paylog.wkjCost;
                            var time = paylog.time == null ? "" : new Date(paylog.time).format("yyyy-MM-dd hh:mm:ss");
                            var ykptime = paylog.ykptime == null ? "" : new Date(paylog.ykptime).format("yyyy-MM-dd hh:mm:ss");
                            var payaccount = paylog.payaccount;
                            oneRow.after("<tr><td>" + paylog.payway + "</td>" +
                                "<td>" + paylog.cost + "</td>\n" +
                                "<td>" + paylog.paybank + "-" + payaccount + "</td>\n" +
                                "<td>" + paylog.shoubank + "-" + paylog.shouaccount + "</td>\n" +
                                "<td>" + invoiceCost + "</td>\n" +
                                " <td>" + time + "</td>\n" +
                                " <td>" + ykptime + "</td>\n" +
                                " <td>" + wkjCost + "</td>\n" +
                                "<td>" + paylog.note + "</td>\n" +
                                " </tr>");
                            oneTb.after("<table  class=\"table table-hover  table-bordered visible-xs\" style='margin-bottom: 5rem'>\n" +
                                "                            <tr >\n" +
                                "                                <td>付款方式:" + paylog.payway + "</td>\n" +
                                "                                <td>付款金额:" + paylog.cost + "</td>\n" +
                                "                            </tr>\n" +
                                "                            <tr>\n" +
                                "                                <td colspan=\"2\"><span style='color: red'>付</span>款银行-账户:" + paylog.paybank + "-" + payaccount + "</td>\n" +
                                "                            </tr>\n" +
                                "                            <tr>\n" +
                                "                                <td colspan=\"2\"><span style='color:green '>收</span>款银行-账户:" + paylog.shoubank + "-" + paylog.shouaccount + "</td>\n" +
                                "                            </tr>\n" +
                                "                            <tr>\n" +
                                "                                <td>发票总金额:" + invoiceCost + "</td>\n" +
                                "                                <td>开具时间:" + time + "</td>\n" +
                                "                            </tr>\n" +
                                "                            <tr>\n" +
                                "                                <td>未开具金额:" + wkjCost + "</td>\n" +
                                "                                <td>已开票时间:" + ykptime + "</td>\n" +
                                "                            </tr>\n" +
                                "                            <tr>\n" +
                                "                                <td colspan=\"2\">发票备注:" + paylog.note + "</td>\n" +
                                "                            </tr>\n" +
                                "                        </table>")


                        }

                    }, "json");
                    $("#mingxiWindow").modal("show");

                })


            });

            createSplitBar(allRecorders);
            //导航栏应该与此时的内容高度相同
            sameHeight();
        },
        error: function (e) {
        }
    });
}

var noPay;
var allCost;
var alReadyPay;

//适配我的输入框组件
function shipeiSinput() {
    var myselect = $("[id=myInput] select");
    var myinput = $("[id=myInput] input");
    if (windowNW < 768) {
        var father = $("[id=myInput]");
        father.css({height: "50px"});
        myselect.css({width: windowNW * 0.6, "margin-left": "0px"});
        myinput.css({width: (windowNW * 0.6 - 20), "margin-left": "0px"});

    } else {
        myselect.css({width: '180px', "margin-left": "4px"});
        myinput.css({width: '160px', "margin-left": "4px"});
    }
    myselect.each(function () {
        $(this).click(function () {
            $(this).next("input").val(this.value);
            var bank=$(this).children("[value="+this.value+"]").attr("name");
            if (bank!=undefined){
                $("#shoubank").val(bank);
            }
        });
        $(this).change(function () {
            $(this).next("input").val(this.value);
            var bank=$(this).children("[value="+this.value+"]").attr("name");

            if (bank!=undefined){
                $("#shoubank").val(bank);
            }
        })
    })

}

//保存账户
function saveAcc(execute) {
    var sureModal = $("<div id=\"sure\" class=\"modal fade\" aria-labelledby=\"title\" aria-hidden=\"true\" tabindex=\"-1\">\n" +
        "            <div class=\"modal-dialog\" style=\"top: 30%;\">\n" +
        "                <div class=\"modal-content\" style=\"z-index: 9999\">\n" +
        "                    <div class=\"modal-header\">\n" +
        "                        <button class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>\n" +
        "                        <span id=\"title\">保存账号：</span>\n" +
        "                    </div>\n" +
        "                    <div class=\"modal-body\" style=\"text-align: center\">\n" +
        "                        <input type='text' id='accountName'/>请输入账号户主姓名\n" +
        "                    </div>\n" +
        "                    <div class=\"modal-footer\">\n" +
        " <small style=\"text-align: center;font-size: 1rem;color: gray\">Esc键、或单机灰色区域关闭窗口</small>" +
        "                        <button class=\"btn btn-danger\" id=\"yes\">确定</button>\n" +
        "                        <button class=\"btn btn-primary\" data-dismiss=\"modal\">取消</button>\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "            </div>\n" +
        "        </div>");
    $("#payWindow").after(sureModal);
    var sureM = $("#sure");
    var yes = $("#yes");
    sureM.modal("show");
    yes.click(function () {
        sureM.modal("hide");
        execute();
    });
    yes.next().click(function () {
        sureM.modal("hide");
        return false;
    })

}


$(function () {

    shipeiSinput();
    $("#saveaccount").click(function () {
        var acc = $("#saveaccount").prev().val();
        var bank = $("#shoubank").val();
        if (acc == "" || bank == "") {
            showAlert($("#warningMsg"), "您还未输入银行或账户");
        } else {
            var regex = /^[0-9]{8,19}$/;
            if (!regex.test(acc)) {
                showAlert($("#warningMsg"), "您的账户输入有误！");
            } else {
                saveAcc(function () {
                    var name = $("#accountName").val();
                    $.post("/pages/back/finance/addAccount", {accountid: acc, name: name, bank: bank}, function (res) {
                        res ? showAlert($("#successMsg"), '保存成功') : showAlert($("#failureMsg"), '保存失败,已存此账户在或其他原因！');
                    }, "json");

                })
            }

        }

    });
    initSuperDetailDate();
    //这里需要复写parameterName;
    loadData();
    enterKeySubmit(loadData);
    //检索按钮绑定事件
    jiansuo(loadData);
    $("#status").change(function () {
        parameterValue = this.value;
        loadData();
    });
    $("#selectAll").click(function () {
        var c = this.checked;
        $("[id^=check-]").each(function () {
            this.checked = c;
        })
    });
    $("#deleCheked").click(function () {
        var str = "";
        $("[id^=check-]").each(function () {
            if (this.checked) {
                str += this.id.split('-')[1] + "-";
            }
        });
        if (str==""){
            showAlert($("#warningMsg"),"您还未选择任何数据")
        }else {
            areYouSure("您确定要删除选中数据？",function () {

                $.post("/pages/back/contract/plDeleteContract", {str: str}, function (res) {
                    if (res) {
                        var ids = str.split('-');
                        for (var x = 0; x < ids.length; x++) {
                            $("#row-" + ids[x]).remove();
                        }
                        $("#selectAll").get(0).checked=false;
                        showAlert($("#successMsg"), "合同删除成功");
                    } else {
                        showAlert($("#failureMsg"), "合同删除失败");
                    }
                }, "json");
            });

        }
    });
    $("#payForm").validate({
        debug: true, // 取消表单的提交操作
        submitHandler: function (form) {

            //出现数据加载
            $.post("/pages/back/finance/addApylog", $(form).serializeArray(), function (res) {
                if (res) {
                    $("#payWindow").modal("hide");
                    var contractid = $("#contractid").val();
                    var cost = $("#cost").val();
                    var nowAlreadyPay = parseInt(alReadyPay) + parseInt(cost);
                    $("#alreadyPay-" + contractid).text(nowAlreadyPay);
                    $("#noPay-" + contractid).text(parseInt(allCost) - nowAlreadyPay);
                    showAlert($("#successMsg"), "收款成功");
                } else {
                    showAlert($("#failureMsg"), "收款失败");
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
                        "box-shadow": "0 0 10px rgba(0,255,0,0.5)"
                    });
                });
            })
        },
        errorClass: "text-danger",
        messages: {
            cost: {
                required: "请输入付款金额",
                digits: "请输入正整数",
                max: function () {
                    return "最大值不能超过" + noPay;
                }
            }
        },
        rules: {
            cost: {
                required: true,
                digits: true,
                max: function () {
                    return parseInt(noPay);
                },
                min: 1
            },
            shouaccount: {
                digits: true,
                maxlength: 19,
                minlength: 8
            },
            payaccount: {
                digits: true,
                maxlength: 19,
                minlength: 8
            }

        }
    });
});


