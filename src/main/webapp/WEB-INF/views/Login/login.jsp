<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:layout>
	<jsp:attribute name="nav">
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
					<c:url var="registerUrl" value="/register" />
	            <li><a href="${registerUrl}">Register</a></li>
	          </ul>
		</div>
	</jsp:attribute>
	<jsp:body>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-4">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Sign in</h3>
				</div>
				<div class="panel-body">
					<c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
						<div class="alert alert-danger">
						  <ul>
						  	${SPRING_SECURITY_LAST_EXCEPTION.message}
						  </ul>
						</div>
					</c:if>
					<c:url var="loginUrl" value="/login" />
					<form class="form-signin" action="${login}" method="post">
						<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
						<label for="loginEmail" class="sr-only">Email</label> 
						<input type="email" name="email" id="loginEmail" class="form-control" placeholder="Email" required="required"> 
						<label for="loginPassword" class="sr-only">Password</label> 
						<input type="password" name="password" id="loginPassword" class="form-control"  placeholder="Password" required="required">
						<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
					</form>
					<c:url var="forgotUrl" value="/forgotpassword" />
					<p><a href="${forgotUrl}">Forgot password</a></p>
				</div>
			</div>

		</div>
		<div class="col-md-4"></div>
	</div>
	</jsp:body>
</t:layout>