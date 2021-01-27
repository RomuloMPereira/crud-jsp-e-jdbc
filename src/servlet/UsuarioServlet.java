package servlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.Usuario;
import dao.UsuarioDao;

@WebServlet("/UsuarioServlet")
@MultipartConfig
public class UsuarioServlet extends HttpServlet {

	private UsuarioDao dao = new UsuarioDao();

	private static final long serialVersionUID = 1L;

	public UsuarioServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");
		String idString = request.getParameter("id");

		if (acao.equalsIgnoreCase("deletar")) {
			dao.deletar(Long.valueOf(idString));

			try {
				RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", dao.listar());
				dispatcher.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		else if (acao.equalsIgnoreCase("editar")) {
			try {
				Usuario usuario = dao.consultar(Long.valueOf(idString));
				RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuario", usuario);
				dispatcher.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		else if (acao.equalsIgnoreCase("listar")) {
			try {
				RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuario", null);
				request.setAttribute("usuarios", dao.listar());
				dispatcher.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		else if (acao.equalsIgnoreCase("download")) {
			try {
				Usuario usuario = dao.consultar(Long.valueOf(idString));
				if (usuario != null) {
					response.setHeader("Content-Disposition",
							"attachment; filename=arquivo." + usuario.getContentType().split("\\/")[1]);

					// Converte a Base64 do banco para array de byte
					byte[] fotoBytes = Base64.decodeBase64(usuario.getFotoBase64());

					// Converte o array de byte para InputStream
					InputStream inputStream = new ByteArrayInputStream(fotoBytes);

					// Início da resposta para o navegador
					int read = 0;
					byte[] bytes = new byte[4096];
					OutputStream outputStream = response.getOutputStream();

					while ((read = inputStream.read(bytes)) != -1) {
						outputStream.write(bytes, 0, read);
					}
					outputStream.flush();
					outputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", dao.listar());
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (acao.equalsIgnoreCase("salvar")) {

			String idString = request.getParameter("id");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String nome = request.getParameter("nome");
			String telefone = request.getParameter("telefone");
			String cep = request.getParameter("cep");
			String rua = request.getParameter("rua");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String uf = request.getParameter("uf");
			String ibge = request.getParameter("ibge");
			Long id = !idString.isEmpty() ? Long.parseLong(idString) : 0;

			Usuario usuario = new Usuario();
			if (!idString.isEmpty()) {
				usuario.setId(id);
			}
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setNome(nome);
			usuario.setTelefone(telefone);
			usuario.setCep(cep);
			usuario.setBairro(bairro);
			usuario.setRua(rua);
			usuario.setCidade(cidade);
			usuario.setUf(uf);
			usuario.setIbge(ibge);

			// Upload de imagem e arquivo
			try {

				if (ServletFileUpload.isMultipartContent(request)) {

					Part part = request.getPart("foto");
					if (part != null) {
						byte[] bytes = converteStreamParaByte(part.getInputStream());
						String base64 = Base64.encodeBase64String(bytes);

						usuario.setFotoBase64(base64);
						usuario.setContentType(part.getContentType());
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			boolean podeInserir = true;

			// Validações para o formulário
			if (login == null || login.isEmpty()) {
				request.setAttribute("msg", "Login deve ser informado");
				podeInserir = false;
			} else if (nome == null || nome.isEmpty()) {
				request.setAttribute("msg", "Nome deve ser informado");
				podeInserir = false;
			} else if (senha == null || senha.isEmpty()) {
				request.setAttribute("msg", "Senha deve ser informada");
				podeInserir = false;
			} else if (telefone == null || telefone.isEmpty()) {
				request.setAttribute("msg", "Telefone deve ser informado");
				podeInserir = false;
			} else if ((idString.isEmpty() || idString == null) && dao.validarLogin(login)) {
				dao.salvar(usuario);
				request.setAttribute("msg", "Salvo com sucesso!");
			} else if ((!idString.isEmpty() || idString != null) && dao.validarLoginUpdate(login, id)) {
				dao.atualizar(usuario);
				request.setAttribute("msg", "Atualizado com sucesso!");
			} else if (!dao.validarLogin(login) || !dao.validarLoginUpdate(login, usuario.getId())) {
				request.setAttribute("msg", "Login já cadastrado!");
				podeInserir = false;
			}

			if (!podeInserir) {
				request.setAttribute("usuario", usuario); // Retorna para a tela o usuário passado
			}

			try {
				RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", dao.listar());
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private byte[] converteStreamParaByte(InputStream inputStream) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {

			int read = inputStream.read();

			while (read != -1) {
				baos.write(read);
				read = inputStream.read();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return baos.toByteArray();
	}
}
