package br.com.alura.escolalura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.alura.escolalura.model.Aluno;
import br.com.alura.escolalura.model.Habilidade;
import br.com.alura.escolalura.repository.AlunoRepository;

@Controller
public class HabilidadeController {
	
	@Autowired
	private AlunoRepository alunoRepository;

	@GetMapping("/habilidade/cadastrar/{id}")
	public String cadastrar(@PathVariable String id, Model model) {
		Aluno aluno = alunoRepository.buscaAlunoPorId(id);
		model.addAttribute("aluno", aluno);
		model.addAttribute("habilidade", new Habilidade());
		return "habilidade/cadastrar";
	}
	
	@PostMapping("/habilidade/salvar/{id}")
	public String salvar(@PathVariable String id, @ModelAttribute Habilidade habilidade) {
		Aluno aluno = alunoRepository.buscaAlunoPorId(id);
		alunoRepository.salvar(aluno.adiciona(aluno,habilidade));
		return "redirect:/aluno/listar";
	}
}
