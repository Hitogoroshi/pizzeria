<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<jsp:include page="../layout/entete.jsp">
	<jsp:param value="Page Lister Pizza" name="title" />
</jsp:include>

<body class="container">
	<jsp:include page="../layout/menu.jsp">
		<jsp:param value="Pizzas" name="page" />
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
			<h1>Liste des pizzas - ${active}</h1>
		</div>
	</div>

	<div class="row">
		<div class="col-xs-4">
			<a class="btn btn-success" href="<%=request.getContextPath()%>/pizzas/new">Nouvelle pizza</a>
		</div>
		<div class="col-xs-8 btn-group">
			<a href="<%=request.getContextPath()%>/pizzas/list/active" class="btn btn-default">Actives</a>
			<a href="<%=request.getContextPath()%>/pizzas/list/inactive" class="btn btn-default">Inactives</a>
			<a href="<%=request.getContextPath()%>/pizzas/list" class="btn btn-default">Toutes</a>
		</div>
	</div>

	<br>

	<table class="table">
		<tr>
			<th>Image</th>
			<th>Id</th>
			<th>Nom</th>
			<th>Prix</th>
			<th>Code</th>
			<th>Catégorie</th>
			<th>Ingrédients</th>
			<th></th>
		</tr>

		<c:if test="${active == 'Actives' || active == 'Toutes'}">
			<c:forEach var="pizza" items="${listePizzas}">
				<c:if test="${pizza.actif}">
					<tr>
						<td><img src="<c:url value="${pizza.urlImage}"/>" style="max-width: 150px; max-height: 120px"></td>
						<td>${pizza.id}</td>
						<td>${pizza.nom}</td>
						<td>${pizza.prix}&nbsp;€</td>
						<td>${pizza.code}</td>
						<td>${pizza.categorie.libelle}</td>
						<td>
							<ul>
								<c:forEach var="ingredient" items="${pizza.ingredients}">
									<li>${ingredient.id.ingredient.nom}</li>
								</c:forEach>
							</ul>
						</td>
						<td>
							<div class="btn-group">
								<a href="<c:url value="/pizzas/edit?code=${pizza.code}"/>" class="btn btn-primary">Éditer</a>
								<form method="POST" class="btn-group">
									<input type="hidden" name="code" value="${pizza.code}">
									<input type="hidden" name="action" value="toggle">
									<button type="submit" class="btn btn-warning">Désactiver</button>
								</form>
							</div>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</c:if>
		<c:if test="${active == 'Inactives' || active == 'Toutes'}">
			<c:forEach var="pizza" items="${listePizzas}">
				<c:if test="${!pizza.actif}">
					<tr>
						<td><img src="<c:url value="${pizza.urlImage}"/>" style="max-width: 150px; max-height: 120px"></td>
						<td>${pizza.id}</td>
						<td>${pizza.nom}</td>
						<td>${pizza.prix}&nbsp;€</td>
						<td>${pizza.code}</td>
						<td>${pizza.categorie.libelle}</td>
						<td>
							<ul>
								<c:forEach var="ingredient" items="${pizza.ingredients}">
									<li>${ingredient.id.ingredient.nom}</li>
								</c:forEach>
							</ul>
						</td>
						<td>
							<div class="btn-group">
								<a href="<c:url value="/pizzas/edit?code=${pizza.code}"/>" class="btn btn-primary">Éditer</a>
								<form method="POST" class="btn-group">
									<input type="hidden" name="code" value="${pizza.code}">
									<input type="hidden" name="action" value="toggle">
									<button type="submit" class="btn btn-success">Réactiver</button>
								</form>
								<form method="POST" class="btn-group">
									<input type="hidden" name="code" value="${pizza.code}">
									<input type="hidden" name="action" value="supprimer">
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