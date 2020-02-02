<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<t:layout>
	<jsp:attribute name="nav">
		<%@ include file="../Menu/menu_logged.jsp"%>
	</jsp:attribute>
	<jsp:body>
	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="media">
						<div class="media-left">
							<c:choose>
								<c:when test="${user.avatar != null }">
									 <img class="media-object" src="<c:url value='/ressources/uploads/${user.avatar.name }' />" alt="avatar" style="width: 50px; height: 50px;">
								</c:when>
								<c:otherwise>
									<img class="media-object" src="<c:url value='/ressources/img/avatar.png' />" alt="avatar">
								</c:otherwise>
							</c:choose>
						</div>
						<div class="media-body">
							<h4 class="media-heading">${user.username}</h4>
							<p>${user.firstname } ${user.lastname }</p>
							<p>Followers <span class="badge">${user.followers.size() }</span></p>
						</div>
						<div class="media-right">
							<%@ include file="../User/follow.jsp"%>
						</div>
					</div>
				</div>
			</div>
			<div id="posts">
			<c:choose>
				<c:when test="${fn:length(posts) gt 0}">
					<%@ include file="../Post/posts.jsp"%>
				</c:when>
				<c:otherwise>
					<div class="alert alert-info">
					  	${user.username } has no post.
					</div>
				</c:otherwise>
			</c:choose>
				
			</div>
		</div>
		<div class="col-md-3"></div>
	</div>
	</jsp:body>
</t:layout>