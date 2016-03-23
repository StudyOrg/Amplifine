<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><g:layoutTitle default="Amplifine | Главная"/></title>
    <link href="${resource(dir: "css", file: "bootstrap.min.css")}" rel="stylesheet">
    <g:layoutHead/>
</head>

<body>

<div class="container">
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">Amplifine</a>
            </div>
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Данные</a></li>
                <li><a href="#">Генератор</a></li>
            </ul>
        </div>
    </nav>

    <h1>Hello, world!</h1>
    <g:layoutBody/>
</div>

<script src="${resource(dir: "js", file: "jquery-1.12.2.min.js")}"></script>
<script src="${resource(dir: "js", file: "bootstrap.min.js")}"></script>
</body>
</html>
