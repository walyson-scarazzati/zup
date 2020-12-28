package br.com.zup.service;

import java.util.List;
import java.util.Optional;

import br.com.zup.domain.Pessoa;

public interface IPessoaService {

	Pessoa salvar(Pessoa pessoa);

	Pessoa editar(Pessoa pessoa);

	void excluir(Pessoa pessoa);

	List<Pessoa> listar();

	Optional<Pessoa> buscarPorId(Long id);

	List<Pessoa> buscarPorNome(String titulo);
}
