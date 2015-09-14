/**
 * Created by Valk on 12.09.15.
 */
var oTable = $('#meals-table');
var oTable_params = {
    "aoColumnDefs": [{
        "aTargets": ["dateTime-field"],
        "mData": "dateTime",
        "bSortable": true
    }, {
        "aTargets": ["description-field"],
        "mData": "description",
        "bSortable": true
    }, {
        "aTargets": ["calories-field"],
        "mData": "calories",
        "bSortable": true
    }, {
        "aTargets": ["update-btn"],
        "mRender": function (data, type, row) {
            return '<a class="btn btn-xs btn-primary" onclick="fillEditForm('+row.id+')" data-toggle="modal" data-target="#edit-form">Изменить</a>';
        },
        "bSortable": false
    }, {
        "aTargets": ["delete-btn"],
        "mRender": function (data, type, row) {
            return '<a class="btn btn-xs btn-danger" onclick="deleteMeal('+row.id+')">Удалить</a>';
        },
        "bSortable": false
    }, {
        "aTargets": ["exceed-field"],
        "mRender": function (data, type, row) {
            return '<span>' + row.exceed + '</span>';
        },
        "sClass": "hidden"
    }],
    "fnCreatedRow": function(nRow, aData, iDataIndex){
        $(nRow).attr("exceed", $(nRow).find("span").text());
    },
    "asStripeClasses": [],
    "bPaginate": false,
    "sAjaxSource": "meals/loaddata",
    "bInfo": false,
    "sAjaxDataProp": "",
    "bDeferRender": true,
    "aaSorting": [ [0, "desc"] ],
    "language": {
        "processing": "Подождите...",
        "search": "Поиск:",
        "lengthMenu": "Показать _MENU_ записей",
        "info": "Записи с _START_ до _END_ из _TOTAL_ записей",
        "infoEmpty": "Записи с 0 до 0 из 0 записей",
        "infoFiltered": "(отфильтровано из _MAX_ записей)",
        "infoPostFix": "",
        "loadingRecords": "Загрузка записей...",
        "zeroRecords": "Записи отсутствуют.",
        "emptyTable": "В таблице отсутствуют данные",
        "paginate": {
            "first": "Первая",
            "previous": "Предыдущая",
            "next": "Следующая",
            "last": "Последняя"
        },
        "aria": {
            "sortAscending": ": активировать для сортировки столбца по возрастанию",
            "sortDescending": ": активировать для сортировки столбца по убыванию"
        }
    }
};

$(document).ready(function () {
    oTable.dataTable(oTable_params);

    $('#dateTime').datetimepicker({
        format: 'Y-m-d H:i',
        lang: 'ru'
    });

    $('.date-picker input').datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        lang: 'ru'
    });

    $('.time-picker input').datetimepicker({
        datepicker: false,
        format: 'H:i',
        lang: 'ru'
    });

    $('#edit-form').submit(function(){
        if ($("#id").val()>0){
            updateMeal();
        } else {
            createMeal();
        }
        return false;
    });

    $('#filter-form').submit(function(){
        refreshMeals();
        return false;
    });



});

