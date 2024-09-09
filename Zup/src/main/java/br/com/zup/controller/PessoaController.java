package br.com.zup.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.domain.Pessoa;
import br.com.zup.service.IPessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("pessoas")
@RequiredArgsConstructor
//@Api(description = "Endpoint para criar, atualizar, deletar, excluir e buscar as Pessoas.", tags = {"Pessoa API"})
@Slf4j
public class PessoaController {
	
	@Autowired
	private IPessoaService pessoaService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Operation(summary = "Listar pessoas", description = "Retorna a lista de pessoas")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retorna a lista de pessoas"),
			@ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(responseCode = "500", description = "Foi gerada uma exceção") })
	@GetMapping("/listar")
	public List<Pessoa> listarPessoas() {
		return pessoaService.listar();
	}

	@Operation(summary = "Cadastrar uma pessoa", description = "Cria uma nova pessoa")
	@PostMapping(value = "/salvar")
	public Pessoa salvar(@Valid @RequestBody Pessoa pessoa) {
		log.info("Criando uma pessoa por id: {}", pessoa.getId());
		return pessoaService.salvar(pessoa);
	}

	@Operation(summary = "Editar uma pessoa", description = "Atualiza uma pessoa existente")
	@PutMapping("/{id}")
	public Pessoa editar(@PathVariable(value = "id") Long id, @Valid @RequestBody Pessoa dto) {

		log.info("Editar cadastro uma pessoa por id: {}", id);
		return pessoaService.buscarPorId(id).map(pessoa -> {
			pessoa.setNome(dto.getNome());
			pessoa.setEmail(dto.getEmail());
			pessoa.setCpf(dto.getCpf());
			pessoa.setDataNascimento(dto.getDataNascimento());
			pessoa = pessoaService.editar(pessoa);
			return modelMapper.map(pessoa, Pessoa.class);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@Operation(summary = "Excluir uma pessoa", description = "Exclui uma pessoa pelo ID")
	@DeleteMapping("/{id}")
	public void excluir(@PathVariable(value = "id") Long id) {
		log.info("Excluir um pessoa por id: {}", id);
		Pessoa pessoa = pessoaService.buscarPorId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		pessoaService.excluir(pessoa);
	}
	
	@Operation(summary = "Obter detalhes de uma pessoa", description = "Retorna os detalhes de uma pessoa pelo ID")
	@GetMapping("{id}")
    public Pessoa buscarPorId(@PathVariable Long id) {
        log.info("Obter detalhes de uma pessoa pelo id: {}", id);
        return pessoaService
                .buscarPorId(id)
                .map( pessoa -> modelMapper.map(pessoa, Pessoa.class) )
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

	@Operation(summary = "Buscar pessoas por nome", description = "Retorna uma lista de pessoas pelo nome")
	@GetMapping("/nome/{nome}")
	public List<Pessoa> buscarPorNome(@RequestParam(value = "nome") String nome) {
		return pessoaService.buscarPorNome(nome);
	}

}
