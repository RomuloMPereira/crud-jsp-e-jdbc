package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.Usuario;
import connection.SingletonConnection;

/**
 * Responável por fazer as queries sql e acessar os dados no banco de dados
 * 
 * @author mprom
 *
 */

public class LoginDao {

	private Connection connection;

	public LoginDao() {
		connection = SingletonConnection.getConnection();
	}

	public boolean validarLogin(String login, String senha) throws SQLException {

		String sql = "select * from usuario where login = '" + login + "' and senha = '" + senha + "'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet result = statement.executeQuery();

		if (result.next()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validarAdmin(String login, String senha) {
		if (login.equalsIgnoreCase("admin") && senha.equalsIgnoreCase("admin")) {
			return true;
		}
		return false;
	}

	public Usuario recuperarUsuario(String login, String senha) {

		try {
			String sql = "select * from usuario where login = '" + login + "' and senha = '" + senha + "'";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				Usuario user = new Usuario();
				user.setId(result.getLong("id"));
				user.setLogin(result.getString("login"));
				user.setSenha(result.getString("senha"));
				user.setNome(result.getString("nome"));
				user.setTelefone(result.getString("telefone"));
				user.setCep(result.getString("cep"));
				user.setRua(result.getString("rua"));
				user.setBairro(result.getString("bairro"));
				user.setCidade(result.getString("cidade"));
				user.setUf(result.getString("uf"));
				user.setIbge(result.getString("ibge"));
				return user;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Usuario recuperarUsuario(Long id) {

		try {
			String sql = "select * from usuario where id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				Usuario user = new Usuario();
				user.setId(result.getLong("id"));
				user.setLogin(result.getString("login"));
				user.setSenha(result.getString("senha"));
				user.setNome(result.getString("nome"));
				user.setTelefone(result.getString("telefone"));
				user.setCep(result.getString("cep"));
				user.setRua(result.getString("rua"));
				user.setBairro(result.getString("bairro"));
				user.setCidade(result.getString("cidade"));
				user.setUf(result.getString("uf"));
				user.setIbge(result.getString("ibge"));
				return user;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
