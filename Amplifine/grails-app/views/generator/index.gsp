<%@ page import="mongodb.MongoDBUtil" %>
<g:set var="db" value="${MongoDBUtil.getDB()}"/>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Список генераторов</title>
</head>

<body>
<g:if test="${notify instanceof String}">
    <g:set var="notify" value="${[notify]}"/>
</g:if>
<g:each in="${notify}" var="i">
    <div class="alert alert-success">
        ${i}
    </div>
</g:each>

<div class="col-md-4">
    <h3>Генератор товаров</h3>
    <p>
        Генератор товаров отключен за неуплату
    </p>
</div>

<div class="col-md-4">
    <form role="form" action="${createLink(controller: "generator", action: "all")}" method="post">
        <h3>Полная генерация</h3>
        <ul>
            <li>Очистка базы</li>
            <li>Генерация базовых записей (магазины, товары)</li>
            <li>Генерация остальных записей (поставки, продажи)</li>
        </ul>
        <div class="form-group">
            <input type="submit" value="Сгенерировать" class="btn btn-danger">
        </div>
    </form>
</div>

<div class="col-md-4">
    <h3>Количество записей</h3>
    <p><strong>Товаров</strong>: ${db.getCollection("goods").count()}</p>
    <p><strong>Магазинов</strong>: ${db.getCollection("shops").count()}</p>
    <p><strong>Сотрудников</strong>: ${db.getCollection("workers").count()}</p>
    <p><strong>Продажи</strong>: ${db.getCollection("sales").count()}</p>
    <p><strong>Поставки</strong>: ${db.getCollection("supplies").count()}</p>
</div>
</body>
</html>
