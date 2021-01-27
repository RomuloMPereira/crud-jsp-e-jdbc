package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Carrinho;
import beans.Compra;
import beans.Usuario;
import connection.SingletonConnection;

public class CompraDao {

	private Connection connection;

	public CompraDao() {
		connection = SingletonConnection.getConnection();
	}
	
	public void salvar(Carrinho carrinho, Usuario usuario) {
		try {
			
			String sql = "insert into compra(usuarioid, usuario_nome, carrinhoid) values (?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, usuario.getId());
			statement.setString(2, usuario.getNome());
			statement.setLong(3, carrinho.getId());
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
	
	public List<Compra> listar() throws SQLException {

		List<Compra> list = new ArrayList<Compra>();

		String sql = "select * from compra";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			Compra compra = new Compra();
			compra.setId(result.getLong("id"));
			compra.setUsuarioId(result.getLong("usuarioid"));
			compra.setUsuarioNome(result.getString("usuario_nome"));
			compra.setCarrinhoId(result.getLong("carrinhoid"));
			
			list.add(compra);
		}

		return list;
	}
}
