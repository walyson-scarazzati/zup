package br.com.zup.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.zup.domain.Pessoa;
import br.com.zup.dto.PessoaDTO;
import br.com.zup.exception.BusinessException;
import br.com.zup.repository.IPessoaRepository;
import br.com.zup.service.IPessoaService;

@Service
@Transactional
public class PessoaServiceImpl implements IPessoaService {

    private IPessoaRepository pessoaRepository;
    private final ModelMapper modelMapper;

    public PessoaServiceImpl(IPessoaRepository pessoaRepository, ModelMapper modelMapper) {
        this.pessoaRepository = pessoaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PessoaDTO salvar(PessoaDTO pessoaDTO) {
        if (pessoaRepository.existsByCpf(pessoaDTO.getCpf()) || pessoaRepository.existsByEmail(pessoaDTO.getEmail())) {
            throw new BusinessException("Cpf e/ou email já cadastrado.");
        }
        Pessoa pessoa = modelMapper.map(pessoaDTO, Pessoa.class);
        pessoa = pessoaRepository.save(pessoa);
        return modelMapper.map(pessoa, PessoaDTO.class);
    }

    @Override
    public List<PessoaDTO> listar() {
        return pessoaRepository.findAll().stream()
                .map(pessoa -> modelMapper.map(pessoa, PessoaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PessoaDTO editar(PessoaDTO pessoaDTO) {
        if (pessoaDTO == null || pessoaDTO.getId() == null) {
            throw new IllegalArgumentException("Pessoa id não pode ser vazio");
        }
        Pessoa pessoa = modelMapper.map(pessoaDTO, Pessoa.class);
        pessoa = pessoaRepository.save(pessoa);
        return modelMapper.map(pessoa, PessoaDTO.class);
    }

    @Override
    public void excluir(PessoaDTO pessoaDTO) {
        Pessoa pessoa = modelMapper.map(pessoaDTO, Pessoa.class);
        pessoaRepository.delete(pessoa);
    }

    @Override
    public Optional<PessoaDTO> buscarPorId(Long id) {
        return pessoaRepository.findById(id)
                .map(pessoa -> modelMapper.map(pessoa, PessoaDTO.class));
    }

    @Override
    public List<PessoaDTO> buscarPorNome(String nome) {
        return pessoaRepository.findByNomeContainingIgnoreCase(nome).stream()
                .map(pessoa -> modelMapper.map(pessoa, PessoaDTO.class))
                .collect(Collectors.toList());
    }

}
