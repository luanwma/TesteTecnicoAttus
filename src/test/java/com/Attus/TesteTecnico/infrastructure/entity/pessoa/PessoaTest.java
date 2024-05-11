package com.Attus.TesteTecnico.infrastructure.entity.pessoa;

import com.Attus.TesteTecnico.infrastructure.entity.endereco.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PessoaTest {
    @Mock
    Endereco enderecoMock;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddEndereco() {
        Pessoa pessoa = new Pessoa();
        List<Endereco> enderecos = new ArrayList<>();
        pessoa.setEnderecos(enderecos);

        pessoa.addEndereco(enderecoMock);
        assertTrue(enderecos.contains(enderecoMock));
    }

    @Test
    void testRemoveEndereco() {
        Pessoa pessoa = new Pessoa();
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(enderecoMock);
        pessoa.setEnderecos(enderecos);

        pessoa.removeEndereco(enderecoMock);
        assertFalse(enderecos.contains(enderecoMock));
    }

    @Test
    void testPessoaConstrutorDTO() {
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO("Nome", LocalDate.now());
        Pessoa pessoa = new Pessoa(pessoaRequestDTO);

        assertEquals("Nome", pessoa.getNomeCompleto());
        assertEquals(LocalDate.now(), pessoa.getDataNascimento());
    }

    @Test
    void testPessoaConstrutorResponseDTO() {
        PessoaResponseDTO pessoaResponseDTO = new PessoaResponseDTO(1, "Nome", LocalDate.now());
        Pessoa pessoa = new Pessoa(pessoaResponseDTO);

        assertEquals(1, pessoa.getPessoaId());
        assertEquals("Nome", pessoa.getNomeCompleto());
        assertEquals(LocalDate.now(), pessoa.getDataNascimento());
    }


}
