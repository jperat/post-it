<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:url var="profileUrl" value="/profile">
	<c:param name="id" value="${comment.user.id }"/>
</c:url>
<div class="panel panel-default" id="comment_${comment.id }">
	<div class="panel-body">
		<div class="media panel">
			<div class="media-left">
				<a href="${profileUrl }"> 
					<c:choose>
						<c:when test="${comment.user.avatar != null }">
							<img class="media-object" src="<c:url value='/ressources/uploads/${comment.user.avatar.name }' />" alt="avatar" style="width: 50px; height: 50px;">
						</c:when>
						<c:otherwise>
							<img class="media-object"
								src="<c:url value='/ressources/img/avatar.png' />" alt="avatar"
								style="width: 25px; height: 25px;">
						</c:otherwise>
					</c:choose>
				</a>
			</div>
			<div class="media-body">
				<h4 class="media-heading">
					<a href="${profileUrl }">${comment.user.username}</a> 
				</h4>
				<span>
					<fmt:formatDate value="${comment.date}" pattern="yyyy-MM-dd HH:mm" />
				</span>
			</div>
			<c:if test="${principalUser.id == comment.user.id || comment.post.user.id == principalUser.id}">
				<div class="media-right">
					<span class="remove-comment" data-id="${comment.id }"><i class="glyphicon glyphicon-remove btn btn-light"></i></span>
				</div>
			</c:if>
		</div>
		<p>${comment.text }</p>
	</div>
</div>