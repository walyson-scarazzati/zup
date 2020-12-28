package br.com.zup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zup.domain.Pessoa;

@Repository
public interface IPessoaRepository extends JpaRepository<Pessoa, Long>{
	
	List<Pessoa> findByNomeContainingIgnoreCase(String nome);
	
	boolean existsByCpf(String isbn);
	
	boolean existsByEmail(String isbn);
}
