<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<jsp:include page="../layout/entete.jsp">
	<jsp:param value="Page Lister Livreurs" name="title" />
</jsp:include>

<body class="container">
	<jsp:include page="../layout/menu.jsp">
		<jsp:param value="Livreurs" name="page" />
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
			<h1>Liste des livreurs - ${active}</h1>
		</div>
	</div>

	<div class="row">
		<div class="col-xs-4">
			<a class="btn btn-success" href="<%=request.getContextPath()%>/livreurs/new">Nouveau livreur</a>
		</div>
		<div class="col-xs-8 btn-group">
			<a href="<%=request.getContextPath()%>/livreurs/list/active" class="btn btn-default">Actifs</a>
			<a href="<%=request.getContextPath()%>/livreurs/list/inactive" class="btn btn-default">Inactifs</a>
			<a href="<%=request.getContextPath()%>/livreurs/list" class="btn btn-default">Tous</a>
		</div>
	</div>

	<br>

	<table class="table">
		<tr>
			<th>Id</th>
			<th>Code</th>
			<th>Nom</th>
			<th>Prénom</th>
			<th></th>
			<th></th>
			<th></th>
		</tr>
		<c:if test="${active == 'Actifs' || active == 'Tous'}">
			<c:forEach var="livreur" items="${listeLivreurs}">
				<c:if test="${livreur.actif}">
					<tr>
						<td>${livreur.id}</td>
						<td>${livreur.code}</td>
						<td>${livreur.nom}</td>
						<td>${livreur.prenom}</td>
						<td>
							<div class="btn-group">
								<a href="<c:url value="/livreurs/edit?id=${livreur.id}"/>" class="btn btn-primary">Éditer</a>
								<form method="POST" class="btn-group">
									<input type="hidden" name="id" value="${livreur.id}"> <input type="hidden" name="action" value="toggle">
									<button type="submit" class="btn btn-warning">Désactiver</button>
								</form>
							</div>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</c:if>
		<c:if test="${active ==  'Inactifs' || active == 'Tous'}">
			<c:forEach var="livreur" items="${listeLivreurs}">
				<c:if test="${!livreur.actif}">
					<tr>
						<td>${livreur.id}</td>
						<td>${livreur.code}</td>
						<td>${livreur.nom}</td>
						<td>${livreur.prenom}</td>
						<td>
							<div class="btn-group">
								<a href="<c:url value="/livreurs/edit?id=${livreur.id}"/>" class="btn btn-primary">Éditer</a>
								<form method="POST" class="btn-group">
									<input type="hidden" name="id" value="${livreur.id}"> <input type="hidden" name="action" value="toggle">
									<button type="submit" class="btn btn-success">Réactiver</button>
								</form>
								<form method="POST" class="btn-group">
									<input type="hidden" name="id" value="${livreur.id}"> <input type="hidden" name="action" value="supprimer">
									<button type="submit" class="btn btn-danger">Supprimer</button>
								</form>
							</div>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</c:if>
	</table>
</body>
</html>