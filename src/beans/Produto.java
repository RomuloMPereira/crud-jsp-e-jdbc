package beans;

import java.math.BigDecimal;

public class Produto {

	private Long id;
	private String nome;
	private double quantidade;
	private BigDecimal valor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public static class ProdutoValidacao {

		public static boolean validarQuantidade(String qtdString) {
			boolean validado;
			double qtd = -1;
			
			if(qtdString.isEmpty()) {
				validado = false;
			} else {
				try {
					qtd = Double.parseDouble(qtdString);
					if(qtd >= 0) {
						validado = true;
					} else {
						validado = false;
					}
				} catch(Exception e) {
					validado = false;
				}
			}	
			return validado;
		}

		public static boolean validarValor(String valorString) {
			boolean validado;
			BigDecimal valor;

			if (valorString.isEmpty()) {
				validado = false;
			} else {
				try {
					valor = new BigDecimal(valorString);
					if (valor.compareTo(BigDecimal.ZERO) > 0) { // Retorna -1, 0 ou 1
						validado = true;
					} else {
						validado = false;
					}
				} catch(Exception e) {
					validado = false;
				}
			}
			return validado;
		}
	}

}
