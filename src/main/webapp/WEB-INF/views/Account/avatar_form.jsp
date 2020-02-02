<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:url var="avatar_url" value="/account/avatar"/>
<form action="${avatar_url }" method="post" modelAttribute="user" name="avatar_form" enctype="multipart/form-data">
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
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	<table class="table">
		<tr>
			<td>
				<c:choose>
					<c:when test="${user.avatar != null }">
						 <img class="media-object" src="<c:url value='/ressources/uploads/${user.avatar.name }' />" alt="avatar" style="width: 50px; height: 50px;">
					</c:when>
					<c:otherwise>
						<img class="media-object" src="<c:url value='/ressources/img/avatar.png' />" alt="avatar" style="width: 50px; height: 50px;">
					</c:otherwise>
				</c:choose>
			</td>
			<td>
				<input type="file" id="avatar" name="avatar" class="custom-file-input" placeholder="avatar">
				<span class="custom-file-control"></span>
			</td>
		</tr>
		<tr>
			<td>
			</td>
			<td>
				<button type="submit" class="btn">Change</button>
			</td>
		</tr>
	</table>
</form>