<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:url var="email_url" value="/account/email"/>
<form:form action="${email_url }" method="post" modelAttribute="user" name="email_form" class="account-form">
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
				Email
			</td>
			<td>
				<form:input type="email" path="email" required="required"/>
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
</form:form>