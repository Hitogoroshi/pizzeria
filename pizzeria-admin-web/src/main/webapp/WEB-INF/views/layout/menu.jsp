<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<nav class="navbar navbar-default">
	<div class="container-fluid row">
		<div class="navbar-header col-xs-2 ">
			<span class="navbar-brand" href="#">The Best Pizzeria</span>
		</div>
		<ul class="nav navbar-nav col-xs-8 ">
			<li ${ ('Commandes' == param.page) ? 'class="active"' : '' }><a href="<%=request.getContextPath()%>/commandes/list">Commandes</a></li>
			<li ${ ('Pizzas' == param.page) ? 'class="active"' : '' }><a href="<%=request.getContextPath()%>/pizzas/list/active">Pizzas</a></li>
			<li ${ ('Ingredients' == param.page) ? 'class="active"' : '' }><a href="<%=request.getContextPath()%>/ingredients/list/active">Ingrédients</a></li>
			<li ${ ('Clients' == param.page) ? 'class="active"' : '' }><a href="<%=request.getContextPath()%>/clients/list/active">Clients</a></li>
			<li ${ ('Livreurs' == param.page) ? 'class="active"' : '' }><a href="<%=request.getContextPath()%>/livreurs/list/active">Livreurs</a></li>
			<%-- <li ${ ('Statistiques' == param.page) ? 'class="active"' : '' }><a href="<%=request.getContextPath()%>/statistiques">Statistiques</a></li> --%>
			<li ${ ('Utilisateurs' == param.page) ? 'class="active"' : '' }><a href="<%=request.getContextPath()%>/utilisateurs/list">Utilisateurs</a></li>
			<li ${ ('Newsletter' == param.page) ? 'class="active"' : '' }><a href="<%=request.getContextPath()%>/newsletter">Newsletter</a></li>
			<li ${ ('Historique' == param.page) ? 'class="active"' : '' }><a href="<%=request.getContextPath()%>/historique">Historique</a></li>
			<li ${ ('Statistiques' == param.page) ? 'class="active"' : '' }><a href="<%=request.getContextPath()%>/statistiques">Statistiques</a></li>
		</ul>
		<div class="navbar-text navbar-right col-xs-2">
			${sessionScope.auth_email} <a id="logout" href="<%=request.getContextPath()%>/logout" class="navbar-link"> <span class="glyphicon glyphicon-log-out"></span></a>
		</div>
	</div>
</nav>
<script type="text/javascript">
	document.getElementById('logout').addEventListener('click', function(evt) {
		$.ajax({
			url : '<%=request.getContextPath()%>/login',
			type : 'DELETE'
		});
	}, false)
</script>