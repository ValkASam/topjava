<%--
  Created by IntelliJ IDEA.
  User: Valk
  Date: 01.11.15
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal fade" id="editMealForm">
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
