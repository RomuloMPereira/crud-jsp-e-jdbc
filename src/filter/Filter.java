package filter;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import connection.SingletonConnection;

/**
 * Respons�vel por invocar a classe SingletonConnection quando a aplica��o subir, levantando assim a conex�o 
 * Respons�vel por interceptar as requisi��es e respostas
 * @author mprom
 *
 */

@WebFilter(urlPatterns = {"/*"})
public class Filter implements javax.servlet.Filter {
	
	private static Connection connection = SingletonConnection.getConnection();

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			
			chain.doFilter(request, response);
			connection.commit();
			
		} catch(Exception e) {
			try {
				e.printStackTrace();
				connection.rollback();
				
			} catch(Exception e1) {
				e1.printStackTrace();
			}			
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		connection = SingletonConnection.getConnection();
		
	}

}
