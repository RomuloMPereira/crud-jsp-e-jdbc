<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cadastro de Telefone</title>
<link rel="stylesheet" href="resources/css/tabela.css" />
<link rel="stylesheet" href="resources/css/cadastro.css" />
</head>
<body>
		<a href="acesso-liberado.jsp">Início</a>
		<a href="index.jsp">Sair</a>
		<h2>Cadastro de telefone</h2>
		
		<h3 style="color: blue;">${msg}</h3>

		<form action="TelefoneServlet" method="post" id="formTelefone" onsubmit="return validarCampos() ? true : false">

			<div>
			<label>Usuário:</label> 
			<div><input type="text" id="userId" name="userId" value="${usuario.id}" readonly="readonly" /></div>
			<div><input type="text" id="nome" name="nome" value="${usuario.nome}"  readonly="readonly" /></div>
			</div>
			
			<div> 
			<label>Número:</label> 
			<div><input type="text" id="numero" name="numero" /></div>
			</div>
			
			<div>
			<label>Tipo:</label>
			<div><select id="tipo" name="tipo">
					  <option id="casa" value="casa" selected>Casa</option> 
					  <option id="trabalho" value="trabalho">Trabalho</option>
					  <option id="outro" value="outro">Outro</option>
				  </select></div>
			</div>
			
			<div>
			<div><input name="submit" type="submit" value="salvar" /></div>
			</div>
			<br>
			<br>
		
		</form>

			<table>
				<thead>
					<tr>
						<th>Id</th>
						<th>Número</th>
						<th>Tipo</th>
						<th>Excluir</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${telefones}" var="telefone">
						<tr>
							<td><c:out value="${telefone.id}"></c:out></td>
							<td><c:out value="${telefone.numero}"></c:out></td>
							<td><c:out value="${telefone.tipo}"></c:out></td>
							<td><a href="TelefoneServlet?acao=excluir&id=${telefone.id}">
									<img src="resources/img/excluir.jpg" alt="excluir" title="Excluir"
									width="20px" height="20px" />
							</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
</body>

<script type="text/javascript">

	function validarCampos(){
		var numero = document.getElementById("numero").value;
		
		if(numero == ''){
			alert('Preencha o número');
			document.getElementById("numero").focus();
			return false;
		} 
		return true;
	}
	
</script>

</html>