<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Hello</title>
</head>
<body>
    <c:set var="user" value="${SPRING_SECURITY_CONTEXT.authentication.principal}"/>

    <%--logout 버튼--%>
    <sec:authorize access="hasAnyRole('ROLE_USER', 'ROLE_ADMIN')">
        <div class="logout" align="right">
            <c:url var="logoutUrl" value="/j_spring_security_logout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="로그 아웃" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
            <br/>
        </div>
    </sec:authorize>
    <sec:authorize access="isAnonymous()">
        <h2 align="center"><a href="/user/signin">로그인</a><br></h2>
        <h2 align="center"><a href="/user/signup">회원가입</a><br></h2>
        <br>

    </sec:authorize>




    <sec:authorize access="hasRole('ROLE_USER')">
        이 문장은 ROLE_USER 권한을 가진 사람에게만 보입니다.<br/>
    </sec:authorize>

    <sec:authorize access="hasRole('ROLE_ADMIN')">
        이 문장은 ROLE_ADMIN 권한을 가진 사람에게만 보입니다.<br/>
    </sec:authorize>

</body>
</html>
