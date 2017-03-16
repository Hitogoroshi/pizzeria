<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<jsp:include page="../layout/entete.jsp">
	<jsp:param value="Page Lister Utilisateurs" name="title" />
</jsp:include>

<body class="container">
	<jsp:include page="../layout/menu.jsp">
		<jsp:param value="Utilisateurs" name="page" />
	</jsp:include>

	<div class="row">
		<c:if test="${msg != null}">
			<div class="alert alert-danger" role="alert">${msg}</div>
		</c:if>
	</div>

	<div class="row">
		<div class="col-xs-12">
			<h1>Liste des utilisateurs</h1>
		</div>
	</div>

	<div class="row">
		<div class="col-xs-4">
			<a class="btn btn-success" href="<%=request.getContextPath()%>/clients/new">Nouvel utilisateur</a>
		</div>
	</div>

	<br>

	<table class="table">
		<tr>
			<th>Id</th>
			<th>Nom</th>
			<th>Prénom</th>
			<th>Email</th>
			<th></th>
		</tr>
		<c:forEach var="utilisateur" items="${listeUtilisateurs}">
			<tr>
				<td>${utilisateur.id}</td>
				<td>${utilisateur.nom}</td>
				<td>${utilisateur.prenom}</td>
				<td>${utilisateur.email}</td>
				<td>
					<div class="btn-group">
						<a href="<c:url value="/utilisateurs/edit?email=${utilisateur.email}"/>" class="btn btn-primary">Éditer</a>
						<form method="POST" class="btn-group">
							<input type="hidden" name="email" value="${utilisateur.email}"> <input type="hidden" name="action" value="supprimer">
							<button type="submit" class="btn btn-danger">Supprimer</button>
						</form>
					</div>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>