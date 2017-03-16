<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<jsp:include page="../layout/entete.jsp">
	<jsp:param value="Page Historique Emails" name="title" />
</jsp:include>

<body class="container">
	<jsp:include page="../layout/menu.jsp">
		<jsp:param value="Emails" name="page" />
	</jsp:include>

	<div class="row">
		<c:if test="${msg != null}">
			<div class="alert alert-danger" role="alert">${msg}</div>
		</c:if>
		<c:if test="${msg_success != null}">
			<div class="alert alert-success" role="alert">${msg_success}</div>
		</c:if>
	</div>

	<div class="row">
		<div class="col-xs-12">
			<h1>Historique des emails</h1>
		</div>
	</div>

	<br>

	<table class="table">
		<tr>
			<th>Id</th>
			<th>Date</th>
			<th>Expéditeur</th>
			<th>Destinataire</th>
			<th>Objet</th>
			<th>Pizza de la semaine</th>
		</tr>
			<c:forEach var="email" items="${listeEmails}">
					<tr>
						<td>${email.id}</td>
						<td><fmt:formatDate value="${email.date}" type="both" dateStyle="short" timeStyle="short" /></td>
						<td>${email.expediteur}</td>
						<td>${email.destinataire}</td>
						<td>${email.objet}</td>
						<td>${email.pizza}</td>
					</tr>
			</c:forEach>
	</table>
</body>
</html>