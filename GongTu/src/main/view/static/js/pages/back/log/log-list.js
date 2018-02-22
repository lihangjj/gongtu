//


function loadData() {
    $.ajax({
        url: "/pages/back/log/listAjax",
        data: {
            "column": column,
            "keyWord": keyWord,
            "lineSize": lineSize,
            "currentPage": currentPage,
            "parameterName": 'dflag',
            "parameterValue":0
        },
        type: "post",
        dataType: "json",
        success: function (data) {
            var allVo = data.allVo;
            allRecorders = data.allRecorders;
            var dntb = $("#logtb");
            $("#row1").nextAll().remove();
            var d=new Date();
            for (var x = 0; x < allVo.length; x++) {
                var log = allVo[x];
                var logid = log.logid;
                var p = log.project;
                var c;
                var companyName;
                var type;
                var cid;
                var pname;
                if (p==null){
                    companyName = "";
                    pname="";
                    type="其他类型日志"

                }else {
                     c=p.contract;
                     cid=c.contractid;
                     companyName = c.companyName;
                     pname=p.name;
                    switch (p.type) {
                        case "px":
                            type = "培训";
                            break;
                        case "qt":
                            type = "其他";
                            break;
                        case "zz":
                            type = "资质";
                            break;
                        case "gk":
                            type = "挂靠";
                            break;
                        case "dp":
                            type = "代评";
                            break;
                    }
                }
                var s=(d.getTime()-log.time)/(1000*60*60);
                var status=s>24?"disabled":"";
                var color=type=="其他类型日志"?"red":"";
                dntb.append($("<tr id='row-" + logid + "' >\n" +
                    "                        <td ><input class=\"form-control\" type=\"checkbox\" id='check-" + logid + "'/></td>\n" +
                    "                        <td style='text-align: left;color: "+color+"' ><a style='text-decoration: none;cursor: pointer' href='/pages/back/contract/editPre?contractid=" + cid + "'>" + companyName + "</a>-" + pname + "-"+type+"</td>\n" +
                    "                        <td  >"+log.type+"</td>\n" +
                    "                        <td style='max-width: 50rem' ><textarea id='log-"+logid+"' class='form-control'>"+log.note+"</textarea></td>\n" +
                    "                        <td>" +new Date( log.time).format("yyyy-MM-dd hh:mm:ss")+"<br/>"+log.member.name+"</td>\n" +
                    "                        <td><button class='btn btn-primary' id='bt-"+logid+"' "+status+" >修改</button> </td>\n" +
                    "                    </tr>"));

            }
            createSplitBar(allRecorders);
            //导航栏应该与此时的内容高度相同
            sameHeight();
        }
        ,
        error: function (e) {
        }
    });
}

var allLogs;

// /*]]>*/
$(function () {
    //这里需要复写parameterName;
    loadData();
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
        if (str == "") {
            showAlert($("#warningMsg"), "您还未选择任何数据")
        } else {
            areYouSure("您确定要删除选中数据？", function () {

                $.post("/pages/back/project/plDeleteProject", {str: str}, function (res) {
                    if (res) {
                        var ids = str.split('-');
                        for (var x = 0; x < ids.length; x++) {
                            $("#row-" + ids[x]).remove();
                        }
                        $("#selectAll").get(0).checked = false;
                        showAlert($("#successMsg"), "客户删除成功");
                    } else {
                        showAlert($("#failureMsg"), "客户删除失败");
                    }
                }, "json");
            });

        }


    });
    enterKeySubmit(loadData);
    //检索按钮绑定事件
    jiansuo(loadData);
    $("#selectType").change(function () {
        currentPage = 1;
        type = this.value;
        loadData();
    });
    $("#status").change(function () {
        currentPage = 1;
        status = this.value;
        loadData();
    })

});
var status = '进行中';
var type = '';

