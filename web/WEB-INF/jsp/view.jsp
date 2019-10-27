<%@ page import="main.model.ListSection" %>
<%@ page import="main.model.OrganizationSection" %>
<%@ page import="main.model.TextSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit">Edit</a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry" type="java.util.Map.Entry<main.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>

        <c:forEach var="section" items="${resume.sections}">
            <jsp:useBean id="section" type="java.util.Map.Entry<main.model.SectionType, main.model.AbstractSection>"/>
            <c:set var="type" value="${section.key}"/>
            <c:set var="sectionValue" value="${section.value}"/>
            <jsp:useBean id="type" type="main.model.SectionType"/>
            <jsp:useBean id="sectionValue" type="main.model.AbstractSection"/>
        <c:choose>
        <c:when test="${type == 'PERSONAL' || type == 'OBJECTIVE'}">
    <dl>
        <dt><h2><dt>${type.title}</dt></h2></dt>
        <dd><%=((TextSection) sectionValue).getText()%>
        </dd>
    </dl>
    </c:when>
    <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
        <dl>
            <dt><h2><dt>${type.title}</dt></h2></dt>
            <dd><%=((ListSection) sectionValue).getList()%>
            </dd>
        </dl>
    </c:when>
    <c:when test="${type == 'EDUCATION' || type == 'EXPERIENCE'}">
        <c:forEach var="organization" items="<%=((OrganizationSection) sectionValue).getList()%>">
            <dl>
                <h2>
                    <dt>${type.title}</dt>
                </h2>
            </dl>
            <dl>
                <dt>Название:</dt>
                <dd>${organization.homePage.homePage}</dd>
            </dl>
            <dl>
                <dt>Ссылка:</dt>
                <dd>${organization.homePage.link}</dd>
            </dl>
            <c:forEach var="place" items="${organization.list}">
                <jsp:useBean id="place" type="main.model.Organization.Place"/>
                <dl>
                    <dt>Дата начала:</dt>
                    <dd><%=place.getDateStart()%>
                    </dd>
                </dl>
                <dl>
                    <dt>Конечная дата:</dt>
                    <dd><%=place.getDateEnd()%>
                    </dd>
                </dl>
                <dl>
                    <dt>Должность:</dt>
                    <dd><%=place.getTitle()%>
                    </dd>
                </dl>
                <dl>
                    <dt class="margin">Описание:</dt>
                    <dd><%=place.getDescription()%>
                    </dd>
                </dl>
            </c:forEach>
        </c:forEach>
    </c:when>

    </c:choose>


    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>