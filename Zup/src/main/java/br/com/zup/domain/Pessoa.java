package br.com.zup.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pessoas")
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String email;

    @NotNull
    private String cpf;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
    @Column(name = "data_nascimento")
    private Date dataNascimento;

    @Override
    public int hashCode() {
        // Use only fields relevant to equality
        return Objects.hash(id, nome, email, cpf, dataNascimento != null ? dataNascimento.getTime() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pessoa that = (Pessoa) obj;
        return Objects.equals(id, that.id) &&
               Objects.equals(nome, that.nome) &&
               Objects.equals(email, that.email) &&
               Objects.equals(cpf, that.cpf) &&
               Objects.equals(dataNascimento, that.dataNascimento);
    }

    @Override
    public String toString() {
        return "Pessoa(id=" + id + ", nome=" + nome + ", email=" + email + ", cpf=" + cpf + ", dataNascimento=" + dataNascimento + ")";
    }
}
