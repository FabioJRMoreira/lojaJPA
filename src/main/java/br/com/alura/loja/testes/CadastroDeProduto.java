package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class CadastroDeProduto {

	public static void main(String[] args) {

		cadastrarProduto();

		EntityManager em = JPAUtil.getEntityManager();

		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		
		Produto p = produtoDAO.buscarPorId(1l);
		
		System.out.println(p.getDataCadastro() +" "+p.getDescricao()+" "+ p.getNome());
		
		List<Produto> todos = produtoDAO.buscarTodos();
		
		todos.forEach(p1->System.out.println(p1.getNome()));
		
		List<Produto> buscarNome = produtoDAO.buscarNome("Xiaomi Redmi");
		
		buscarNome.forEach(p2->System.out.println(p2.getNome()+" "+p2.getDataCadastro()));
		
		
		List<Produto> buscarNomeCategoria = produtoDAO.buscarNomeCategoria("CELULARES");
		
		buscarNomeCategoria.forEach(p3->System.out.println(p3.getNome()+" "+p3.getCategoria().getNome()));
		
		
		BigDecimal precoProduto = produtoDAO.buscarPrecoProdutoComNome("Xiaomi Redmi");
		
		System.out.println(precoProduto);

	}

	private static void cadastrarProduto() {
		Categoria celulares = new Categoria("CELULARES");
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);

		EntityManager em = JPAUtil.getEntityManager();

		CategoriaDAO categoriaDAO = new CategoriaDAO(em);
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		em.getTransaction().begin();

		categoriaDAO.cadastrar(celulares);
		produtoDAO.cadastrar(celular);
		em.getTransaction().commit();
		em.close();
	}

}
