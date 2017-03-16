<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<jsp:include page="../layout/entete.jsp">
	<jsp:param value="Page Statistiques" name="title" />
</jsp:include>

<body class="container">
	<jsp:include page="../layout/menu.jsp">
		<jsp:param value="Statistiques" name="page" />
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
			<h1>Statistiques</h1>
		</div>
	</div>

	<br>
	
	<div class="row">
		<div class="col-xs-12">
			<h3>Nombre de pizzas vendues</h3>
		</div>
	</div>

	<br>
	<table class="table">
		<tr>
			<th>Id</th>
			<th>Nom</th>
			<th>Prix</th>
			<th>Code</th>
			<th>Catégorie</th>
			<th>Ventes</th>
		</tr>
			<c:forEach var="pizzaV" items="${PizzasParVentes}">
					<tr>
						<td>${pizzaV.pizza.id}</td>
						<td>${pizzaV.pizza.nom}</td>
						<td>${pizzaV.pizza.prix}</td>
						<td>${pizzaV.pizza.code}</td>
						<td>${pizzaV.pizza.categorie.libelle}</td>
						<td>${pizzaV.qteVentes}</td>
					</tr>
			</c:forEach>
	</table>
	
	<br>
	
	<div class="row">
		<div class="col-xs-12">
			<h3>Moyenne des commandes client</h3>
		</div>
	</div>

	<br>
	<table class="table">
		<tr>
			<th>Id</th>
			<th>Nom</th>
			<th>Prénom</th>
			<th>Email</th>
			<th>Ventes</th>
		</tr>
			<c:forEach var="clientC" items="${ClientsParCommandes}">
					<tr>
						<td>${clientC.client.id}</td>
						<td>${clientC.client.nom}</td>
						<td>${clientC.client.prenom}</td>
						<td>${clientC.client.email}</td>
						<td>${clientC.valMoyenne}</td>
					</tr>
			</c:forEach>
	</table>
	
	<br>
	
	<div class="row">
		<div class="col-xs-12">
			<h3>Livraisons effectuées pendant le mois</h3>
		</div>
	</div>

	<br>
	<table class="table">
		<tr>
			<th>Id</th>
			<th>Nom</th>
			<th>Prénom</th>
			<th>Code</th>
			<th>Livraisons</th>
		</tr>
			<c:forEach var="livreurL" items="${LivreursParCommandes}">
					<tr>
						<td>${livreurL.livreur.id}</td>
						<td>${livreurL.livreur.nom}</td>
						<td>${livreurL.livreur.prenom}</td>
						<td>${livreurL.livreur.code}</td>
						<td>${livreurL.nbCommandes}</td>
					</tr>
			</c:forEach>
	</table>
</body>
</html>