package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.LoginDao;
import dao.ProdutoDao;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private LoginDao dao = new LoginDao();
	private ProdutoDao daoProduto = new ProdutoDao();

	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String login = request.getParameter("login");
		String senha = request.getParameter("senha");

		try {
			boolean ehAdmin = dao.validarAdmin(login, senha);
			boolean ehCadastrado = dao.validarLogin(login, senha);

			if (ehAdmin) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-liberado.jsp");
				dispatcher.forward(request, response);
			} else if (ehCadastrado) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-liberado-cliente.jsp");
				request.getSession().setAttribute("usuario", dao.recuperarUsuario(login, senha));
				request.getSession().setAttribute("produtos", daoProduto.listar());
				dispatcher.forward(request, response);
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-negado.jsp");
				dispatcher.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
