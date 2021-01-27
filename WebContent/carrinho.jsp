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
				<th>Produto Id</th>
				<th>Descrição</th>
				<th>Quantidade</th>
				<th>Valor por unidade</th>
				<th>Valor</th>
				<th>Deletar</th>
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
					<td><a href="ClienteServlet?acao=deletar&id=${item.produtoId}&carrinhoid=${carrinho.id}&userid=${usuario.id}">
							<img src="resources/img/excluir.jpg" alt="excluir"
							title="Excluir" width="20px" height="20px" />
					</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="ClienteServlet?acao=continuar&id=${carrinho.id}&userid=${usuario.id}">Continuar Comprando</a>
	<a href="ClienteServlet?acao=finalizar&id=${carrinho.id}&userid=${usuario.id}">Finalizar compra</a>
</body>
</html>