package br.com.zup.service;

import java.util.List;
import java.util.Optional;

import br.com.zup.dto.PessoaDTO;

public interface IPessoaService {

    PessoaDTO salvar(PessoaDTO pessoa);

    PessoaDTO editar(PessoaDTO pessoa);

    void excluir(PessoaDTO pessoa);

    List<PessoaDTO> listar();

    Optional<PessoaDTO> buscarPorId(Long id);

    List<PessoaDTO> buscarPorNome(String titulo);
}
