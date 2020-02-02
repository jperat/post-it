<%@page import="com.jperat.postit.model.Post"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.jperat.postit.model.Comment"%>
<%@page import="java.util.List"%>
<%@page import="com.jperat.postit.dao.CommentDaoImpl"%>
<%@page import="com.jperat.postit.dao.CommentDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<c:url var="profileUrl" value="/profile/${post.user.id }">
</c:url>
<div class="panel panel-default" id="post_${post.id }">
	<div class="panel-body">
		<div class="media panel">
			<div class="media-left">
				<a href="${profileUrl }">
				<c:choose>
					<c:when test="${post.user.avatar != null }">
						 <img class="media-object" src="<c:url value='/ressources/uploads/${post.user.avatar.name }' />" alt="${post.user.avatar.originalName }" style="width: 50px; height: 50px;">
					</c:when>
					<c:otherwise>
						<img class="media-object" src="<c:url value='/ressources/img/avatar.png' />" alt="avatar" style="width: 50px; height: 50px;">
					</c:otherwise>
				</c:choose>
				</a>
			</div>
			<div class="media-body">
				<h4 class="media-heading"><a href="${profileUrl }">${post.user.username}</a></h4>
				<p><fmt:formatDate value="${post.date}" pattern="yyyy-MM-dd HH:mm" /><p>
			</div>
			<c:if test="${principalUser.id == post.user.id}">
				<div class="media-right">
					<span class="remove-post" data-id="${post.id}"><i class="glyphicon glyphicon-remove btn btn-light"></i></span>
				</div>
			</c:if>
		</div>
		<c:if test="${post.picture != null }">
			<div class="col-xs-6 col-md-3">
				<a href="#" class="thumbnail"> <img src="<c:url value='/ressources/uploads/${post.picture.name }' />" alt="${post.picture.originalName }">
				</a>
			</div>
		</c:if>
		<p>${post.message }</p>
		<div>
			<div class="pull-left">
			Likes <span class="badge" id="post_like_${post.id }">${post.likes.size()}</span> 
			</div>
			<form class="form-inline" action="<c:url  value="/like" />" method="post" id="like_form_${post.id }" name="like_form" post_id="${post.id }" <c:if test="${post.likes.contains(principalUser.id)}">style="display:none;"</c:if> >
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input type="hidden" name="id" value="${post.id }"/>
				<button type="submit" class="btn btn-primary"><i class="glyphicon glyphicon-thumbs-up"></i></button>
			</form>	
			<form class="form-inline" action="<c:url  value="/unlike" />" method="post" id="unlike_form_${post.id }" name="unlike_form" post_id="${post.id }" <c:if test="${!post.likes.contains(principalUser.id)}">style="display:none;"</c:if> >
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input type="hidden" name="id" value="${post.id }"/>
				<button type="submit" class="btn btn-primary"><i class="glyphicon glyphicon-thumbs-down"></i></button>
			</form>	
		</div>
		<br>
		<%@ include file="../Comment/comment_form.jsp"%>
		<br>
		<div class="comment_list" id="comment_list_${post.id }">
		 	<c:set var="comments" value="${post.comments }" scope="request" /> 
			<%@ include file="../Comment/comments.jsp"%>
		</div>
		
	</div>
</div>