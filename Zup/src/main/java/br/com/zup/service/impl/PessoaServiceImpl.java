package br.com.zup.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.zup.domain.Pessoa;
import br.com.zup.exception.BusinessException;
import br.com.zup.repository.IPessoaRepository;
import br.com.zup.service.IPessoaService;

@Service
@Transactional
public class PessoaServiceImpl implements IPessoaService {
	
	private IPessoaRepository pessoaRepository;

	public PessoaServiceImpl(IPessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }
	
	@Override
	public Pessoa salvar(Pessoa pessoa) {
		if (pessoaRepository.existsByCpf(pessoa.getCpf()) || pessoaRepository.existsByEmail(pessoa.getEmail())) {
            throw new BusinessException("Cpf e/ou email já cadastrado.");
        }
		return pessoaRepository.save(pessoa);
	}
	
	@Override
	public List<Pessoa> listar() {
	        return pessoaRepository.findAll();
	}

	@Override
	public Pessoa editar(Pessoa pessoa) {
		 if (pessoa == null || pessoa.getId() == null) {
	            throw new IllegalArgumentException("Pessoa id não pode ser vazio");
	        }
		return pessoaRepository.save(pessoa); 
	}

	@Override
	public void excluir(Pessoa pessoa) {
		pessoaRepository.delete(pessoa);
	}

	@Override
	public Optional<Pessoa> buscarPorId(Long id) {
		return pessoaRepository.findById(id);
	}

	@Override
	public List<Pessoa> buscarPorNome(String nome) {
		return pessoaRepository.findByNomeContainingIgnoreCase(nome);
	}

}
