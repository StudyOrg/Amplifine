<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><g:layoutTitle default="Amplifine | Главная"/></title>
    <link href="${resource(dir: "css", file: "bootstrap.min.css")}" rel="stylesheet">
    <link href="${resource(dir: "css", file: "rewrite.css")}" rel="stylesheet">
    <g:layoutHead/>
</head>

<body>
<script src="${resource(dir: "js", file: "jquery-1.12.2.min.js")}"></script>
<script src="${resource(dir: "js", file: "bootstrap.min.js")}"></script>
<script src="${resource(dir: "js", file: "Hilitor.js")}"></script>
<style>
    .container {
        max-width: 1200px;
    }

    .jumbotron {
        background: url("${resource(dir: "images", file: "logo.png")}") center;
        /*width: 1200px;*/
        height: 250px;
        margin-bottom: 0;
        border-radius: 0 !important;
    }

    .navbar {
        border-radius: 0;
        border-color: rgb(166, 41, 0);
        border-top: none;
    }
</style>

<div class="container">
    <div class="jumbotron">

    </div>

    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">Amplifine</a>
            </div>
            <ul class="nav navbar-nav">
                <li><a href="${createLink(controller: "generator", action: "index")}">Генераторы</a></li>
                <li><a href="${createLink(controller: "data", action: "textSearch")}">Поиск</a></li>
                <li><a href="https://github.com/StudyOrg/Amplifine">Github</a></li>
            </ul>
        </div>
    </nav>
    <g:layoutBody/>
</div>
</body>
</html>
