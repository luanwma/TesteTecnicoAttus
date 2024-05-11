package com.Attus.TesteTecnico.controller;

import com.Attus.TesteTecnico.infrastructure.entity.pessoa.NomeCompletoRequestDTO;
import com.Attus.TesteTecnico.infrastructure.entity.pessoa.Pessoa;
import com.Attus.TesteTecnico.infrastructure.entity.pessoa.PessoaRequestDTO;
import com.Attus.TesteTecnico.infrastructure.entity.pessoa.PessoaResponseDTO;
import com.Attus.TesteTecnico.repository.PessoaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/pessoa")
public class PessoaController {

  //  @Autowired
    private PessoaRepository pessoaRepository;


    @GetMapping("/todaspessoas")
    public ResponseEntity<List<PessoaResponseDTO>> todasPessoas(){
        List<Pessoa> list ;
        list = pessoaRepository.findAll();
        List<PessoaResponseDTO> pessoasDTO = new ArrayList<>();

        for(Pessoa p : list){
            PessoaResponseDTO pdto = new PessoaResponseDTO(p.getPessoaId(),
                    p.getNomeCompleto(),
                    p.getDataNascimento() /*, p.getEnderecos()*/);
            pessoasDTO.add(pdto);
        }
        return ResponseEntity.ok(pessoasDTO);
    }

    @PostMapping("/salvar")
    public ResponseEntity<Pessoa> salvar(@RequestBody PessoaRequestDTO pessoaDTO){
        if(pessoaDTO.nomeCompleto().isBlank() || pessoaDTO.nomeCompleto().isEmpty()){
            return ResponseEntity.badRequest().build();
        }else{
            Pessoa pessoa = new Pessoa(pessoaDTO);
            return ResponseEntity.ok(pessoaRepository.save(pessoa));
        }

    }

    @PostMapping("/buscarpessoas")
    public ResponseEntity<List<PessoaResponseDTO>> pessoasPorPrimeiroNome(@RequestBody NomeCompletoRequestDTO nomeDigitado){

        List<Pessoa> lista = new ArrayList<>();
        if(nomeDigitado.nomeCompleto().isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        String nome = nomeDigitado.nomeCompleto();

        System.out.println("nome -> "+nome);


        String []partes = nome.split(" ");
        if(partes.length == 1){
            System.out.println("parte 0 -> "+partes[0]);
            lista = pessoaRepository.listarPessoasPeloNome(partes[0]);
        }
        if(partes.length  == 2){
            lista = pessoaRepository.listarPessoasPorDoisNomes(partes[0], partes[1]);
        }
        if(partes.length  == 3){
            lista = pessoaRepository.listarPessoasPorTresNomes(partes[0], partes[1], partes[2]);
        }

        if(lista.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<PessoaResponseDTO> listResp = new ArrayList<>();

        for(Pessoa p : lista){
            PessoaResponseDTO pDto = new PessoaResponseDTO(p.getPessoaId() ,p.getNomeCompleto(), p.getDataNascimento());
            listResp.add(pDto);
        }

        return ResponseEntity.ok(listResp);

    }

    @PutMapping("/editar/{pessoaId}")
    @Transactional
    public ResponseEntity editar(@RequestBody PessoaRequestDTO pessoaRequestDTO ,
                                         @PathVariable Integer pessoaId){

        Optional<Pessoa> oP = pessoaRepository.findById(pessoaId);
        if(oP.isPresent()) {
            String nomeCompleto = pessoaRequestDTO.nomeCompleto();
            LocalDate dataNascimento = pessoaRequestDTO.dataNascimento();
            try {
                int x = pessoaRepository.editarPessoa(pessoaId, nomeCompleto, dataNascimento);

                if (x == 1) {
                    return ResponseEntity.ok("Editado com sucesso");
                }else{
                    throw new RuntimeException("Erro: mais de um registro foi alterado");
                }
            } catch (RuntimeException ex) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao alterar pessoa: " + ex.getMessage());
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/delete/{pessoaId}")
    public ResponseEntity deletar(@PathVariable Integer pessoaIid){
        Optional<Pessoa> pessoa = pessoaRepository.findById(pessoaIid);
        if(pessoa.isPresent()){
            pessoaRepository.deleteById(pessoaIid);
            return ResponseEntity.ok("Excluido com sucesso");
        }else{
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }


}
