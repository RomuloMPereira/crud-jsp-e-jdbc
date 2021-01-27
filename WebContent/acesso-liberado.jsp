<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Acesso liberado</title>
</head>
<body>
	<h1>Bem vindo ao sistema!</h1>
	<br>
	<a href="UsuarioServlet?acao=listar"><img alt="Usuário" src="resources/img/usuario.png" width="200px" height="200px"/></a>
	<a href="ProdutoServlet?acao=listar"><img alt="Produto" src="resources/img/product.png" width="200px" height="200px"/></a>
	<a href="CompraServlet?acao=listar"><img alt="Compra" src="resources/img/compra.png" width="200px" height="200px"/></a>
	<a href="index.jsp"><img alt="Sair" src="resources/img/sair.jpg" width="200px" height="200px" /></a>
</body>
</html>