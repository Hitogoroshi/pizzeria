<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<jsp:include page="../layout/entete.jsp">
	<jsp:param value="Page Lister Clients" name="title" />
</jsp:include>

<body class="container">
	<jsp:include page="../layout/menu.jsp">
		<jsp:param value="Clients" name="page" />
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
			<h1>Liste des clients - ${active}</h1>
		</div>
	</div>

	<div class="row">
		<div class="col-xs-4">
			<a class="btn btn-success" href="<%=request.getContextPath()%>/clients/new">Nouveau Client</a>
		</div>
		<div class="col-xs-8 btn-group">
			<a href="<%=request.getContextPath()%>/clients/list/active" class="btn btn-default">Actives</a>
			<a href="<%=request.getContextPath()%>/clients/list/inactive" class="btn btn-default">Inactives</a>
			<a href="<%=request.getContextPath()%>/clients/list" class="btn btn-default">Toutes</a>
		</div>
	</div>

	<br>

	<table class="table">
		<tr>
			<th>Id</th>
			<th>Dernière modification</th>
			<th>Nom</th>
			<th>Prénom</th>
			<th>Email</th>
			<th>Téléphone</th>
			<th>Abonné</th>
			<th>Adresse</th>
			<th></th>
		</tr>
		<c:if test="${active == 'Actifs' || active == 'Tous'}">
			<c:forEach var="client" items="${listeClients}">
				<c:if test="${client.actif}">
					<tr>
						<td>${client.id}</td>
						<td>${client.dateDerniereModification}</td>
						<td>${client.nom}</td>
						<td>${client.prenom}</td>
						<td>${client.email}</td>
						<td>${client.telephone}</td>
						<td class="text-center"><input type="checkbox" name="abonne" ${ client.abonne ? 'checked' : '' } disabled></td>
						<td>${client.adresse}</td>
						<td>
							<div class="btn-group">
								<a href="<c:url value="/clients/edit?email=${client.email}"/>" class="btn btn-primary">Éditer</a>
								<form method="POST" class="btn-group">
									<input type="hidden" name="email" value="${client.email}"> <input type="hidden" name="action" value="toggle">
									<button type="submit" class="btn btn-warning">Désactiver</button>
								</form>
							</div>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</c:if>
		<c:if test="${active == 'Inactifs' || active == 'Tous'}">
			<c:forEach var="client" items="${listeClients}">
				<c:if test="${!client.actif}">
					<tr>
						<td>${client.id}</td>
						<td>${client.dateDerniereModification}</td>
						<td>${client.nom}</td>
						<td>${client.prenom}</td>
						<td>${client.email}</td>
						<td>${client.telephone}</td>
						<td class="text-center"><input type="checkbox" name="abonne" ${ client.abonne ? 'checked' : '' } disabled></td>
						<td>${client.adresse}</td>
						<td>
							<div class="btn-group">
								<a href="<c:url value="/clients/edit?email=${client.email}"/>" class="btn btn-primary">Éditer</a>
								<form method="POST" class="btn-group">
									<input type="hidden" name="email" value="${client.email}"> <input type="hidden" name="action" value="toggle">
									<button type="submit" class="btn btn-success">Réactiver</button>
								</form>
								<form method="POST" class="btn-group">
									<input type="hidden" name="email" value="${client.email}"> <input type="hidden" name="action" value="supprimer">
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