package com.Attus.TesteTecnico.infrastructure.entity.pessoa;

import java.time.LocalDate;

public record PessoaResponseDTO(Integer pessoaId, String nomeCompleto, LocalDate dataNascimento /*,
                                List<Endereco> listEnderecos */) {
}
