var type = '';
var accountid = '';
var beginDate = '';
var overDate = '';

function getBeginDate(beginDate) {
    if (beginDate == '') {
        return '';
    }
    return " AND date>='" + beginDate + "'";

}

function getOverDate(overDate) {
    if (overDate == '') {
        return '';
    }
    return " AND date<='" + overDate + "'";

}

function getType(type) {
    if (type == '') {
        return '';
    }
    return " AND type='" + type + "'";
}

function getAccountid(accountid) {
    if (accountid == '') {
        return '';
    }
    return " AND accountid='" + accountid + "'";
}

lineSize = 15;

function loadData() {
    $.ajax({
        url: "/pages/back/shoupay/listAjax",
        data: {
            "column": column,
            "keyWord": keyWord,
            "lineSize": lineSize,
            "currentPage": currentPage,
            "parameterName": parameterName,
            "parameterValue": ' 1=1 ' + getType(type) + getBeginDate(beginDate) + getOverDate(overDate) + getAccountid(accountid)
        },
        type: "post",
        dataType: "json",
        success: function (data) {
            allRecorders = data.allRecorders;
            var allVo = data.allVo;
            $("#chaxunzsr").text(data.chaxunzongshou);
            $("#chaxunzzc").text(data.chaxunzongpay);

            $("#row1").nextAll().remove();
            var tb = $("#addTb");
            for (var x = 0; x < allVo.length; x++) {
                var sp = allVo[x];
                var spid = allVo[x].shoupayid;
                var shou = sp.shou == 0 ? '' : sp.shou;
                var pay = sp.pay == 0 ? '' : sp.pay;
                tb.append("<tr > <td>" +
                    "<input type='checkbox' id='check-" + sp.shou + "-" + sp.pay + "' class='form-control' style=\"width: 2rem;padding: 0;height: 2.5rem\"/>\n" +
                    "</td>" +
                    "<td >" + sp.date + "</td>\n" +
                    " <td>" + sp.account.bank + "-" + sp.account.name + "</td>\n" +
                    "  <td>" + sp.mingxi + "</td>\n" +
                    "  <td style='color: green'>" + shou + "</td>\n" +
                    "  <td style='color: red'>" + pay + "</td>\n" +
                    "  <td style='color: orange'>" + sp.yue + "</td>\n" +
                    "  <td>" + sp.type + "</td>\n" +
                    "  <td>" + sp.note + "</td>\n" +
                    "  <td><button id='edit-" + spid + "' class='btn btn-primary btn-xs' style='margin: 2px'>修改</button><button style='margin: 2px' id='delete-" + spid + "' class='btn btn-danger btn-xs'>删除</button></td>\n" +
                    "</tr>")
            }
            tb.append("<tr ><td ></td><td ></td><td ></td><td ></td><td id='dangqainzsr'>总收入：8888</td><td id='dangqainzzc'>总支出：8888</td>" +
                "<td ></td><td ></td><td ></td>\n" +
                "</tr>");
            $("#dangqainzzc").html("<span>总支出：</span>" + data.currentPagezongpay);
            $("#dangqainzsr").html("<span>总收入：</span>" + data.currentPagezongshou);
            $("[id^=check-]").click(function () {
                getCheckedFeiyong();
            });
            $("#checkAll").click(function () {
                var flag = this.checked;
                $("[id^=check-]").each(function () {
                    this.checked = flag;
                });
                getCheckedFeiyong();

            });
            $("[id^=delete-]").click(function () {
                var spid = this.id.split('-')[1];
                areYouSure('您确定要删除吗？', function () {
                    $.post("/pages/back/shoupay/deleteShoupay", {shoupayid: spid}, function (res) {
                        if (res) {
                            showAlert($("#successMsg"), '该收支记录删除成功！');
                            currentPage = 1;
                            loadData();
                        }

                    }, "json")
                })

            });
            $("[id^=edit-]").click(function () {
                var spid = this.id.split('-')[1];
                $.get("/pages/back/shoupay/getShouPayInfo", {shoupayid: spid}, function (sp) {
                    $("#shoupayid").val(sp.shoupayid);
                    $("#date").val(sp.date);
                    $("#mingxi").val(sp.mingxi);
                    $("#shou").val(sp.shou);
                    $("#pay").val(sp.pay);
                    $("#note").val(sp.note);
                    var type = sp.type;
                    $("#type").children("option:contains(" + type + ")").attr("selected", 'true');
                    var accountid = sp.accountid;
                    $("#accountid").children("[value=" + accountid + "]").attr("selected", 'true');
                }, "json");
                $("#editModal").modal("show");
            });


            //为处理按钮添加点击事件
            createSplitBar(allRecorders);
            //导航栏应该与此时的内容高度相同
            sameHeight();
        }
        ,
        error: function (e) {
        }
    });
}


function getCheckedFeiyong() {
    var xuanzhongzongshou = 0.0;
    var xuanzhongzongpay = 0.0;
    $("[id^=check-]").each(function () {
        if (this.checked) {
            xuanzhongzongshou += parseFloat(this.id.split('-')[1]);
            xuanzhongzongpay += parseFloat(this.id.split('-')[2]);

        }
        $("#xuanzhongzsr").text(xuanzhongzongshou);
        $("#xuanzhongzzc").text(xuanzhongzongpay)
    });

}

function initSimpleDate() {
    $('[id=datetimepicker1]').datetimepicker({
        format: "yyyy-mm-dd",
        language: 'zh-CN',//这里是language,autoclose: 1,
        todayHighlight: 1,//今天高亮，
        todayBtn: 1,//开启今天按钮
        autoclose: 1,//点击完自动关
        startView: 2,//日期页面
        minView: 2,//最小几个页面，选择日期和时间，如果是2就只是日期
        forceParse: 0,
        pickerPosition: 'bottom-right'//日期插件弹出的位置
    });
}

// /*]]>*/
$(function () {
    shoujiOpenOverFlowX();
    initSimpleDate();
    $("#chaxun").click(function () {
        beginDate = $("#beginDate").val();
        overDate = $("#overDate").val();
        currentPage = 1;
        loadData();
    });
    //这里需要复写parameterName;
    parameterName = 'dealFlag';
    enterKeySubmit(loadData);
    //检索按钮绑定事件
    jiansuo(loadData);

    $("#accountSe").change(function () {
        accountid = this.value;
        currentPage = 1;
        loadData();
    });
    $("#feiyongType").change(function () {
        type = this.value;
        currentPage = 1;
        loadData();
    });
    $("#chaxun").click();


});



