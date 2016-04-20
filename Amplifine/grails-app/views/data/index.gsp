<%@ page import="amplifine.utils.SearchUtil" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Поиск продаж</title>
</head>

<body>
<div class="row">
    <div class="col-md-6">
        <h3>Поиск продаж по наименованию товара</h3>

        <g:form controller="data" action="textSearch" method="get" class="form">
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-btn">
                        <input type="submit" class="btn btn-default" value="Go!">
                    </span>
                    <input type="text" id="search" name="search" value="${search == "null" || !search ? "" : search}" class="form-control" placeholder="Введите запрос"/>
                </div>
            </div>
        </g:form>
    </div>
</div>

<div class="row">
    <div id="search-list" class="col-md-9">
        <g:if test="${result && result.size() > 0}">
            <g:if test="${initialSearch != search}">
                Исправлена раскладка клавиатуры в "${initialSearch}"
            </g:if>
            <h3>Результат поиска</h3>
            <g:each in="${result}" var="row">
                <b>Магазин: ${row.key.name} (${row.key.address.city}, ${row.key.address.street})</b><br/>
                <g:each in="${row.value}" var="sale">
                    Покупатель: <b>${sale.customer.name} ${sale.customer.surnames}</b><br/>
                    Наименование товара:<br/>

                    <g:set var="totalPrice" value="${0.0}"/>
                    <g:each in="${sale.goods}" var="good">
                        <g:set var="goodDesc" value="${SearchUtil.getGoodDescription(good.goodId)}"/>
                        <i>${goodDesc.type}</i> ${goodDesc.manufacturer} ${goodDesc.model} <i>(${good.qty} шт.)</i>. Цена: ${good.retailPrice.round(2)} у.е.<br/>
                        <g:set var="totalPrice" value="${totalPrice + good.retailPrice}"/>
                    </g:each>

                    <b>Итоговая цена: ${totalPrice.round(2)} у.е.</b><br/><br/>
                </g:each>
                <br/>
            </g:each>

            <g:if test="${size > SearchUtil.LIMIT}">
                <g:each in="${SearchUtil.getPaginateMap(offset, size).sort { it.num }}" var="page">
                    <ul class="pagination">
                        <li>
                            <a onclick="document.location = '${createLink(action: "textSearch", params: [offset: page.offset, search: search])}'">
                                ${page.num}
                            </a>
                        </li>
                    </ul>
                </g:each>
            </g:if>
            <script>
                $(function() {
                    var hilitor = new Hilitor("search-list");
                    hilitor.apply("${search}");
                });
            </script>
        </g:if>
        <g:else>
            По запросу "<i>${initialSearch}</i>" ничего не найдено
        </g:else>
    </div>
</div>
</body>
</html>
