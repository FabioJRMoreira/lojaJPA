package br.com.alura.loja.modelo;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//imbuta os atributos da classe em cliente
	@Embedded
	private DadosPessoais dadosPessoais;
	

	public Cliente() {

	}

	public Cliente(String nome,String cpf) {
		this.dadosPessoais= new DadosPessoais(nome,cpf);
	}

	public Long getId() {
		return id;
	}

	public DadosPessoais getDadosPessoais() {
		return dadosPessoais;
	}
	
	

}
