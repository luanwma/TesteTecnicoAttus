package com.Attus.TesteTecnico.controller;

import com.Attus.TesteTecnico.infrastructure.entity.endereco.Endereco;
import com.Attus.TesteTecnico.infrastructure.entity.endereco.EnderecoRequestDTO;
import com.Attus.TesteTecnico.infrastructure.entity.endereco.EnderecoResponseDTO;
import com.Attus.TesteTecnico.infrastructure.entity.pessoa.Pessoa;
import com.Attus.TesteTecnico.repository.EnderecoRepository;
import com.Attus.TesteTecnico.repository.PessoaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/endereco")
public class EnderecoController {

    private EnderecoRepository enderecoRepository;

    private PessoaRepository pessoaRepository;


    @GetMapping("/todosenderecos")
    public ResponseEntity<List<EnderecoResponseDTO>> todosEnderecos() {
        List<Endereco> listEnd = new ArrayList<>();
        listEnd = enderecoRepository.findAll();
        List<EnderecoResponseDTO> endsDTO = new ArrayList<>();
        for (Endereco end : listEnd) {
            EnderecoResponseDTO endDto = new EnderecoResponseDTO(end.getEnderecoId(), end.getCEP(),
                    end.getLogradouro(),
                    end.getCidade(),
                    end.getEstado(), end.getNumero(),
                    end.getComplemento(), end.isPrincipal()
            );
            endsDTO.add(endDto);
        }
        return ResponseEntity.ok(endsDTO);
    }

    @PostMapping("/salvar")
    @Transactional
    public ResponseEntity<EnderecoResponseDTO> salvar(@RequestBody EnderecoRequestDTO enderecoRequestDTO,
                                                      @RequestHeader("pessoaId") Integer pessoaId) {

        if ( enderecoRequestDTO.CEP().isBlank() ||  enderecoRequestDTO.logradouro().isBlank()
                ||  enderecoRequestDTO.cidade().isBlank() ||
                enderecoRequestDTO.estado().isBlank() || pessoaId == 0) {

            return ResponseEntity.badRequest().build();
        } else {

            Optional<Pessoa> oP = pessoaRepository.findById(pessoaId);
            Pessoa pessoa = new Pessoa(oP.get().getPessoaId(), oP.get().getNomeCompleto(),
                    oP.get().getDataNascimento(), oP.get().getEnderecos());
            Endereco end = new Endereco(enderecoRequestDTO, pessoa);

            Endereco enderecoSalvo = enderecoRepository.save(end);

            EnderecoResponseDTO endRes = new EnderecoResponseDTO(enderecoSalvo.getEnderecoId(),
                    enderecoSalvo.getCEP(), enderecoSalvo.getLogradouro(), enderecoSalvo.getCidade(),
                    enderecoSalvo.getEstado(), enderecoSalvo.getNumero(),
                    enderecoSalvo.getComplemento(), enderecoSalvo.isPrincipal()/*, enderecoSalvo.getPessoa().getPessoaId() */);

            return ResponseEntity.ok(endRes);

        }
    }

    @PutMapping("/editar/{enderecoId}")
    @Transactional
    public ResponseEntity<EnderecoResponseDTO> editar(@RequestBody EnderecoRequestDTO enderecoRequestDTO,
                                                      @PathVariable Integer enderecoId,
                                                      @RequestHeader("pessoaId") Integer pessoaId) {

        Optional opEnd = enderecoRepository.encontrarPorPessoaAndEndereco(pessoaId, enderecoId);
        if (opEnd.isPresent()) {
            Endereco endAlterado = enderecoRepository.editarEndereco(enderecoId,pessoaId,enderecoRequestDTO.CEP(),
                    enderecoRequestDTO.logradouro(), enderecoRequestDTO.numero(),
                    enderecoRequestDTO.cidade(), enderecoRequestDTO.estado(), enderecoRequestDTO.complemento());
            EnderecoResponseDTO endDTO = new EnderecoResponseDTO(enderecoId,endAlterado.getCEP(), endAlterado.getLogradouro(),
                    endAlterado.getCidade(), endAlterado.getEstado(), endAlterado.getNumero(), endAlterado.getComplemento(), endAlterado.isPrincipal() );

            return ResponseEntity.ok(endDTO);

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/buscarenderecos/{pessoaId}")
    public ResponseEntity<List<EnderecoResponseDTO>> buscarEnderecosPessoa(@PathVariable Integer pessoaId) {

        List<Endereco> enderecosBD = new ArrayList<>();
        enderecosBD = enderecoRepository.listarEnderecosPessoa(pessoaId);
        if (enderecosBD.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<EnderecoResponseDTO> listResp = new ArrayList<>();
        Optional<Pessoa> optional = pessoaRepository.findById(pessoaId);


        for (Endereco end : enderecosBD) {
            EnderecoResponseDTO endDTO = new EnderecoResponseDTO(end.getEnderecoId(), end.getCEP(),
                    end.getLogradouro(), end.getCidade(), end.getEstado(), end.getNumero(),
                    end.getComplemento(), end.isPrincipal());


            listResp.add(endDTO);
        }
        return ResponseEntity.ok(listResp);

    }

    @GetMapping("/buscarenderecoprincipal")
    public ResponseEntity<EnderecoResponseDTO> buscarEnderecoPrincipal(@RequestHeader("pessoaId") Integer pessoaId){
        Endereco end = enderecoRepository.buscarEnderecoPrincipal(pessoaId);
        EnderecoResponseDTO endDTO = new EnderecoResponseDTO(end.getEnderecoId() ,
                end.getCEP(), end.getLogradouro(), end.getCidade(), end.getEstado(), end.getNumero(),
                end.getComplemento(), end.isPrincipal());
        return ResponseEntity.ok(endDTO);
    }

    @PutMapping("/setenderecoprincipal/{enderecoId}")
    public ResponseEntity definirEnderecoPrincipal(@PathVariable Integer enderecoId ,
                                                   @RequestHeader("pessoaId") Integer pessoaId){

        try{
            enderecoRepository.configEnderecoPrincipal(enderecoId, pessoaId);
            return ResponseEntity.ok("Endereço principal definido com sucesso");
        }catch (RuntimeException ex ){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar registros: " + ex.getMessage());

        }


    }





}