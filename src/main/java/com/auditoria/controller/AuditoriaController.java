package com.auditoria.controller;

import com.auditoria.model.Auditoria;
import com.auditoria.model.NaoConformidade;
import com.auditoria.model.Pergunta;
import com.auditoria.service.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuditoriaController {
    
    private final AuditoriaService auditoriaService;
    
    @GetMapping("/perguntas")
    public ResponseEntity<List<Pergunta>> obterPerguntas() {
        return ResponseEntity.ok(auditoriaService.obterPerguntas());
    }
    
    @PostMapping("/auditorias")
    public ResponseEntity<Auditoria> criarAuditoria(@RequestBody Auditoria auditoria) {
        Auditoria saved = auditoriaService.salvarAuditoria(auditoria);
        return ResponseEntity.ok(saved);
    }
    
    @GetMapping("/auditorias")
    public ResponseEntity<List<Auditoria>> listarAuditorias() {
        return ResponseEntity.ok(auditoriaService.listarAuditorias());
    }
    
    @GetMapping("/auditorias/{id}")
    public ResponseEntity<Auditoria> buscarAuditoria(@PathVariable Long id) {
        return ResponseEntity.ok(auditoriaService.buscarPorId(id));
    }
    
    @PutMapping("/nao-conformidades/{id}/resolver")
    public ResponseEntity<NaoConformidade> resolverNC(@PathVariable Long id) {
        return ResponseEntity.ok(auditoriaService.resolverNC(id));
    }
    
    @GetMapping("/nao-conformidades/abertas")
    public ResponseEntity<List<NaoConformidade>> listarNCsAbertas() {
        return ResponseEntity.ok(auditoriaService.listarNCsAbertas());
    }
}