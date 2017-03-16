<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<jsp:include page="../layout/entete.jsp">
	<jsp:param value="Page news letter" name="title" />
</jsp:include>

<body class="container">
	<jsp:include page="../layout/menu.jsp">
		<jsp:param value="newsletter" name="page" />
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
			<h1>Page de gestion de la newsletter</h1>
		</div>
	</div>

	<form method="POST" action="<c:url value="/newsletter"></c:url>">
		<div class="form-group">
			<label for="pizzaPromo">Nom de la pizza en promo :</label> <input type="text" class="form-control" id="pizzaPromo" name="pizzaPromo" value="${newsletter.pizzaPromo}">
		</div>
		<button type="submit" class="btn btn-primary">Envoyer la newsletter</button>
	</form>
</body>
</html>