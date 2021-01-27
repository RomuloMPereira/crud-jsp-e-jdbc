package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Telefone;
import connection.SingletonConnection;

public class TelefoneDao {

	private Connection connection;

	public TelefoneDao() {
		connection = SingletonConnection.getConnection();
	}

	public void salvar(Telefone telefone) {
		try {
			
			String sql = "insert into telefone(numero, tipo, usuarioid) values(?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, telefone.getNumero());
			statement.setString(2, telefone.getTipo());
			statement.setLong(3, telefone.getUsuarioId());
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
	
	public List<Telefone> listarPorUsuario(Long usuarioId){
		List<Telefone> list = new ArrayList<Telefone>();
		try {
			
			String sql = "select * from telefone where usuarioid = " + usuarioId;
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				Telefone telefone = new Telefone();
				telefone.setId(result.getLong("id"));
				telefone.setNumero(result.getString("numero"));
				telefone.setTipo(result.getString("tipo"));
				telefone.setUsuarioId(result.getLong("usuarioid"));
				list.add(telefone);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Telefone consultar(Long id) {
		try {
			
			String sql = "select * from telefone where id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				Telefone telefone = new Telefone();
				telefone.setId(result.getLong("id"));
				telefone.setNumero(result.getString("numero"));
				telefone.setTipo(result.getString("tipo"));
				telefone.setUsuarioId(result.getLong("usuarioid"));
				return telefone;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void excluir(Long id) {
		try {
			
			String sql = "delete from telefone where id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
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
}
