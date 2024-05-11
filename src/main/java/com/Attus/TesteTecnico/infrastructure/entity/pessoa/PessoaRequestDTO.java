package com.Attus.TesteTecnico.infrastructure.entity.pessoa;

import java.time.LocalDate;

public record PessoaRequestDTO(String nomeCompleto, LocalDate dataNascimento) {
}
