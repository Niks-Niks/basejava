<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="main.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="css/Style.css"/>
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
            <jsp:useBean id="type" type="main.model.SectionType"/>
            <jsp:useBean id="sectionValue" type="main.model.AbstractSection"/>
            <h4>${type.title}</h4>
            <c:choose>
                <c:when test="${type == 'PERSONAL' || type == 'OBJECTIVE'}">
                    <input type="text" name="${type}" size="40" value="<%=((TextSection)sectionValue).getText()%>"/>
                </c:when>
                <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                    <textarea name='${type}' cols=60
                              rows=5><%=String.join("\n", ((ListSection) sectionValue).getList()).replace(" ", "\n")%></textarea>
                </c:when>
                <c:when test="${type == 'EDUCATION' || type == 'EXPERIENCE'}">
                    <c:forEach var="organization" items="<%=((OrganizationSection) sectionValue).getList()%>">
                        <dl>
                            <dt>Название:</dt>
                            <dd><input type="text" name="${type}title" size="40"
                                       value="${organization.homePage.homePage}"/></dd>
                        </dl>
                        <dl>
                            <dt>Ссылка:</dt>
                            <dd><input type="text" name="${type}url" size="40" value="${organization.homePage.link}"/>
                            </dd>
                        </dl>
                        <c:forEach var="place" items="${organization.list}">
                            <jsp:useBean id="place" type="main.model.Organization.Place"/>
                            <dl>
                                <dt>Дата начала:</dt>
                                <dd><input type="text" name="${type}start" size="40" value="<%=place.getDateStart()%>"/>
                                </dd>
                            </dl>
                            <dl>
                                <dt>Конечная дата:</dt>
                                <dd><input type="text" name="${type}end" size="40" value="<%=place.getDateEnd()%>"/>
                                </dd>
                            </dl>
                            <dl>
                                <dt>Должность:</dt>
                                <dd><input type="text" name="${type}name" size="40" value="<%=place.getTitle()%>"/></dd>
                            </dl>
                            <dl>
                                <dt>Описание:</dt>
                                <dd><input type="text" name="${type}des" size="40" value="<%=place.getDescription()%>"/>
                                </dd>
                            </dl>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button name="back" type="back" value="back">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>