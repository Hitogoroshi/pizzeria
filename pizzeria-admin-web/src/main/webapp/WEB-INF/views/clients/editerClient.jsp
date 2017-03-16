<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<jsp:include page="../layout/entete.jsp">
	<jsp:param value="Page Editer Client" name="title" />
</jsp:include>

<body class="container">
	<jsp:include page="../layout/menu.jsp">
		<jsp:param value="Clients" name="page" />
	</jsp:include>
	<c:set var="urlToPost" value="${client.id != null ? '/clients/edit':  '/clients/new'}" />

	<h1>
		<c:out value="${ (client != null && client.id != null) ? 'Éditer le client' : 'Créer un client' }" />
	</h1>

	<c:if test="${client != null}">
		<form method="POST" action="<c:url value="${urlToPost}"></c:url>">
			<c:if test="${client.id != null}">
				<div class="form-group">
					<label for="id">Id :</label> <input type="text" class="form-control" id="id" name="id" value="${client.id}" readonly>
				</div>
			</c:if>
			<div class="form-group">
				<label for="nom">Nom :</label> <input type="text" class="form-control" id="nom" name="nom" value="${client.nom}" required>
			</div>
			<div class="form-group">
				<label for="prenom">Prénom :</label> <input type="text" class="form-control" name="prenom" id="prenom" value="${client.prenom}" required>
			</div>
			<div class="form-group">
				<label for="email">Email :</label> <input type="email" class="form-control" name="email" id="email" value="${client.email}" required>
			</div>
			<c:if test="${!empty msgErreur}">
				<div class="alert alert-danger" role="alert">${msgErreur}</div>
			</c:if>
			<div class="form-group">
				<label for="telephone">Téléphone :</label> <input type="tel" pattern="\d*" class="form-control" name="telephone" id="telephone" value="${client.telephone}" required>
			</div>
			<div class="form-group">
				<label for="abonne">S'abonner à la newsletter ?</label> <input type="checkbox" name="abonne" id="abonne" ${ client.abonne ? 'checked' : '' }>
			</div>
			<div class="form-group">
				<label for="adresse">Adresse :</label>
				<textarea rows="10" cols="50" class="form-control" name="adresse" id="adresse" required><c:out value="${client.adresse}"></c:out></textarea>
			</div>
			<input type="hidden" name="oldEmail" value="${client.email}" />
			<button type="submit" class="btn btn-success">Valider</button>
			<a href="<c:url value="/clients/list"></c:url>" class="btn btn-primary">Retour</a>
		</form>
	</c:if>
</body>
</html>