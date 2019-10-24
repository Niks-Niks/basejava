<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="javax.xml.soap.Text" %>
<%@ page import="main.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/Style.css">
    <jsp:useBean id="resume" type="main.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <h1>Резюме: <span>${resume.fullName}</span>, редактировать</h1>
        <input type="hidden" name="uuid" value="${resume.uuid}">
        Имя:
        <input type="text" name="fullName" size=50 value="${resume.fullName}">

        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="sectionValue" value="${resume.getSection(type)}"/>
            <jsp:useBean id="type"  type="main.model.SectionType"/>
<%--            <jsp:useBean id="sectionValue"  type="main.model.AbstractSection"/>--%>
            <h4>${type.title}</h4>
            <c:choose>
                <c:when test="${type == 'PERSONAL'}">
                    <input type="text" name="${type}" size="40" value=
                            "<%=((TextSection) resume.getSection(SectionType.PERSONAL)).getText()%>"/>
                </c:when>
                <c:when test="${type == 'OBJECTIVE'}">
                    <input type="text" name="${type}" size="40" value="<%=((TextSection) resume.getSection(type)).getText()%>"/>
                </c:when>
                <c:when test="${type == 'ACHIEVEMENT'}">
<%--                    <textarea name='${type.title}' cols=60 rows=5><c:if test="${resume.getSection(type) != null}"><%=((ListSection) resume.getSection(type)).getList()%></c:if></textarea>--%>
                </c:when>
                <c:when test="${type == 'QUALIFICATIONS'}">
<%--                    <textarea name='${type.title}' cols=60 rows=5><c:if test="${resume.getSection(type) != null}"><%=((ListSection) resume.getSection(type)).getList()%></c:if></textarea>--%>
                </c:when>
                <c:when test="${type == 'EDUCATION'}">

                </c:when>
                <c:when test="${type == 'EXPERIENCE'}">

                </c:when>

            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>