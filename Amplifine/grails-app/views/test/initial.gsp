<%@ page import="amplifine.Address" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Hello!</title>
</head>

<body>
    <h1>Hello, ${records.name} ${records.surname}!</h1>
    <g:if test="${records.address}">
        <g:set var="fullAddress" value="${Address.get(records.address)}"/>
        <h2>${fullAddress.city}, ${fullAddress.street}</h2>
    </g:if>
</body>
</html>