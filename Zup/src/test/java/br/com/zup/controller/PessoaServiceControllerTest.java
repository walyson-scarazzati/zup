package br.com.zup.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zup.dto.PessoaDTO;
import br.com.zup.service.IPessoaService;

@WebMvcTest(PessoaController.class)
public class PessoaServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPessoaService pessoaService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private PessoaDTO pessoaDTO;

    @BeforeEach
    void setUp() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        pessoaDTO = new PessoaDTO(1L, "John Doe", "john.doe@example.com", "12345678901", sdf.parse("1990-01-01"));
    }

    @Test
    void testListarPessoas() throws Exception {
        when(pessoaService.listar()).thenReturn(Collections.singletonList(pessoaDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/pessoas/listar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(pessoaDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value(pessoaDTO.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(pessoaDTO.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cpf").value(pessoaDTO.getCpf()));
    }

    @Test
    void testSalvarPessoa() throws Exception {
        when(pessoaService.salvar(any(PessoaDTO.class))).thenReturn(pessoaDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/pessoas/salvar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(pessoaDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(pessoaDTO.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(pessoaDTO.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(pessoaDTO.getCpf()));
    }

    @Test
    void testEditarPessoa() throws Exception {
        PessoaDTO updatedPessoaDTO = new PessoaDTO(1L, "Jane Doe", "jane.doe@example.com", "12345678901", new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01"));

        when(pessoaService.buscarPorId(anyLong())).thenReturn(Optional.of(pessoaDTO));
        when(pessoaService.editar(any())).thenReturn(updatedPessoaDTO);
        when(modelMapper.map(any(), any())).thenReturn(updatedPessoaDTO);

        mockMvc.perform(put("/pessoas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPessoaDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Jane Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("jane.doe@example.com"));
    }

    @Test
    void testExcluirPessoa() throws Exception {
        when(pessoaService.buscarPorId(anyLong())).thenReturn(Optional.of(pessoaDTO));

        mockMvc.perform(delete("/pessoas/{id}", pessoaDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testBuscarPorId() throws Exception {
        when(pessoaService.buscarPorId(anyLong())).thenReturn(Optional.of(pessoaDTO));
        when(modelMapper.map(any(), any())).thenReturn(pessoaDTO);

        mockMvc.perform(get("/pessoas/{id}", pessoaDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(pessoaDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(pessoaDTO.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(pessoaDTO.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(pessoaDTO.getCpf()));
    }

    @Test
    void testBuscarPorId_NotFound() throws Exception {
        when(pessoaService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/pessoas/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testBuscarPorNome() throws Exception {
        String nome = "John Doe";
        PessoaDTO pessoaDTO = new PessoaDTO(1L, "John Doe", "john.doe@example.com", "12345678901", new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01"));

        when(pessoaService.buscarPorNome(nome)).thenReturn(List.of(pessoaDTO));

        mockMvc.perform(get("/pessoas/nome")
                        .param("nome", nome)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("john.doe@example.com"));
    }

    @Test
    void testBuscarPorNome_NoResults() throws Exception {
        String nome = "Nonexistent Name";

        when(pessoaService.buscarPorNome(nome)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/pessoas/nome")
                        .param("nome", nome)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    void testSalvarPessoa_ValidationFailed() throws Exception {
        PessoaDTO invalidPessoaDTO = new PessoaDTO(); // Create an invalid DTO

        mockMvc.perform(MockMvcRequestBuilders.post("/pessoas/salvar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPessoaDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testEditarPessoa_NotFound() throws Exception {
        PessoaDTO updatedPessoaDTO = new PessoaDTO(1L, "Jane Doe", "jane.doe@example.com", "12345678901", new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01"));

        when(pessoaService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(put("/pessoas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPessoaDTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testExcluirPessoa_NotFound() throws Exception {
        when(pessoaService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/pessoas/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testListarPessoas_WithNoResults_ShouldReturnEmptyList() throws Exception {
        // Arrange
        when(pessoaService.listar()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/pessoas/listar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    void testSalvarPessoa_InvalidDTO_ShouldReturnBadRequest() throws Exception {
        // Arrange
        PessoaDTO invalidPessoaDTO = new PessoaDTO(); // DTO inválido, sem campos obrigatórios

        mockMvc.perform(MockMvcRequestBuilders.post("/pessoas/salvar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPessoaDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testEditarPessoa_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // Arrange
        PessoaDTO invalidPessoaDTO = new PessoaDTO(); // DTO inválido, sem campos obrigatórios
        when(pessoaService.buscarPorId(anyLong())).thenReturn(Optional.of(invalidPessoaDTO));

        mockMvc.perform(put("/pessoas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPessoaDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testExcluirPessoa_WithExistingId_ShouldReturnOk() throws Exception {
        when(pessoaService.buscarPorId(anyLong())).thenReturn(Optional.of(pessoaDTO));

        mockMvc.perform(delete("/pessoas/{id}", pessoaDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testBuscarPorId_WithNonExistingId_ShouldReturnNotFound() throws Exception {
        when(pessoaService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/pessoas/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
