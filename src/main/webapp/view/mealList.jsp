<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Подсчет калорий</title>
    <link href="${pageContext.request.contextPath}/resources/images/title.png" rel="shortcut icon">
    <!-- bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
    <!-- DataTables -->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/jquery.dataTables.min.css"/>
    <!-- -->
    <link type="text/css" href="${pageContext.request.contextPath}/resources/datetimepicker/jquery.datetimepicker.css"
          rel="stylesheet"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/user-meals.css"/>
</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="meals">Подсчет калорий</a>
        </div>
        <div>
            <div class="collapse navbar-collapse">
                <form class="navbar-form navbar-right" method="post" action="meals/logout">
                    <a class="btn btn-info" href="meals">Список пользователей</a>
                    <a class="btn btn-info" href="profile">Админ профиль</a>
                    <a class="btn btn-primary" href="#">Logout</a>
                </form>
            </div>
        </div>
    </div>
</nav>
<div class="jumbotron">
    <div id="container">
        <h3>Список еды</h3>
        <div>
            <form id="filter-form" class="form-horizontal" role="form" action="#" method="post">
                <div class="form-group date-picker">
                    <label class="col-sm-2">Нач.&nbsp;дата</label>

                    <div class="col-sm-2">
                        <input class="form-control" type="text" id="startDate" name="startDate"
                               placeholder="Нач.дата">
                    </div>
                    <label class="col-sm-2">Кон.&nbsp;дата</label>

                    <div class="col-sm-2">
                        <input class="form-control" type="text" id="endDate" name="endDate"
                               placeholder="Кон.дата">
                    </div>
                </div>
                <div class="form-group time-picker">
                    <label class="col-sm-2">Нач.&nbsp;время</label>

                    <div class="col-sm-2">
                        <input class="form-control" type="text" id="startTime" name="startTime"
                               placeholder="Нач.время">
                    </div>
                    <label class="col-sm-2">Кон.&nbsp;время</label>

                    <div class="col-sm-2">
                        <input class="form-control" type="text" id="endTime" name="endTime"
                               placeholder="Кон.время">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-8">
                        <button class="btn btn-primary pull-right" type="submit">Фильтр</button>
                    </div>
                </div>
            </form>

            <div>
                <button class="btn btn-info" onclick="fillEditForm(0)" data-toggle="modal"
                        data-target="#edit-form">
                    Добавить
                </button>
            </div>

            <table id="meals-table" class="table dataTable table-striped display">
                <thead>
                <tr>
                    <th class="dateTime-field">Дата</th>
                    <th class="description-field">Описание</th>
                    <th class="calories-field">Калории</th>
                    <th class="update-btn"></th>
                    <th class="delete-btn"></th>
                    <th class="exceed-field hidden"></th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div class="footer">
    <div class="container">
        Онлайн Java проект
        <p> Домашнее задание #2. Самирханов Валентин (ValkSam) </p>
    </div>
</div>
<!-- окно редактирования-->
<div id="edit-form" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4></h4>
            </div>
            <div class="modal-body">
                <form id="entering-form" role="form" class="form-horizontal" method="post" action="#">
                    <input id="id" class="form-control hidden" name="id">

                    <div class="form-group">
                        <label class="control-label col-sm-3">Дата</label>

                        <div class="col-sm-9">
                            <input id="dateTime" required class="form-control" type="datetime" placeholder="Дата"
                                   name="dateTime">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Описание</label>

                        <div class="col-sm-9">
                            <input id="description" required class="form-control" type="text" placeholder="Описание"
                                   name="description">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Калории</label>

                        <div class="col-sm-9">
                            <input id="calories" required class="form-control" type="number" placeholder="0"
                                   name="calories" min="1" max="10000">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-9 col-sm-offset-3">
                            <button class="btn btn-default" type="submit">Сохранить</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- jQuery -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.min.js"></script>

<!--bootstrap-->
<script type="text/javascript"
        src='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js'></script>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/resources/datetimepicker/jquery.datetimepicker.js"></script>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/jquery.noty.packaged.js"></script>

<!-- DataTables -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.dataTables.min.js"></script>

<!-- -->
<script type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/user-meals-processing.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/user-meals-init.js"></script>

</body>
</html>
