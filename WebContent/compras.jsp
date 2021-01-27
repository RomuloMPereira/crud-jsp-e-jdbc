<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/tabela.css" />
<link rel="stylesheet" href="resources/css/cadastro.css" />
</head>
<body>
	<a href="acesso-liberado.jsp">Início</a>
	<a href="index.jsp">Sair</a>
	<table>
		<thead>
			<tr>
				<th>Compra Id</th>
				<th>Nome</th>
				<th>Ver detalhes</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${compras}" var="compra">
				<tr>
					<td><c:out value="${compra.id}"></c:out></td>
					<td><c:out value="${compra.usuarioNome}"></c:out></td>
					<td><a href="CompraServlet?acao=verItens&carrinhoId=${compra.carrinhoId}&userId=${compra.usuarioId}">
							<img src="resources/img/lupa.png" alt="Detalhe" title="Detalhe"
							width="20px" height="20px" />
					</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>