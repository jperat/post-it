<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:url var="logoutUrl" value="/logout" />
<c:url var="searchUrl" value="/search" />
<c:url var="profileUrl" value="/profile/${principalUser.id }"/>
<c:url var="accountUrl" value="/account"/>

<div id="navbar" class="navbar-collapse collapse">
	<form class="navbar-form navbar-left" method="get" action="${searchUrl}">
		<input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search" name="q">
		<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
	</form>

	<ul class="navbar-nav mr-auto">
		<li class="nav-item"><a class="btn btn-light" href="${profileUrl}"
			role="button">My Posts</a></li>
		<li class="nav-item"><a class="btn btn-light" href="${accountUrl}"
			role="button">My Account</a></li>
	</ul>
	<form action="${logoutUrl}" method="post"
		class="navbar-form navbar-right">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		<button type="submit" class="btn btn-success">Log out</button>
	</form>
</div>