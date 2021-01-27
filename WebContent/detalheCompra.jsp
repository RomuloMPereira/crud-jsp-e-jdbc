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
<table>
		<thead>
			<tr>
				<th>Nome</th>
				<th>Telefone</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><c:out value="${usuario.nome}"></c:out></td>
				<td><c:out value="${usuario.telefone}"></c:out></td>
			</tr>
		</tbody>
	</table>
	<br>
	<br>
	<table>
		<thead>
			<tr>
				<th>Produto Id</th>
				<th>Descrição</th>
				<th>Quantidade</th>
				<th>Valor por unidade</th>
				<th>Valor</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itens}" var="item">
				<tr>
					<td><c:out value="${item.produtoId}"></c:out></td>
					<td><c:out value="${item.produtoDescricao}"></c:out></td>
					<td><c:out value="${item.quantidade}"></c:out></td>
					<td><c:out value="${item.valorProduto}"></c:out></td>
					<td><c:out value="${item.valor}"></c:out></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="CompraServlet?acao=listar">Voltar</a>
</body>
</html>