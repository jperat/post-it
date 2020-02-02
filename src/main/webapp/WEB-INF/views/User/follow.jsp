<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:if test="${principalUser.id != user.id}">
		<form action="<c:url value='/follow'/>" method="post" id="follow_form_${user.id}" name='follow_form' profile_id="${user.id }" <c:if test="${principalUser.follows.contains(user.id)}">style="display: none;"</c:if> >
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="hidden" name="id" value="${user.id }"/>
			<button type="submit" class="btn btn-primary">Follow</button>
		</form>
		<form action="<c:url value='/unfollow'/>" method="post" id="unfollow_form_${user.id}" name='unfollow_form' profile_id="${user.id }" <c:if test="${!principalUser.follows.contains(user.id)}">style="display: none;"</c:if> >
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="hidden" name="id" value="${user.id }"/>
			<button type="submit" class="btn btn-primary">Unfollow</button>
		</form>
</c:if>