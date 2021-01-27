package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Carrinho;
import beans.ItemCompra;
import beans.Produto;
import connection.SingletonConnection;

public class ItemCompraDao {

	private Connection connection;

	public ItemCompraDao() {
		connection = SingletonConnection.getConnection();
	}

	public void adicionarItem(Carrinho carrinho, Produto produto, double qtd) {
		try {
			BigDecimal valor = produto.getValor().multiply(new BigDecimal(qtd));
			String sql = "insert into item_compra(produtoid, produto_descricao, valor_produto, qtd, valor, carrinhoid) values(?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, produto.getId());
			statement.setString(2, produto.getNome());
			statement.setBigDecimal(3, produto.getValor());
			statement.setDouble(4, qtd);
			statement.setBigDecimal(5, valor);
			statement.setLong(6, carrinho.getId());
			statement.execute();
			connection.commit();

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public void removerProduto(Carrinho carrinho, Produto produto) {
		try {

			String sql = "delete from item_compra where produtoid = ? and carrinhoid = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, produto.getId());
			statement.setLong(2, carrinho.getId());
			statement.executeUpdate();
			connection.commit();

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public List<ItemCompra> listarCarrinho(Long carrinhoId) {

		List<ItemCompra> list = new ArrayList<ItemCompra>();
		
		try {

			String sql = "select * from item_compra where carrinhoid = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, carrinhoId);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				ItemCompra item = new ItemCompra();
				item.setId(result.getLong("id"));
				item.setProdutoId(result.getLong("produtoid"));
				item.setProdutoDescricao(result.getString("produto_descricao"));
				item.setValorProduto(result.getBigDecimal("valor_produto"));
				item.setQuantidade(result.getDouble("qtd"));
				item.setValor(result.getBigDecimal("valor"));
				item.setCarrinhoId(result.getLong("carrinhoid"));
				list.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void atualizarQtd(Carrinho carrinho, Produto produto) {
		try {
			//Consultar o item para descobrir sua quantidade
			double qtd;
			String sql = "select * from item_compra where produtoid = ? and carrinhoid = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, produto.getId());
			statement.setLong(2, carrinho.getId());
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				qtd = result.getDouble("qtd") + 1;
			} else {
				qtd = 0;
			}
			
			//Adicionar 1 à quantidade do item
			BigDecimal valor = produto.getValor().multiply(new BigDecimal(qtd));
			String sql2 = "update item_compra set qtd = ?, valor = ? where produtoid = ? and carrinhoid = ?";
			PreparedStatement statement2 = connection.prepareStatement(sql2);
			statement2.setDouble(1, qtd);
			statement2.setBigDecimal(2, valor);
			statement2.setLong(3, produto.getId());
			statement2.setLong(4, carrinho.getId());
			statement2.executeUpdate();
			connection.commit();

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public BigDecimal calcularValorTotal() {
		return null;
	}

	public void limparCarrinho() {

	}

}
