package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ClienteDAO;
import br.com.alura.loja.dao.PedidoDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ItemPedido;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioDeVendasVO;

public class CadastroDePedito {

	public static void main(String[] args) {
		cadastrarProduto();

		CadastrarPedido();

		getValorTotalVendido();

	}

	private static void cadastrarProduto() {
		Categoria celulares = new Categoria("CELULARES");
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);
		Cliente cliente = new Cliente("Fabio", "1234567");
		EntityManager em = JPAUtil.getEntityManager();

		CategoriaDAO categoriaDAO = new CategoriaDAO(em);
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		ClienteDAO clienteDAO = new ClienteDAO(em);
		em.getTransaction().begin();

		categoriaDAO.cadastrar(celulares);
		produtoDAO.cadastrar(celular);
		clienteDAO.cadastrar(cliente);
		em.getTransaction().commit();
		em.close();
	}

	private static void CadastrarPedido() {
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		Produto produto = produtoDAO.buscarPorId(1l);

		ClienteDAO clienteDAO = new ClienteDAO(em);
		Cliente cliente = clienteDAO.buscarPorId(1l);

		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, pedido, produto));

		PedidoDAO pedidoDAO = new PedidoDAO(em);
		em.getTransaction().begin();
		pedidoDAO.cadastrar(pedido);
		em.getTransaction().commit();
		em.close();
	}

	private static void getValorTotalVendido() {
		EntityManager em = JPAUtil.getEntityManager();
		PedidoDAO pedidoDAO = new PedidoDAO(em);
		BigDecimal totalVendido = pedidoDAO.valorTotalVendido();
		System.out.println("Valor Total: " + totalVendido);
		
		List<RelatorioDeVendasVO> relatorioDeVendas = pedidoDAO.relatorioDeVendas();
		
		relatorioDeVendas.forEach(System.out::println);
	}

}
