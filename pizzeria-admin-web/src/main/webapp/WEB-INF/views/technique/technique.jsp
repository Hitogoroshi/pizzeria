<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<jsp:include page="../layout/entete.jsp">
	<jsp:param value="Page Lister Pizza" name="title" />
</jsp:include>

<body class="container">
	<h1>Page Technique</h1>
	<h2>Nombre de session active = ${applicationScope.compteurSession}</h2>
	<h2>Mesures de performance</h2>
	<table class="table">
		<tr>
			<td></td>
			<td></td>
		</tr>

		<c:forEach var="entry" items="${applicationScope.PERF_MAP}">
			<tr>
				<td>${entry.key}</td>
				<td><c:forEach var="tempsExec" items="${entry.value}">
					${tempsExec} ms<br>
					</c:forEach></td>

			</tr>
		</c:forEach>

	</table>
	

</body>
</html>