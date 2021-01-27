package servlet;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Produto;
import beans.Produto.ProdutoValidacao;
import dao.ProdutoDao;

@WebServlet("/ProdutoServlet")
public class ProdutoServlet extends HttpServlet {
	
	private ProdutoDao dao = new ProdutoDao();
	
	private static final long serialVersionUID = 1L;
       
    
    public ProdutoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		String idString = request.getParameter("id");
		
		
		if(acao.equalsIgnoreCase("listar")) {
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroProduto.jsp");
			request.setAttribute("produtos", dao.listar());
			dispatcher.forward(request, response);
			
		} else if(acao.equalsIgnoreCase("editar")) {
			
			Produto produto = dao.consultar(Long.valueOf(idString));
			RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroProduto.jsp");
			request.setAttribute("produto", produto);
			dispatcher.forward(request, response);
			
		} else if(acao.equalsIgnoreCase("deletar")) {
			
			dao.deletar(Long.valueOf(idString));
			RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroProduto.jsp");
			request.setAttribute("produtos", dao.listar());
			dispatcher.forward(request, response);
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		
		if(acao.equalsIgnoreCase("salvar")) {
			
			String idString = request.getParameter("id");
			String nome = request.getParameter("nome");
			String qtdString = request.getParameter("quantidade");
			String valorString = request.getParameter("valor");
			Long id = !idString.isEmpty() ? Long.parseLong(idString) : 0;
			
			Produto produto = new Produto();
			if(!idString.isEmpty()) {
				produto.setId(id);
			}
			produto.setNome(nome);
			if(ProdutoValidacao.validarQuantidade(qtdString)) {
				produto.setQuantidade(Double.valueOf(qtdString));
			} else {
				produto.setQuantidade(0);
			}
			if(ProdutoValidacao.validarValor(valorString)) {
				produto.setValor(new BigDecimal(valorString));
			} else {
				produto.setValor(new BigDecimal(0));
			}
			
			boolean podeInserir = true;
			
			if(nome == null || nome.isEmpty()) {
				request.setAttribute("msg", "Nome deve ser informado");
				podeInserir = false;
			} else if(qtdString == null || qtdString.isEmpty()) {
				request.setAttribute("msg", "Quantidade deve ser informada");
				podeInserir = false;
			} else if(!ProdutoValidacao.validarQuantidade(qtdString)) {
				request.setAttribute("msg", "Quantidade deve ser um número maior que zero");
				podeInserir = false;
			} else if(valorString == null || valorString.isEmpty()) {
				request.setAttribute("msg", "Valor deve ser informado");
				podeInserir = false;
			} else if(!ProdutoValidacao.validarValor(valorString)) {
				request.setAttribute("msg", "Valor deve ser um número maior que zero");
				podeInserir = false;
			}  else if((idString == null || idString.isEmpty()) && dao.validarNome(nome)) {
				dao.salvar(produto);
				request.setAttribute("msg", "Salvo com sucesso!");
			} else if((idString != null || !idString.isEmpty()) && dao.validarNomeUpdate(nome, id)) {
				dao.atualizar(produto);
				request.setAttribute("msg", "Atualizado com sucesso!");
			} else if(!dao.validarNome(nome)) {
				request.setAttribute("msg", "Nome inválido");
				podeInserir = false;
			} 
			
			if(!podeInserir){
				request.setAttribute("produto", produto);
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroProduto.jsp");
			request.setAttribute("produtos", dao.listar());
			dispatcher.forward(request, response);
			
			
		} else if(acao.equalsIgnoreCase("cancelar")) {
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroProduto.jsp");
			request.setAttribute("produtos", dao.listar());
			dispatcher.forward(request, response);
			
		}
		
	}

}
