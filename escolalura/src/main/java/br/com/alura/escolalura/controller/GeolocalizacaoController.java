package br.com.alura.escolalura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.alura.escolalura.model.Aluno;
import br.com.alura.escolalura.repository.AlunoRepository;

@Controller
public class GeolocalizacaoController {
	
	@Autowired
	private AlunoRepository alunoRepository;

	@GetMapping("/geolocalizacao/iniciarpesquisa")
	public String inicializarPesquisa(Model model) {
		List<Aluno> alunos = alunoRepository.obterTodosAlunos();
		model.addAttribute("alunos", alunos);
		return "geolocalizacao/pesquisar";
	}
	
	@GetMapping("/geolocalizacao/pesquisar")
	public String pesquisar(@RequestParam("alunoId") String alunoId, Model model) {
		Aluno aluno = alunoRepository.buscaAlunoPorId(alunoId);
		List<Aluno> alunosProximos = alunoRepository.pesquisarPorGeolocalizacao(aluno);
		model.addAttribute("alunosProximos", alunosProximos);
		return "geolocalizacao/pesquisar";
	}
}
