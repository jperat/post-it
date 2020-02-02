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
			<div id="posts">
			<c:choose>
				<c:when test="${fn:length(users) gt 0}">
					<%@ include file="../User/users.jsp"%>
				</c:when>
				<c:otherwise>
					<div class="alert alert-info">
					  	No result for your request.
					</div>
				</c:otherwise>
			</c:choose>
				
			</div>
		</div>
		<div class="col-md-3"></div>
	</div>
	</jsp:body>
</t:layout>