<%@ tag language="java" pageEncoding="UTF-8"%>
<%@attribute name="head" fragment="true"%>
<%@attribute name="nav" fragment="true"%>
<%@attribute name="footer" fragment="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Post-It</title>
<sec:csrfMetaTags/>
<link href="<c:url value='/ressources/css/bootstrap.min.css' />"
	rel="stylesheet"></link>
<link href="<c:url value='/ressources/css/bootstrap-theme.min.css' />"
	rel="stylesheet"></link>
<link href="<c:url value='/ressources/css/style.css' />"
	rel="stylesheet"></link>
<jsp:invoke fragment="head" />
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="<c:url value='/' />">Post-It</a>
			</div>
			<jsp:invoke fragment="nav" />
		</div>
	</nav>
	<div class="container">
		<jsp:doBody />
	</div>
	<c:url var="followUrl" value="/follow"/>
	<c:url var="unfollowUrl" value="/unfollow"/>
	<script type="text/javascript">
		var followUrl = "${followUrl }";
		var unfollowUrl = "${unfollowUrl }";
	</script>
	<script src="<c:url value='/ressources/js/jquery-3.2.1.min.js' />"></script>
	<script src="<c:url value='/ressources/js/bootstrap.min.js' />"></script>
	<script src="<c:url value='/ressources/js/script.js' />"></script>
	<jsp:invoke fragment="footer" />
</body>
</html>