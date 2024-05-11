package com.Attus.TesteTecnico.repository;

import com.Attus.TesteTecnico.infrastructure.entity.endereco.Endereco;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE endereco end SET end.CEP = :CEP , end.logradouro = :logradouro ," +
            "end.numero = :numero , end.cidade = :cidade , end.estado = :estado , " +
            "end.complemento = :complemento  " +
            "WHERE end.enderecoId = :enderecoId AND end.pessoa.pessoaId = :pessoaId" )
    Endereco editarEndereco(@Param("enderecoId") Integer enderecoId, @Param("pessoaId") Integer pessoaId,
                            @Param("CEP") String CEP, @Param("logradouro") String logradouro,
                            @Param("numero") Integer numero, @Param("cidade") String cidade,
                            @Param("estado") String estado, @Param("complemento") String complemento
                            );

    @Modifying
    @Transactional
    @Query("UPDATE endereco end SET end.principal = (CASE WHEN end.enderecoId = :enderecoId THEN true ELSE false end)" +
            " WHERE end.pessoa.pessoaId = :pessoaId")
    void configEnderecoPrincipal(@Param("enderecoId") Integer enderecoId ,
                                    @Param("pessoaId") Integer pessoaId);

    @Query("SELECT end FROM endereco end where end.pessoa.pessoaId = :pessoaId and end.principal = true")
    Endereco buscarEnderecoPrincipal(@Param("pessoaId") Integer pessoaId);



    @Query("SELECT end FROM endereco end where end.pessoa.pessoaId = :pessoaId and end.enderecoId = :enderecoId")
    Optional encontrarPorPessoaAndEndereco(@Param("pessoaId") Integer pessoaId,
                                           @Param("enderecoId") Integer enderecoId);

    @Query("SELECT end FROM endereco end where end.pessoa.pessoaId = :pessoaId")
    List<Endereco> listarEnderecosPessoa(@Param("pessoaId") Integer pessoaId);


}
