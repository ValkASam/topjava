function fillEditForm(id) {
    $.ajax({
        url: "meals/getusermeal",
        type: "GET",
        data: {"id": id},
        dataType: "json",
        async: false,
        success: function (data) {
            $.each(data, function (k, v) {
                $("#entering-form input[id=" + k + "]").val(v);
            })
        }
    });
}

function refreshMeals() {
    $.ajax({
        url: "meals/loaddata",
        type: "GET",
        data: $("#filter-form").serialize(),
        dataType: "json",
        async: false,
        success: function (data) {
            oTable.fnClearTable();
            $.each(data, function (key, item) {
                oTable.fnAddData(item);
            });
            oTable.fnDraw();
        }
    })
}

function updateMeal() {
    $.ajax({
        url: "meals/update",
        method: "POST",
        data: $("#entering-form").serialize(),
        async: false,
        success: function (data) {
            refreshMeals();
            if (data == undefined) {
                notyType = "error";
                notyText = "ошибка при сохранении изменений";
            }
            else {
                notyType = "success";
                notyText = "запись успешно изменена";
            }
            noty({
                    layout: 'bottomRight',
                    theme: 'defaultTheme',
                    type: notyType,
                    text: notyText,
                    closeWith: ['hover']
                }
            )
        }
    });
    $("#edit-form").modal("hide");
}

function deleteMeal(id) {
    $.ajax({
        url: "meals/delete",
        type: "POST",
        data: {"id": id},
        async: false,
        success: function (data) {
            refreshMeals();
            if (!data) {
                notyType = "error";
                notyText = "ошибка при удалении";
            }
            else {
                notyType = "success";
                notyText = "запись успешно удалена";
            }
            noty({
                    layout: 'bottomRight',
                    theme: 'defaultTheme',
                    type: notyType,
                    text: notyText,
                    closeWith: ['hover']
                }
            )
        }
    })
}

function createMeal() {
    $.ajax({
        url: "meals/create",
        method: "POST",
        data: $("#entering-form").serialize(),
        dataType: "json",
        async: false,
        success: function (data) {
            refreshMeals();
            if (data == undefined) {
                notyType = "error";
                notyText = "ошибка при добавлении";
            }
            else {
                notyType = "success";
                notyText = "запись успешно добавлена";
            }
            noty({
                layout: 'bottomRight',
                theme: 'defaultTheme', // or 'relax'
                type: notyType,
                text: notyText, // can be html or string
                dismissQueue: false, // If you want to use queue feature set this true
                template: '<div class="noty_message"><span class="noty_text"></span><div class="noty_close"></div></div>',
                animation: {
                    open: {height: 'toggle'}, // or Animate.css class names like: 'animated bounceInLeft'
                    close: {height: 'toggle'}, // or Animate.css class names like: 'animated bounceOutLeft'
                    easing: 'swing',
                    speed: 500 // opening & closing animation speed
                },
                timeout: false, // delay for closing event. Set false for sticky notifications
                force: false, // adds notification to the beginning of queue when set to true
                modal: false,
                maxVisible: 5, // you can set max visible notification for dismissQueue true option,
                killer: false, // for close all notifications before show
                closeWith: ['hover'], // ['click', 'button', 'hover', 'backdrop'] // backdrop click will close all notifications
                callback: {
                    onShow: function () {
                    },
                    afterShow: function () {
                    },
                    onClose: function () {
                    },
                    afterClose: function () {
                    },
                    onCloseClick: function () {
                    }
                },
                buttons: false // an array of buttons
            });
        }
    });
    $("#edit-form").modal("hide");
}