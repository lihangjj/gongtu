function addOption() {
    $("[name=sydanwei]").change(function () {
        var cid = $(this).children(":selected").get(0).id;
        var pSelect = $(this).parent().next().find("select");
        $.get("/pages/back/rencai/getProjectsByContractId", {contractid: cid}, function (data) {
            console.log(data);
            pSelect.empty();
            for (var x = 0; x < data.length; x++) {
                var p = data[x];
                pSelect.append("<option >" + p.name + "</option>")
            }
        }, "json")
    });
}


function deleteDanwei() {
    $("[id^=delete-]").each(function () {
        $(this).click(function () {
            $(this).parent().remove()
        })
    });
}
function hideXXDivByAction() {

    for (var i in arguments){
        console.log(arguments[i]+'人才');
        $("[name="+arguments[i]+"]").css({display:'none'});
    }

}
$(function () {

    initSimpleDate();
    op = $("#sydanwei").children().clone();
    console.log(op);
    deleteDanwei();

    $("#idCard").blur(function () {
        var id = this.value;
        var yyyy = id.substring(6, 10);
        var sex = id.substring(16, 17);
        if (parseInt(sex) % 2 == 0) {
            $("[value=女]").get(0).checked = true;
        } else {
            $("[value=男]").get(0).checked = true;
        }
        var birthday = yyyy + '-' + id.substring(10, 12) + '-' + id.substring(12, 14);
        $("#birthday").val(birthday);
        var nowYYYY = new Date().getFullYear();
        $("#age").val(nowYYYY - yyyy);
        console.log(birthday)
    });
    windowNW < 500 ? $("[id=m]").css({width: '100%'}) : $("[id=m]").css({width: '300px'});
    windowNW < 500 ? $("[name=rencaiPhoto]").css({width: '100%'}) : $("[name=rencaiPhoto]").css({width: '250px'});
    //设置人才开户行的宽度
    setKuan('rckhh');
    setKuan('zszhuanye');
    setKuan('zjkhh');
    setKuan('zzType');

    $("#rcType").change(function () {
        var type = this.value;
        $("[id=m]:gt(0)").css({display: 'inline-block'});
        var nameStr = "";
        if (type == '建造师') {
            //要隐藏的div名字
            nameStr += 'zsbgDiv-yjzmDiv-zyddDiv-jpzmDiv-hongtouDiv-psbiaoDiv';
        } else if (type == '注册结构师' || type == '建筑师') {
            nameStr += 'kaobDiv-bzbgDiv-zczsbgDiv-hongtouDiv-psbiaoDiv-qitaDiv';

        } else if (type == '施工类工程师') {
            nameStr += 'kaobDiv-bzbgDiv-zczsbgDiv-nsTimeDiv-yzbgDiv-jpzmDiv-zyddDiv-yjzmDiv-qitaDiv';

        } else if (type == '设计工程师') {
            nameStr += 'kaobDiv-bzbgDiv-zczsbgDiv-nsTimeDiv-qitaDiv';

        } else if (type == '员') {
            nameStr += 'kaobDiv-bzbgDiv-yzbgDiv-zsdengjiDiv-zczsbgDiv-hongtouDiv-yjzmDiv-zyddDiv-jpzmDiv-psbiaoDiv-qitaDiv';

        } else if (type == '三类人员') {
            nameStr += 'kaobDiv-guaxmDiv-yzbgDiv-chuchangDiv-zsdengjiDiv-zczsbgDiv-hongtouDiv-yjzmDiv-zyddDiv-jpzmDiv-psbiaoDiv-qitaDiv';

        } else if (type == '建委技工') {
            nameStr += 'kaobDiv-guaxmDiv-chuchangDiv-zsdengjiDiv-nsTimeDiv-zczsbgDiv-hongtouDiv-yjzmDiv-zyddDiv-jpzmDiv-psbiaoDiv-qitaDiv';

        } else if (type == '安监技工' || type == '高压进网电工' || type == '高压进网电工-特种') {
            nameStr += 'kaobDiv-guaxmDiv-bzbgDiv-chuchangDiv-yzbgDiv-zsdengjiDiv-zczsbgDiv-zsbgDiv-hongtouDiv-yjzmDiv-zyddDiv-jpzmDiv-psbiaoDiv-qitaDiv';
        } else if (type == '其他') {
            nameStr += 'zczsbgDiv-';
        }
        hideXxDiv(nameStr);
    });
    //默认为建造师，的表单
    $("#rcType").change();
    hasRole('rencai:总经理-副总-财务主管-人才主管')?'':hideXXDivByAction('hide');


    $("#reUpload").click(function () {
        $("#rcPhoto").click();
    });
    $("#rcPhoto").change(
        function () {
            var span = $("#showUrl");
            span.empty();
            span.append(this.value);
            imgPreview(this);
        }
    );
    $("#hetongBt").click(function () {
        $("#hetongFile").click();
    });
    $("#hetongFile").change(
        function () {
            var span = $("#showHetongUrl");
            span.empty();
            span.append("已选择<span style='color: red'>" + this.files.length + "</span>个文件。<small style='color: gray'>支持多文件上传的<span style='color: red'>手机浏览器</span>暂时有：qq浏览器，谷歌浏览器</small>");
        }
    );
    $("#qyTime").change(function () {
        setExpiredDate('qyTime', 'qyqx', 'dqTime')
    });
    $("#qyTime").blur(function () {
        setExpiredDate('qyTime', 'qyqx', 'dqTime')
    });
    $("#qyqx").change(function () {
        setExpiredDate('qyTime', 'qyqx', 'dqTime')
    });
    addOption();

    $("#addDanwe").click(function () {
        var danweiGroup = $("  <div  >\n" +
            "                                <div class='form-inline form-group ' id='m' >\n" +
            "                                    <div>\n" +
            "                                        <label>使用单位名称：</label>\n" +
            "                                    </div><select  id='sydanwei' name='sydanwei'  class='form-control'>\n" +
            "                                    </select>\n" +
            "                                </div> " +
            "                                   <div class='form-inline form-group' id='m' >\n" +
            "                                    <div>\n" +
            "                                        <label>项目名称：</label>\n" +
            "                                    </div>\n" +
            "                                    <select  id='xmName' name='xmName' class='form-control' >\n" +
            "                                        <option value=''>无</option>\n" +
            "                                    </select>\n" +
            "                                </div>\n" +
            "                                <div style='display: inline-block;position: relative;' id='m'>\n" +
            "                                    <div><label>资质种类：</label></div>\n" +
            "                                    <div style='display: inline-block;position: relative;'>\n" +
            "                                        <select id='zzType' class='form-control'\n" +
            "                                                style='display: inline-block;'>\n" +
            "                                            <option>建委</option>\n" +
            "                                            <option>承装修</option>\n" +
            "                                        </select>\n" +
            "                                        <input style='position: absolute;border-radius: 5px 0 0 5px;z-index:2;border-right: none;top: 0;'\n" +
            "                                               value='建委' type='text' id='zzTypeInput' name='zzType' class='form-control'/>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "\n" +
            "                                <div class='form-inline form-group' id='m'>\n" +
            "                                    <div >\n" +
            "                                        <label>起始时间:</label>\n" +
            "                                    </div>\n" +
            "                                    <div class='input-group date' id='datetimepicker1'>\n" +
            "                                        <input type='text' class='form-control' style='margin-left: -0.1rem'\n" +
            "                                               placeholder='格式为：1991-03-02' id='startTime' name='startTime'/>\n" +
            "                                        <span class='input-group-addon'>\n" +
            "                                  <span class='glyphicon glyphicon-calendar'></span>\n" +
            "                                 </span>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                                <div class='form-inline form-group' id='m'>\n" +
            "                                    <div >\n" +
            "                                        <label>终止时间:</label>\n" +
            "                                    </div>\n" +
            "                                    <div class='input-group date' id='datetimepicker1'>\n" +
            "                                        <input type='text' class='form-control' style='margin-left: -0.1rem'\n" +
            "                                               placeholder='格式为：1991-03-02' id='overTime' name='overTime'/>\n" +
            "                                        <span class='input-group-addon'>\n" +
            "                                  <span class='glyphicon glyphicon-calendar'></span>\n" +
            "                                 </span>\n" +
            "                                    </div>\n" +
            "                                </div><button id='delete-' class='btn btn-danger'>删除</button></div>");
        $("#danweiDiv").append(danweiGroup);
        setKuan('zzType');
        windowNW < 500 ? $("[id=m]").css({width: '100%'}) : $("[id=m]").css({width: '300px'});
        deleteDanwei();
        var last = $("[name=sydanwei]").length - 1;
        var lastSelect = $("[name=sydanwei]:eq(" + last + ")");
        var opClone = op.clone();
        lastSelect.append(opClone);
        addOption();
        lastSelect.change();

        initSimpleDate();
    });
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
                        "box-shadow": "0 0 5px rgba(255,0,0,1)"
                    })
                });

            })
        },
        unhighlight: function (element, errorClass) {
            $(element).fadeOut(1, function () {
                $(element).fadeIn(1, function () {
                    $(element).css({
                        "box-shadow": "0 0 5px rgba(0,255,0,1)"
                    });
                });
            })
        },
        errorClass: "text-danger",
        messages: {
            name: "请输入姓名",
            phone: "请输入正确的手机号",
            idCard: {
                required: "请输入身份证",
                remote: function () {
                    return messages;

                }
            }, rczh: "8到19位数字", zjzh: "8到19位数字", zjlxrPhone: "请输入正确的手机号",


        },
        rules: {
            idCard: {
                required: true,
                remote: {
                    url: "/pages/back/rencai/checkIdCardAlreadyExceptCurrent", // 后台处理程序
                    type: "post", // 数据发送方式
                    dataType: "json", // 接受数据格式
                    data: { // 要传递的数据
                        data: function () {//必须用过这样的方式取得表单数据
                            return $("#idCard").val();
                        }, oldId: function () {
                            return $("#oldId").val();
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
            sfzyxq: {
                dateISO: true
            }, nsTime: {
                dateISO: true
            }, rczh: {
                digits: true,
                minlength: 8,
                maxlength: 19
            }, zjzh: {
                digits: true,
                minlength: 8,
                maxlength: 19
            }, zjlxrPhone: {
                digits: true,
                rangelength: [11, 11],
                remote: {
                    url: "/checkCellPhoneNumber", // 后台处理程序
                    type: "post", // 数据发送方式
                    dataType: "json", // 接受数据格式
                    data: { // 要传递的数据
                        phone: function () {//必须用过这样的方式取得表单数据
                            return $("#zjlxrPhone").val();
                        }
                    },
                    dataFilter: function (data) {
                        return data;
                    }
                }
            }, qyPrice: {
                digits: true
            }, qyTime: {
                date: true
            }, startTime: {
                date: true
            }, overTime: {
                date: true
            }, rck: {
                digits: true
            },

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

            }
        }
    });
});
var op;
var messages = 'nihao';

