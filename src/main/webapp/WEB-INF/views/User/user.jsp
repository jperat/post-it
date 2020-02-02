<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:url var="profileUrl" value="/profile/${user.id }">
</c:url>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="media">
			<div class="media-left">
				<a href="${profileUrl }">
				<c:choose>
					<c:when test="${user.avatar != null }">
						 <img class="media-object" src="<c:url value='/ressources/uploads/${user.avatar.name }' />" alt="avatar" style="width: 50px; height: 50px;">
					</c:when>
					<c:otherwise>
						<img class="media-object" src="<c:url value='/ressources/img/avatar.png' />" alt="avatar">
					</c:otherwise>
				</c:choose>
				</a>
			</div>
			<div class="media-body">
				<h4 class="media-heading"><a href="${profileUrl }">${user.username}</a></h4>
				<p><a href="${profileUrl }">${user.firstname } ${user.lastname }</a></p>
				<p>Followers <span class="badge" id="follow_${user.id}">${user.followers.size() }</span></p>
			</div>
			<div class="media-right">
				<%@ include file="follow.jsp"%>
			</div>
		</div>

	</div>
</div>