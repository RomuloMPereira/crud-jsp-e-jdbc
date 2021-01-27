package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Compra;
import beans.ItemCompra;
import beans.Usuario;
import dao.CompraDao;
import dao.ItemCompraDao;
import dao.UsuarioDao;

@WebServlet("/CompraServlet")
public class CompraServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CompraDao daoCompra = new CompraDao();
	private ItemCompraDao daoItemCompra = new ItemCompraDao();
	private UsuarioDao daoUsuario = new UsuarioDao();

	private List<Compra> compras;
	private List<ItemCompra> itens;
	private Usuario usuario;

	public CompraServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");
		String carrinhoIdString = request.getParameter("carrinhoId");
		String usuarioIdString = request.getParameter("userId");

		if (acao.equalsIgnoreCase("listar")) {

			try {

				compras = daoCompra.listar();

			} catch (Exception e) {
				e.printStackTrace();
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher("compras.jsp");
			request.getSession().setAttribute("compras", compras);
			dispatcher.forward(request, response);

		} else if (acao.equalsIgnoreCase("verItens")) {

			try {
				itens = daoItemCompra.listarCarrinho(Long.valueOf(carrinhoIdString));
				usuario = daoUsuario.consultar(Long.valueOf(usuarioIdString));
			} catch (Exception e) {
				e.printStackTrace();
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher("detalheCompra.jsp");
			request.getSession().setAttribute("itens", itens);
			request.getSession().setAttribute("usuario", usuario);
			dispatcher.forward(request, response);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
