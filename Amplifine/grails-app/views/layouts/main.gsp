<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" type="image/x-icon" href="${resource(dir: "images", file: "favicon.ico")}">
    <title><g:layoutTitle default="Amplifine | Главная"/></title>
    <link href="${resource(dir: "css", file: "bootstrap.min.css")}" rel="stylesheet">
    <link href="${resource(dir: "css", file: "rewrite.css")}" rel="stylesheet">
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
                <li><a href="/">Данные</a></li>
                <li><a href="/Amplifine/generator/index">Генераторы</a></li>
                <li><a href="https://github.com/StudyOrg/Amplifine">Github</a></li>
            </ul>
        </div>
    </nav>
    <g:layoutBody/>
</div>

<script src="${resource(dir: "js", file: "jquery-1.12.2.min.js")}"></script>
<script src="${resource(dir: "js", file: "bootstrap.min.js")}"></script>
</body>
</html>
