package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Produto;
import connection.SingletonConnection;

public class ProdutoDao {

	private Connection connection;
	
	public ProdutoDao() {
		connection = SingletonConnection.getConnection();
	}
	
	public void salvar(Produto produto) {
		try {
			
			String sql = "insert into produto(nome, quantidade, valor) values (?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, produto.getNome());
			statement.setDouble(2, produto.getQuantidade());
			statement.setBigDecimal(3, produto.getValor());
			statement.execute();
			connection.commit();
			
		} catch(SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public List<Produto> listar(){
		List<Produto> list = new ArrayList<Produto>();
		try {
			
			String sql = "select * from produto";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				Produto produto = new Produto();
				produto.setId(result.getLong("id"));
				produto.setNome(result.getString("nome"));
				produto.setQuantidade(result.getDouble("quantidade"));
				produto.setValor(result.getBigDecimal("valor"));
				list.add(produto);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void deletar(Long id) {
		try {
			
			String sql = "delete from produto where id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			statement.execute();
			connection.commit();
			
		}catch(SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public Produto consultar(Long id) {
		try {
			
			Produto produto = new Produto();
			String sql = "select * from produto where id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				produto.setId(result.getLong("id"));
				produto.setNome(result.getString("nome"));
				produto.setQuantidade(result.getDouble("quantidade"));
				produto.setValor(result.getBigDecimal("valor"));
			}
			return produto;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void atualizar(Produto produto) {
		try {
			
			String sql = "update produto set nome = ?, quantidade = ?, valor = ? where id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, produto.getNome());
			statement.setDouble(2, produto.getQuantidade());
			statement.setBigDecimal(3, produto.getValor());
			statement.setLong(4, produto.getId());
			statement.executeUpdate();
			connection.commit();
			
		} catch(SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	

	public boolean validarNome(String nome) {
		try {
			
			String sql = "select count(*) as qtd from produto where nome = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, nome);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				return result.getInt("qtd") <= 0;
			}
			return false;
			
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean validarNomeUpdate(String nome, Long id) {
		try {
			
			String sql = "select count(*) as qtd from produto where nome = ? and id <> ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, nome);
			statement.setLong(2, id);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				return result.getInt("qtd") <= 0;
			}
			return false;
			
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
