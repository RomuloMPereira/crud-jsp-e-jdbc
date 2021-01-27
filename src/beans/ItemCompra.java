package beans;

import java.math.BigDecimal;

public class ItemCompra {
	
	private Long id;
	private Long produtoId;
	private String produtoDescricao;
	private BigDecimal valorProduto;
	private double quantidade;
	private BigDecimal valor;
	private Long carrinhoId;
	
	public BigDecimal getValorProduto() {
		return valorProduto;
	}
	public void setValorProduto(BigDecimal valorProduto) {
		this.valorProduto = valorProduto;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProdutoId() {
		return produtoId;
	}
	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}
	public String getProdutoDescricao() {
		return produtoDescricao;
	}
	public void setProdutoDescricao(String produtoDescricao) {
		this.produtoDescricao = produtoDescricao;
	}
	public double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}
	public Long getCarrinhoId() {
		return carrinhoId;
	}
	public void setCarrinhoId(Long carrinhoId) {
		this.carrinhoId = carrinhoId;
	}
	
}
