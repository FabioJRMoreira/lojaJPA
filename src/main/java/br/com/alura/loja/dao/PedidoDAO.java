package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.vo.RelatorioDeVendasVO;

public class PedidoDAO {

	private EntityManager em;

	public PedidoDAO(EntityManager em) {

		this.em = em;
	}

	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}

	public BigDecimal valorTotalVendido() {

		String jpql = "SELECT SUM(p.valorTotal) from Pedido p";

		return em.createQuery(jpql, BigDecimal.class).getSingleResult();
	}
	
	public List<RelatorioDeVendasVO> relatorioDeVendas(){
		//passar o caminho compleno para usar o select new
		String jpql ="SELECT new br.com.alura.loja.vo.RelatorioDeVendasVO( "
				   + "produto.nome,"
		           + "SUM(item.quantidade),"
		           + "MAX(pedido.date)) "
		           + "FROM Pedido pedido "
		           + "JOIN pedido.intens item "
		           + "JOIN item.produto produto "
		           + "GROUP BY produto.nome "
		           + "ORDER BY item.quantidade DESC ";
		return em.createQuery(jpql.toString(),RelatorioDeVendasVO.class).getResultList();
	}
	
	public Pedido bucarPedidoComCliente(Long id) {
		//join fetch é usado para carregar um determinado relacionamento
		//carrega mesmo sendo o relacionamento lazy
		
		/*
		 * O join fetch permite escolher quais relacionamentos serão carregados em determinada consulta, ao invés de sempre os carregar
		 * */
		return em.createQuery("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id= :id",Pedido.class)
				.setParameter("id", id)
				.getSingleResult();
		
	}

}
