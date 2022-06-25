package br.com.alura.loja.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "valor_total")
	private BigDecimal valorTotal = BigDecimal.ZERO;
	private LocalDate date = LocalDate.now();
	//quando há toOne (relacionamento) por padrao o jpa cria um join 
	//isso pode deixar a consulta lenta.
	//por padrao Eager carrega mesmo sem precisar
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	// boa pratica inicializar o atributo lista
	// evita ter que fazer teste para verificar se a colecao esta inicializada.
	// tomany é carregado apenas se acessar a lista 
	//tomany por padrao é Lazy carrega se fizer o acesso.
	private List<ItemPedido> intens = new ArrayList<ItemPedido>();

	public Pedido() {

	}

	public Pedido(Cliente cliente) {

		this.cliente = cliente;
	}

	// metodo para adcionar um item ao pedido
	// boa pratica visto se tratar de um relacionamento bi direcional.
	public void adicionarItem(ItemPedido item) {
		item.setPedido(this);
		this.intens.add(item);
		this.valorTotal = this.valorTotal.add(item.getValor());

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
