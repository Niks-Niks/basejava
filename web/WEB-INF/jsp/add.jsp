<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="javax.xml.soap.Text" %>
<%@ page import="main.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="css/Style.css"/>
    <jsp:useBean id="resume" type="main.model.Resume" scope="request"/>
    <title>Add new resume</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <h1>Create new resume</h1>
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50></dd>
        </dl>

        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}"><br/></dd>
            </dl>
        </c:forEach>

        <h3>Секции:</h3>

        <c:forEach var="type" items="<%=SectionType.values()%>">
            <h4>${type.title}</h4>
            <%--            <c:set var="section" value="${resume.getSection(type)}"/>--%>
            <jsp:useBean id="type" type="main.model.SectionType"/>
            <c:choose>
                <c:when test="${type == 'PERSONAL'}">
                    <input type="text" name="${type}" size="40"/>
                </c:when>
                <c:when test="${type == 'OBJECTIVE'}">
                    <input type="text" name="${type}" size="40"/>
                </c:when>
                <c:when test="${type == 'ACHIEVEMENT'}">
                    <textarea name='${type}' cols=60 rows=5></textarea>
                </c:when>
                <c:when test="${type == 'QUALIFICATIONS'}">
                    <textarea name='${type}' cols=60 rows=5></textarea>
                </c:when>
                <c:when test="${type == 'EDUCATION' || type == 'EXPERIENCE'}">
                    <dl>
                        <dt>Название:</dt>
                        <dd><input type="text" name="${type}title" size="40"/></dd>
                    </dl>
                    <dl>
                        <dt>Ссылка:</dt>
                        <dd><input type="text" name="${type}url" size="40"/></dd>
                    </dl>
                    <dl>
                        <dt>Дата начала:</dt>
                        <dd><input type="text" name="${type}start" size="40" placeholder="yyyy/mm/dd"/></dd>
                    </dl>
                    <dl>
                        <dt>Конечная дата:</dt>
                        <dd><input type="text" name="${type}end" size="40" placeholder="yyyy/mm/dd"/></dd>
                    </dl>
                    <dl>
                        <dt>Должность:</dt>
                        <dd><input type="text" name="${type}name" size="40"/></dd>
                    </dl>
                    <dl>
                        <dt>Описание:</dt>
                        <dd><input type="text" name="${type}des" size="40"/></dd>
                    </dl>
<%--                    <button class="addElse">Добавить еще должность</button>--%>
                </c:when>
<%--                <button class="addE">Добавить еще <%=type%>></button>--%>
            </c:choose>
        </c:forEach>
        <br/>
        <hr/>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>