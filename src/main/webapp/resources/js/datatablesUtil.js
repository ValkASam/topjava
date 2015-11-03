function makeEditable() {
    $('#add').click(function () {
        $('#id').val(0);
        $('#editRow').modal();
    });

    $('.delete').click(function () {
        deleteRow($(this).parentsUntil("tr").parent().attr("id"));
    });

    $('#detailsForm').submit(function () {
        save();
        return false;
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });

    $("input[type=checkbox]").change(function () {
        var id = $(this).parentsUntil("tr").parent().attr("id");
        var currCheckBox = $("input[id=" + id + "]");
        var newActive = !currCheckBox.attr("checked");
        var user;
        $.ajax({
            type: "GET",
            url: ajaxUrl + id,
            async: false,
            success: function (data) {
                user = {
                    "id": data.id,
                    "name": data.name,
                    "email": data.email,
                    "password": data.password,
                    "registered": new Date(data.registered),
                    "enabled": newActive,
                    "caloriesPerDay": data.caloriesPerDay
                };
            }
        });
        $.ajax({
            type: "POST",
            url: ajaxUrl,
            data: user,
            success: function () {
                updateTable();
                successNoty('Saved');
            }
        });
    });
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('Deleted');
        }
    });
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        oTable_datatable.fnClearTable();
        $.each(data, function (key, item) {
            item.registered = new Date(item.registered); //с форматированием не заморачивался - нужен плагин
            oTable_datatable.fnAddData(item);
        });
        oTable_datatable.fnDraw();
    });
}

function save() {
    var form = $('#detailsForm');
    debugger;
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('Saved');
        }
    });
}

var failedNote;

function closeNote() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNote();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNote();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}
