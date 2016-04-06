<%@ page import="amplifine.utils.SearchUtil" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome to Grails</title>

    <style>
    a {
        cursor: pointer;
    }
    </style>
</head>

<body>
<h1>Поиск продаж</h1>

<p>
    <g:form controller="data" action="textSearch" method="post">
        <input type="text" id="search" name="search" value="${search}"/>
    </g:form>

    <g:if test="${result && result.size() > 0}">
        <br/>
        <g:each in="${result}" var="row">
            <b>Store: ${row.key.name} (${row.key.address.city}, ${row.key.address.street})</b><br/>
            <g:each in="${row.value}" var="sale">
                Customer: <b>${sale.customer.name} ${sale.customer.surnames}</b><br/>
                Goods:<br/>

                <g:set var="totalPrice" value="${0.0}"/>
                <g:each in="${sale.goods}" var="good">
                    <g:set var="goodDesc" value="${SearchUtil.getGoodDescription(good.goodId)}"/>
                    ${goodDesc.type} ${goodDesc.manufacturer} ${goodDesc.model} - ${good.qty}. Price(1): ${good.retailPrice.round(2)} C.U.<br/>
                    <g:set var="totalPrice" value="${totalPrice + good.retailPrice}"/>
                </g:each>

                <b>Total price: ${totalPrice.round(2)} C.U.</b><br/><br/>
            </g:each>
            <br/>
        </g:each>

        <br/>

        <g:if test="${size > SearchUtil.LIMIT}">
            <g:each in="${SearchUtil.getPaginateMap(offset, size).sort { it.num }}" var="page">
                <a onclick="document.location = '${createLink(action: "textSearch", params: [offset: page.offset, search: search])}'">
                    ${page.num}
                </a>&nbsp;&nbsp;
            </g:each>
        </g:if>
    </g:if>
</p>
</body>
</html>
