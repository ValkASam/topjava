function makeEditable() {
    $('#add').click(function () {
        $('#id').val(0);
        fillEditMealForm(0);
    });

    $('.delete').click(function () {
        deleteRow($(this).attr("id"));
    });

    $('.edit').click(function () {
        fillEditMealForm($(this).attr("id"));
    });
}

function fillEditMealForm(id) {
    if (id > 0) {
        $.ajax({
            url: ajaxUrl + id,
            type: "GET",
            success: function (data) {
                $.each(data, function (k, v) {
                    $("#entering-form").find("input[id=" + k + "]").val(v);
                })
            }
        });
    }
    else {
        $("#entering-form").find("input").each(function (idx, element) {
            $(element).val("");
        });
    }
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
            oTable_datatable.fnAddData(item, false);
        });
        oTable_datatable.fnDraw();
    });
}

function create(form) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: ajaxUrl,
        data: JSON.stringify($(form).serializeJSON()),
        success: function () {
            updateTable();
            successNoty('Saved');
        }
    });
}

function save(form, id) {
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: ajaxUrl+id,
        data: JSON.stringify($(form).serializeJSON()),
        success: function () {
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
