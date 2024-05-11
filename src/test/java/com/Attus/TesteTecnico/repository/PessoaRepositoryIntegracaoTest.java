package com.Attus.TesteTecnico.repository;

import com.Attus.TesteTecnico.infrastructure.entity.pessoa.Pessoa;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(properties = {"spring.config.name=application-test"})
public class PessoaRepositoryIntegracaoTest {


    @Autowired
    private PessoaRepository pessoaRepository;

    @Test
    public void testInsercaoBD(){
        Pessoa p = new Pessoa();
        String nomeTeste = "Jose Silva";

        p.setNomeCompleto(nomeTeste);
        p.setDataNascimento(LocalDate.of(1990,05,13));

        pessoaRepository.save(p);

        List<Pessoa> results = pessoaRepository.listarPessoasPeloNome(p.getNomeCompleto());
        assertEquals(false, results.isEmpty());
        assertEquals(nomeTeste, results.get(0).getNomeCompleto());
        assertEquals("1990-05-13", results.get(0).getDataNascimento().toString());

    }

    @Test
    public void testSelectListaPessoasPorNome(){
        // CASO DE TESTE PARA ENCONTRAR 2 NOMES NO BANCO DE DADOS
        String nomeTeste = "Luan William";
        List<Pessoa> resuts = pessoaRepository.listarPessoasPeloNome(nomeTeste);
        assertEquals(2, resuts.size());
        assertEquals("Luan William", resuts.get(0).getNomeCompleto());
        assertEquals("Luan William Marques", resuts.get(1).getNomeCompleto());
    }

    @Test
    public void testSelectListaPessoasPorNomeUmSobrenome(){
        // CASO DE TESTE PARA ENCONTRAR 2 NOMES NO BANCO DE DADOS COM FUNÇÃO LIKE
        String nome= "Luan";
        String sobrenome = "William";
        List<Pessoa> resuts = pessoaRepository.listarPessoasPorDoisNomes(nome, sobrenome);
        assertEquals(2, resuts.size());
        assertEquals("Luan William", resuts.get(0).getNomeCompleto());
        assertEquals("Luan William Marques", resuts.get(1).getNomeCompleto());
    }
    @Test
    public void testSelectListaPessoasPorNomeDoisSobrenomes(){
        // CASO DE TESTE PARA ENCONTRAR APENAS 1 NOME NO BANCO DE DADOS COM FUNÇÃO LIKE DADO QUE O USUARIO
        // ESTA BUSCANDO ALGUÉM COM ESSES NOMES E SOBRENOMES
        String nome= "Luan";
        String sobrenome = "William";
        String sobrenome2 = "Marques";
        List<Pessoa> resuts = pessoaRepository.listarPessoasPorTresNomes(nome, sobrenome, sobrenome2);
        assertEquals(1, resuts.size());
        assertEquals("Luan William Marques", resuts.get(0).getNomeCompleto());

    }

    @Test
    public void findById(){
        int id = 11;
        Optional<Pessoa> op = pessoaRepository.findById(id);
        Pessoa p = new Pessoa(op.get().getPessoaId(), op.get().getNomeCompleto(),
                op.get().getDataNascimento(), op.get().getEnderecos());
        assertEquals(true,op.isPresent() );
        assertEquals("Luan William", p.getNomeCompleto());
    }


}
