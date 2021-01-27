<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cadastro de produto</title>
<link rel="stylesheet" href="resources/css/tabela.css" />
<link rel="stylesheet" href="resources/css/cadastro.css" />
</head>
<body>
		<a href="acesso-liberado.jsp">Início</a>
		<a href="index.jsp">Sair</a>
		<h2>Cadastro de produto</h2>
		
		<h3 style="color: blue;">${msg}</h3>

		<form action="ProdutoServlet" method="post" id="formUser" onclick="return validarCampos() ? true : false;">

			<div>
			<label>Código:</label> 
			<div><input type="text" id="id" name="id" value="${produto.id}" readonly="readonly" /></div>
			</div>
			
			<div> 
			<label>Nome:</label> 
			<div><input type="text" id="nome" name="nome" value="${produto.nome}" /></div>
			</div>
			
			<div>
			<label>Quantidade:</label>
			<div><input type="text" id="quantidade" name="quantidade" value="${produto.quantidade}" /></div>
			</div>

			<div>
			<label>Valor R$:</label> 
			<div><input type="text" id="valor" name="valor" value="${produto.valor}" /></div>
			</div>
			
			<div>
			<div><input name="submit" type="submit" value="salvar" onclick="document.getElementById('formUser').action='ProdutoServlet?acao=salvar'"></div>
			</div>
			<div> 
			<div><input name="submit" type="submit" value="cancelar" onclick="document.getElementById('formUser').action='ProdutoServlet?acao=cancelar'"></div>
			</div>
			<br>
			<br>
		
		</form>

			<table>
				<thead>
					<tr>
						<th>Id</th>
						<th>Nome</th>
						<th>Quantidade</th>
						<th>Valor</th>
						<th>Editar</th>
						<th>Deletar</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${produtos}" var="produto">
						<tr>
							<td><c:out value="${produto.id}"></c:out></td>
							<td><c:out value="${produto.nome}"></c:out></td>
							<td><c:out value="${produto.quantidade}"></c:out></td>
							<td><c:out value="${produto.valor}"></c:out></td>
							<td><a href="ProdutoServlet?acao=editar&id=${produto.id}">
									<img src="resources/img/editar.png" alt="editar" title="Editar"
									width="20px" height="20px" />
							</a></td>
							<td><a href="ProdutoServlet?acao=deletar&id=${produto.id}"> 
							<img src="resources/img/excluir.jpg" alt="excluir" title="Excluir" width="20px" height="20px" />
							</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
</body>

<script type="text/javascript">

	function validarCampos(){
		var nome = document.getElementById("nome").value;
		var qtd = document.getElementById("quantidade").value;
		var valor = document.getElementById("valor").value;
		
		if(nome == ''){
			alert('Preencha o nome');
			document.getElementById("nome").focus();
			return false;
		} else if(qtd == ''){
			alert('Preencha a quantidade');
			document.getElementById("quantidade").focus();
			return false;
		} else if(qtd < 0){
			alert('Quantidade deve ser maior ou igual a zero');
			document.getElementById("quantidade").focus();
			return false;
		} else if(valor == ''){
			alert('Preencha o valor');
			document.getElementById("valor").focus();
			return false;
		} else if(valor < 0){
			alert('Valor deve ser maior ou igual a zero');
			document.getElementById("valor").focus();
			return false;
		}
		return true;
	}

</script>

</html>