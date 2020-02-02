<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<div class="col-md-4"></div>
	<div class="col-md-4">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Register</h3>
			</div>
			<div class="panel-body">
				<c:url var="registerUrl" value="/register" />
				<c:if test="${errors.size() > 0}">
					<div class="alert alert-danger">
					  <ul>
					  	<c:forEach items="${errors}" var="error">
					  	<li>${error.getDefaultMessage()}</li>
					  	</c:forEach>
					  </ul>
					</div>
				</c:if>
				<form:form class="form-signin" action="${registerUrl}" method="post" modelAttribute="user">
					<form:label path="firstname" class="sr-only" for="registerFirstName">Firstname</form:label>
					<form:input path="firstname" required="required" class="form-control" id="registerFirstName" type="text" placeholder="Fistname"/>
					<form:label path="lastname" class="sr-only" for="registerLastName">Lastname</form:label>
					<form:input path="lastname" required="required" class="form-control" id="registerLastName" type="text" placeholder="Lastname"/>
					<form:label path="username" class="sr-only" for="registerUserName">Username</form:label>
					<form:input path="username" required="required" class="form-control" id="registerUserName" type="text" placeholder="Username"/>
					<form:label path="email" class="sr-only" for="registerEmail">Email</form:label>
					<form:input path="email" required="required" class="form-control" id="registerEmail" type="email" placeholder="Email"/>
<%-- 					<form:errors path="email"/> --%>
					<form:label path="password" class="sr-only" for="registerPassword">Password</form:label>
					<form:password path="password" required="required" class="form-control" id="registerPassword" placeholder="Password" />
					<form:button class="btn btn-lg btn-primary btn-block" type="submit">Register</form:button>
				</form:form>
			</div>
		</div>

	</div>
	<div class="col-md-4"></div>
</div>