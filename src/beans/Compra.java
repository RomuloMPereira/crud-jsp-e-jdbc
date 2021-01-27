package beans;

public class Compra {
	private Long id;
	private Long usuarioId;
	private String usuarioNome;
	private Long carrinhoId;
	
	
	public String getUsuarioNome() {
		return usuarioNome;
	}
	public void setUsuarioNome(String usuarioNome) {
		this.usuarioNome = usuarioNome;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	public Long getCarrinhoId() {
		return carrinhoId;
	}
	public void setCarrinhoId(Long carrinhoId) {
		this.carrinhoId = carrinhoId;
	}
	
	
}
