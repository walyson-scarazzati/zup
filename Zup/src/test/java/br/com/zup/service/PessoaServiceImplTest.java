package br.com.zup.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import br.com.zup.domain.Pessoa;
import br.com.zup.dto.PessoaDTO;
import br.com.zup.exception.BusinessException;
import br.com.zup.repository.IPessoaRepository;
import br.com.zup.service.impl.PessoaServiceImpl;

public class PessoaServiceImplTest {

    @Mock
    private IPessoaRepository pessoaRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PessoaServiceImpl pessoaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvar_WhenPessoaIsNew_ShouldReturnPessoaDTO() {
        // Arrange
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setCpf("12345678901");
        pessoaDTO.setEmail("test@example.com");
        
        Pessoa pessoa = new Pessoa();
        when(modelMapper.map(pessoaDTO, Pessoa.class)).thenReturn(pessoa);
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
        when(modelMapper.map(pessoa, PessoaDTO.class)).thenReturn(pessoaDTO);
        when(pessoaRepository.existsByCpf(pessoaDTO.getCpf())).thenReturn(false);
        when(pessoaRepository.existsByEmail(pessoaDTO.getEmail())).thenReturn(false);

        // Act
        PessoaDTO result = pessoaService.salvar(pessoaDTO);

        // Assert
        assertNotNull(result);
        assertEquals(pessoaDTO.getCpf(), result.getCpf());
        assertEquals(pessoaDTO.getEmail(), result.getEmail());
        verify(pessoaRepository, times(1)).save(pessoa);
    }

    @Test
    void testSalvar_WhenCpfExists_ShouldThrowBusinessException() {
        // Arrange
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setCpf("12345678901");
        pessoaDTO.setEmail("test@example.com");
        
        when(pessoaRepository.existsByCpf(pessoaDTO.getCpf())).thenReturn(true);

        // Act & Assert
        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            pessoaService.salvar(pessoaDTO);
        });
        assertEquals("Cpf e/ou email já cadastrado.", thrown.getMessage());
    }

    @Test
    void testListar_ShouldReturnPessoaDTOList() {
        // Arrange
        Pessoa pessoa = new Pessoa();
        PessoaDTO pessoaDTO = new PessoaDTO();
        when(pessoaRepository.findAll()).thenReturn(Collections.singletonList(pessoa));
        when(modelMapper.map(pessoa, PessoaDTO.class)).thenReturn(pessoaDTO);

        // Act
        List<PessoaDTO> result = pessoaService.listar();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testEditar_WhenPessoaDTOIsValid_ShouldReturnPessoaDTO() {
        // Arrange
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(1L);
        Pessoa pessoa = new Pessoa();
        when(modelMapper.map(pessoaDTO, Pessoa.class)).thenReturn(pessoa);
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
        when(modelMapper.map(pessoa, PessoaDTO.class)).thenReturn(pessoaDTO);

        // Act
        PessoaDTO result = pessoaService.editar(pessoaDTO);

        // Assert
        assertNotNull(result);
        verify(pessoaRepository, times(1)).save(pessoa);
    }

    @Test
    void testEditar_WhenPessoaDTOIsNull_ShouldThrowIllegalArgumentException() {
        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            pessoaService.editar(null);
        });
        assertEquals("Pessoa id não pode ser vazio", thrown.getMessage());
    }

    @Test
    void testExcluir_ShouldInvokeDelete() {
        // Arrange
        PessoaDTO pessoaDTO = new PessoaDTO();
        Pessoa pessoa = new Pessoa();
        when(modelMapper.map(pessoaDTO, Pessoa.class)).thenReturn(pessoa);

        // Act
        pessoaService.excluir(pessoaDTO);

        // Assert
        verify(pessoaRepository, times(1)).delete(pessoa);
    }

    @Test
    void testBuscarPorId_WhenIdExists_ShouldReturnPessoaDTO() {
        // Arrange
        Pessoa pessoa = new Pessoa();
        PessoaDTO pessoaDTO = new PessoaDTO();
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(modelMapper.map(pessoa, PessoaDTO.class)).thenReturn(pessoaDTO);

        // Act
        Optional<PessoaDTO> result = pessoaService.buscarPorId(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(pessoaDTO, result.get());
    }

    @Test
    void testBuscarPorId_WhenIdDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<PessoaDTO> result = pessoaService.buscarPorId(1L);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testBuscarPorNome_ShouldReturnPessoaDTOList() {
        // Arrange
        Pessoa pessoa = new Pessoa();
        PessoaDTO pessoaDTO = new PessoaDTO();
        when(pessoaRepository.findByNomeContainingIgnoreCase("John")).thenReturn(Collections.singletonList(pessoa));
        when(modelMapper.map(pessoa, PessoaDTO.class)).thenReturn(pessoaDTO);

        // Act
        List<PessoaDTO> result = pessoaService.buscarPorNome("John");

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testBuscarPorNome_WhenNoResults_ShouldReturnEmptyList() {
        // Arrange
        when(pessoaRepository.findByNomeContainingIgnoreCase("John")).thenReturn(Collections.emptyList());

        // Act
        List<PessoaDTO> result = pessoaService.buscarPorNome("John");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
