package br.com.alura.loja.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Produto;

public class ProdutoDAO {

	private EntityManager em;

	public ProdutoDAO(EntityManager em) {

		this.em = em;
	}

	public void cadastrar(Produto produto) {
		this.em.persist(produto);
	}

	public Produto buscarPorId(Long id) {
		return em.find(Produto.class, id);
	}

	public List<Produto> buscarTodos() {

		String jpql = "SELECT p FROM Produto as p";

		return em.createQuery(jpql, Produto.class).getResultList();
	}

	public List<Produto> buscarNome(String nome) {

		String jpql = "SELECT p FROM Produto as p where p.nome= :nome";

		return em.createQuery(jpql, Produto.class)
				.setParameter("nome", nome)
				.getResultList();
	}
	
	public List<Produto> buscarNomeCategoria(String nome) {

		String jpql = "SELECT p FROM Produto as p where p.categoria.nome= :nome";

		return em.createQuery(jpql, Produto.class)
				.setParameter("nome", nome)
				.getResultList();
	}
}
