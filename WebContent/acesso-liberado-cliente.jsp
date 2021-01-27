<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Área do cliente</title>
<link rel="stylesheet" href="resources/css/tabela.css" />
<link rel="stylesheet" href="resources/css/cadastro.css" />
</head>
<body>
	<h1>Bem vindo! Escolha o produto e a quantidade...</h1>
	<br>
	<table>
		<thead>
			<tr>
				<th>Id</th>
				<th>Descrição</th>
				<th>Valor</th>
				<th>Adicionar</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${produtos}" var="produto">
				<tr>
					<td><c:out value="${produto.id}"></c:out></td>
					<td><c:out value="${produto.nome}"></c:out></td>
					<td><c:out value="${produto.valor}"></c:out></td>
					<td><a href="ClienteServlet?acao=adicionar&id=${produto.id}&userid=${usuario.id}">
							<img src="resources/img/carrinho-compras.png" alt="adicionar" title="Adicionar"
							width="20px" height="20px" />
					</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<a href="ClienteServlet?acao=listarCarrinho&id=${carrinho.id}&userid=${usuario.id}">Carrinho de compras</a>
</body>
</html>