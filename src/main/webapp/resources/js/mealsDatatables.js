var ajaxUrl = 'ajax/profile/meals/';
var datatableApi;

function updateTable(filter) {
    filter = false;
    if (updateTable.caller.arguments[0] != "create"){
        //если "create", то фильтр не восстанавливаем
        $("#filter").find("input").each(function(indx, element){
            filter = filter||!($(element).val().trim()==="");
        });
    }
    if (!filter) {
        $("#filter").find("input").val("");
    }
    $.get(ajaxUrl + (filter ? "filter?"+$("#filter").serialize() : ""), function (data) {
        updateTableByData(data);
    });
}

$(function () {
    datatableApi = $('#datatable').DataTable({
        "ajax": {
            url: ajaxUrl,
            dataSrc: ""
        },
        "paging": false,
        "info": false,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "sortable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "sortable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "sorting": [
            [
                0,
                "asc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            data.exceed ? $(row).addClass("exceeded") : $(row).addClass("normal");
        },
        "initComplete": makeEditable
    });

    $('#filter').submit(function () {
        updateTable(true);
        return false;
    });
});