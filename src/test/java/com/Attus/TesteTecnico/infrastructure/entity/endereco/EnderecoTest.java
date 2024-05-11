package com.Attus.TesteTecnico.infrastructure.entity.endereco;

import com.Attus.TesteTecnico.infrastructure.entity.pessoa.Pessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class EnderecoTest {
    @Mock
    Pessoa pessoaMock;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEnderecoConstrutorDTOPrincipalFalse() {
        EnderecoRequestDTO enderecoRequestDTO = new EnderecoRequestDTO("CEP Teste",
                "Logradouro Teste", 123, "Cidade Teste",
                "Estado Teste", "Complemento Teste");
        Endereco endereco = new Endereco(enderecoRequestDTO, pessoaMock);

        assertEquals("CEP Teste", endereco.getCEP());
        assertEquals("Logradouro Teste", endereco.getLogradouro());
        assertEquals(123, endereco.getNumero());
        assertEquals("Cidade Teste", endereco.getCidade());
        assertEquals("Estado Teste", endereco.getEstado());
        assertEquals("Complemento Teste", endereco.getComplemento());
        assertFalse(endereco.isPrincipal());
        assertEquals(pessoaMock, endereco.getPessoa());
    }

    @Test
    void testEnderecoConstrutorDTOPrincipalTrue(){
        EnderecoRequestDTO enderecoRequestDTO = new EnderecoRequestDTO("CEP Teste",
                "Logradouro Teste", 123, "Cidade Teste",
                "Estado Teste", "Complemento Teste");
        Endereco endereco = new Endereco(enderecoRequestDTO, pessoaMock);

        assertEquals("CEP Teste", endereco.getCEP());
        assertEquals("Logradouro Teste", endereco.getLogradouro());
        assertEquals(123, endereco.getNumero());
        assertEquals("Cidade Teste", endereco.getCidade());
        assertEquals("Estado Teste", endereco.getEstado());
        assertEquals("Complemento Teste", endereco.getComplemento());
        assertTrue(endereco.isPrincipal());
        assertEquals(pessoaMock, endereco.getPessoa());
    }



}
