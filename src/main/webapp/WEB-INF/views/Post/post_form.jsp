<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:url var="newPostUrl" value="/newpost"/>
<div class="panel panel-default" id="new_post">
	<div class="panel-body">
		<c:if test="${errors != null}">
			<div class="alert alert-danger" role="alert">
			  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
			    <span aria-hidden="true">&times;</span>
			  </button>
  			  <ul>
			  	<c:forEach items="${errors}" var="error">
			  	<li>${error.getDefaultMessage()}</li>
			  	</c:forEach>
			  </ul>
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
		<form:form action="${newPostUrl}" method="post" modelAttribute="post" id="new_post_form" name='new_post_form' enctype="multipart/form-data">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"  id="new-post-token"/>
			<div class="form-group">
				<form:label path="message" class="sr-only" for="newPostMessage">Message</form:label>
				<form:textarea path="message" class="form-control" id="newPostMessage" placeholder="Message" maxlength="255"/>
			</div>
			<div class="form-group form-inline">
				Image
				<label class="custom-file">
					<input type="file" id="image" name="image" class="custom-file-input" placeholder="Image">
  					<span class="custom-file-control"></span>	
				</label>
				<form:button class="btn btn-primary pull-right btn-xs">Post-It</form:button>
			</div>
		</form:form>
	</div>
</div>