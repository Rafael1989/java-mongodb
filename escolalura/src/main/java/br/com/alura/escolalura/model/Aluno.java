package br.com.alura.escolalura.model;

import java.util.Calendar;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;

public class Aluno {

	private ObjectId id;
	private String nome;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar dataNascimento;
	private Curso curso;
	private List<Nota> notas;
	private List<Habilidade> habilidades;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Calendar getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<Nota> getNotas() {
		return notas;
	}

	public void setNotas(List<Nota> notas) {
		this.notas = notas;
	}

	public List<Habilidade> getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(List<Habilidade> habilidades) {
		this.habilidades = habilidades;
	}

	public Aluno criaId() {
		setId(new ObjectId());
		return this;
	}

}
