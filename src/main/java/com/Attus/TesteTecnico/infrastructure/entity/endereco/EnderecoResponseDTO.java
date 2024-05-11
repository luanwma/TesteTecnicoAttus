package com.Attus.TesteTecnico.infrastructure.entity.endereco;

public record EnderecoResponseDTO(Integer enderecoId, String  CEP ,
                                  String logradouro ,
                                  String cidade ,
                                  String estado, Integer  numero ,

                                  String complemento, boolean principal

                                  ) {
}
