function fillProfileForm(id) {
    $.ajax({
        url: "profile/getprofile",
        type: "GET",
        data: {"id": id},
        dataType: "json",
        async: false,
        success: function (data) {
            $.each(data, function (k, v) {
                $("#profile-form input[id=" + k + "]").val(v);
            })
        }
    });
}

function updateProfile() {
    $.ajax({
        url: "profile/update",
        method: "POST",
        data: $("#profile-form").serialize(),
        async: false,
        success: function (data) {
            fillProfileForm(data.id);
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
}
