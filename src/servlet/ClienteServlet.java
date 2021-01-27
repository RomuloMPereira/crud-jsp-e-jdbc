package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Carrinho;
import beans.ItemCompra;
import beans.Produto;
import beans.Usuario;
import dao.CarrinhoDao;
import dao.CompraDao;
import dao.ItemCompraDao;
import dao.LoginDao;
import dao.ProdutoDao;

@WebServlet("/ClienteServlet")
public class ClienteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ProdutoDao daoProduto = new ProdutoDao();
	private ItemCompraDao daoItemCompra = new ItemCompraDao();
	private CarrinhoDao daoCarrinho = new CarrinhoDao();
	private LoginDao daoLogin = new LoginDao();
	private CompraDao daoCompra = new CompraDao();
	private Carrinho carrinho;
	private List<ItemCompra> itensCompra;
	private Usuario usuario;

	public ClienteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");
		String idString = request.getParameter("id");
		String userIdString = request.getParameter("userid");

		if (acao.equalsIgnoreCase("adicionar")) {
			// Checa se carrinho já existe
			if (carrinho == null) {
				carrinho = new Carrinho();
				carrinho = daoCarrinho.salvar(carrinho);
			}

			// Adicionar item no carrinho (primeiro, conferir se produto já existe na lista)
			boolean existe = false;
			Produto produto = daoProduto.consultar(Long.valueOf(idString));
			itensCompra = daoItemCompra.listarCarrinho(carrinho.getId());
			for (ItemCompra item : itensCompra) {
				if (item.getProdutoId() == produto.getId()) {
					daoItemCompra.atualizarQtd(carrinho, produto);
					existe = true;
				}
			}
			if (produto != null && existe == false) {
				try {
					daoItemCompra.adicionarItem(carrinho, produto, 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			usuario = daoLogin.recuperarUsuario(Long.valueOf(userIdString));
			itensCompra = daoItemCompra.listarCarrinho(carrinho.getId());
			RequestDispatcher dispatcher = request.getRequestDispatcher("carrinho.jsp");
			request.getSession().setAttribute("carrinho", carrinho);
			request.getSession().setAttribute("itens", itensCompra);
			request.getSession().setAttribute("usuario", usuario);
			dispatcher.forward(request, response);

		} else if (acao.equalsIgnoreCase("listarCarrinho")) { // acesso-liberado-cliente.jsp

			// Listar os itens selecionados no carrinho
			try {
				carrinho = daoCarrinho.consultar(Long.valueOf(idString));
				itensCompra = daoItemCompra.listarCarrinho(carrinho.getId());
				usuario = daoLogin.recuperarUsuario(Long.valueOf(userIdString));
			} catch (Exception e) {
				e.printStackTrace();
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("carrinho.jsp");
			request.getSession().setAttribute("carrinho", carrinho);
			request.getSession().setAttribute("itens", itensCompra);
			request.getSession().setAttribute("usuario", usuario);
			dispatcher.forward(request, response);

		} else if (acao.equalsIgnoreCase("deletar")) {

			String carrinhoidString = request.getParameter("carrinhoid");
			// Deletar produto
			Produto produto = daoProduto.consultar(Long.valueOf(idString));
			try {
				carrinho = daoCarrinho.consultar(Long.valueOf(carrinhoidString));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (produto != null) {
				try {
					daoItemCompra.removerProduto(carrinho, produto);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			usuario = daoLogin.recuperarUsuario(Long.valueOf(userIdString));
			itensCompra = daoItemCompra.listarCarrinho(carrinho.getId());
			RequestDispatcher dispatcher = request.getRequestDispatcher("carrinho.jsp");
			request.getSession().setAttribute("carrinho", carrinho);
			request.getSession().setAttribute("itens", itensCompra);
			request.getSession().setAttribute("usuario", usuario);
			dispatcher.forward(request, response);

		} else if (acao.equalsIgnoreCase("continuar")) {

			// Encaminhar para acesso-liberado-cliente.jsp para continuar a compra,
			// recuperando o carrinho
			try {
				carrinho = daoCarrinho.consultar(Long.valueOf(idString));
			} catch (Exception e) {
				e.printStackTrace();
			}
			usuario = daoLogin.recuperarUsuario(Long.valueOf(userIdString));
			RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-liberado-cliente.jsp");
			request.getSession().setAttribute("carrinho", carrinho);
			request.getSession().setAttribute("produtos", daoProduto.listar());
			request.getSession().setAttribute("usuario", usuario);
			dispatcher.forward(request, response);

		} else if (acao.equalsIgnoreCase("finalizar")) {
			try {
				carrinho = daoCarrinho.consultar(Long.valueOf(idString));
				usuario = daoLogin.recuperarUsuario(Long.valueOf(userIdString));
				itensCompra = daoItemCompra.listarCarrinho(carrinho.getId());
				daoCompra.salvar(carrinho, usuario);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("compra-finalizada.jsp");
			request.getSession().setAttribute("carrinho", carrinho);
			request.getSession().setAttribute("itens", itensCompra);
			request.getSession().setAttribute("usuario", usuario);
			dispatcher.forward(request, response);

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
