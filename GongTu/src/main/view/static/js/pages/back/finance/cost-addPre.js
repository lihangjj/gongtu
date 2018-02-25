$(function () {
    windowNW > 992 ? $("#form label").css({
        width: '8rem',
        'text-align': 'right'
    }) : $("#form label").css({width: '8rem'});
    initSuperDetailDate();
    $("#form").validate({
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
        messages: {},
        rules: {
            fangzu: {
                number: true
            }, shui: {
                number: true
            }, dian: {
                number: true
            }, qiche: {
                number: true
            }, canyin: {
                number: true
            }, haocai: {
                number: true
            }, shebei: {
                number: true
            }, tuiguang: {
                number: true
            }


        }
    });
});

