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
			<div class="panel panel-default">
				<div class="media panel">
					<div class="media-body">
						<h4 class="media-heading center">Avatar</h4>
					</div>
				</div>
				<div class="panel-body">
					<%@ include file="avatar_form.jsp" %>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="media panel">
					<div class="media-body">
						<h4 class="media-heading center">Identity</h4>
					</div>
				</div>
				<div class="panel-body">
					<%@ include file="identity_form.jsp" %>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="media panel">
					<div class="media-body">
						<h4 class="media-heading center">Email</h4>
					</div>
				</div>
				<div class="panel-body">
					<%@ include file="email_form.jsp" %>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="media panel">
					<div class="media-body">
						<h4 class="media-heading center">Password</h4>
					</div>
				</div>
				<div class="panel-body">
					<%@ include file="password_form.jsp" %>
				</div>
			</div>
		</div>
		<div class="col-md-3"></div>
	</div>
	</jsp:body>
</t:layout>