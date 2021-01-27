<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cadastro de usuário</title>
<link rel="stylesheet" href="resources/css/tabela.css" />
<link rel="stylesheet" href="resources/css/cadastro.css" />
<!-- Adicionando JQuery -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
</head>
<body>
		<a href="acesso-liberado.jsp">Início</a>
		<a href="index.jsp">Sair</a>
		<h2>Cadastro de usuário</h2>
		
		<h3 style="color: blue;">${msg}</h3>

		<form action="UsuarioServlet" method="post" id="formUser" onsubmit="return validarCampos() ? true : false" 
			enctype="multipart/form-data">

			<div>
			<label>Código:</label> 
			<div><input type="text" id="id" name="id" value="${usuario.id}" readonly="readonly" /></div>
			</div>
			
			<div> 
			<label>Login:</label> 
			<div><input type="text" id="login" name="login" value="${usuario.login}" /></div>
			</div>
			
			<div>
			<label>Nome:</label>
			<div><input type="text" id="nome" name="nome" value="${usuario.nome}" /></div>
			</div>

			<div>
			<label>Senha:</label> 
			<div><input type="password" id="senha" name="senha" value="${usuario.senha}" /></div>
			</div>
			
			<div>
			<label>Telefone:</label>
			<div><input type="text" id="telefone" name="telefone" value="${usuario.telefone}" /></div>
			</div>
			
			<div>
			<label>CEP:</label>
			<div><input type="text" id="cep" name="cep" value="${usuario.cep}"/></div>
			</div>
			<div>
			<label>Rua:</label>
			<div><input type="text" id="rua" name="rua" value="${usuario.rua}"/></div>
			</div>
			<div>
			<label>Bairro:</label>
			<div><input type="text" id="bairro" name="bairro" value="${usuario.bairro}"/></div>
			</div>
			<div>
			<label>Cidade:</label>
			<div><input type="text" id="cidade" name="cidade" value="${usuario.cidade}"/></div>
			</div>
			<div>
			<label>Estado:</label>
			<div><input type="text" id="uf" name="uf" value="${usuario.uf}"/></div>
			</div>
			<div>
			<label>IBGE:</label>
			<div><input type="text" id="ibge" name="ibge" value="${usuario.ibge}" /></div>
			</div>
			
			<div>
			<label>Foto:</label>
			<div><input type="file" name="foto" value="Foto"></div>
			</div>
			
			<div>
			<div><input name="submit" type="submit" value="salvar" onclick="document.getElementById('formUser').action='UsuarioServlet?acao=salvar'"></div>
			</div>
			
			<div> 
			<div><input name="submit" type="submit" value="cancelar" onclick="document.getElementById('formUser').action='UsuarioServlet?acao=reset'"></div>
			</div>
			<br>
			<br>
		
		</form>

			<table>
				<thead>
					<tr>
						<th>Id</th>
						<th>Login</th>
						<th>Nome</th>
						<th>Imagem</th>
						<th>Telefone</th>
						<th>Editar</th>
						<th>Deletar</th>
						<th>Telefone</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${usuarios}" var="user">
						<tr>
							<td><c:out value="${user.id}"></c:out></td>
							<td><c:out value="${user.login}"></c:out></td>
							<td><c:out value="${user.nome}"></c:out></td>
							
							<td><a href="UsuarioServlet?acao=download&id=${user.id}">
									<img src='<c:out value="${user.tempFotoBase64}"></c:out>'
									width="20px" height="20px" /></a></td>
									
							<td><c:out value="${user.telefone}"></c:out></td>
							<td><a href="UsuarioServlet?acao=editar&id=${user.id}">
									<img src="resources/img/editar.png" alt="editar" title="Editar"
									width="20px" height="20px" />
							</a></td>
							<td><a
								href="UsuarioServlet?acao=deletar&id=${user.id}"> <img
									src="resources/img/excluir.jpg" alt="excluir" title="Excluir"
									width="20px" height="20px" />
							</a></td>
							<td><a
								href="TelefoneServlet?acao=editar&userId=${user.id}"> <img
									src="resources/img/telefone.jpg" alt="telefone" title="Telefone"
									width="20px" height="20px" />
							</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
</body>

<script type="text/javascript">

	function validarCampos(){
		var login = document.getElementById("login").value;
		var nome = document.getElementById("nome").value;
		var senha = document.getElementById("senha").value;
		var telefone = document.getElementById("telefone").value;
		
		if(login == ''){
			alert('Preencha o login');
			document.getElementById("login").focus();
			return false;
		} else if(nome == ''){
			alert('Preencha o nome');
			document.getElementById("nome").focus();
			return false;
		} else if(senha == ''){
			alert('Preencha o senha');
			document.getElementById("senha").focus();
			return false;
		} else if(telefone == ''){
			alert('Preencha o telefone');
			document.getElementById("telefone").focus();
			return false;
		}
		return true;
	}
	
	//Web Service CEP
	 $(document).ready(function() {

         function limpa_formulário_cep() {
             // Limpa valores do formulário de cep.
             $("#rua").val("");
             $("#bairro").val("");
             $("#cidade").val("");
             $("#uf").val("");
             $("#ibge").val("");
         }
         
         //Quando o campo cep perde o foco.
         $("#cep").blur(function() {

             //Nova variável "cep" somente com dígitos.
             var cep = $(this).val().replace(/\D/g, '');

             //Verifica se campo cep possui valor informado.
             if (cep != "") {

                 //Expressão regular para validar o CEP.
                 var validacep = /^[0-9]{8}$/;

                 //Valida o formato do CEP.
                 if(validacep.test(cep)) {

                     //Preenche os campos com "..." enquanto consulta webservice.
                     $("#rua").val("...");
                     $("#bairro").val("...");
                     $("#cidade").val("...");
                     $("#uf").val("...");
                     $("#ibge").val("...");

                     //Consulta o webservice viacep.com.br/
                     $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {

                         if (!("erro" in dados)) {
                             //Atualiza os campos com os valores da consulta.
                             $("#rua").val(dados.logradouro);
                             $("#bairro").val(dados.bairro);
                             $("#cidade").val(dados.localidade);
                             $("#uf").val(dados.uf);
                             $("#ibge").val(dados.ibge);
                         } //end if.
                         else {
                             //CEP pesquisado não foi encontrado.
                             limpa_formulário_cep();
                             alert("CEP não encontrado.");
                         }
                     });
                 } //end if.
                 else {
                     //cep é inválido.
                     limpa_formulário_cep();
                     alert("Formato de CEP inválido.");
                 }
             } //end if.
             else {
                 //cep sem valor, limpa formulário.
                 limpa_formulário_cep();
             }
         });
     });


</script>

</html>