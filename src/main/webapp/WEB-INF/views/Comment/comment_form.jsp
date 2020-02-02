<%@page import="com.jperat.postit.model.Post"%>
<%@page import="com.jperat.postit.model.Comment"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:url var="newCommentUrl" value="/newcomment"/>

<div class="comment_form" id="comment_form_${post.id }">
<c:if test="${errors != null}">
	<div class="alert alert-danger" role="alert">
	  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
	    <span aria-hidden="true">&times;</span>
	  </button>
	  	<c:forEach items="${errors}" var="error">
	  		<p>${error.getDefaultMessage()}</p>
	  	</c:forEach>
	</div>
</c:if>
<c:if test="${success != null}">
	<div class="alert alert-success" role="alert">
	  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
	    <span aria-hidden="true">&times;</span>
	  </button>
	  <p>${success}</p>
	</div>
</c:if>

<form:form class="form" action="${newCommentUrl}" method="post" modelAttribute="comment" id="new_comment_form_${post.id }" name='new_comment_form' post_id="${post.id }">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	<input type="hidden" name="post_id" value="${post.id }" />
	<div class="form-group">
	<form:label path="text" class="sr-only " for="newCommentText_${post.id}">Comment</form:label>
	<form:textarea path="text" class="form-control" id="newCommentText_${post.id }" placeholder="Comment hello" maxlength="255" rows="3"/>
	<form:button class="btn btn-primary">Send</form:button>
	</div>
</form:form>
</div>