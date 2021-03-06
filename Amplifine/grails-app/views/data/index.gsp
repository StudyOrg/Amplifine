<%@ page import="amplifine.utils.SearchUtil" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Поиск продаж</title>
</head>

<body>
<script>
    function searchAlgo(algo) {
        $("input[name='algorithm']").val(algo);
        $("input[name='offset']").val(0);
        $("form")[0].submit();
    }

    function searchOffset(offset) {
        $("input[name='offset']").val(offset);
        $("form")[0].submit();
    }
</script>

<div class="row">
    <div class="col-md-6">
        <h3>Поиск по товарам</h3>

        <g:form controller="data" id="search-form" action="textSearch" method="get" class="form">
            <input type="hidden" name="offset" value="${offset && offset != -1 ? offset : 0}"/>
            <input type="hidden" name="algorithm" value="${algo ?: "lev"}"/>

            <div class="form-group">
                <div class="input-group">
                    <div class="input-group-btn">
                        <button type="submit" class="btn btn-default">Go!</button>
                        <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span class="caret"></span>
                            <span class="sr-only"></span>
                        </button>
                        <ul class="dropdown-menu">
                            <li><button class="btn btn-link" onclick="searchAlgo('lev')">Расстояние Левенштайн</button></li>
                            <li><button class="btn btn-link" onclick="searchAlgo('jv')">Расстояние Джаро-Винклера</button></li>
                            <li><button class="btn btn-link" onclick="searchAlgo('lcs')">Нахождение НОП</button></li>
                        </ul>
                    </div>
                    <input type="text" id="search" name="search" value="${search == "null" || !search ? "" : search}" class="form-control" placeholder="Введите запрос"/>
                </div>
            </div>
        </g:form>
    </div>
</div>

<div class="row">
    <div id="search-list" class="col-md-12">
        <g:if test="${result && result.size() > 0}">
            <g:if test="${initialSearch != search}">
                Исправлена раскладка клавиатуры в "${initialSearch}"
            </g:if>
            <p>Запрос выполнен за ${totalTime}мс</p>

            <h3>Результат поиска</h3>

            <div>
                <table class="table">
                    <thead>
                    <tr>
                        <th>Тип</th>
                        <th>Производитель</th>
                        <th>Модель</th>
                        <th>Цена</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${result}" var="row">
                        <tr>
                            <td>${row.type}</td>
                            <td>${row.manufacturer}</td>
                            <td>${row.model}</td>
                            <td><g:formatNumber number="${row.retailPrice}" type="currency" currencyCode="RUB"/></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>

            <script>
                $(function() {
                    var hilitor = new Hilitor("search-list");
                    hilitor.apply("${search}");
                });
            </script>
            <ul class="pager">
                <g:if test="${offset > 0}">
                    <li>
                        <button class="btn btn-link" onclick="searchOffset(${offset - 1})">← Назад</button>
                    </li>
                </g:if>
                <g:else>
                    <li>
                        <button class="btn btn-link disabled" disabled>← Назад</button>
                    </li>
                </g:else>

                <g:if test="${offset > -1}">
                    <li>
                        <button class="btn btn-link" onclick="searchOffset(${offset + 1})">Вперед →</button>
                    </li>
                </g:if>
                <g:else>
                    <li>
                        <button class="btn btn-link disabled" disabled>Вперед →</button>
                    </li>
                </g:else>

            </ul>
        </g:if>
        <g:elseif test="${result && result.size() == 0}">
            По запросу "<i>${initialSearch}</i>" ничего не найдено
        </g:elseif>
    </div>
</div>
</body>
</html>
