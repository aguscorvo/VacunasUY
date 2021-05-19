<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nodos periféricos</title>
</head>
<body>

	<h1>Simulador de nodos periféricos</h1>
	
	<h2>Vacunatorios</h2>

	<table border="1">
	    <tr>
	        <th>ID</th>
	        <th>Nombre</th>
	        <th>Dirección</th>
	        <th>Clave</th>
	        <th>Acciones</th>
	    </tr>
	    <c:forEach var="vacunatorio" items="${vacunatorios}">
	        <tr>
	            <td>${vacunatorio.id}</td>
	            <td>${vacunatorio.nombre}</td>
	            <td>${vacunatorio.direccion}</td>
	            <td>${vacunatorio.clave}</td>
	            <td>
	            	<a href="obtenerVacunadores/<c:out value='${vacunatorio.id}' />">Recibir vacunadores</a>
					<a href="eliminar?id=<c:out value='${vacunatorio.id}' />">Enviar reporte agenda</a>
				</td>
	        </tr>
	    </c:forEach>
	</table>
	
	<br>
	<h2>Transportistas</h2>

</body>
</html>