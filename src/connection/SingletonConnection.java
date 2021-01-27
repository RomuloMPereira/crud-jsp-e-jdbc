package connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Responsável por estabelecer a conexão com o banco de dados
 * @author mprom
 *
 */

public class SingletonConnection {
	
	private static String url = "jdbc:postgresql://localhost:5432/curso-jsp?autoReconnect=true";
	
	private static String password = "admin";
	
	private static String user = "postgres";
	
	private static Connection connection;
	
	static {
		conectar();
	}
	
	public SingletonConnection() {
		conectar();
	}
	
	
	
	public static void conectar() {
		
		try {
			if(connection == null) {
			
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false);
				System.out.println("Conectou com sucesso!");
				
			}
		} catch(Exception e) {
			System.out.println("Erro ao conectar com o banco de dados...");
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}

}
