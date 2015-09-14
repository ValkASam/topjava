<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Профиль</title>
    <link href="${pageContext.request.contextPath}/resources/images/title.png" rel="shortcut icon">
    <!-- bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
    <!-- -->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/profile.css"/>
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
        <h3>Админ.&nbsp;профиль</h3>
        <div>
            <form id="profile-form" class="form-horizontal" role="form" action="#" method="post">
                <input id="id" class="form-control hidden" name="id" value='${profile.id}'>
                <div class="form-group">
                    <label class="col-sm-2">Имя</label>

                    <div class="col-sm-8">
                        <input class="form-control" required type="text" id="name" name="name"
                               value='${profile.name}'
                                       placeholder="Имя&nbsp;пользователя">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2">Email</label>

                    <div class="col-sm-8">
                        <input class="form-control" required type="email" id="email" name="email"
                               value='${profile.email}'
                               placeholder="электронная@почта">
                    </div>
                </div>
                <div class="form-group time-picker">
                    <label class="col-sm-2">Пароль</label>

                    <div class="col-sm-8">
                        <input class="form-control" required type="password" id="password" name="password"
                               value='${profile.password}'
                               placeholder="Пароль">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2">Норма&nbsp;калорий&nbsp;в&nbsp;день</label>

                    <div class="col-sm-8">
                        <input class="form-control" required type="number" id="calories" name="caloriesPerDay"
                               min="1" max="50000"
                               value='${profile.caloriesPerDay}'
                               placeholder="Количество&nbsp;калорий">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2"></div>
                    <div class="col-sm-8">
                        <button class="btn btn-primary pull-left" type="submit">Обновить</button>
                    </div>
                </div>
            </form>

        </div>
    </div>
</div>
<div class="footer">
    <div class="container">
        Онлайн Java проект
        <p> Домашнее задание #2. Самирханов Валентин (ValkSam) </p>
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
        src="${pageContext.request.contextPath}/resources/js/profile-processing.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/profile-init.js"></script>

</body>
</html>
