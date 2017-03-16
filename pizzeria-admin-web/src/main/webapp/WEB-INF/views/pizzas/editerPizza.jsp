<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="util" uri="http://pizzeria.dta/functions"%>
<!DOCTYPE html>
<html>
<jsp:include page="../layout/entete.jsp">
	<jsp:param value="Page Editer Pizza" name="title" />
</jsp:include>

<body class="container">
	<jsp:include page="../layout/menu.jsp">
		<jsp:param value="Pizzas" name="page" />
	</jsp:include>

	<h1>
		<c:out value="${ (pizza != null && pizza.id != null) ? 'Éditer la pizza' : 'Créer une pizza' }" />
	</h1>

	<c:if test="${!empty msgErreur}">
		<div class="alert alert-danger" role="alert">${msgErreur}</div>
	</c:if>

	<div id="alert"></div>

	<c:if test="${pizza != null}">
		<form method="POST">
			<c:if test="${pizza.id != null}">
				<div class="form-group">
					<label for="id">Id :</label> <input type="text" class="form-control" id="id" name="id" value="${pizza.id}" readonly>
				</div>
			</c:if>
			<div class="form-group">
				<label for="nom">Nom :</label> <input type="text" class="form-control" id="nom" name="nom" value="${pizza.nom}" required>
			</div>
			<div class="form-group">
				<label for="urlImage">URL Image :</label> <input type="text" class="form-control" name="urlImage" id="urlImage" value="${pizza.urlImage}" required>
			</div>
			<c:if test="${pizza.code == null}">
				<div class="form-group">
					<label for="code">Code :</label> <input type="text" class="form-control" name="code" id="code" value="${pizza.code}" required>
				</div>
			</c:if>
			<div class="form-group">
				<label for="prix">Prix :</label> <input type="number" min="0" max="1000000" step="0.01" class="form-control" name="prix" id="prix" value="${pizza.prix}" required>
			</div>
			<div class="form-group">
				<label for="categorie">Catégorie :</label> <select name="categorie" class="form-control btn btn-default">
					<option value="VIANDE" <c:if test="${pizza.categorie == 'VIANDE'}">selected</c:if>>Viande</option>
					<option value="SANS_VIANDE" <c:if test="${pizza.categorie == 'SANS_VIANDE'}">selected</c:if>>Sans Viande</option>
					<option value="POISSON" <c:if test="${pizza.categorie == 'POISSON'}">selected</c:if>>Poisson</option>
				</select>
			</div>
			<div class="form-group">
				<div class="col-md-6 col-lg-6">
					<label for="ingredient">Liste des ingrédients dans la pizza :</label>
					<ul id="pizzaIngredient" class="list-group">
						<c:forEach var="ingredients" items="${pizza.ingredients}">
							<li id="ingredient-${ ingredients.id.ingredient.code }" class="list-group-item item-ingredient-pizza">${ ingredients.id.ingredient.nom}<input type="hidden" name="ingredient" value="${ ingredients.id.ingredient.code}"><div class="myQteIng"><label >Quantité :</label><input name ="qteIngredient" type="number" min="0.005" max="1" step="0.005" class="myQteIng" value="${ ingredients.quantiteRequise }"></div></li>
						</c:forEach>
					</ul>
					<p class="jumbotron">
						Cliquer sur un ingrédient pour le <strong>retirer</strong> de la pizza.
					</p>
				</div>
				<div class="col-md-6 col-lg-6">
					<label for="ingredients">Liste de tous les ingrédients :</label>
					<ul id="allIngredient" class="list-group">
						<c:forEach var="ingredients" items="${listeIngredient}">
							<li id="li-${ingredients.code}" onclick="addIngredient('${ingredients.code}', '${util:escapeJS(ingredients.nom)}')" class="list-group-item item-ingredient-pizza">${util:escapeHTML(ingredients.nom)}</li>
						</c:forEach>
					</ul>
					<p class="jumbotron">
						Cliquer sur un ingrédient pour l'<strong>ajouter</strong> à la pizza.
					</p>
					<a class="btn btn-primary" href="<%=request.getContextPath()%>/ingredients/new">Nouvel ingrédient</a>
				</div>
			</div>
			<button type="submit" class="btn btn-success">Valider</button>
			<a href="<c:url value="/pizzas/list"></c:url>" class="btn btn-primary">Retour</a>
		</form>
	</c:if>

	<script type="text/javascript">
		function addIngredient(code, name) {
			var html = '<li id="ingredient-' + code + '" class="list-group-item item-ingredient-pizza">' + name + '<input type="hidden" name="ingredient" value="' + code + '"><div class="myQteIng"><label >Quantité :</label><input name ="qteIngredient" type="number" min="0.005" max="1" value="0.005" step="0.005" class="myQteIng"></div></li>';
			if (!document.getElementById('ingredient-' + code)) {
				$("#pizzaIngredient").append(html);
			} else {
				var modalBody = '<div class="alert alert-danger fade in" role="alert" id="myAlert">' + '<button type="button" class="close" data-dismiss="alert">'
						+ '<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span></button>"Attention ! Cet ingrédient est déjà présent sur cette pizza."</div>'
				$("#alert").append(modalBody);
			}
		}
		document.getElementById('pizzaIngredient').addEventListener('click', function(evt) {
			$("#" + evt.srcElement.attributes[0].nodeValue).remove();
		}, false)
	</script>
</body>
</html>