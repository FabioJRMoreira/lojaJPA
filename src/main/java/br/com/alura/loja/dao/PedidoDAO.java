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

}
