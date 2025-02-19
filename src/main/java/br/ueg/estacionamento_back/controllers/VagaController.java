package br.ueg.estacionamento_back.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ueg.estacionamento_back.mappers.VagaMapper;
import br.ueg.estacionamento_back.models.VagaModel;
import br.ueg.estacionamento_back.models.dtos.VagaDTO;
import br.ueg.estacionamento_back.services.VagaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/vagas")
public class VagaController {
    
    @Autowired
    private VagaService vagaService;

    @Autowired
    private VagaMapper vagaMapper;

    @PostMapping
    @Operation(description = "Endpoint para adicionar uma vaga")
    public ResponseEntity<Object> create(@Valid @RequestBody VagaDTO vagaDTO) {
        try {
            VagaModel vaga = vagaService.create(vagaMapper.toVagaModel(vagaDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(vaga);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    @Operation(description = "Endpoint para listar todas as vagas")
    public ResponseEntity<Object> getAll() {
        try {
            List<VagaModel> vagas = vagaService.getAll();
            if (vagas == null || vagas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
            }
            return ResponseEntity.ok(vagas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(description = "Endpoint para exibir os dados de uma vaga pelo ID")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            Optional<VagaModel> vaga = vagaService.getById(id);
            if (vaga.isPresent()) {
                return ResponseEntity.ok(vaga);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(description = "Endpoint para atualizar os dados de uma vaga pelo ID")
    public ResponseEntity<Object> updateById(@PathVariable Long id, @Valid @RequestBody VagaDTO vagaDTO) {
        try {
            Optional<VagaModel> vaga = vagaService.updateById(id, vagaMapper.toVagaModel(vagaDTO));
            if (vaga.isPresent()) {
                return ResponseEntity.ok(vaga);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Endpoint para deletar uma vaga pelo ID")
    public ResponseEntity<Object> deleteById(@PathVariable("id") Long id) {
        try {
            Optional<VagaModel> vaga = vagaService.deleteById(id);
            if (vaga.isPresent()) {
                return ResponseEntity.ok(vaga);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}

