//
function getSydw(sydw) {

    if (sydw == '') {
        return ''
    } else {
        return " AND rencaiid in (select rencaiid from sydw where sydanwei='" + sydw + "')"
    }
}
function getType(type) {

    if (type == '') {
        return ''
    } else {
        return " AND rcType='" + type + "'"
    }
}

function getZsdengji(zsdengji) {
    if (zsdengji == '') {
        return ''
    } else {
        return " AND zsdengji='" + zsdengji + "'"
    }
}


function loadData() {
    $.ajax({
        url: "/pages/back/rencai/listAjax",
        data: {
            "column": column,
            "keyWord": keyWord,
            "lineSize": lineSize,
            "currentPage": currentPage,
            "parameterName": 'condition',
            "parameterValue": "dflag=0 " + getType(type) + getZsdengji(zsdengji) + getSydw(sydw)
        },
        type: "post",
        dataType: "json",
        success: function (data) {
            var allVo = data.allVo;
            allRecorders = data.allRecorders;
            var dntb = $("#dntb");

            $("#row1").nextAll().remove();
            for (var x = 0; x < allVo.length; x++) {
                var rc = allVo[x];
                var id = rc.rencaiid;
                var qyPrice = rc.qyPrice == null ? '' : rc.qyPrice;
                var alreadyPay = rc.alreadyPay == null ? '' : rc.alreadyPay;
                var qyTime = rc.qyTime == null ? '' : rc.qyTime;
                var dqDate = new Date(rc.dqTime);
                var nowDate = new Date();
                nowDate.setMonth(nowDate.getMonth() + 2);
                var dqTimeColor = (nowDate - dqDate) > 0 ? 'red;font-weight:bold' : "";

                var dqTime = rc.dqTime == null ? '' : rc.dqTime;
                var m = qyPrice == '' ? 0 : parseInt(qyPrice);
                var n = alreadyPay == '' ? 0 : parseInt(alreadyPay);
                var noPay = (m - n) == 0 ? '' : (m - n);
                var sydw = rc.sydw.length;
                var sydwColor = sydw == 0 ? '' : 'green';
                var disabled = rc.hetong == "" ? 'disabled' : rc.hetong;
                var status = rc.hetong == "" ? '无' : '查看';
                var zsdengji = rc.zsdengji == null ? "" : rc.zsdengji;


                dntb.append("<tr id='tr' >\n" +
                    "                        <td><input type='checkbox' class='form-control' style='width:25px' id='check-" + id + "'/></td>\n" +
                    "                        <td><img style='width: 5rem;border-radius: 5px' src=" + rc.photo + " name='" + rc.bigPhoto + "'></td>\n" +
                    "                        <td><a href='/pages/back/rencai/editPre?rencaiid=" + id + "'>" + rc.name + "</a></td>\n" +
                    "                        <td>" + rc.rcType + "</td>\n" +
                    "                        <td>" + rc.zszhuanye + "-" + zsdengji + "</td>\n" +
                    "                        <td>" + rc.xlzhuanye + "-" + rc.xldengji + "</td>\n" +
                    "                        <td><span style='color:" + dqTimeColor + "'>" + dqTime + "</span></td>\n" +
                    "                        <td id='qyP-" + id + "' name='qyP-" + rc.qyren + "'>" + qyPrice + "</td>\n" +
                    "                        <td  style='cursor: pointer' id='alreadyPayTd-" + id + "'>" +
                    "<div class='dropdown'><span  id='alreadyPay-" + id + "' data-toggle='dropdown' style='color: red'>" + alreadyPay + "</span><div class='dropdown-menu' id='payMenu-" + id + "'  style='margin-left: -50rem;'>" +
                    "<table class='table' style='text-align: center;width: 60rem' id='payTable-" + id + "'></table></div></div></td>\n" +
                    "                        <td id='noPay-" + id + "'>" + noPay + "</td>\n" +
                    "                        <td id='drop-" + id + "'><table class='table' style='text-align: center;margin-bottom: 0;' id='sydw-" + id + "'></table></td>\n" +
                    "                        <td ><button class='btn btn-info' id='ht|" + rc.hetong + "' " + disabled + " name='" + rc.name + "'>" + status + "</button></td>\n" +
                    "                        <td><button style='margin: 0.5rem' class='btn btn-danger' id='pay-" + id + "-" + qyPrice + "-" + alreadyPay + "'>付款</button>" +
                    "                           <button style='margin: 0.5rem' class='btn btn-success' id='mingxi-" + id + "'>明细</button></td>\n" +
                    "                    </tr>");
                $("#mingxi-" + id).get(0).payLogs = rc.rcpaylogList;
                var sydwTb = $("#sydw-" + id);

                var allSydw = rc.sydw;
                for (var y = 0; y < allSydw.length; y++) {
                    var sydw = allSydw[y];
                    var xuhao = y + 1;
                    var startTime = sydw.startTime == null ? '' : new Date(sydw.startTime).format("yyyy-MM-dd");
                    var overTime = sydw.overTime == null ? '' : new Date(sydw.overTime).format("yyyy-MM-dd");
                    sydwTb.append("<tr style='border: dashed 0.1px #ff7f19'>\" +\n" +
                        "                    <td width='2%'>" + xuhao + "</td> +\n" +
                        "                    <td width='55%' style='text-align: left'>" + sydw.sydanwei + "-" + sydw.xmName + "</td> +\n" +
                        "                   <td width='13%'>" + sydw.zzType + "</td> +\n" +
                        "                    <td width='15%'>" + startTime + "</td> +\n" +
                        "                   <td width='15%'>" + overTime + "</td> +\n" +
                        "                   </tr>")
                }


                var payTb = $("#payTable-" + id);
                payTb.append("<tr >" +
                    "<td colspan='4' style='color: green'>付款记录</td>" +
                    "</tr>" +
                    "<tr style='font-weight: bold'>" +
                    "<td >金额</td>" +
                    "<td>方式</td>" +
                    "<td >付款账户</td>" +
                    "<td >时间</td>" +
                    "</tr>");

                var allRcpaylog = rc.rcpaylogList;
                if (allRcpaylog != null) {
                    for (var z = 0; z < allRcpaylog.length; z++) {
                        var rcpaylog = allRcpaylog[z];
                        payTb.append("<tr >" +
                            "<td >" + rcpaylog.payCost + "</td>" +
                            "<td>" + rcpaylog.payWay + "</td>" +
                            "<td >" + rcpaylog.payAccount + "</td>" +
                            "<td >" + new Date(rcpaylog.time).format("yyyy-MM-dd hh:mm:ss") + "</td>" +
                            "</tr>")
                    }
                }


            }
            var has = hasRole('rencai:总经理-副总-财务主管-人才主管');
            has ? '' : $("[id^=alreadyPayTd-]").remove();
            has ? '' : $("[id^=qyP-]").css({opacity: 0});
            has ? '' : $("[id^=noPay-]").remove();

            $($("[id^=qyP-]")).each(function () {
                var qyr = $(this).attr('name').split('-')[1];
                if (qyr == memberid) {
                    this.style.opacity = 1;
                }

            });
            $("[id^=sydw-] tr td").css({padding: 4});
            console.log($("[id^=sydw-] tr td").length + "哈哈");
            $("[id^=drop-]").each(function () {
                var id = this.id.split('-')[1];
                var spanText = $(this).find("span").text();
                if (windowNW > 768) {
                    $(this).hover(function () {
                        if (spanText != '0家') {
                            $("#dropdown-" + id).show()
                        }
                    }, function () {
                        $("#dropdown-" + id).hide()
                    });
                }

            });
            $("[id^=ht\\|]").each(function () {
                has ? '' : $(this).parent().remove();
                var name = this.name;
                var imgs = this.id.split('\|')[1];
                $(this).click(function () {
                    window.open("/pages/back/rencai/showHetong?name=" + name + "&imgs=" + imgs);
                })


            });
            $("[id^=pay-]").each(function () {
                $(this).click(function () {
                    var id = this.id.split('-')[1];
                    var allCost = this.id.split('-')[2] == "" ? 0 : this.id.split('-')[2];
                    var alreadyPay = $("#alreadyPay-" + id).text() == "" ? 0 : $("#alreadyPay-" + id).text();
                    alRpay = alreadyPay;
                    allC = allCost;
                    $("#rencaiid").val(id);
                    $("#allCost").text(allCost);
                    $("#alreadyPay").text(alreadyPay);
                    NoPay = allCost - alreadyPay;
                    $("#noPay").text(NoPay);
                    $("#payWindow").modal('show');
                })
            });
            $("[id^=mingxi-]").each(function () {
                hasRole('rencai:总经理-副总-财务主管-人才主管') ? '' : $(this).parent().remove();
                $(this).click(function () {
                    var alllog = this.payLogs;
                    var mxTb = $("#mingxiTb");
                    var tr1 = $("#mingxiRow1");
                    tr1.nextAll().remove();
                    for (var x = 0; x < alllog.length; x++) {
                        var rcPaylog = alllog[x];
                        mxTb.append("<tr>\n" +
                            "                        <td>" + rcPaylog.payWay + "</td>\n" +
                            "                        <td>" + rcPaylog.payCost + "</td>\n" +
                            "                        <td>" + rcPaylog.payBank + "-" + rcPaylog.payAccount + "</td>\n" +
                            "                        <td>" + rcPaylog.shouBank + "-" + rcPaylog.shouAccount + "</td>\n" +
                            "                        <td>" + new Date(rcPaylog.time).format("yyyy-MM-dd hh:mm:ss") + "</td>\n" +
                            "                        <td>" + rcPaylog.note + "</td>\n" +
                            "                    </tr>")
                    }

                    $("#mingxiWindow").modal('show')
                })
            });
            windowNW > 768 ? $("[id^=alreadyPayTd-]").hover(function () {
                var id = this.id.split('-')[1];
                $("#alreadyPay-" + id).text() == '' ? '' : $("#payMenu-" + id).show();


            }, function () {
                var id = this.id.split('-')[1];
                $("#alreadyPay-" + id).text() == '' ? '' : $("#payMenu-" + id).hide();


            }) : "";
            createSplitBar(allRecorders);
            showBigImg();
            //导航栏应该与此时的内容高度相同
            sameHeight();
        }
        ,
        error: function (e) {
        }
    });
}

