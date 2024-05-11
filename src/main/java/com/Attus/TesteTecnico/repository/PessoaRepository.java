package com.Attus.TesteTecnico.repository;

import com.Attus.TesteTecnico.infrastructure.entity.pessoa.Pessoa;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {


    @Modifying
    @Transactional
    @Query("UPDATE pessoa p SET p.nomeCompleto = :nomeCompleto , " +
            "p.dataNascimento = :dataNascimento WHERE p.pessoaId = :pessoaId")
    Integer editarPessoa(@Param("pessoaId") Integer pessoaId, @Param("nomeCompleto") String nomeCompleto,
                      @Param("dataNascimento") LocalDate dataNascimento);

    @Query("SELECT p FROM pessoa p where p.nomeCompleto like concat('%',:primeiroNome,'%')")
    List<Pessoa> listarPessoasPeloNome(@Param("primeiroNome") String primeiroNome);

    @Query("SELECT p FROM pessoa p where p.nomeCompleto  like concat('%',:primeiroNome,'%') " +
            "and p.nomeCompleto like concat('%',:segundoNome,'%') ")
    List<Pessoa> listarPessoasPorDoisNomes(@Param("primeiroNome") String primeiroNome,
                                               @Param("segundoNome") String segundoNome);

    @Query("SELECT p FROM pessoa p where p.nomeCompleto like concat('%',:primeiroNome,'%')" +
            "and p.nomeCompleto like concat('%',:segundoNome,'%') " +
            "and p.nomeCompleto like concat('%',:terceiroNome,'%') ")
    List<Pessoa> listarPessoasPorTresNomes(@Param("primeiroNome") String primeiroNome,
                                           @Param("segundoNome") String segundoNome,
                                            @Param("terceiroNome") String terceiroNome);
}
