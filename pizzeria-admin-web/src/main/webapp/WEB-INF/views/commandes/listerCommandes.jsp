<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<jsp:include page="../layout/entete.jsp">
	<jsp:param value="Page Lister Commandes" name="title" />
</jsp:include>

<body class="container">
	<jsp:include page="../layout/menu.jsp">
		<jsp:param value="Commandes" name="page" />
	</jsp:include>

	<div class="row">
		<c:if test="${msg != null}">
			<div class="alert alert-danger" role="alert">${msg}</div>
		</c:if>
	</div>

	<div class="row">
		<div class="col-xs-12">
			<h1>Liste des commandes</h1>
		</div>
	</div>

	<div class="row">
		<div class="col-xs-4">
			<a class="btn btn-success" href="<%=request.getContextPath()%>/commandes/new">Nouvelle commande</a>
		</div>
	</div>

	<br>

	<table class="table">
		<tr>
			<th>Id</th>
			<th>Numéro</th>
			<th>Statut Paiement</th>
			<th>Statut</th>
			<th>Date</th>
			<th>Livreur</th>
			<th>Client</th>
			<th></th>
		</tr>
		<c:forEach var="commande" items="${listeCommandes}">
			<tr>
				<td>${commande.id}</td>
				<td>${commande.numeroCommande}</td>
				<td>${commande.statutPaiement.libelle}</td>
				<td>${commande.statut.libelle}</td>
				<td><fmt:formatDate value="${commande.dateCommande.time}" type="both" dateStyle="short" timeStyle="short" /></td>
				<td>${commande.livreur.prenom}&nbsp;${commande.livreur.nom}</td>
				<td>${commande.client.prenom}&nbsp;${commande.client.nom}</td>
				<td>
					<div class="btn-group">
						<a href="<c:url value="/commandes/edit?code=${commande.numeroCommande}"/>" class="btn btn-primary">Éditer</a>
						<form method="POST" class="btn-group">
							<input type="hidden" name="code" value="${commande.numeroCommande}"> <input type="hidden" name="action" value="supprimer">
							<button type="submit" class="btn btn-danger">Supprimer</button>
						</form>
					</div>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>