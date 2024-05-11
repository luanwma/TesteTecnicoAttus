package com.Attus.TesteTecnico.infrastructure.entity.endereco;

import com.Attus.TesteTecnico.infrastructure.entity.pessoa.Pessoa;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "endereco")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer enderecoId;

    @Column
    private String CEP;
    @Column
    private String logradouro;
    @Column
    private Integer numero;
    @Column
    private String complemento;
    @Column
    private String cidade;
    @Column
    private String estado;

    @Column
    private boolean principal;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    public Pessoa getPessoa() {
        return pessoa;
    }



    public Endereco(EnderecoRequestDTO enderecoRequestDTO, Pessoa p){
        this.CEP = enderecoRequestDTO.CEP();
        this.logradouro = enderecoRequestDTO.logradouro();
        this.cidade = enderecoRequestDTO.cidade();
        this.estado = enderecoRequestDTO.estado();
        this.numero = enderecoRequestDTO.numero();

        this.complemento =enderecoRequestDTO.complemento();
        this.pessoa = p;
    }
}
