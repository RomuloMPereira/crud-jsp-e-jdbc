package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Carrinho;
import connection.SingletonConnection;

public class CarrinhoDao {
	private Connection connection;

	public CarrinhoDao() {
		connection = SingletonConnection.getConnection();
	}

	// Retorna o carrinho com o id salvo no banco
	public Carrinho salvar(Carrinho carrinho) {
		try {
			List<Carrinho> list = new ArrayList<Carrinho>();

			String sql = "insert into carrinho(nome) values (?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, carrinho.getNome());
			statement.execute();
			connection.commit();

			// Iterar sobre todos os carrinhos para obter o último carrinho adicionado (só
			// assim se consegue o id gerado automaticamente pelo banco)
			String sql2 = "select * from carrinho";
			PreparedStatement statement2 = connection.prepareStatement(sql2);
			ResultSet result = statement2.executeQuery();

			while (result.next()) {
				Carrinho carrinhoSalvo = new Carrinho();
				carrinhoSalvo.setId(result.getLong("id"));
				carrinhoSalvo.setNome(result.getString("nome"));
				list.add(carrinhoSalvo);
			}

			Carrinho carrinhoRetorno = list.get(list.size() - 1);
			return carrinhoRetorno;

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return null;
		}
	}

	public Carrinho consultar(Long id) throws SQLException {

		String sql = "select * from carrinho where id = '" + id + "'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet result = statement.executeQuery();

		if (result.next()) {
			Carrinho carrinho = new Carrinho();
			carrinho.setId(result.getLong("id"));
			carrinho.setNome(result.getString("nome"));
			return carrinho;
		}
		return null;
	}
}