//设置到期时间/**
/**
 *
 * @param sigingDateId 签约时间
 * @param qixianId 签约期限
 * @param daoqiId 到期时间
 */
function setExpiredDate(sigingDateId, qixianId, daoqiId) {
    var signingDate = $("#" + sigingDateId).val();
    var qixian = $("#" + qixianId).val();

    if (signingDate != "" && qixian != "") {
        //这里因为有很多个时间组件的id相同，所以用一个名字属性来指定
        var d = $("[name=qyTimeDiv]").data("datetimepicker").getDate();
        d.setMonth(d.getMonth() + parseInt(qixian));
        $("#" + daoqiId).val(d.format("yyyy-MM-dd"))
    } else {
        $("#" + daoqiId).val("");
    }
}

//复写，因为要王下边
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
        pickerPosition: 'top-right'//日期插件弹出的位置
    });
}

/**
 * 设置我的输入框宽度的函数
 * @param id
 */
function setKuan(id) {
    var select = $("[id=" + id + "]");
    var input = select.next();
    var kuan = select.width();
    input.css({width: kuan-20});
    select.each(function () {
        $(this).change(function () {
            $(this).next().val(this.value);
        })
    })
}

function hideXxDiv(nameStr) {
    var names = nameStr.split('-');
    $("input").removeAttr("disabled");
    $("select").removeAttr("disabled");
    for (var x = 0; x < names.length; x++) {
        var name = names[x];
        var nameDiv = $("[name=" + name + "]");
        nameDiv.find("input").attr("disabled", true);
        nameDiv.find("select").attr("disabled", true);
        nameDiv.css({display: "none"})
    }
}