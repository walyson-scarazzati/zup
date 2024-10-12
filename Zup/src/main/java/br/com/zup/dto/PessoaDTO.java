package br.com.zup.dto;

import java.util.Date;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PessoaDTO {

    @NotNull
    private Long id;

    @NotEmpty
    private String nome;

    @NotEmpty
    private String email;

    @NotEmpty
    private String cpf;

    @NotNull
    private Date dataNascimento;

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, email, cpf, dataNascimento);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PessoaDTO that = (PessoaDTO) obj;
        return Objects.equals(id, that.id) &&
               Objects.equals(nome, that.nome) &&
               Objects.equals(email, that.email) &&
               Objects.equals(cpf, that.cpf) &&
               Objects.equals(dataNascimento, that.dataNascimento);
    }
}
