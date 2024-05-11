package com.Attus.TesteTecnico.infrastructure.entity.pessoa;

import com.Attus.TesteTecnico.infrastructure.entity.endereco.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "pessoa")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pessoa {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pessoa_id")
    private Integer pessoaId;

    @Column(name = "nome_completo")
    private String nomeCompleto;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;




    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Endereco> enderecos = new ArrayList<>();

    public void addEndereco(Endereco endereco){
        if(enderecos.isEmpty()){
            endereco.setPrincipal(true);
        }else{
            endereco.setPrincipal(false);
        }
        enderecos.add(endereco);
    }

    public void removeEndereco(Endereco endereco){
        enderecos.remove(endereco);

    }

    public Pessoa(PessoaRequestDTO pessoaDTO){
        if(!pessoaDTO.nomeCompleto().isEmpty() || !pessoaDTO.nomeCompleto().isBlank()){
            this.nomeCompleto = pessoaDTO.nomeCompleto();
            this.dataNascimento = pessoaDTO.dataNascimento();
        }
    }

    public Pessoa(PessoaResponseDTO pessoaResDTO){
        this.pessoaId = pessoaResDTO.pessoaId();
        this.nomeCompleto = pessoaResDTO.nomeCompleto();
        this.dataNascimento = pessoaResDTO.dataNascimento();
       /* this.enderecos = pessoaResDTO.listEnderecos(); */
    }

    public Pessoa(Integer pessoaId, String nomeCompleto, LocalDate dataNascimento) {
        this.pessoaId = pessoaId;
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
    }
}
