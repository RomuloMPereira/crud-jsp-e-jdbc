package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Usuario;
import connection.SingletonConnection;

public class UsuarioDao {

	private Connection connection;

	public UsuarioDao() {
		connection = SingletonConnection.getConnection();
	}

	public void salvar(Usuario usuario) {
		try {

			String sql = "insert into usuario(login, senha, nome, telefone, "
					+ "cep, rua, bairro, cidade, uf, ibge, fotobase64, contenttype) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, usuario.getLogin());
			statement.setString(2, usuario.getSenha());
			statement.setString(3, usuario.getNome());
			statement.setString(4, usuario.getTelefone());
			statement.setString(5, usuario.getCep());
			statement.setString(6, usuario.getRua());
			statement.setString(7, usuario.getBairro());
			statement.setString(8, usuario.getCidade());
			statement.setString(9, usuario.getUf());
			statement.setString(10, usuario.getIbge());
			statement.setString(11, usuario.getFotoBase64());
			statement.setString(12, usuario.getContentType());
			statement.execute();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public List<Usuario> listar() throws SQLException {

		List<Usuario> list = new ArrayList<Usuario>();

		String sql = "select * from usuario";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			Usuario usuario = new Usuario();
			usuario.setId(result.getLong("id"));
			usuario.setLogin(result.getString("login"));
			usuario.setSenha(result.getString("senha"));
			usuario.setNome(result.getString("nome"));
			usuario.setTelefone(result.getString("telefone"));
			usuario.setCep(result.getString("cep"));
			usuario.setRua(result.getString("rua"));
			usuario.setBairro(result.getString("bairro"));
			usuario.setCidade(result.getString("cidade"));
			usuario.setUf(result.getString("uf"));
			usuario.setIbge(result.getString("ibge"));
			usuario.setFotoBase64(result.getString("fotobase64"));
			usuario.setContentType(result.getString("contenttype"));
			list.add(usuario);
		}

		return list;
	}

	public void deletar(Long id) {
		try {

			String sql = "delete from usuario where id = '" + id + "'";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public Usuario consultar(Long id) throws SQLException {

		String sql = "select * from usuario where id = '" + id + "'";
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
			user.setFotoBase64(result.getString("fotobase64"));
			user.setContentType(result.getString("contenttype"));
			return user;
		}
		return null;
	}

	public void atualizar(Usuario user) {
		try {

			String sql = "update usuario set login = ?, senha = ?, nome = ?, telefone = ?, "
					+ "cep = ?, rua = ?, bairro = ?, cidade = ?, uf = ?, ibge = ?, "
					+ "fotobase64 = ?, contenttype = ? where id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, user.getLogin());
			statement.setString(2, user.getSenha());
			statement.setString(3, user.getNome());
			statement.setString(4, user.getTelefone());
			statement.setString(5, user.getCep());
			statement.setString(6, user.getRua());
			statement.setString(7, user.getBairro());
			statement.setString(8, user.getCidade());
			statement.setString(9, user.getUf());
			statement.setString(10, user.getIbge());
			statement.setString(11, user.getFotoBase64());
			statement.setString(12, user.getContentType());
			statement.setLong(13, user.getId());
			statement.executeUpdate();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public boolean validarLogin(String login) {
		try {

			String sql = "select count(*) as qtd from usuario where login = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, login);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				return result.getInt("qtd") <= 0; // Se for true, significa que o login não existe
			}
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean validarLoginUpdate(String login, Long id) {
		try {

			String sql = "select count(*) as qtd from usuario where login = ? and id <> ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, login);
			statement.setLong(2, id);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				return result.getInt("qtd") <= 0; // Se for true, significa que o login não existe
			}
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean validarSenha(String senha) {
		try {
			
			String sql = "select count(*) as qtd from usuario where senha = '" + senha + "'";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				return result.getInt("qtd") <= 0;
			}
			return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean validarSenhaUpdate(String senha, Long id) {
		try {
			
			String sql = "select count(*) as qtd from usuario where senha = '" + senha + "' and id <> " + id;
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				return result.getInt("qtd") <= 0;
			}
			return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	

}
