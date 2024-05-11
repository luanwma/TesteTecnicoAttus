package com.Attus.TesteTecnico.infrastructure.entity.endereco;

public record EnderecoRequestDTO(String CEP , String logradouro, Integer numero,
                                 String cidade, String estado , String complemento ) {


}
