<jsp:useBean id="calcula" class="beans.Usuario" type="beans.Usuario" scope="page" />

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" href="resources/css/index.css" />

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="login">
  <div class="login-triangle"></div>
  
  <h2 class="login-header">Log in</h2>

	
	<form action="LoginServlet" method="post" class="login-container">
		<p><label>Login: </label></p>
		<p><input type="text" id="login" name="login" ></p>
		<p><label>Senha: </label></p>
		<p><input type="password" id="senha" name="senha" ></p>
		<p><input type="submit" value="Logar" ></p>
	</form>
</div>
	
</body>
</html>