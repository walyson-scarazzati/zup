package br.com.zup.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import br.com.zup.domain.Pessoa;

public class PessoaTest {

    @Test
    public void testEquals() {
        Date fixedDate = new Date(0); // Data fixa para fins de teste
        Pessoa pessoa1 = new Pessoa(1L, "John Doe", "john.doe@example.com", "12345678901", fixedDate);
        Pessoa pessoa2 = new Pessoa(1L, "John Doe", "john.doe@example.com", "12345678901", fixedDate);
        assertEquals(pessoa1, pessoa2);
    }

    @Test
    public void testEqualsDifferentTypes() {
        Pessoa pessoa = new Pessoa(1L, "John Doe", "john.doe@example.com", "12345678901", new Date(0));
        assertFalse(pessoa.equals("Not a Pessoa object"));
    }

    @Test
    public void testEqualsNull() {
        Pessoa pessoa = new Pessoa(1L, "John Doe", "john.doe@example.com", "12345678901", new Date(0));
        assertFalse(pessoa.equals(null));
    }

    @Test
    public void testHashCode() {
        Date fixedDate = new Date(0); // Data fixa para fins de teste
        Pessoa pessoa = new Pessoa(1L, "John Doe", "john.doe@example.com", "12345678901", fixedDate);
        
        int expectedHashCode = Objects.hash(pessoa.getId(), pessoa.getNome(), pessoa.getEmail(), 
                                             pessoa.getCpf(), fixedDate.getTime());
                                             
        assertEquals(expectedHashCode, pessoa.hashCode());
    }

    @Test
    public void testHashCodeCollision() {
        Pessoa pessoa1 = new Pessoa(1L, "John Doe", "john.doe@example.com", "12345678901", new Date(0));
        Pessoa pessoa2 = new Pessoa(2L, "Jane Doe", "jane.doe@example.com", "12345678902", new Date(1));
        
        assertNotEquals(pessoa1.hashCode(), pessoa2.hashCode());
    }

    @Test
    public void testToString() {
        Date fixedDate = new Date(0); // Data fixa para fins de teste
        Pessoa pessoa = new Pessoa(1L, "John Doe", "john.doe@example.com", "12345678901", fixedDate);
        
        String expectedString = "Pessoa(id=1, nome=John Doe, email=john.doe@example.com, cpf=12345678901, dataNascimento=" + fixedDate + ")";
        
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
        Pessoa pessoa = Pessoa.builder()
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

    @Test
    public void testSetId() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        assertEquals(Long.valueOf(1L), pessoa.getId());
    }

    @Test
    public void testSetNome() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Jane Doe");
        assertEquals("Jane Doe", pessoa.getNome());
    }

    @Test
    public void testSetEmail() {
        Pessoa pessoa = new Pessoa();
        pessoa.setEmail("jane.doe@example.com");
        assertEquals("jane.doe@example.com", pessoa.getEmail());
    }

    @Test
    public void testSetCpf() {
        Pessoa pessoa = new Pessoa();
        pessoa.setCpf("12345678902");
        assertEquals("12345678902", pessoa.getCpf());
    }

    @Test
    public void testSetDataNascimento() {
        Date dataNascimento = new Date();
        Pessoa pessoa = new Pessoa();
        pessoa.setDataNascimento(dataNascimento);
        assertEquals(dataNascimento, pessoa.getDataNascimento());
    }
}
