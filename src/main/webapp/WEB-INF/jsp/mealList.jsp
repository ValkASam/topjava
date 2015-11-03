<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <div class="jumbotron">
        <div class="container">
            <div class="shadow">
                <%--http://stackoverflow.com/questions/10327390/how-should-i-get-root-folder-path-in-jsp-page--%>
                <h3><a href="${pageContext.request.contextPath}">Home</a></h3>

                <h3><fmt:message key="meals.title"/></h3>

                <div class="view-box">
                    <form id="filterForm" method="post" action="#">
                        <dl>
                            <dt>From Date:</dt>
                            <dd><input type="date" name="startDate"></dd>
                        </dl>
                        <dl>
                            <dt>To Date:</dt>
                            <dd><input type="date" name="endDate"></dd>
                        </dl>
                        <dl>
                            <dt>From Time:</dt>
                            <dd><input type="time" name="startTime"></dd>
                        </dl>
                        <dl>
                            <dt>To Time:</dt>
                            <dd><input type="time" name="endTime"></dd>
                        </dl>
                        <button type="submit">Filter</button>
                    </form>
                    <hr>
                    <a class="btn btn-sm btn-info" id="add" data-toggle="modal" data-target="#editMealForm"><fmt:message
                            key="meals.add"/></a>
                    <hr>
                    <table border="1" cellpadding="8" cellspacing="0" class="table table-striped display"
                           id="datatable">
                        <thead>
                        <tr>
                            <th>Date</th>
                            <th>Description</th>
                            <th>Calories</th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="fragments/footer.jsp"/>
<jsp:include page="fragments/editMealForm.jsp"/>
</body>
<script type="text/javascript" src="webjars/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="webjars/jquery-serializejson/2.5.0/jquery.serializejson.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/datetimepicker/2.3.4/jquery.datetimepicker.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.9/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="webjars/noty/2.2.4/jquery.noty.packaged.min.js"></script>
<script type="text/javascript" src="resources/js/datatablesMealsUtil.js"></script>
<script type="text/javascript">
    var ajaxUrl = 'ajax/profile/meals/';
    var oTable_datatable;
    var oTable_datatable_params;

    $(function () {
        oTable_datatable = $('#datatable');
        oTable_datatable_params = {
            "sAjaxSource": ajaxUrl,
            "bPaginate": false,
            "bInfo": false,
            "sAjaxDataProp": "", //наш массив еды - безымянный
            "aoColumns": [
                {
                    "mData": "dateTime"
                },
                {
                    "mData": "description"
                },
                {
                    "mData": "calories"
                },
                {
                    "mRender": function (data, type, row) {
                        return '<a class="btn btn-xs btn-primary edit" ' +
                                'data-toggle="modal" data-target="#editMealForm">Edit</a>';
                    },
                    "bSortable": false
                },
                {
                    "mRender": function (data, type, row) {
                        return '<a class="btn btn-xs btn-danger delete" >Delete</a>';
                    },
                    "bSortable": false
                }
            ],
            "aaSorting": [
                [
                    0,
                    "asc"
                ]
            ],
            "fnCreatedRow": function (nRow, aData, iDataIndex) {
                $(nRow).attr("id", aData.id);
                aData.exceed ? $(nRow).addClass("exceeded") : $(nRow).addClass("normal");
            },
            "fnDrawCallback": function () {
                makeEditable();
            }
        };
        oTable_datatable.dataTable(oTable_datatable_params);
        /**/
        $('#editMealForm').submit(function () {
            try {
                var id = $("input[id=id]").val();
                if (id === "") {
                    create("#entering-form");
                }
                else {
                    save("#entering-form", id);
                }
            } catch (e) {
                alert(e);
            }
            $("#editMealForm").modal("hide");
            return false;
        });

        $('#filterForm').submit(function () {
            try {
                updateTable(true);
            } catch (e) {
                alert(e);
            }
            return false;
        });

        $(document).ajaxError(function (event, jqXHR, options, jsExc) {
            failNoty(event, jqXHR, options, jsExc);
        });
    });
</script>

</html>
