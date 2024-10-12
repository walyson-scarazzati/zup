package br.com.zup.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import br.com.zup.dto.PessoaDTO;

public class PessoaDTOTest {

    @Test
    public void testEquals() {
        Date fixedDate = new Date(); // Create a fixed date instance
        PessoaDTO pessoa1 = new PessoaDTO(1L, "John Doe", "john.doe@example.com", "12345678901", fixedDate);
        PessoaDTO pessoa2 = new PessoaDTO(1L, "John Doe", "john.doe@example.com", "12345678901", fixedDate);
        assertEquals(pessoa1, pessoa2);
    }

    @Test
    public void testHashCode() {
        // Arrange
        Long id = 1L;
        String nome = "John Doe";
        String email = "john.doe@example.com";
        String cpf = "12345678901";
        Date fixedDate = new Date(); // Create a fixed date instance

        PessoaDTO pessoa = new PessoaDTO(id, nome, email, cpf, fixedDate);
        
        // Calculate expected hash code using the fixed date
        int expectedHashCode = Objects.hash(id, nome, email, cpf, fixedDate);
        
        // Act
        int actualHashCode = pessoa.hashCode();
        
        // Log the values for debugging
        System.out.println("Expected Hash Code: " + expectedHashCode);
        System.out.println("Actual Hash Code: " + actualHashCode);
        
        // Assert
        assertEquals(expectedHashCode, actualHashCode);
    }

    @Test
    public void testToString() {
        Date fixedDate = new Date(); // Create a fixed date instance
        PessoaDTO pessoa = new PessoaDTO(1L, "John Doe", "john.doe@example.com", "12345678901", fixedDate);
        String expectedString = "PessoaDTO(id=1, nome=John Doe, email=john.doe@example.com, cpf=12345678901, dataNascimento="
                + fixedDate + ")"; // Use the fixed date here
        assertEquals(expectedString, pessoa.toString());
    }
    
    @Test
    public void testBuilder() {
        // Arrange
        Long id = 1L;
        String nome = "John Doe";
        String email = "john.doe@example.com";
        String cpf = "12345678901";
        Date dataNascimento = new Date();

        // Act
        PessoaDTO pessoa = PessoaDTO.builder()
                .id(id)
                .nome(nome)
                .email(email)
                .cpf(cpf)
                .dataNascimento(dataNascimento)
                .build();

        // Assert
        assertNotNull(pessoa);
        assertEquals(id, pessoa.getId());
        assertEquals(nome, pessoa.getNome());
        assertEquals(email, pessoa.getEmail());
        assertEquals(cpf, pessoa.getCpf());
        assertEquals(dataNascimento, pessoa.getDataNascimento());
    }
}