var alRpay;
var allC;
var type = '';
var zsdengji = '';
var NoPay;
var sydw = '';
// /*]]>*/

$(function () {

    //这里需要复写parameterName;
    shoujiOpenOverFlowX();
    loadData();
    $("#rcType").change(function () {
        currentPage=1;
        type = this.value;
        loadData();
    });
    $("#zsdengji").change(function () {
        currentPage=1;
        zsdengji = this.value;
        loadData();
    });
    initDetailDate();
    setKuan('payWay');
    setKuan('payBank');
    setKuan('payAccount');
    setKuan('shouBank');
    $("#payForm").validate({
        debug: true, // 取消表单的提交操作
        submitHandler: function (form) {
            $.post("/pages/back/rencai/pay", $(form).serializeArray(), function (res) {

                if (res) {
                    loadData();
                    showAlert($("#successMsg"), "向人才付款成功");

                } else {
                    showAlert($("#failureMsg"), "向人才付款失败")
                }
                $("#payWindow").modal('hide')

            }, "json")

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
        messages: {
            payCost: {
                required: "请输入付款金额",
                digits: "请输入正整数",
                max: function () {
                    return "最大值不能超过" + NoPay;
                }
            }, time: {
                datetime: '格式如：1999-01-01 11:11:11'
            }
        },
        rules: {
            payCost: {
                required: true,
                digits: true,
                min: 1,
                max: function () {
                    return parseInt(NoPay);
                }
            },
            time: {
                datetime: true
            }

        }
    });
    //初始化选择银行和账户
    $("#payAccount").change(function () {
        var accid = this.value;
        var bank = $("[value=" + accid + "]").attr("name");
        $("#payBankInput").val(bank)
    });
    $("#checkAll").click(function () {
        var c = this.checked;
        $("[id^=check-]").each(function () {
            this.checked = c;
        })
    });


    $("#pldele").click(function () {
        var str = "";
        $("[id^=check-]").each(function () {
            if (this.checked) {
                str += this.id.split('-')[1] + "-";
            }
        });
        if (str == "") {
            showAlert($("#warningMsg"), "您还未选择任何数据")
        } else {
            areYouSure("您确定要删除选中数据？", function () {

                $.post("/pages/back/rencai/plDeleteRencai", {str: str}, function (res) {
                    if (res) {
                        var ids = str.split('-');
                        for (var x = 0; x < ids.length; x++) {
                            $("#row-" + ids[x]).remove();
                        }
                        $("#checkAll").get(0).checked = false;
                        showAlert($("#successMsg"), "人才删除成功");
                    } else {
                        showAlert($("#failureMsg"), "人才删除失败");
                    }
                }, "json");
            });

        }


    });
    $("#rencaisAddToProject").click(function () {
        var str = "";
        $("[id^=check-]").each(function () {
            if (this.checked) {
                str += this.id.split('-')[1] + "-";
            }
        });
        if (str == "") {
            showAlert($("#warningMsg"), "您还未选择任何数据")
        } else {
            var projectid = $("#projectid").val();
            if (projectid == '') {
                showAlert($("#warningMsg"), "请先选择项目")
            } else {
                $.post("/pages/back/project/rencaisAddToProject", {projectid: projectid, str: str}, function (res) {
                    if (res) {
                        showAlert($("#successMsg"), "人才分配成功！");
                    } else {
                        showAlert($("#failureMsg"), "人才分配失败！")

                    }
                    loadData();
                }, "json")
            }


        }

    });
    $("#rencaisFromProjectRemove").click(function () {
        var str = "";
        $("[id^=check-]").each(function () {
            if (this.checked) {
                str += this.id.split('-')[1] + "-";
            }
        });
        if (str == "") {
            showAlert($("#warningMsg"), "您还未选择任何数据")
        } else {
            var projectid = $("#projectid").val();
            if (projectid == '') {
                showAlert($("#warningMsg"), "请先选择项目")
            } else {
                $.post("/pages/back/project/rencaisFromProjectRemove", {
                    projectid: projectid,
                    str: str
                }, function (res) {
                    if (res) {
                        showAlert($("#successMsg"), "人才移除成功！")
                    } else {
                        showAlert($("#failureMsg"), "人才移除失败！")

                    }
                    loadData();
                }, "json")
            }


        }

    });
    enterKeySubmit(loadData);
    //检索按钮绑定事件
    jiansuo(loadData);
    $("#sydw").change(function () {
        sydw = this.value;
        currentPage=1;
        loadData();
    })


});



/**
 * 设置我的输入框宽度的函数
 * @param id
 */
function setKuan(id) {
    var select = $("[id=" + id + "]");
    var input = select.next();
    var kuan = select.width();

    input.css({width: kuan});
    select.each(function () {
        $(this).change(function () {
            $(this).next().val(this.value);
        })
    })
}




