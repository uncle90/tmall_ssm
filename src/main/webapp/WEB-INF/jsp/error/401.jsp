<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%--
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>401 - 未经授权</title>
</head>

<body>
<h2>401 -  未经授权：访问由于凭据无效被拒绝.</h2>
<p><a href="<c:url value="/"/>">返回首页</a></p>
</body>
</html>
