<%@ page import="amplifine.Goods" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Список генераторов</title>
</head>

<body>
<g:each in="${notify}" var="i">
    <div class="alert alert-success">
        ${i}
    </div>
</g:each>

<h1>Список генераторов</h1>

<div class="col-md-4">
    <form role="form" action="${createLink(action: "goods")}" method="post">
        <h3>Генератор товаров</h3>
        <div class="form-group">
           <label>Всего записей</label>
           <p class="form-control-static">${Goods.count}</p>
        </div>
        <div class="form-group">
            <label>Количество товаров</label>
            <input type="text" name="count" class="form-control"/>
        </div>
        <div class="form-group">
            <input type="submit" value="Сгенерировать" class="btn btn-primary"/>
        </div>
    </form>
</div>
</body>
</html>
