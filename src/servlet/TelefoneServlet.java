package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Telefone;
import beans.Usuario;
import dao.TelefoneDao;
import dao.UsuarioDao;

@WebServlet("/TelefoneServlet")
public class TelefoneServlet extends HttpServlet {

	private UsuarioDao daoUsuario = new UsuarioDao();
	private TelefoneDao daoTelefone = new TelefoneDao();

	private static final long serialVersionUID = 1L;

	public TelefoneServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");
		String userIdString = request.getParameter("userId");

		if (acao.equalsIgnoreCase("editar")) { // Esse parâmetro é passado no cadastroUsuario.jsp
			try {

				Long userId = Long.valueOf(userIdString);
				Usuario usuario = daoUsuario.consultar(userId);
				request.getSession().setAttribute("usuario", usuario);
				request.setAttribute("usuario", usuario);
				List<Telefone> listTel = new ArrayList<Telefone>();
				listTel = daoTelefone.listarPorUsuario(userId);

				RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroTelefone.jsp");
				request.getSession().setAttribute("telefones", listTel);
				request.setAttribute("telefones", listTel);
				dispatcher.forward(request, response);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (acao.equalsIgnoreCase("excluir")) { // Esse parâmetro é passado no cadastroTelefone.jsp

			String telefoneIdString = request.getParameter("id");
			Long telefoneId = Long.valueOf(telefoneIdString);
			Telefone telefone = daoTelefone.consultar(telefoneId); // Guarda a consulta para poder setar como atributo
																	// da requisição o usuário
			daoTelefone.excluir(telefoneId);

			RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroTelefone.jsp");
			request.getSession().setAttribute("telefones", daoTelefone.listarPorUsuario(telefone.getUsuarioId()));
			request.setAttribute("telefones", daoTelefone.listarPorUsuario(telefone.getUsuarioId()));
			request.setAttribute("msg", "Excluído com sucesso");
			dispatcher.forward(request, response);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String numero = request.getParameter("numero");
			String tipo = request.getParameter("tipo");
			String userIdString = request.getParameter("userId");
			Long userId = Long.valueOf(userIdString);
			Usuario usuario = daoUsuario.consultar(userId);

			Telefone telefone = new Telefone();
			telefone.setNumero(numero);
			telefone.setTipo(tipo);
			telefone.setUsuarioId(usuario.getId());

			daoTelefone.salvar(telefone);

			RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroTelefone.jsp");
			request.setAttribute("telefones", daoTelefone.listarPorUsuario(usuario.getId()));
			request.getSession().setAttribute("usuario", usuario);
			request.setAttribute("usuario", usuario);
			request.setAttribute("msg", "Salvo com sucesso");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
